package net.kurien.blog.module.post.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.kurien.blog.domain.SearchCriteria;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.kurien.blog.module.post.dao.PostDao;
import net.kurien.blog.module.post.entity.Post;

@Repository
public class BasicPostDao implements PostDao {
	private final SqlSession sqlSession;
	private final static String mapper = "net.kurien.blog.module.post.mapper.PostMapper";

	@Autowired
	public BasicPostDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public List<Post> selectList(String manageYn, SearchCriteria criteria) {
		Map<String, Object> param = new HashMap<>();
		param.put("manageYn", manageYn);
		param.put("criteria", criteria);
		
		return sqlSession.selectList(mapper + ".selectList", param);
	}

	@Override
	public List<Post> selectListByCategoryIds(List<String> categoryIds, String manageYn, SearchCriteria criteria) {
		Map<String, Object> param = new HashMap<>();
		param.put("categoryIds", categoryIds);
		param.put("manageYn", manageYn);
		param.put("criteria", criteria);
		
		return sqlSession.selectList(mapper + ".selectListByCategoryIds", param);
	}

	@Override
	public List<Post> selectList(String manageYn) {
		Map<String, Object> param = new HashMap<>();
		param.put("manageYn", manageYn);
		
		return sqlSession.selectList(mapper + ".selectList", param);
	}
	
	@Override
	public int selectCount(String manageYn) {
		Map<String, Object> param = new HashMap<>();
		param.put("manageYn", manageYn);
		
		return sqlSession.selectOne(mapper + ".selectCount", param);		
	}
	
	@Override
	public Post selectOne(int postNo, String manageYn) {
		Map<String, Object> param = new HashMap<>();
		param.put("postNo", postNo);
		param.put("manageYn", manageYn);
		
		return sqlSession.selectOne(mapper + ".selectOne", param);
	}

	@Override
	public void insert(Post post) {
		sqlSession.insert(mapper + ".insert", post);
	}

	@Override
	public void update(Post post) {
		sqlSession.update(mapper + ".update", post);
	}

	@Override
	public void delete(int postNo) {
		sqlSession.delete(mapper + ".delete", postNo);
	}

	@Override
	public void deleteList(List<Integer> postNos) {
		sqlSession.delete(mapper + ".deleteList", postNos);
	}

	@Override
	public void deleteAll() {
		sqlSession.delete(mapper + ".deleteAll");
	}
	
	@Override
	public int removeCategoryId(String categoryId) {
		return sqlSession.update(mapper + ".removeCategoryId", categoryId);
	}

	@Override
	public int isExist(int postNo, String manageYn) {
		Map<String, Object> param = new HashMap<>();
		param.put("postNo", postNo);
		param.put("manageYn", manageYn);
		
		return sqlSession.selectOne(mapper + ".isExist", param);
	}

	@Override
	public int selectCountByCategoryId(String categoryId, String manageYn, SearchCriteria criteria) {
		Map<String, Object> param = new HashMap<>();
		param.put("categoryId", categoryId);
		param.put("manageYn", manageYn);
		param.put("criteria", criteria);
		
		return sqlSession.selectOne(mapper + ".selectCountByCategoryId", param);
	}

	@Override
	public int selectCountByCategoryIds(List<String> categoryIds, String manageYn, SearchCriteria criteria) {
		Map<String, Object> param = new HashMap<>();
		param.put("categoryIds", categoryIds);
		param.put("manageYn", manageYn);
		param.put("criteria", criteria);

		return sqlSession.selectOne(mapper + ".selectCountByCategoryIds", param);
	}

	@Override
	public List<Post> search(String[] queries) {
		String[] searchColumns = new String[]{"postSubject", "postContent"};

		Map<String, Object> param = new HashMap<>();
		param.put("searchColumns", searchColumns);
		param.put("manageYn", "N");
		param.put("searchQueries", queries);

		return sqlSession.selectList(mapper + ".search", param);
	}
}