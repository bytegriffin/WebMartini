package com.webmartini.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelUtil {

	@SuppressWarnings("resource")
	public static List<Map<Integer,String>> readExcel(InputStream inputStream) {
		List<Map<Integer,String>> list = null; 
		try {
			list = new ArrayList<Map<Integer,String>>(); 
			// 构造 Workbook 对象，execelFile 是传入文件路径(获得Excel工作区)
			Workbook book = null;
			try {
				// Excel 2007获取方法
				book = new XSSFWorkbook(inputStream);
			} catch (Exception ex) {
				// Excel 2003获取方法
				book = new HSSFWorkbook(inputStream);
			}
			// 读取表格的第一个sheet页
			Sheet sheet = book.getSheetAt(0);
			// 总共有多少行,从0开始
			int totalRows = sheet.getLastRowNum();
			// 循环输出表格中的内容,首先循环取出行,再根据行循环取出列
			for (int i = 1; i <= totalRows; i++) {
				Map<Integer,String> rowcontent = new HashMap<Integer,String>();
				Row row = sheet.getRow(i);
				// 处理空行
				if (row == null) {
					continue;
				}
				// 总共有多少列,从0开始
				int totalCells = row.getLastCellNum();
				for (int j = row.getFirstCellNum(); j < totalCells; j++) {
					// 处理空列
					if (row.getCell(j) == null) {
						continue;
					}
					// 通过 row.getCell(j).toString() 获取单元格内容
					String cell = row.getCell(j).toString();
					rowcontent.put(j, cell);					
				}
				list.add(rowcontent);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 将数据写入到Excel文件
	 * 
	 * @param file
	 *            文件名称
	 * @param sheetName
	 *            工作表名称
	 * @param title
	 *            工作表标题栏
	 * @param data
	 *            工作表数据
	 * @throws FileNotFoundException
	 *             文件不存在异常
	 * @throws IOException
	 *             IO异常
	 */
	public static void writeToFile(HttpServletResponse response, String filename, String sheetName, String[] title,
			List<String[]> datalist) throws FileNotFoundException, IOException {
		String[] sheetNames = new String[1];
		sheetNames[0] = sheetName;
		List<String[]> titles = new ArrayList<String[]>();
		titles.add(title);
		List<List<String[]>> datas = new ArrayList<List<String[]>>();
		datas.add(datalist);
		// 创建并获取工作簿对象
		Workbook wb = getWorkBook(sheetNames, titles, datas);
		// 写入到文件
		// FileOutputStream out = new FileOutputStream(filePath);
		// wb.write(out);
		// out.close();
		OutputStream output = response.getOutputStream();
		response.reset();
		response.setContentType("application/OCTET-STREAM;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
		wb.write(output);
		output.flush();
		output.close();
		wb.close();
	}

	/**
	 * 将数据写入到Excel文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param sheetName
	 *            工作表名称
	 * @param title
	 *            工作表标题栏
	 * @param data
	 *            工作表数据
	 * @throws FileNotFoundException
	 *             文件不存在异常
	 * @throws IOException
	 *             IO异常
	 */
	public static void writeToFile(String filePath, String[] sheetName, List<? extends Object[]> title,
			List<? extends List<? extends Object[]>> data) throws FileNotFoundException, IOException {
		// 创建并获取工作簿对象
		Workbook wb = getWorkBook(sheetName, title, data);
		// 写入到文件
		FileOutputStream out = new FileOutputStream(filePath);
		wb.write(out);
		out.close();
	}

	/**
	 * 创建工作簿对象<br>
	 * <font color="red">工作表名称，工作表标题，工作表数据最好能够对应起来</font><br>
	 * 比如三个不同或相同的工作表名称，三组不同或相同的工作表标题，三组不同或相同的工作表数据<br>
	 * <b> 注意：<br>
	 * 需要为每个工作表指定<font color="red">工作表名称，工作表标题，工作表数据</font><br>
	 * 如果工作表的数目大于工作表数据的集合，那么首先会根据顺序一一创建对应的工作表名称和数据集合，然后创建的工作表里面是没有数据的<br>
	 * 如果工作表的数目小于工作表数据的集合，那么多余的数据将不会写入工作表中 </b>
	 * 
	 * @param sheetName
	 *            工作表名称的数组
	 * @param title
	 *            每个工作表名称的数组集合
	 * @param data
	 *            每个工作表数据的集合的集合
	 * @return Workbook工作簿
	 * @throws FileNotFoundException
	 *             文件不存在异常
	 * @throws IOException
	 *             IO异常
	 */
	public static Workbook getWorkBook(String[] sheetName, List<? extends Object[]> title,
			List<? extends List<? extends Object[]>> data) throws FileNotFoundException, IOException {
		// 创建工作簿，支持2007及以后的文档格式
		Workbook wb = new XSSFWorkbook();
		// 创建一个工作表sheet
		Sheet sheet = null;
		// 申明行
		Row row = null;
		// 申明单元格
		Cell cell = null;
		// 单元格样式
		CellStyle titleStyle = wb.createCellStyle();
		CellStyle cellStyle = wb.createCellStyle();
		// 字体样式
		Font font = wb.createFont();
		// 粗体
		//font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);
		// 水平居中
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		// 垂直居中
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 水平居中
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		// 垂直居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 标题数据
		Object[] title_temp = null;
		// 行数据
		Object[] rowData = null;
		// 工作表数据
		List<? extends Object[]> sheetData = null;
		// 遍历sheet
		for (int sheetNumber = 0; sheetNumber < sheetName.length; sheetNumber++) {
			// 创建工作表
			sheet = wb.createSheet();
			// 设置工作表名称
			wb.setSheetName(sheetNumber, sheetName[sheetNumber]);
			// 设置标题
			title_temp = title.get(sheetNumber);
			row = sheet.createRow(0);
			// 写入标题
			for (int i = 0; i < title_temp.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(titleStyle);
				cell.setCellValue(title_temp[i].toString());
			}
			try {
				sheetData = data.get(sheetNumber);
			} catch (Exception e) {
				continue;
			}
			// 写入行数据
			int size = sheetData.size();
			for (int rowNumber = 0; rowNumber < size; rowNumber++) {
				// 如果没有标题栏，起始行就是0，如果有标题栏，行号就应该为1
				row = sheet.createRow(title_temp == null ? rowNumber : (rowNumber + 1));
				rowData = sheetData.get(rowNumber);
				for (int columnNumber = 0; columnNumber < rowData.length; columnNumber++) {
					cell = row.createCell(columnNumber);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(rowData[columnNumber].toString());
				}
			}
		}
		return wb;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String[] title1 = { "第一列a", "第二列b", "第三列c" };
		String[] title2 = { "第一列d", "第二列e", "第三列f" };
		String[] title3 = { "第一列h", "第二列i", "第三列j" };

		List<String[]> titles = new ArrayList<String[]>();
		titles.add(title1);
		titles.add(title2);
		titles.add(title3);

		String[] data1 = { "i", "j", "k" };
		String[] data2 = { "m", "n", "o" };
		String[] data3 = { "x", "y", "z" };

		List<String[]> data = new ArrayList<String[]>();
		data.add(data1);
		data.add(data2);
		data.add(data3);
		List<List<String[]>> data_ = new ArrayList<List<String[]>>();
		data_.add(data);
		String[] sheetName = { "第一张表", "第二张表", "第三张表" };
		ExcelUtil.writeToFile("D:\\xx.xlsx", sheetName, titles, data_);
	}
}