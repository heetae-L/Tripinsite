package project.com.tripinsite.controller;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import project.com.tripinsite.helper.UploadHelper;
import project.com.tripinsite.helper.WebHelper;

/**
 * Servlet implementation class Download
 */
@Controller
public class Download {

	/** 사용하고자 하는 Helper + Service 객체 선언 */
	protected static Logger logger = LoggerFactory.getLogger("Download");

	@Autowired
	WebHelper web;
	@Autowired
	UploadHelper upload;

	/**
	 * 파일 다운로드 처리를 수행할 컨트롤러 메서드. 다운로드할 파일의 경로를 파라미터로 받는다.
	 */
	@RequestMapping(value = "/download.do", method = RequestMethod.GET)
	public ModelAndView download(Locale locale, Model model) {
		
		// WebHelper를 통해서 다운로드할 파일의 경로를 받는다.
		web.init();
		String filePath = web.getString("file");
		
		// 생성할 썸네일 이미지의 해상도.
		int width = web.getInt("width");
		int height = web.getInt("height");
		
		// 크롭여부 
		String crop = web.getString("crop", "Y");
		boolean isCrop = true;
		
		if (!crop.equals("Y")) {
			isCrop = false;
		}
		
		// 파라미터로 전달된 파일경로를 model에 등록한다.
		model.addAttribute("filePath", filePath);
		
		// 파일데이터를 View로 구성하기 위한 객체를 생성한다.
		DownloadView view = new DownloadView(filePath, null, width, height, isCrop);
		
		// 가상의 View 리턴하기 --> 전달된 객체의 renderMergedOutputModel()이 호출된다.
		return new ModelAndView(view);
	}
	
	/**
	 * 다운로드를 위한 스트림 출력 처리를 담당하는 가상의 View를 생성하기 위한 클래스
	 */
	public class DownloadView extends AbstractView {

		private String filePath;		// 다운로드할 파일의 서버상 경로
		private String originName;		// 원본 파일 이름
		private int width = 0;			// (이미지) 리사이즈 할 가로 크기
		private int height = 0;			// (이미지) 리사이즈 할 세로 크기
		private boolean isCrop = true;	// 크롭 여부

		public DownloadView(String filePath, String originName, int width, int height, 
				boolean isCrop) {
			this.filePath = filePath;
			this.originName = originName;
			this.width = width;
			this.height = height;
			this.isCrop = isCrop;
		}

		@Override
		protected void renderMergedOutputModel(Map<String, Object> arg0, HttpServletRequest arg1,
				HttpServletResponse arg2) throws Exception {
			
			// 파일 경로명이 존재한다면?
			if (filePath != null) {
				File f = new File(filePath);
				
				if (f.exists()) {
					// 썸네일을 생성하기 위한 해상도가 지정되었다면?
					if  (width > 0 || height > 0) {
						upload.printFileStream(filePath, width, height, isCrop);
					} else {
						upload.printFileStream(filePath, originName);
					}
				}
			} // end if
			
		}
	} // end class

}
