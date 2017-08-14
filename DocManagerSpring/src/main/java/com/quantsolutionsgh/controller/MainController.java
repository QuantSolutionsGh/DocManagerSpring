package com.quantsolutionsgh.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Img;
import com.hp.gagawa.java.elements.Strong;
import com.quantsolutionsgh.fxn.IDocManagerMethods;
import com.quantsolutionsgh.model.IUploadedFile;
import com.quantsolutionsgh.model.StringResponse;
import com.quantsolutionsgh.model.UploadedFile;

@Controller
@CrossOrigin
public class MainController {

	private IDocManagerMethods docMan;
	
	private IDocManagerMethods sigVer;
	

	@Autowired
	@Qualifier("ISSDocMethods")
	public void setSigVer(IDocManagerMethods sigVer) {
		this.sigVer = sigVer;
	}

	@Autowired
	@Qualifier("docManagerMethods")
	public void setDocMan(IDocManagerMethods docMan) {
		this.docMan = docMan;
	}

	@RequestMapping(value = {"/index","/"})

	public String showMain() {
		return "index";
	}
	
	
	
	

	@RequestMapping(value = "/viewer", method = RequestMethod.GET)
	public String showViewer() {
		return "viewer";
	}

	@RequestMapping(value = "/upload")
	@ResponseBody
	public StringResponse uploadDocs(MultipartHttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String docRef = docMan.getDocRef();

		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		while (itr.hasNext()) {
			mpf = request.getFile(itr.next());

			IUploadedFile upf = new UploadedFile(mpf);

			docMan.uploadDocument(upf, docRef);

			// System.out.println(mpf.getOriginalFilename() + " uploaded");
			//Thread.sleep(4000);
		}

		StringResponse result = new StringResponse("Your document reference # is " + "<h1>"+docRef+"</h1>");

		return result;
	}
	
	@RequestMapping(value = "/getdocs", method = RequestMethod.GET)
	public ModelAndView getDocs(@RequestParam("docRef") String docRef,
			@RequestParam("sourceSystem") String sourceSystem,@RequestParam(value="opt",required=false) String opt) {
		String imageDir = null;

		List<String> al = new ArrayList<String>();
		
		if (sourceSystem.contains("JB")) {
			al = this.docMan.getDocs(docRef);
			imageDir = "pictures/";
		} else if (sourceSystem.contains("SV")) {
			al = this.sigVer.getDocs(docRef);
			imageDir = "sigver/" + docRef + "/";
		}
		
		ModelAndView modelAndView = new ModelAndView("retrievedDocs");
		modelAndView.addObject("docList", al);
		modelAndView.addObject("imageDir",imageDir);
		modelAndView.addObject("opt",opt);

		return modelAndView;
	}
	

	@RequestMapping(value = "/dummy", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String dummy(@RequestParam("docRef") String docRef, @RequestParam("sourceSystem") String sourceSystem) {

		String imageDir=null;
		
		List<String> al = new ArrayList<String>();
		StringBuilder z = new StringBuilder();
		if (sourceSystem.contains("JB")) {
			al = this.docMan.getDocs(docRef);
			imageDir="pictures/";
		}else if (sourceSystem.contains("SV")){
			al=this.sigVer.getDocs(docRef);
			imageDir="sigver/"+docRef+"/";
		}

		
		//header search results
		Div myDiva = new Div().setCSSClass("alert alert-success");
		Strong myStronga= new Strong().appendText("Search Results");
		myDiva.appendChild(myStronga);
		z.append(myDiva.write());
		
		//end header search results
		
		
		for (String a : al) {
			
			
			Div div = new Div();
			div.setCSSClass("col-md-4");
			Div div2 = new Div();
			div2.setCSSClass("thumbnail");
			A ab = new A();
			ab.setHref(imageDir + a);
			// ab.setAttribute("onclick",
			// "window.open('/spring-mvc/pictures/"+a+, '#{imageFile}',
			// 'width=2479,height=3504'); return false;");
			Img image = new Img(a, imageDir + a);
			// image.setWidth("304");
			// image.setHeight("236");
			image.setStyle("width:100%");
			image.setCSSClass("img-responsive");
			div.appendChild(div2.appendChild(ab.appendChild(image)));

			z.append(div.write());

		}

		if (al.isEmpty()) {
			Div myDiv = new Div().setCSSClass("alert alert-warning");
			Strong myStrong= new Strong().appendText("No data returned");
			myDiv.appendChild(myStrong);
			//return "<p>No data returned from the the search query</p>";
			return myDiv.write();

		} else {
			
			return z.toString();
		}

	}

}
