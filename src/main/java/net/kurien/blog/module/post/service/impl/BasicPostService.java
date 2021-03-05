package net.kurien.blog.module.post.service.impl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.kurien.blog.domain.SearchCriteria;
import net.kurien.blog.module.search.dto.SearchDto;
import net.kurien.blog.module.search.dto.Searchable;
import net.kurien.blog.module.sitemap.SitemapCreatable;
import net.kurien.blog.module.sitemap.SitemapDto;
import net.kurien.blog.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kurien.blog.exception.DuplicatedKeyException;
import net.kurien.blog.exception.EmptyParameterException;
import net.kurien.blog.exception.NotFoundDataException;
import net.kurien.blog.exception.NotUsePrimaryKeyException;
import net.kurien.blog.module.file.service.ServiceFileService;
import net.kurien.blog.module.post.dao.PostDao;
import net.kurien.blog.module.post.entity.Post;
import net.kurien.blog.module.post.service.PostService;

@Service
public class BasicPostService implements PostService, Searchable, SitemapCreatable {
	private final PostDao postDao;
	private final ServiceFileService serviceFileService;

	@Autowired
	public BasicPostService(PostDao postDao, ServiceFileService serviceFileService) {
		this.postDao = postDao;
		this.serviceFileService = serviceFileService;
	}

	@Override
	public List<Post> getList(String manageYn) {
		return postDao.selectList(manageYn);
	}
	
	@Override
	public List<Post> getList(String manageYn, SearchCriteria criteria) {
		return postDao.selectList(manageYn, criteria);
	}

	@Override
	public List<Post> getListByCategoryIds(List<String> categoryIds, String manageYn, SearchCriteria criteria) {
		return postDao.selectListByCategoryIds(categoryIds, manageYn, criteria);
	}
	
	@Override
	public int getCount(String manageYn) {
		return postDao.selectCount(manageYn);
	}

	@Override
	public int getCountByCategoryId(String categoryId, String manageYn) {
		return postDao.selectCountByCategoryId(categoryId, manageYn, null);
	}

	@Override
	public int getCountByCategoryIds(List<String> categoryIds, String manageYn, SearchCriteria criteria) {
		return postDao.selectCountByCategoryIds(categoryIds, manageYn, criteria);
	}
	
	@Override
	public Post get(int postNo, String manageYn) throws Exception {
		if(!isExist(postNo, manageYn)) {
			throw new NotFoundDataException(postNo + "번 포스트를 찾을 수 없습니다.");
		}

		return postDao.selectOne(postNo, manageYn);
	}

	@Override
	public void write(Post post, Integer[] fileNos) throws Exception {
		if(post.getPostNo() != null) {
			if(post.getPostNo() == 0) {
				throw new NotUsePrimaryKeyException("작성될 포스트의 번호가 잘못 입력되었습니다.");
			}
			
			if(isExist(post.getPostNo(), "Y")) {
				throw new DuplicatedKeyException(post.getPostNo() + "번 포스트가 이미 존재합니다.");
			}
		}

		post.setPostWriteTime(TimeUtil.currentTime());

		postDao.insert(post);

		addServiceFiles(post, fileNos);
	}

	@Override
	public void modify(Post post, Integer[] fileNos) throws Exception {
		if(post.getPostNo() == null || post.getPostNo() == 0) {
			throw new NotUsePrimaryKeyException("수정될 포스트의 번호가 입력되지 않았습니다.");
		}
		
		if(!isExist(post.getPostNo(), "Y")) {
			throw new NotFoundDataException(post.getPostNo() + "번 포스트를 찾을 수 없습니다.");
		}

		postDao.update(post);

		addServiceFiles(post, fileNos);
	}

	@Override
	public void delete(int postNo) {
		serviceFileService.removeFiles("post", postNo);
		postDao.delete(postNo);
	}

	@Override
	public void deleteList(List<Integer> postNos) throws EmptyParameterException {
		if(postNos == null || postNos.size() == 0) {
			throw new EmptyParameterException("빈 데이터가 전달되었습니다.");
		}
		
		postDao.deleteList(postNos);
	}

	@Override
	public void deleteAll() {
		postDao.deleteAll();
	}

	@Override
	public boolean isExist(int postNo, String manageYn) throws Exception {
		int count = postDao.isExist(postNo, manageYn);

		if(count < 1) {
			return false;
		}

		return true;
	}

	@Override
	public int removeCategoryId(String categoryId) {
		return postDao.removeCategoryId(categoryId);
	}

	private void addServiceFiles(Post post, Integer[] fileNos) {
		if(fileNos != null) {
			serviceFileService.addFiles("post", post.getPostNo(), fileNos, post.getPostWriteIp());
		}

		Set<Integer> useFilesNo = new HashSet<>();

		List<Pattern> patterns = new ArrayList<>();

		patterns.add(Pattern.compile("['|\"]/file/viewer/post/(\\d+?)['|\"]"));
		patterns.add(Pattern.compile("['|\"]/file/download/post/(\\d+?)['|\"]"));

		for(Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(post.getPostContent());

			while(matcher.find()) {
				useFilesNo.add(Integer.parseInt(matcher.group(1)));
			}
		}

		serviceFileService.syncFiles("post", post.getPostNo(), useFilesNo, post.getPostWriteIp());
	}

	@Override
	public SearchDto search(String[] queries) {
		List<Map<String, Object>> contents = new ArrayList<>();
		SearchDto searchDto = new SearchDto();

		List<Post> posts = postDao.search(queries);

		for(Post post : posts) {
			Map<String, Object> content = new LinkedHashMap<>();

			content.put("id", post.getPostNo());
			content.put("name", post.getPostAuthor());
			content.put("title", post.getPostSubject());
			content.put("description", post.getPostContent());

			contents.add(content);
		}

		searchDto.setTitle("POST");
		searchDto.setContents(contents);

		return searchDto;
	}

	@Override
	public List<SitemapDto> sitemap(String siteUrl) {
		List<SitemapDto> sitemapDtos = new ArrayList<>();

		List<Post> posts = postDao.selectList("N");

		for(Post post : posts) {
			SitemapDto sitemapDto = new SitemapDto();

			sitemapDto.setLoc(siteUrl + "/post/view/" + post.getPostNo());
			sitemapDto.setLastmod(post.getPostWriteTime());
			sitemapDto.setChangefreq("daily");
			sitemapDto.setPriority(0.5);

			sitemapDtos.add(sitemapDto);
		}

		return sitemapDtos;
	}
}