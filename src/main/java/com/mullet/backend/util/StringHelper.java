package com.mullet.backend.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mullet.backend.entity.GroupRef;
import com.mullet.backend.entity.RoleRef;
import com.mullet.backend.entity.UserRef;

public final class StringHelper {

	public static final String ROOT_CODE = "0000";
	public static final String FIRST_CODE = "0001";
	public static final String SUB_CODE = "0001";
	private static final int UNIT_LENGTH = 4;// 每个单元长度

	public static List<UserRef> StringToUserRefList(String userId, String[] ids) {
		List<UserRef> list = new ArrayList<UserRef>();
		for (int i = 0; i < ids.length; i++) {
			list.add(new UserRef(userId, ids[i]));
		}
		return list;
	}

	public static List<GroupRef> StringToGroupRefList(String groupId, String[] ids) {
		List<GroupRef> list = new ArrayList<GroupRef>();
		for (int i = 0; i < ids.length; i++) {
			list.add(new GroupRef(groupId, ids[i]));
		}
		return list;
	}

	public static List<RoleRef> StringToRoleRefList(String roleId, String[] ids) {
		List<RoleRef> list = new ArrayList<RoleRef>();
		for (int i = 0; i < ids.length; i++) {
			list.add(new RoleRef(roleId, ids[i]));
		}
		return list;
	}

	public static List<GroupRef> StringToGroupRefList(String groupId, String ids) {
		List<GroupRef> list = new ArrayList<GroupRef>();
		if (StringHelper.isNullOrBlank(ids)) {
			return list;
		} else {
			String[] proxys = ids.split(",");
			for (int i = 0; i < proxys.length; i++) {
				list.add(new GroupRef(groupId, proxys[i]));
			}
			return list;
		}
	}

	public static List<RoleRef> StringToRoleRefList(String roleId, String ids) {
		List<RoleRef> list = new ArrayList<RoleRef>();
		if (StringHelper.isNullOrBlank(ids)) {
			return list;
		} else {
			String[] proxys = ids.split(",");
			for (int i = 0; i < proxys.length; i++) {
				list.add(new RoleRef(roleId, proxys[i]));
			}
			return list;
		}
	}

	public static List<String> StringToList(String str) {
		List<String> list = new ArrayList<String>();
		if (StringHelper.isNullOrBlank(str)) {
			return list;
		} else {
			String[] proxys = str.split(",");
			for (int i = 0; i < proxys.length; i++) {
				list.add(proxys[i]);
			}
			return list;
		}
	}

	public static String ListToString(List<String> list) {
		if (list == null || list.isEmpty()) {
			return "";
		}
		String ns = "";
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			ns += str;
			if (i < list.size() - 1) {
				ns += ",";
			}
		}
		return ns;
	}

	public static String SetToString(Set<String> roles) {
		if (roles == null || roles.isEmpty()) {
			return "";
		}
		String ns = "";
		Iterator<String> it = roles.iterator();
		while (it.hasNext()) {
			String str = it.next();
			ns += str + ",";
		}
		return ns.substring(0, ns.length() - 1);
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isNullOrBlank(String str) {
		if (null == str || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 获取编码,如：0001/00010002/00010003
	 * 
	 * @param maxcode
	 * @return
	 */
	public static synchronized String getNextCode(String maxcode) {
		int strlength = maxcode.length();
		String prefix = "";
		String suffix = "";
		if (strlength > UNIT_LENGTH) {
			prefix = maxcode.substring(0, strlength - UNIT_LENGTH);
			suffix = maxcode.substring(strlength - UNIT_LENGTH, strlength);
		} else {
			suffix = maxcode;
		}
		int increat = Integer.valueOf(suffix) + 1;
		int yu = UNIT_LENGTH - (increat + "").length();
		if (yu == 1) {
			suffix = "0" + increat;
		} else if (yu == 2) {
			suffix = "00" + increat;
		} else if (yu == 3) {
			suffix = "000" + increat;
		}
		return prefix + suffix;
	}

	public static synchronized String getChildCode(String originCode, String parentCode) {
		int strlength = originCode.length();
		String prefix = parentCode;
		String suffix = "";
		if (strlength > UNIT_LENGTH) {
			suffix = originCode.substring(strlength - UNIT_LENGTH, strlength);
		} else {
			suffix = originCode;
		}
		return prefix + suffix;
	}

	public static void main(String... args) {
		Set<String> set = new HashSet<String>();
		set.add("a");
		set.add("b");
		System.out.println(SetToString(set));
	}

}
