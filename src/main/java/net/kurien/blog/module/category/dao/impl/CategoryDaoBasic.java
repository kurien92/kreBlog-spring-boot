package net.kurien.blog.module.category.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.kurien.blog.module.category.dao.CategoryDao;
import net.kurien.blog.module.category.entity.CategoryEntity;

@Repository
public class CategoryDaoBasic implements CategoryDao {
	private final SqlSession sqlSession;
	private final static String mapper = "net.kurien.blog.module.category.mapper.CategoryMapper";

	@Autowired
	public CategoryDaoBasic(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<CategoryEntity> selectList() {
		return sqlSession.selectList(mapper + ".selectList");
	}

	@Override
	public List<CategoryEntity> selectListByParentNo(Integer categoryParentNo) {
		// Integer를 파라미터로 사용하는 경우에는 Mapper에서 변수명을 value로 사용해야한다.
		// value로 사용하는 경우 가독성이 떨어질 것으로 생각되어 Map에 추가하여 파라미터 이름을 지정.
		Map<String, Object> param = new HashMap<>();
		param.put("categoryParentNo", categoryParentNo);
		
		return sqlSession.selectList(mapper + ".selectListByParentNo", param);
	}
	
	@Override
	public CategoryEntity select(int categoryNo) {
		return sqlSession.selectOne(mapper + ".selectOneByNo", categoryNo);
	}
	
	@Override
	public CategoryEntity select(String categoryId) {
		return sqlSession.selectOne(mapper + ".selectOneById", categoryId);
	}
	
	@Override
	public void insert(CategoryEntity category) {
		sqlSession.insert(mapper + ".insert", category);
	}

	@Override
	public void update(CategoryEntity category) {
		sqlSession.update(mapper + ".update", category);
	}

	@Override
	public void delete(int categoryNo) {
		sqlSession.delete(mapper + ".delete", categoryNo);
	}

	@Override
	public void delete(String categoryId) {
		sqlSession.delete(mapper + ".delete", categoryId);
	}
}
