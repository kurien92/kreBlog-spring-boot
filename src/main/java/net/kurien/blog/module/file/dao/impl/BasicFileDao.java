package net.kurien.blog.module.file.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.kurien.blog.module.file.dao.FileDao;
import net.kurien.blog.module.file.entity.File;

@Repository
public class BasicFileDao implements FileDao {
	private final SqlSession sqlSession;
    private final static String mapper = "net.kurien.blog.module.file.mapper.FileMapper";

    @Autowired
    public BasicFileDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<File> selectList(List<Integer> fileNos) {
        return sqlSession.selectList(mapper + ".selectList", fileNos);
    }

    @Override
    public File selectOne(int fileNo) {
        return sqlSession.selectOne(mapper + ".selectOne", fileNo);
    }

    @Override
    public int selectCount(Integer fileNo) {
        return sqlSession.selectOne(mapper + ".selectCount", fileNo);
    }

	@Override
	public boolean isExistFilename(String randomizeString) {
		int isCount = sqlSession.selectOne(mapper + ".isExistFilename", randomizeString);
		boolean isExist = isCount > 0;
		
		return isExist;
	}
	
    @Override
    public void insert(File file) {
        sqlSession.insert(mapper + ".insert", file);
    }

    @Override
    public void delete(int fileNo) {
        sqlSession.delete(mapper + ".delete", fileNo);
    }

    @Override
    public void deleteList(List<Integer> fileNos) {
        sqlSession.delete(mapper + ".deleteList", fileNos);
    }

    @Override
    public void deleteAll() {
        sqlSession.delete(mapper + ".deleteAll");
    }
}