package com.mullet.backend.util;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * 集成了mybatis的dataTables分页, 属性名称写死的为了与前台相呼应。
 *
 * @param <T>
 */
public class DataTablesPage<T> implements Serializable {
	private static final long serialVersionUID = 8656597559014685635L;
	private long recordsTotal; // 总记录数
	private long recordsFiltered;// 过滤记录数
	private List<T> data; // 结果集
	private int draw; // 请求序号。由于Ajax请求是异步的，和返回的参数draw一起用来确定序号
	private int start; // 当前从第几条记录开始读取
	private int length; // pageSize 每页记录数, 
	private Order order;

	/**
	 * 包装Page对象，因为直接返回Page对象，在JSON处理以及其他情况下会被当成List来处理， 而出现一些问题。
	 * 
	 * @param list
	 *            page结果
	 * @param navigatePages
	 *            页码数量
	 */
	public DataTablesPage(List<T> list,int draw,int start,int length) {
		if (list instanceof Page) {
			Page<T> page = (Page<T>) list;
			this.draw = draw;
			this.start = start;
			this.recordsTotal = page.getTotal();
			this.recordsFiltered = page.getTotal();
			this.length = length;
			this.data = page.getResult();
		}
	}

	public static int getPageNum(int start, int length) {
		return start / length + 1;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public long getTotal() {
		if (recordsTotal % length == 0)
			return recordsTotal / length;
		else
			return recordsTotal / length + 1;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

//	public int getPageNum() {
//		return pageNum;
//	}
//
//	public void setPageNum(int pageNum) {
//		this.pageNum = pageNum;
//	}

//	public int getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public class Search {
		private String value;
		private boolean regex;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public boolean isRegex() {
			return regex;
		}

		public void setRegex(boolean regex) {
			this.regex = regex;
		}

		@Override
		public String toString() {
			return "Search [value=" + value + ", regex=" + regex + "]";
		}

	}

	public class Order {
		private int column;
		private String dir;

		public int getColumn() {
			return column;
		}

		public void setColumn(int column) {
			this.column = column;
		}

		public String getDir() {
			return dir;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}

		@Override
		public String toString() {
			return "Order [column=" + column + ", dir=" + dir + "]";
		}

	}

}