package com.server.ecommerce.service.implementation;

import com.server.ecommerce.service.FileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadImp implements FileUpload{
	@Override
	public String uploadFile(String path, MultipartFile file) throws IOException{
		String fullPath;
		String originalFilename = file.getOriginalFilename();

		String RandomImageName=UUID.randomUUID().toString();
		String RandomImageNameWithExtenstion= RandomImageName.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
		          //fullPath=path+File.separator+originalFilename;
		fullPath=path+File.separator+RandomImageNameWithExtenstion;
		File folderFile=new File(path);
		     
		if(!folderFile.exists()) {
			folderFile.mkdirs();
		}
		Files.copy(file.getInputStream(),Paths.get(fullPath));
		return RandomImageNameWithExtenstion;
    }

	

	@Override
	public InputStream getResource(String path) throws FileNotFoundException {
		InputStream is=new FileInputStream(path);
		return is;
	}

	@Override
	public void deleteFile(String file) {
		// TODO Auto-generated method stub
	}
}
