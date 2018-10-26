package com.accp.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.accp.util.file.Reader;
import com.accp.util.file.Upload;

@SuppressWarnings("deprecation")
@Configuration
public class ResourceConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(Reader.VISIT_FOLDER + "**").addResourceLocations("file:" + Upload.UPLOADED_FOLDER);
	}
}