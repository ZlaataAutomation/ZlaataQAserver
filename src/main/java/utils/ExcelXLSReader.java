package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import database.ExcelRow;
import manager.FileReaderManager;
import stepDef.Hooks;
public class ExcelXLSReader {

	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	public ExcelXLSReader(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			//sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}
	}

	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);
			if (cell == null)
				return "";
			if (cell.getCellType() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {

					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

					// System.out.println(cellText);

				}

				return cellText;
			} else if (cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	// returns the data from a cell
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";
			if (cell.getCellType() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
				String cellText = String.valueOf(cell.getStringCellValue());
				/*
				 * if (DateUtil.isCellDateFormatted(cell)) { // format in form of M/D/YY double
				 * d = cell.getNumericCellValue(); Calendar cal = Calendar.getInstance();
				 * cal.setTime(DateUtil.getJavaDate(d)); cellText =
				 * (String.valueOf(cal.get(Calendar.YEAR))).substring(2); cellText =
				 * cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" +
				 * cellText; }
				 */
				return cellText;
			} else if (cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			// CellStyle cs = workbook.createCellStyle();
			// cs.setWrapText(true);
			// cell.setCellStyle(cs);
			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setCellData(String sheetName, int colNum, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			if (colNum < 0)
				return false;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			// cell style
			XSSFCell cell = row.createCell(1);
			cell.setCellValue("welcome");
			CellStyle cs = workbook.createCellStyle();
			cs.setWrapText(true);
			cell.setCellValue(data);
			cell.setCellStyle(cs);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {
		// System.out.println("setCellData setCellData******************");
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);
			// System.out.println("A");
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum = i;
			}

			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum); // ashish
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			cell.setCellValue(data);
			XSSFCreationHelper createHelper = workbook.getCreationHelper();

			// cell style for hyperlinks
			// by default hypelrinks are blue and underlined
			CellStyle hlink_style = workbook.createCellStyle();
			XSSFFont hlink_font = workbook.createFont();
			hlink_font.setUnderline(HSSFFont.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			// hlink_style.setWrapText(true);

			XSSFHyperlink link = createHelper.createHyperlink(HyperlinkType.FILE);
			link.setAddress(url);
			cell.setHyperlink(link);
			cell.setCellStyle(hlink_style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if sheet is created successfully else false
	public boolean addSheet(String sheetname) {

		FileOutputStream fileOut;
		try {
			workbook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if sheet is removed successfully else false if sheet does
	// not exist
	public boolean removeSheet(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return false;

		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if column is created successfully

	/**
	 * @param sheetName
	 * @param colName
	 * @return
	 */
	public boolean addColumn(String sheetName, String colName) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return false;

			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);

			if (row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());

			cell.setCellValue(colName);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	// removes a column and all the contents

	/**
	 * @param sheetName
	 * @param colNum
	 * @return
	 */
	public boolean removeColumn(String sheetName, int colNum) {
		try {
			if (!isSheetExist(sheetName))
				return false;
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
			// XSSFCreationHelper createHelper = workbook.getCreationHelper();
			style.setFillPattern(FillPatternType.NO_FILL);

			for (int i = 0; i < getRowCount(sheetName); i++) {
				row = sheet.getRow(i);
				if (row != null) {
					cell = row.getCell(colNum);
					if (cell != null) {
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	// find whether sheets exists

	/**
	 * @param sheetName
	 * @return
	 */
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	// returns number of columns in a sheet

	/**
	 * @param sheetName
	 * @return
	 */
	public int getColumnCount(String sheetName) {
		// check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		if (row == null)
			return -1;
		return row.getLastCellNum();
	}

	/**
	 * @param sheetName
	 * @param screenShotColName
	 * @param testCaseName
	 * @param index
	 * @param url
	 * @param message
	 * @return
	 */
	public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url,
			String message) {
		url = url.replace('\\', '/');
		if (!isSheetExist(sheetName))
			return false;
		sheet = workbook.getSheet(sheetName);
		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {
				setCellData(sheetName, screenShotColName, i + index, message, url);
				break;
			}
		}
		return true;
	}

	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;

	}

	/**
	 * Read all rows and columns and return 2D Object array: can be used with testNG
	 * dataprovider.
	 *
	 * @param sheetName
	 * @return
	 */
	public Object[][] getSheetData(String sheetName) {
		Workbook book;
		Sheet sheet = null;
		Object data[][] = null;
		try {
			FileInputStream ip = new FileInputStream(path);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
				data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
			}
		}
		return data;
	}

	public String writeRow(String tableName, List<ExcelRow> rowList) {
		String tdID = Common.getValueFromHashMap(Hooks.testDataMap, "TD_ID");
		if (FileReaderManager.getInstance().getConfigReader().isBPReportReq()) {
			try {
				if (!isSheetExist(tdID)) {
					addSheet(tdID);
				} else {
					// System.out.println("Sheet already exists");
				}
				int index = workbook.getSheetIndex(tdID);
				if (index == -1)
					return "";
				sheet = workbook.getSheetAt(index);
				int tableNameDetailsRow = sheet.getLastRowNum() + 3;
				setCellDataForTableHeader(tdID, 0, tableNameDetailsRow, "Table Name : ");
				setCellDataForTableHeader(tdID, 1, tableNameDetailsRow, tableName.toUpperCase());

				XSSFFont font = workbook.createFont();
				font.setFontHeightInPoints((short) 11);
				font.setFontName("Calibri");
				font.setColor(IndexedColors.BLACK.getIndex());
				font.setBold(true);

				XSSFCellStyle style = workbook.createCellStyle();
				style.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
				style.setFillPattern(FillPatternType.LESS_DOTS);
				style.setBorderBottom(BorderStyle.MEDIUM);
				style.setBorderTop(BorderStyle.MEDIUM);
				style.setBorderRight(BorderStyle.MEDIUM);
				style.setBorderLeft(BorderStyle.MEDIUM);
				style.setFont(font);

				XSSFCellStyle style1 = workbook.createCellStyle();
				style1.setBorderBottom(BorderStyle.MEDIUM);
				style1.setBorderTop(BorderStyle.MEDIUM);
				style1.setBorderRight(BorderStyle.MEDIUM);
				style1.setBorderLeft(BorderStyle.MEDIUM);

				int rowIdx = tableNameDetailsRow;
				for (int i = 0; i < rowList.size(); i++) {
					ExcelRow row = rowList.get(i);
					List<String> dataList = row.getDataList();
					Row testDataKey = sheet.createRow(rowIdx);
					for (int j = 0; j < dataList.size(); j++) {
						Cell cell = testDataKey.createCell(j);
						if (i == 0) {
							cell.setCellStyle(style);
							cell.setCellValue(dataList.get(j));

						} else {
							cell.setCellStyle(style1);
							cell.setCellValue(dataList.get(j));
						}
						sheet.autoSizeColumn(j);
					}
					rowIdx++;
				}
				fileOut = new FileOutputStream(path);
				workbook.write(fileOut);
				fileOut.close();
			} catch (Exception e) {
				e.printStackTrace();
				return "row " + " or column " + " does not exist  in xls";
			}
		} else {
			System.out.println("Report configuration not enabled if need kindly enable the report");
		}

		return tdID;

	}

	public void writeBatchJobName(String sheetName, String batchJob) {
		try {
			if (!isSheetExist(sheetName)) {
				addSheet(sheetName);
			} else {
				// System.out.println("Sheet already exists");
			}
			int index = workbook.getSheetIndex(sheetName);
			sheet = workbook.getSheetAt(index);
			int tableNameDetailsRow = sheet.getLastRowNum() + 4;
			setCellDataForBatchJobName(sheetName, 0, tableNameDetailsRow, "Batch Job Name : ");
			setCellDataForBatchJobName(sheetName, 1, tableNameDetailsRow, batchJob);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean setCellDataForBatchJobName(String sheetName, int colNum, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			if (colNum < 0)
				return false;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			XSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			// cell style
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			style.setFillPattern(FillPatternType.LESS_DOTS);
			style.setBorderBottom(BorderStyle.MEDIUM);
			style.setBorderTop(BorderStyle.MEDIUM);
			style.setBorderRight(BorderStyle.MEDIUM);
			style.setBorderLeft(BorderStyle.MEDIUM);
			style.setWrapText(true);
			style.setFont(font);
			cell.setCellStyle(style);
			cell.setCellValue(data);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setCellDataForTableHeader(String sheetName, int colNum, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			if (colNum < 0)
				return false;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			XSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			// cell style
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			style.setFillPattern(FillPatternType.LESS_DOTS);
			style.setBorderBottom(BorderStyle.MEDIUM);
			style.setBorderTop(BorderStyle.MEDIUM);
			style.setBorderRight(BorderStyle.MEDIUM);
			style.setBorderLeft(BorderStyle.MEDIUM);
			style.setWrapText(true);
			style.setFont(font);
			cell.setCellStyle(style);
			cell.setCellValue(data);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	 
	//Saroj Excel Reader
	public static List<Map<String, Object>> readProductsWithMultipleListing(String filePath) throws IOException {
	    List<Map<String, Object>> productList = new ArrayList<>();
	    FileInputStream fis = new FileInputStream(filePath);
	    XSSFWorkbook workbook = new XSSFWorkbook(fis);
	    XSSFSheet sheet = workbook.getSheetAt(0);

	    DataFormatter formatter = new DataFormatter();

	    int rowCount = sheet.getPhysicalNumberOfRows();
	    Row headerRow = sheet.getRow(0);

	    for (int i = 1; i < rowCount; i++) {
	        Row row = sheet.getRow(i);
	        if (row == null) {
	            continue;
	        }
	        Map<String, Object> product = new HashMap<>();

	        for (int j = 0; j < headerRow.getPhysicalNumberOfCells(); j++) {
	            String header = formatter.formatCellValue(headerRow.getCell(j)).trim();
	            String value  = formatter.formatCellValue(row.getCell(j)).trim();

	            // Handle multiple listing names separated by commas
	            if (header.equalsIgnoreCase("Product Listing Name")) {
	                product.put(header, Arrays.asList(value.split(",")));
	            } else {
	                product.put(header, value);
	            }
	        }
	        productList.add(product);
	    }

	    workbook.close();
	    fis.close();
	    return productList;
	}

	
	public static List<Map<String, Object>> readProductsWithMultipleNames(String filePath) throws IOException {
	    List<Map<String, Object>> productList = new ArrayList<>();
	    FileInputStream fis = new FileInputStream(filePath);
	    XSSFWorkbook workbook = new XSSFWorkbook(fis);
	    XSSFSheet sheet = workbook.getSheetAt(0);

	    DataFormatter formatter = new DataFormatter();
	    Row headerRow = sheet.getRow(0);
	    int rowCount = sheet.getPhysicalNumberOfRows();

	    for (int i = 1; i < rowCount; i++) {
	        Row row = sheet.getRow(i);
	        if (row == null) continue;

	        Map<String, Object> product = new HashMap<>();

	        for (int j = 0; j < headerRow.getPhysicalNumberOfCells(); j++) {
	            String header = formatter.formatCellValue(headerRow.getCell(j)).trim();
	            String value = formatter.formatCellValue(row.getCell(j)).trim();

	            // Split multiple product names into a list
	            if (header.equalsIgnoreCase("Product Name")) {
	                List<String> names = Arrays.stream(value.split(","))
	                                           .map(String::trim)
	                                           .filter(s -> !s.isEmpty())
	                                           .collect(Collectors.toList());
	                product.put(header, names);
	            } else {
	                product.put(header, value);
	            }
	        }

	        productList.add(product);
	    }

	    workbook.close();
	    fis.close();
	    return productList;
	}
	
	
	
	public static void updateRandomQuantityOnly(String filePath) throws IOException {

	    FileInputStream fis = new FileInputStream(filePath);
	    Workbook workbook = new XSSFWorkbook(fis);
	    Sheet sheet = workbook.getSheetAt(0);

	    Random random = new Random();

	    for (int i = 1; i <= sheet.getLastRowNum(); i++) {

	        Row row = sheet.getRow(i);
	        if (row == null) continue;

	        int topQty = random.nextInt(50) + 1;
	        int bottomQty = random.nextInt(50) + 1;

	        // Column D – Top Quantity
	        row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
	                .setCellValue(topQty);

	        // Column F – Bottom Quantity
	        row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
	                .setCellValue(bottomQty);
	    }

	    fis.close();
	    FileOutputStream fos = new FileOutputStream(filePath);
	    workbook.write(fos);
	    fos.close();
	    workbook.close();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}