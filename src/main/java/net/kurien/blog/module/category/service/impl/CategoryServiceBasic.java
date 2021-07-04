package net.kurien.blog.module.category.service.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import net.kurien.blog.entity.Category;
import net.kurien.blog.module.category.repository.CategoryRepository;
import net.kurien.blog.module.sitemap.SitemapCreatable;
import net.kurien.blog.module.sitemap.SitemapDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kurien.blog.module.category.dao.CategoryDao;
import net.kurien.blog.module.category.entity.CategoryEntity;
import net.kurien.blog.module.category.service.CategoryService;
import net.kurien.blog.module.post.service.PostService;

@Service
@RequiredArgsConstructor
public class CategoryServiceBasic implements CategoryService, SitemapCreatable {
	private final CategoryDao categoryDao;
	private final PostService postService;
	private final CategoryRepository categoryRepository;

	public List<CategoryEntity> getList() {
		return categoryDao.selectList();
	}
	
	public CategoryEntity get(int categoryNo) {
		return categoryDao.select(categoryNo);
	}
	
	public Category get(String categoryId) {
		return categoryRepository.findByCategoryKey(categoryId);
	}
	
	public List<CategoryEntity> getCategoryAndChilds(String categoryId) {
		List<CategoryEntity> categories = new ArrayList<CategoryEntity>();
		
		CategoryEntity parentCategory = categoryDao.select(categoryId);
		
		if(parentCategory == null) {
			return null;
		}
		
		categories.add(parentCategory);
		
		List<CategoryEntity> childCategories = categoryDao.selectListByParentNo(parentCategory.getCategoryNo());
		
		categories.addAll(childCategories);
		
		return categories;
	}
	
	public void create(CategoryEntity category) {
		int categoryDepth = createCategoryDepth(category.getCategoryParentNo());
		category.setCategoryDepth(categoryDepth);
		
		categoryDao.insert(category);
	}
	
	public void modify(CategoryEntity category) {
		int categoryDepth = createCategoryDepth(category.getCategoryParentNo());
		category.setCategoryDepth(categoryDepth);
		
		categoryDao.update(category);
	}

	@Override
	public void remove(int categoryNo) {
		categoryDao.delete(categoryNo);
	}

//	@Transactional 추후에 적용
	@Override
	public void remove(String categoryId) {
		try {
			postService.removeCategoryId(categoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		categoryDao.delete(categoryId);
	}

	/**
	 * 카테고리 Depth만큼의 HTML을 만든다.
	 */
	public String getCategoryHTML(String contextPath) {
		String html = createCategoryHTML(null, contextPath, 1);
		
		return html;
	}
	
	private String createCategoryHTML(List<CategoryEntity> categoryList, String contextPath, int depth) {
		String html = "";
		
		if(depth == 1) {
			categoryList = categoryDao.selectListByParentNo(null);
		}
		
		if(categoryList.size() == 0) {
			return html;
		}
		
		html += "<ul class=\"category_list category_depth_" + depth++ + "\">";
		
		for(CategoryEntity category : categoryList) {
			int postCount = postService.getCountByCategoryId(category.getCategoryId(), "N");
			
			html += "<li>";
			html += "<a href=\"" + contextPath + "/category/" + category.getCategoryId() + "\"><span class=\"material-icons\">\r\n" + 
					"arrow_right\r\n" + 
					"</span>" + category.getCategoryName() + " <span class=\"category_post_count\">(" + postCount + ")</span></a>";
			
			List<CategoryEntity> childCategoryList = categoryDao.selectListByParentNo(category.getCategoryNo());
			html += this.createCategoryHTML(childCategoryList, contextPath, depth);
			
			html += "</li>";
		}
		
		html += "</ul>";
		
		return html;
	}
	
	private int createCategoryDepth(Integer parentCategryNo) {
		Integer categoryDepth = 0;
		
		if(parentCategryNo != null) {
			CategoryEntity parentCategory = categoryDao.select(parentCategryNo);
			categoryDepth = parentCategory.getCategoryDepth() + 1;
		}
		
		return categoryDepth;
	}

	@Override
	public List<SitemapDto> sitemap(String siteUrl) {
		List<SitemapDto> sitemapDtos = new ArrayList<>();

		List<CategoryEntity> categories = categoryDao.selectList();

		for(CategoryEntity category : categories) {
			SitemapDto sitemapDto = new SitemapDto();

			sitemapDto.setLoc(siteUrl + "/category/" + category.getCategoryId());
			sitemapDto.setLastmod(null);
			sitemapDto.setChangefreq("weekly");
			sitemapDto.setPriority(0.3);

			sitemapDtos.add(sitemapDto);
		}

		return sitemapDtos;
	}
}
