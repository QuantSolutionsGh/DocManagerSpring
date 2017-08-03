package com.quantsolutionsgh.model;

import java.io.IOException;
import java.io.InputStream;

public interface IUploadedFile {
	
public String getFileName();
	
	public InputStream getInputstream() throws IOException;
	
	public long getSize();
	
	public byte[] getContents() throws IOException;
	
	public String getContentType();
    
    
}
