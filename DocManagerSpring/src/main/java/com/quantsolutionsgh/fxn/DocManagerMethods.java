package com.quantsolutionsgh.fxn;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quantsolutionsgh.model.IUploadedFile;

@Service
public class DocManagerMethods extends JdbcDaoSupport implements IDocManagerMethods {

	
	@Value( "${docman.storagePath}" )
	private String storagePath;
	
	
	
	
	

	
	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}
	
	@Autowired
	public DocManagerMethods(DataSource dataSource){
		this.setDataSource(dataSource);
		
	}

	@Override
	@Transactional
	public String getDocRef() {
		
		String sql = "select max(doc_ref)+1 from sequencer";
		int docRef = this.getJdbcTemplate().queryForObject(sql, Integer.class);
		this.getJdbcTemplate().update("update sequencer set doc_ref=?", docRef);
		return String.valueOf(docRef);
	}

	@Override
	public void uploadDocument(IUploadedFile doc, String docRef) throws IOException {

		if (doc.getFileName().toUpperCase().contains(".PDF")) {
			uploadPDF(doc, docRef);   //take photos of pdf pages as images

			//uploading original PDF as well here

			uploadPic(doc,docRef);

		} else
			uploadPic(doc, docRef);

		// uploadFile(item);
		// this.storeInDb(docRef, item.getFileName());
	}

	private void uploadPic(IUploadedFile doc, String docRef) throws IOException {
		String filename = doc.getFileName();
		InputStream input = doc.getInputstream();
		OutputStream output = new FileOutputStream(new File(storagePath, filename));

		try {
			IOUtils.copy(input, output);
			this.storeInDb(docRef, doc.getFileName());
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}

		
	}

	
	@Transactional
	private void storeInDb(String docRef, String fileName) {
		String sql = "insert into tbl_main(doc_ref,file_name) values (?,?)";
		this.getJdbcTemplate().update(sql, new Object[] { docRef, fileName });
		
	}

	private void uploadPDF(IUploadedFile doc, String docRef) throws IOException {
PDDocument document = PDDocument.load(doc.getInputstream());
		
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		int pageCounter = 0;
		for (PDPage page : document.getPages()) {
			// note that the page number parameter is zero based
			BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, 300, ImageType.RGB);
			
			
			String fileName = doc.getFileName() + "-" + (pageCounter++) + ".jpeg";
			OutputStream output = new FileOutputStream(new File(storagePath, fileName));
			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
			if (!writers.hasNext())
				throw new IllegalStateException("No writers found");
			ImageWriter writer = (ImageWriter) writers.next();
			
			ImageOutputStream ios = ImageIO.createImageOutputStream(output);
			
			writer.setOutput(ios);
			
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(0.1f);
			writer.write(null, new IIOImage(bim, null, null), param);
			 this.storeInDb(docRef, fileName);
			
			
			output.close();
			ios.close();
			writer.dispose();
			
			
			

		
			// suffix in filename will be used as the file format
		//	String fileName = _file.getFileName() + "-" + (pageCounter++) + ".png";
		//	OutputStream output = new FileOutputStream(new File(storagePath, fileName));
			
		//	ImageIOUtil.writeImage(bim, "jpg", output, 300);
			// this.storeInDb(docRef, item.getFileName());
			
			

			

		}
		document.close();
		
	}

	

	@Override
	public List<String> getDocs(String docRef) {
		// TODO Auto-generated method stub
		String sql = "select file_name from tbl_main where doc_ref=?";
		List<String> fileList = new ArrayList<String>();
		List<Map<String, Object>> rows = this.getJdbcTemplate().queryForList(sql, docRef);

		for (Map row : rows) {

			fileList.add((String) row.get("file_name"));

		}
		return fileList;
	}

	

}
