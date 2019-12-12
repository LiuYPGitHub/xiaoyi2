package com.xiaoyi.bis.user.controller.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResPageInfo<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	// 当前页
	private int pageNum;
	// 每页的数量
	private int pageSize;
	// 当前页的数量
	private int size;
	// 总记录数
	private long total;
	// 总页数
	private int pages;
	// 结果集
	private List<T> content;
}
