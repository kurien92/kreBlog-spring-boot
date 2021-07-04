package net.kurien.blog.module.category.dao;

import java.util.List;

import net.kurien.blog.module.category.entity.CategoryEntity;

public interface CategoryDao {
	List<CategoryEntity> selectList();

	CategoryEntity select(int CategoryNo);

	CategoryEntity select(String categoryId);
	
	void insert(CategoryEntity category);
	
	void update(CategoryEntity category);
	
	void delete(int categoryNo);

	void delete(String categoryId);

	List<CategoryEntity> selectListByParentNo(Integer categoryParentNo);
}
