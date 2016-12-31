package com.webmartini.util;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class PrintUtil {

	
	public static String print(HttpServletResponse response,String title,String[] head, List<String[]> datalist) {
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Pragma",   "no-cache");   //HTTP   1.0  
		response.setDateHeader("Expires",   0);   //prevents   caching   at   the   proxy   server  
		
		StringBuilder sb = new StringBuilder();
		sb.append("<table id='print_table' class='table table-striped table-bordered table-hover dt-responsive' style='font-size: 12px;'>");
		sb.append("<thead><tr >");
		for(String htitle : head){
			sb.append("<th>" + htitle + "</th>");
		}
		sb.append("</tr></thead>");
		sb.append("<tbody>");
		for(String[] array : datalist){
			sb.append("<tr><td>" + array[0] + "</td>");
			sb.append("<td>" + array[1] + "</td>");
			sb.append("<td>" + array[2] + "</td>");
			sb.append("<td>" + array[3] + "</td>");
			sb.append("<td>" + array[4] + "</td>");
			sb.append("<td>" + array[5] + "</td>");
			sb.append("<td>" + array[6] + "</td></tr>");
		}
		sb.append("</tbody>");
		sb.append("</table>");
		return sb.toString();
	}

}