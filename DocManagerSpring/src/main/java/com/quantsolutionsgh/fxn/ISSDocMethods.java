package com.quantsolutionsgh.fxn;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.quantsolutionsgh.model.IUploadedFile;

@Service
public class ISSDocMethods implements IDocManagerMethods {

	
	private String storagePath;

	@Value( "${docman.sigverPath}" )
	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	@Override
	public String getDocRef() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void uploadDocument(IUploadedFile doc, String docRef) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getDocs(String docRef) {
		List<String> imageList = new ArrayList<String>();

		File dir = new File(this.storagePath + File.separatorChar + docRef);

		if (dir.isDirectory()) {
			for (File f : dir.listFiles()) {

				if (!f.getName().contains("Thumbs.db")) {
					imageList.add(f.getName());

				}

			}
		}

		return imageList;
	}

}
