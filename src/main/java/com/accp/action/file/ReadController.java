package com.accp.action.file;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accp.util.file.Reader;
import com.accp.util.file.Upload;

@RestController
@RequestMapping("/api")
public class ReadController {
	private ResourceLoader resourceLoader;

	@Autowired
	public ReadController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@GetMapping(value = "/img/**", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> img(HttpServletRequest request) {
		String img = request.getRequestURL().toString().split(Reader.IMG_VISIT_SUFFIX)[1];
		try {
			return ResponseEntity.ok(resourceLoader.getResource("file:" + Upload.UPLOADED_FOLDER + img));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/file/**", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> file(HttpServletRequest request) throws IOException {
		String file = request.getRequestURL().toString().split(Reader.FILE_VISIT_SUFFIX)[1];
		File download = new File(Upload.UPLOADED_FOLDER + file);
		byte[] bytes = Reader.readFileToBytes(download);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", download.getName());
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
}