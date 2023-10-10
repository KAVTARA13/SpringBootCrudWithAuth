package com.example.crud.controllers;

import com.example.crud.entities.Product;
import com.example.crud.services.impl.ProductService;
import com.example.crud.utils.ProductExcelExporter;
import com.example.crud.utils.ProductPDFExporter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService =  productService;
    }
    @RequestMapping("/")
    public String viewHomePage(Model model, Authentication authentication, HttpServletRequest request){
        Locale currentLocale = request.getLocale();
        String countryCode = currentLocale.getCountry();
        String countryName = currentLocale.getDisplayCountry();

        String langCode = currentLocale.getLanguage();
        String langName = currentLocale.getDisplayLanguage();

        System.out.println(countryCode + " " + countryName);
        System.out.println(langCode + " " + langName);

        return viewHomePageByNumber(model,authentication,1,"id","asc",null,"");
    }
    @RequestMapping("/page/{pageNumber}")
    public String viewHomePageByNumber(Model model, Authentication authentication,
                                       @PathVariable("pageNumber")int currentPage,
                                       @Param("sortField") String sortField,
                                       @Param("sortDir")String sortDir,
                                       @Param("keyword")String keyword,
                                       @Param("lang") String lang){
        Page<Product> page = productService.listAll(currentPage,sortField,sortDir,keyword);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);

        String reversSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reversSortDir", reversSortDir);
        List<Product> productList = page.getContent();
        model.addAttribute("listProducts",productList);

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("user", userDetails);
        }
        return "index";
    }

    @RequestMapping("/new")
    public String showNewProductForm(Model model){
        Product product = new Product();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user",userDetails);
        model.addAttribute("product",product);
        return "new_product";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product) throws IOException {
         productService.save(product);

        return "redirect:/";
    }
    @RequestMapping(value = "/edit_save",method = RequestMethod.POST)
    public String saveEditProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        return "redirect:/";
    }
    @RequestMapping("/edit/{id}")
    public ModelAndView showNewProductForm(@PathVariable("id")Long id,Model model) throws ChangeSetPersister.NotFoundException {
        ModelAndView mav = new ModelAndView("edit_product");
        Optional<Product> productOptional = productService.get(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user",userDetails);

        Product product = productOptional.orElseThrow(ChangeSetPersister.NotFoundException::new);
        model.addAttribute("product", product);

        mav.addObject("product",product);
        return mav;
    }
    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id")Long id){
        productService.delete(id);
        return "redirect:/";
    }

    @RequestMapping("/product/exportCSV")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName="product"+currentDateTime+".csv";

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename="+fileName;

        response.setHeader(headerKey,headerValue);

        List<Product> productList = productService.listAll();

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Product Id", "Product Name","Product Brand","Product Made In", "Product Price"};
        String[] nameMapping = {"id","name","brand","madeIn","price"};

        csvBeanWriter.writeHeader(csvHeader);
        for (Product product:productList){
            csvBeanWriter.write(product,nameMapping);
        }
        csvBeanWriter.close();
    }

    @GetMapping("/product/exportExcel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        response.setContentType("application/octet-stream");
        String headerKey="Content-Disposition";
        String headerValue = "attachment; filename=product"+currentDateTime+".xlsx";

        response.setHeader(headerKey,headerValue);

        List<Product> productList = productService.listAll();
        ProductExcelExporter excelExporter = new ProductExcelExporter(productList);
        excelExporter.export(response);
    }
    @GetMapping("/product/exportPDF")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=product"+currentDateTime+".pdf";

        response.setHeader(headerKey,headerValue);
        List<Product> productList = productService.listAll();
        ProductPDFExporter exporter = new ProductPDFExporter(productList);
        exporter.export(response);
    }
}
