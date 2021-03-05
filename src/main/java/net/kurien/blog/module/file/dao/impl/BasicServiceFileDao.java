package net.kurien.blog.module.file.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.kurien.blog.module.file.dao.ServiceFileDao;
import net.kurien.blog.module.file.entity.ServiceFile;

@Repository
public class BasicServiceFileDao implements ServiceFileDao {
	private final SqlSession sqlSession;
    private final static String mapper = "net.kurien.blog.module.file.mapper.ServiceFileMapper";

    @Autowired
    public BasicServiceFileDao(SqlSession sqlSession) {
    	this.sqlSession = sqlSession;
	}

	@Override
	public ServiceFile selectOne(String serviceName, Integer serviceNo, Integer fileNo) {
		Map<String, Object> param = new HashMap<>();
		param.put("serviceName", serviceName);
		param.put("serviceNo", serviceNo);
		param.put("fileNo", fileNo);

		return sqlSession.selectOne(mapper + ".selectOne", param);
	}

	@Override
	public List<ServiceFile> selectList(String serviceName, Integer serviceNo) {
		Map<String, Object> param = new HashMap<>();
		param.put("serviceName", serviceName);
		param.put("serviceNo", serviceNo);

		return sqlSession.selectList(mapper + ".selectList", param);
	}

	@Override
	public int selectCount(Integer fileNo) {
		Map<String, Object> param = new HashMap<>();
		param.put("fileNo", fileNo);

		return sqlSession.selectOne(mapper + ".selectCount", param);
	}

	@Override
	public int selectCount(String serviceName, Integer serviceNo) {
		Map<String, Object> param = new HashMap<>();
		param.put("serviceName", serviceName);
		param.put("serviceNo", serviceNo);

		return sqlSession.selectOne(mapper + ".selectCount", param);
	}

	@Override
	public void insert(ServiceFile serviceFile) {
		sqlSession.insert(mapper + ".insert", serviceFile);
	}

	@Override
	public void insertFiles(List<ServiceFile> serviceFiles) {
		sqlSession.insert(mapper + ".insertFiles", serviceFiles);
	}

	@Override
	public void delete(Integer fileNo) {
		Map<String, Object> param = new HashMap<>();
		param.put("fileNo", fileNo);

		sqlSession.delete(mapper + ".delete", param);
	}

	@Override
	public void delete(String serviceName, Integer serviceNo, Integer fileNo) {
		Map<String, Object> param = new HashMap<>();
		param.put("serviceName", serviceName);
		param.put("serviceNo", serviceNo);
		param.put("fileNo", fileNo);

		sqlSession.delete(mapper + ".delete", param);
	}

	@Override
	public void deleteList(String serviceName, Integer serviceNo, Integer[] fileNos) {
		Map<String, Object> param = new HashMap<>();
		param.put("serviceName", serviceName);
		param.put("serviceNo", serviceNo);
		param.put("fileNos", fileNos);

		sqlSession.delete(mapper + ".deleteList", param);
	}

	@Override
	public void deleteList(String serviceName, Integer serviceNo) {
		Map<String, Object> param = new HashMap<>();
		param.put("serviceName", serviceName);
		param.put("serviceNo", serviceNo);

		sqlSession.delete(mapper + ".deleteList", param);
	}

	@Override
	public void deleteList(String serviceName) {
		Map<String, Object> param = new HashMap<>();
		param.put("serviceName", serviceName);

		sqlSession.delete(mapper + ".deleteList", param);
	}

	@Override
	public void deleteAll() {
		sqlSession.delete(mapper + ".deleteAll");
	}
}
