package kosta.web.mvc.cotroller;



import kosta.web.mvc.dto.FileVO2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller

public class UploadController2 {
	@RequestMapping("/uploadForm2")
	public void formFile() {	   
	}
	@RequestMapping("/upload2")
	public ModelAndView saveFile(FileVO2 vo) {
	     System.out.println(vo.getUploadFiles().length+"개가 업로드 됨");
	     String resultStr = "";
	     //String path = "/uploadtest/multi";
		String path ="C:\\uploadtest/multi";
		 File isDir = new File(path);
		 if (!isDir.isDirectory()) {
			isDir.mkdirs();
		 }
	     ModelAndView mav = new ModelAndView();
	     mav.setViewName("uploadForm2");
	     
	     for (MultipartFile mfile : vo.getUploadFiles()) {
				String fileName = mfile.getOriginalFilename();
				try {
					File f = new File("C:\\uploadtest/multi/" + fileName);
					if (f.exists()) {
						resultStr += fileName + " : 파일이 이미 존재해요!!<br>";
					} else {
						mfile.transferTo(f);
						resultStr += fileName + " : 파일이 저장되었어요!!<br>";
					}
				} catch (IOException e) {
					e.printStackTrace();
					resultStr += fileName + " : 오류가 발생했어요!!";				
				}
		  }	   
	     mav.addObject("msg", resultStr);	
	     return mav;
	}
}

