package com.quantsolutionsgh.model;

import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class UploadedFile implements IUploadedFile {
	
	private MultipartFile _mpf;

	private String hackedFileName;
	
	public UploadedFile(){
		
	}
	
	public UploadedFile(MultipartFile mpf){
		
		_mpf=mpf;


		Format formatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		String s = formatter.format(new Date());


		hackedFileName = s +"_"+mpf.getOriginalFilename();


		
	}



	@Override
	public String getFileName() {
		return hackedFileName;
		//return _mpf.getOriginalFilename();
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
