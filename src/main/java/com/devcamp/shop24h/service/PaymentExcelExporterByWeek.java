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

import com.devcamp.shop24h.getquery.GetPayment;

public class PaymentExcelExporterByWeek {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	List<GetPayment> payments;

	public PaymentExcelExporterByWeek(List<GetPayment> payments) {
		super();
		this.payments = payments;
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
		sheet = workbook.createSheet("Customers");
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		style.setFont(font);
		font.setBold(true);
		font.setFontHeight(14);
		int rowCount = 0;
		createCell(row, rowCount++, "Tuần", style);
		createCell(row, rowCount++, "Tổng thu nhập", style);
	}
	
	private void writeDataLine() {
		int rowCount = 1;
		CellStyle style = workbook.createCellStyle();

		XSSFFont font = workbook.createFont();
		font.setFontHeight(11);
		style.setFont(font);

		for (GetPayment payment : payments) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++ , payment.getWeek(), style);
			createCell(row, columnCount++ , payment.getTotal(), style);
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
