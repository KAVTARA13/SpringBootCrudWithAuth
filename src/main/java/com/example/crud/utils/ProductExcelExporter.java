package com.example.crud.utils;

import com.example.crud.entities.Product;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jdk.dynalink.linker.LinkerServices;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ProductExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Product> productList;

    public ProductExcelExporter(List<Product> list){
        this.productList = list;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Products");
    }

    private void writeHeaderRow(){
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("Product Id");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("Product Name");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("Product Brand");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("Product Made In");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("Product Price");
        cell.setCellStyle(style);
    }
    private void writeDataRow(){
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Product product: productList){
            Row row = sheet.createRow(rowCount++);
            Cell cell = row.createCell(0);
            cell.setCellValue(product.getId());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(0);
            cell = row.createCell(1);
            cell.setCellValue(product.getName());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(1);
            cell = row.createCell(2);
            cell.setCellValue(product.getBrand());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(2);
            cell = row.createCell(3);
            cell.setCellValue(product.getMadeIn());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(3);
            cell = row.createCell(4);
            cell.setCellValue(product.getPrice());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(4);
        }
    }
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRow();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
