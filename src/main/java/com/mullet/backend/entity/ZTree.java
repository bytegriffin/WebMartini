package com.mullet.backend.entity;

import java.util.List;

public abstract class ZTree {

	private boolean open=true;//zTree属性，是否打开所有子菜单
	private boolean checked;//zTree属性，是否被选中
	private List<?> children;//children写死，zTree要求
	private int num;//序列号

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<?> getChildren() {
		return children;
	}

	public void setChildren(List<?> children) {
		this.children = children;
	}

}
