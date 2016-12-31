package com.webmartini.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public final class PdfUtil {

	/**
	 * 设置PDF创建者信息
	 * 
	 * @param pdfDocument
	 */
	public static Document setCreatorInfo(Document pdfDocument, String title) {
		if (pdfDocument == null) {
			return null;
		}
		// 文档属性
		pdfDocument.addTitle(title);
		pdfDocument.addAuthor("");
		pdfDocument.addSubject("");
		pdfDocument.addKeywords("");// 文档关键字信息
		pdfDocument.addCreator("");// 应用程序名称
		return pdfDocument;
	}

	/**
	 * 设置成只读权限
	 * 
	 * @param pdfWriter
	 */
	public static PdfWriter setReadOnlyPDFFile(PdfWriter pdfWriter) throws DocumentException {
		pdfWriter.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
		return pdfWriter;
	}

	public static void exportPDF(HttpServletResponse response,String filename, String title,String[] head, List<String[]> datalist) throws IOException {
		OutputStream output = null;
		Document pdfDocument = new Document(PageSize.A4, 50, 50, 50, 50);
		try {
			// 构建一个PDF文档输出流程
			output = response.getOutputStream();
			response.reset();
			response.setContentType("application/OCTET-STREAM;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8")); 

//			OutputStream pdfFileOutputStream = new FileOutputStream(new File(fullFilePath));
//			PdfWriter pdfWriter = 
			PdfWriter.getInstance(pdfDocument, output);
			// 设置作者信息
			setCreatorInfo(pdfDocument, title);
			// 设置文件只读权限
			//setReadOnlyPDFFile(pdfWriter);
			// 通过PDF页面事件模式添加文字水印功能
			// PdfFileExportUtil pdfFileExportUtil = new PdfFileExportUtil();
			//pdfWriter.setPageEvent(pdfFileExportUtil.new TextWaterMarkPdfPageEvent(""));
			// 通过PDF页面事件模式添加图片水印功能
			// String waterMarkFullFilePath = "D:/temp/pdftest/login_logo.gif";//水印图片
			// pdfWriter.setPageEvent(pdfFileExportUtil.new PictureWaterMarkPdfPageEvent(waterMarkFullFilePath));
			// 通过PDF页面事件模式添加页头和页脚信息功能
			//pdfWriter.setPageEvent(pdfFileExportUtil.new HeadFootInfoPdfPageEvent());
			// 设置中文字体和字体样式
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font f8 = new Font(bfChinese, 8, Font.NORMAL);
      
			// 打开PDF文件流
			pdfDocument.open();
			// 创建一个N列的表格控件
			PdfPTable pdfTable = new PdfPTable(head.length);
			// 设置表格占PDF文档100%宽度
			pdfTable.setWidthPercentage(100);
			// 水平方向表格控件左对齐
			pdfTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
			// 创建一个表格的表头单元格
			PdfPCell pdfTableHeaderCell = new PdfPCell();
			// 设置表格的表头单元格颜色
			// pdfTableHeaderCell.setBackgroundColor(new Color(213, 141, 69));
			pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			for (String tableHeaderInfo : head) {
				pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo,f8));
				pdfTable.addCell(pdfTableHeaderCell);
			}

			// 创建一个表格的正文内容单元格
			PdfPCell pdfTableContentCell = new PdfPCell();
			pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			// 表格内容行数的填充
			int size = datalist.size();
			for (int i = 0; i < size; i++) {
				String[] content = datalist.get(i);
				for (String info : content) {
					pdfTable.addCell(new Paragraph(info, f8));
				}
				pdfDocument.add(pdfTable);
				pdfTable.deleteBodyRows();
			}

		} catch (FileNotFoundException de) {
			de.printStackTrace();
		} catch (DocumentException de) {
			de.printStackTrace();
		} catch (IOException de) {
			de.printStackTrace();
		} finally {
			// 关闭PDF文档流，OutputStream文件输出流也将在PDF文档流关闭方法内部关闭
			if (pdfDocument != null) {
				pdfDocument.close();
			}
			output.flush();
			output.close();
		}
	}

}
