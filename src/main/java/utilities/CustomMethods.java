package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import base.BaseClass;
import base.FrameworkConstants;
import fabricator.Fabricator;

public class CustomMethods extends BaseClass {

	static WebDriver driver = getDriver();
	static ConfigurationSupport cs = new ConfigurationSupport(FrameworkConstants.getGlobalpropertiespath());

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
	
	public static String randomName() {
		return Fabricator.contact().firstName();
	}

}
