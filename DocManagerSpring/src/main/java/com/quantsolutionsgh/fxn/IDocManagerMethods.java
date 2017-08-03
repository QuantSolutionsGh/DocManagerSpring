package com.quantsolutionsgh.fxn;

import java.util.List;

import com.quantsolutionsgh.model.IUploadedFile;

public interface IDocManagerMethods {
	
	public String getDocRef() throws Exception;
	
	public void uploadDocument(IUploadedFile doc, String docRef) throws Exception;
	
	public List<String> getDocs(String docRef);
	
	

}
