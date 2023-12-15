package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import base.FrameworkConstants;

public final class ExcelLibrary {

	public String path = FrameworkConstants.getExcelpath();

	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	public ExcelLibrary() {

		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("excel file not found");
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("can not read excel file");
		}
	}

	public ExcelLibrary(String path) {

		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("excel file not found");
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("can not read excel file");
		}
	}

	// returns the row count in a sheet
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
				return "row less than 0";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "Sheet not found";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "Col num < 0";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "No row";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "Cell is null";
			
			if (cell.getCellType().name().equals("STRING"))
				return cell.getStringCellValue();
			else if (cell.getCellType().name().equals("NUMERIC") || cell.getCellType().name().equals("FORMULA")) {

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
			} else if (cell.getCellType().name().equals("BLANK"))
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

			if (cell.getCellType().name().equals("STRING"))
				return cell.getStringCellValue();
			else if (cell.getCellType().name().equals("NUMERIC") || cell.getCellType().name().equals("FORMULA")) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

				}

				return cellText;
			} else if (cell.getCellType().name().equals("BLANK"))
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
	
	// returns true if data is set successfully else false
		public static boolean setCellData(String path, int colNum, int rowNum, String data) {
			try {
				FileInputStream fis = new FileInputStream(path);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);

				XSSFSheet sheet = workbook.getSheetAt(0);
				XSSFRow row = sheet.getRow(0);

				sheet.autoSizeColumn(colNum);
				row = sheet.getRow(0);
				if (row == null)
					row = sheet.createRow(rowNum);

				XSSFCell cell = row.getCell(colNum);
				if (cell == null)
					cell = row.createCell(colNum);

				cell.setCellValue(data);

				FileOutputStream fileOut = new FileOutputStream(path);

				workbook.write(fileOut);

				fis.close();
				fileOut.close();

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

	
	
	// find whether sheets exists
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
		
		
		
		public int getCellRowNum(String sheetName, String colName, String cellValue) {

			for (int i = 2; i <= getRowCount(sheetName); i++) {
				if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
					return i;
				}
			}
			return -1;

		}
	
		public String getCellFromWorkbook(String path, String sheetName,int colNum,int rowNum) {
			try {
				fis=new FileInputStream(path);
				workbook=new XSSFWorkbook(fis);
				if(rowNum<=0)
					return "";
				
				int index=workbook.getSheetIndex(sheetName);
				
				if(index==-1)
					return "";
				
				sheet=workbook.getSheetAt(index);
				row=sheet.getRow(rowNum-1);
				if(row==null)
					return "";
				cell=row.getCell(colNum);
				
				if(cell==null)
					return "";
				
				if(cell.getCellType().name().equals("STRING"))
					return cell.getStringCellValue();
				else if(cell.getCellType().name().equals("NUMERIC") || cell.getCellType().name().equals("FORMULA")) {
					
					String cellText=String.valueOf(cell.getNumericCellValue());
					if(DateUtil.isCellDateFormatted(cell)) {
						double d=cell.getNumericCellValue();
						
						Calendar cal=Calendar.getInstance();
						cal.setTime(DateUtil.getJavaDate(d));
						cellText=(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
						cellText=cal.get(Calendar.MONTH)+1+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+ cellText;
					}
					
					return cellText;
				} else if(cell.getCellType().name().equals("BLANK"))
					return "";
				else
					return String.valueOf(cell.getBooleanCellValue());
			}catch(Exception e) {
				e.printStackTrace();
				return "row" + rowNum +"Or column" + colNum + " does not exist in xls";
			}
		}

	
	
}