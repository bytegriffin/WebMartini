package com.webmartini.domain;

import com.webmartini.util.StringHelper;

//导航栏
public class Menu extends ZTree{

	public static final int ENABLE_STATUS = 1;
	public static final int UNABLE_STATUS = 0;

	private String id;
	private String parentId;
	private String name;
	private String url;
	private String icon;
	private Integer status;//0或null 为关闭，１为开启
	private Integer priority;
	private Integer levels;
//	private boolean isLeaf;//jqgrid要求的属性，是否为叶节点
//	private boolean expanded=true;//jqgrid要求的属性,是否展开
//	private boolean loaded=true;//jqgrid要求的属性，是否已经加载过子节点
	private String description;
	private String code;
	private String parentCode;
	
	public static String getLevels(int levels) {
		String value = "";
		if (levels == 0) {
			value = "顶级菜单";
		} else if (levels == 1) {
			value = "二级菜单";
		} else if (levels == 2) {
			value = "三级菜单";
		} else if (levels == 3) {
			value = "四级菜单";
		} else if (levels == 4) {
			value = "五级菜单";
		} else if (levels == 5) {
			value = "六级菜单";
		} else if (levels == 6) {
			value = "七级菜单";
		} else if (levels == 7) {
			value = "八级菜单";
		} else if (levels == 8) {
			value = "九级菜单";
		} else if (levels == 9) {
			value = "十级菜单";
		}
		return value;
	}

	public static int getStatuts(String status){
		if("on".equals(status)){
			return ENABLE_STATUS;
		} else {
			return UNABLE_STATUS;
		}
	}

	public static String getIcon(String icon){
		if(StringHelper.isNullOrBlank(icon) || icon.equals("fa-")){
			return "";
		}
		return icon;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

//	public boolean getIsLoaded() {
//		return loaded;
//	}
//
//	public void setLoaded(boolean loaded) {
//		this.loaded = loaded;
//	}
//
//	public boolean getIsExpanded() {
//		return expanded;
//	}
//
//	public void setExpanded(boolean expanded) {
//			this.expanded = expanded;
//	}
//
//	public boolean getIsLeaf() {
//		return isLeaf;
//	}
//
//	public void setLeaf(boolean isLeaf) {
//		this.isLeaf = isLeaf;
//	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

}
