package net.kurien.blog.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.kurien.blog.exception.NotFoundDataException;
import net.kurien.blog.module.file.entity.File;
import net.kurien.blog.module.file.service.FileService;
import net.kurien.blog.util.FileUtil;

@Slf4j
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
	private final FileService fileService;

	@Value("${user.dir}")
	private String userDir;

	@RequestMapping("/viewer/{service}/{fileNo}")
	public void imageFileView(@PathVariable String service, @PathVariable int fileNo, HttpServletResponse response) throws NotFoundDataException, IOException {
		File file = fileService.get(fileNo);

		if(file == null) {
			throw new NotFoundDataException("파일이 업로드 되지 않았거나 삭제되었습니다.");
		}
		
		String uploadPath = userDir + "/files/" + service;
		String filePath = uploadPath + java.io.File.separator + file.getFileStoredName();

		FileUtil.view(filePath, response);
	}

	@RequestMapping("/download/{service}/{fileNo}")
	public void fileDownload(@PathVariable String service, @PathVariable int fileNo, HttpServletRequest request, HttpServletResponse response) throws NotFoundDataException, IOException {
		File file = fileService.get(fileNo);

		if(file == null) {
			throw new NotFoundDataException("파일이 업로드 되지 않았거나 삭제되었습니다.");
		}

		String uploadPath = userDir + "/files/" + service;
		String filePath = uploadPath + java.io.File.separator + file.getFileStoredName();

		FileUtil.download(filePath, file.getFileName(), request, response);
	}
}
