package com.server.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;
public interface FileUpload {
	//create a file on server at given
	String uploadFile(String path,MultipartFile file) throws Exception;
	
	//get the resource 
	InputStream getResource(String path) throws FileNotFoundException;
	
	//delete file
	void deleteFile(String file);

}
