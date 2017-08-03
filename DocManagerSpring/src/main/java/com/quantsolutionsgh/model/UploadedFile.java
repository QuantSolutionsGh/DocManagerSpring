package com.quantsolutionsgh.model;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class UploadedFile implements IUploadedFile {
	
	private MultipartFile _mpf;
	
	public UploadedFile(){
		
	}
	
	public UploadedFile(MultipartFile mpf){
		
		_mpf=mpf;
		
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return _mpf.getOriginalFilename();
	}

	@Override
	public InputStream getInputstream() throws IOException {
		// TODO Auto-generated method stub
		return _mpf.getInputStream();
	}

	@Override
	public long getSize() {
		// TODO Auto-generated method stub
		return _mpf.getSize();
	}

	@Override
	public byte[] getContents() throws IOException {
		return _mpf.getBytes();
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return _mpf.getContentType();
	}

	

}
