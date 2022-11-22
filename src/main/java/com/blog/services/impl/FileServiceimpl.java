package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.FileService;

@Service
public class FileServiceimpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//File Name
		
		
		
		String fname=file.getOriginalFilename();
		
String randomId=UUID.randomUUID().toString();
		
String newfilename=randomId.concat(fname.substring(fname.lastIndexOf(".")));
		
		//Full Path
		
		String fpath=path+File.separator+newfilename;
		
		
		
		
		
		//Create folder if not created
		
		File file1=new File(fpath);
		
		if(!file1.exists())
		{
			file1.mkdir();
		}
		 
		
		//file copy
		Files.copy(file.getInputStream(), Paths.get(fpath));
		
		
		
		
		return newfilename;
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
		
		String fullPath=path+File.separator+filename;
		InputStream IS=new FileInputStream(fullPath);
		
		
		return IS;
	}

}
