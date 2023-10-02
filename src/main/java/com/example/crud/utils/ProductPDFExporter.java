package com.example.crud.utils;

import com.example.crud.entities.Product;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ProductPDFExporter {
    private List<Product> productList;

    public ProductPDFExporter(List<Product> list){
        this.productList = list;
     }
     private void writeTableHeader(PdfPTable table){
         PdfPCell cell = new PdfPCell();
         cell.setBackgroundColor(Color.BLUE);
         cell.setPadding(5);

         Font font = FontFactory.getFont(FontFactory.HELVETICA);
         font.setColor(Color.WHITE);

         cell.setPhrase(new Phrase("Product Id",font));
         table.addCell(cell);
         cell.setPhrase(new Phrase("Product Name",font));
         table.addCell(cell);
         cell.setPhrase(new Phrase("Product Brand",font));
         table.addCell(cell);
         cell.setPhrase(new Phrase("Product Made In",font));
         table.addCell(cell);
         cell.setPhrase(new Phrase("Product Price",font));
         table.addCell(cell);
     }

    private void writeTableData(PdfPTable table){
        for (Product product:productList){
            table.addCell(String.valueOf(product.getId()));
            table.addCell(product.getName());
            table.addCell(product.getBrand());
            table.addCell(product.getMadeIn());
            table.addCell(String.valueOf(product.getPrice()));
        }
    }
    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());
        document.open();
        document.add(new Paragraph("List of all products"));
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }
}
