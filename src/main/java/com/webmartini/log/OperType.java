package com.webmartini.log;

/**
 *   用户操作类型   <br />
 *     
 */
public enum OperType{

	login("登入"),
	logout("登出"),	
	view("浏览"),
	sugguest("获取搜索建议"),
	search("搜索"),
	add("新增"),
	delete("删除"),
	edit("修改"),
	upload("上传"),
	import_excel("批量导入"),
	exp_excel("导出excel"),
	exp_pdf("导出pdf"),
	print("打印"),
	role_menu("授权菜单"),
	role_user("授权用户"),
	role_group("授权组织"),
	group_user("组织关联用户"),
	group_role("组织关联角色"),
	send("发信"),
	download("下载"),
	unknown("未知功能");

	private final String value;

	OperType(final String value) {
	  this.value = value;
     }

	public String getValue() {
		return value;
	}

}