package net.kurien.blog.module.post.dao;

import java.util.List;

import net.kurien.blog.domain.SearchCriteria;
import net.kurien.blog.module.post.entity.PostEntity;

public interface PostDao {
	List<PostEntity> selectList(String manageYn);
	List<PostEntity> selectList(String manageYn, SearchCriteria criteria);
	PostEntity selectOne(int postNo, String manageYn);
	List<PostEntity> selectListByCategoryIds(List<String> categoryIds, String manageYn, SearchCriteria criteria);
	int selectCount(String manageYn);
	int selectCountByCategoryId(String categoryId, String manageYn, SearchCriteria criteria);
	int selectCountByCategoryIds(List<String> categoryIds, String manageYn, SearchCriteria criteria);
	void insert(PostEntity post);
	void update(PostEntity post);
	void delete(int postNo);
	void deleteList(List<Integer> postNos);
	void deleteAll();
	int isExist(int postNo, String manageYn);
	int removeCategoryId(String categoryId);
    List<PostEntity> search(String[] queries);
}
