package net.kurien.blog.module.category.entity;

import net.kurien.blog.entity.Category;

public class CategoryEntity {
	private Integer categoryNo;
	private Integer categoryParentNo;
	private Integer categoryDepth;
	private Integer categoryOrder;
	private String categoryId;
	private String categoryName;
	
	public Integer getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(Integer categoryNo) {
		this.categoryNo = categoryNo;
	}
	public Integer getCategoryParentNo() {
		return categoryParentNo;
	}
	public void setCategoryParentNo(Integer categoryParentNo) {
		this.categoryParentNo = categoryParentNo;
	}
	public Integer getCategoryDepth() {
		return categoryDepth;
	}
	public void setCategoryDepth(Integer categoryDepth) {
		this.categoryDepth = categoryDepth;
	}
	public Integer getCategoryOrder() {
		return categoryOrder;
	}
	public void setCategoryOrder(Integer categoryOrder) {
		this.categoryOrder = categoryOrder;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public static CategoryEntity from(Category category) {
		if(category == null) {
			return null;
		}

		CategoryEntity ce = new CategoryEntity();

		ce.setCategoryNo(category.getCategoryId().intValue());
		ce.setCategoryDepth(category.getCategoryDepth().intValue());
		ce.setCategoryName(category.getCategoryName());
		ce.setCategoryOrder(category.getCategoryOrder().intValue());
		ce.setCategoryId(category.getCategoryKey());
		if(category.getCategoryParent() != null) {
			ce.setCategoryParentNo(category.getCategoryParent().getCategoryId().intValue());
		}

		return ce;
	}

	@Override
	public String toString() {
		return "Category [categoryNo=" + categoryNo + ", categoryParentNo=" + categoryParentNo + ", categoryDepth="
				+ categoryDepth + ", categoryOrder=" + categoryOrder + ", categoryId=" + categoryId + ", categoryName="
				+ categoryName + "]";
	}
}
