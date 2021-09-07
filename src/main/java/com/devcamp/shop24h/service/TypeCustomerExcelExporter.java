package com.devcamp.shop24h.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.devcamp.shop24h.getquery.GetTotalPaymentCustomer;

public class TypeCustomerExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	List<GetTotalPaymentCustomer> customers;

	public TypeCustomerExcelExporter(List<GetTotalPaymentCustomer> customers) {
		super();
		this.customers = customers;
		this.workbook = new XSSFWorkbook();
	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof String) {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}
	
	public void writeHeaderLine() {
		sheet = workbook.createSheet("Customer Type");
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		style.setFont(font);
		font.setBold(true);
		font.setFontHeight(14);
		int rowCount = 0;
		createCell(row, rowCount++, "Họ và tên", style);
		createCell(row, rowCount++, "Số điện thoại", style);
		createCell(row, rowCount++, "Số tiền đã thanh toán", style);
	}
	
	private void writeDataLine() {
		int rowCount = 1;
		CellStyle style = workbook.createCellStyle();

		XSSFFont font = workbook.createFont();
		font.setFontHeight(11);
		style.setFont(font);

		for (GetTotalPaymentCustomer customer : customers) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++ , customer.getFullName(), style);
			createCell(row, columnCount++ , customer.getPhoneNumber(), style);
			createCell(row, columnCount++ , customer.getTotalPayment(), style);
		}
	}
	
	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLine();
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		
		outputStream.close();
	}
}
