package com.bytegriffin.webmartini.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 *  此filter可以避免不用在Tomcat下写URIEncoding="UTF-8"<br />
 *  保证get/post请求参数非乱码（即：保证bootstrap-typeahead搜索建议和datatables搜索提交的参数编码正常）<br />
 *  而spring的CharacterEncodingFilter是<br />
 *  是保证POST提交非乱码格式
 */
@WebFilter(filterName="customCharacterEncodingFilter",urlPatterns={"/*"})
public class CustomCharacterEncodingFilter implements Filter {

	private String defaultCharset = "UTF-8";
	private FilterConfig filterConfig = null;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String charset = filterConfig.getInitParameter("encoding");
		if (charset == null) {
			charset = defaultCharset;
		}
		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);
		response.setContentType("text/html;charset=" + charset);

		//禁止浏览器缓存所有动态页面
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		
		MyCharacterEncodingRequest requestWrapper = new MyCharacterEncodingRequest(request);
		chain.doFilter(requestWrapper, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	class MyCharacterEncodingRequest extends HttpServletRequestWrapper {
		private HttpServletRequest request;

		public MyCharacterEncodingRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}

		@Override
		public String getParameter(String name) {
			try {
				// 获取参数的值
				String value = this.request.getParameter(name);
				if (value == null) {
					return null;
				}
				String newstr = new String(value.getBytes("ISO8859-1"),"ISO8859-1");
				//如果参数字符串的编码是ISO8859-1,则进行转码
				if(newstr.equals(value)){
					value = new String(value.getBytes("ISO8859-1"), this.request.getCharacterEncoding());
					return value;
				}

				return value;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
	}

}
