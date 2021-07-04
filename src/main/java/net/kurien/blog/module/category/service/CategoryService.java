package net.kurien.blog.module.category.service;

import java.util.List;

import net.kurien.blog.entity.Category;
import net.kurien.blog.module.category.entity.CategoryEntity;

public interface CategoryService {
	List<CategoryEntity> getList();
	
	CategoryEntity get(int categoryNo);
	
	Category get(String categoryId);
	
	void create(CategoryEntity category);
	
	void modify(CategoryEntity category);
	
	void remove(int categoryNo);
	
	void remove(String categoryId);

	List<CategoryEntity> getCategoryAndChilds(String categoryId);

	String getCategoryHTML(String contextPath);
}
