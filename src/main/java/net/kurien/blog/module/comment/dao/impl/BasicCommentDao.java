package net.kurien.blog.module.comment.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.kurien.blog.module.comment.dao.CommentDao;
import net.kurien.blog.module.comment.entity.Comment;

@Repository
public class BasicCommentDao implements CommentDao {
	private final SqlSession sqlSession;
	private final static String mapper = "net.kurien.blog.module.comment.mapper.CommentMapper";

	@Autowired
	public BasicCommentDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<Comment> selectList() {
		return sqlSession.selectList(mapper + ".selectList");
	}
	
	@Override
	public List<Comment> selectListByPostNo(int postNo) {
		return sqlSession.selectList(mapper + ".selectListByPostNo", postNo);
	}

	@Override
	public Comment selectOne(int commentNo) {
		return sqlSession.selectOne(mapper + ".selectOne", commentNo);
	}

	@Override
	public Integer getLastOrder(int parentCommentNo, int commentDepth) {
		Map<String, Object> param = new HashMap<>();
		param.put("parentCommentNo", parentCommentNo);
		param.put("commentDepth", commentDepth);

		return sqlSession.selectOne(mapper + ".getLastOrder", param);
	}

	@Override
	public void insert(Comment comment) {
		sqlSession.insert(mapper + ".insert", comment);
	}

	@Override
	public void update(Comment comment) {
		sqlSession.update(mapper + ".update", comment);
	}

	@Override
	public void delete(int commentNo) {
		sqlSession.delete(mapper + ".delete", commentNo);
	}

	@Override
	public List<Comment> search(String[] queries) {
		String[] searchColumns = new String[]{"author", "comment"};

		Map<String, Object> param = new HashMap<>();
		param.put("searchColumns", searchColumns);
		param.put("searchQueries", queries);

		return sqlSession.selectList(mapper + ".search", param);
	}
}
