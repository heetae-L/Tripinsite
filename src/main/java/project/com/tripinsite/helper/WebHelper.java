package project.com.tripinsite.helper;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebHelper {
	/** 쿠키에서 사용할 도메인 */
	private static final String DOMAIN = "localhost";

	/** JSP의 request 내장 객체 */
	// --> import javax.servlet.http.HttpServletRequest;
	private HttpServletRequest request;

	/** JSP의 response 내장 객체 */
	// --> import javax.servlet.http.HttpServletResponse;
	private HttpServletResponse response;

	/** JSP의 out 내장 객체 */
	// --> import java.io.PrintWriter;
	private PrintWriter out;

	/** JSP의 session 내장 객체 */
	// --> import javax.servlet.http.HttpSession;
	private HttpSession session;

	/**
	 * WebHelper 기능을 초기화 한다. Spring이 제공하는 ServletRequestAttributes 객체를 통해서 request,
	 * response객체를 직접 생성할 수 있다.
	 */
	public void init() {
		/** JSP 내장객체를 담고 있는 Spring의 객체를 통해서 내장 객체를 획득하기 */
		ServletRequestAttributes requestAttr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		// response와 request 객체를 추출한다.
		this.request = requestAttr.getRequest();
		this.response = requestAttr.getResponse();

		// 세션객체 생성하기
		this.session = request.getSession();

		// 페이지 이동 없이 세션이 유지되는 시간 설정 (초)
		// --> 24시간
		this.session.setMaxInactiveInterval(60 * 60 * 24);

		/** 내장객체 초기화 -> utf-8 설정, out객체 생성 */
		try {
			// 인코딩 설정하기
			this.request.setCharacterEncoding("utf-8");
			this.response.setCharacterEncoding("utf-8");
			// out객체 생성하기
			this.out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 메시지 표시 후, 페이지를 지정된 곳으로 이동한다.
	 *
	 * @param url - 이동할 페이지의 URL, Null일 경우 이전페이지로 이동
	 * @param msg - 화면에 표시할 메시지. Null일 경우 표시 안함
	 */
	public ModelAndView redirect(String url, String msg) {
		// 가상의 View로 만들기 위한 HTML 태그 구성
		String html = "<!doctype html>";
		html += "<html>";
		html += "<head>";
		html += "<meta charset='utf-8'>";

		// 메시지 표시
		if (msg != null) {
			html += "<script type='text/javascript'>alert('" + msg + "');</script>";
		}

		// 페이지 이동
		if (url != null) {
			html += "<meta http-equiv='refresh' content='0; url=" + url + "' />";
		} else {
			html += "<script type='text/javascript'>history.back();</script>";
		}

		html += "</head>";
		html += "<body></body>";
		html += "</html>";

		// 구성된 HTML을 출력한다.
		// out.print(html);

		// 익명클래스 방식은 상수만 인식할 수 있으므로, HTML태그를 상수에 복사
		final String page_content = html;

		/** 가상의 View를 익명 클래스 방식으로 생성하여 리턴 */
		View view = new AbstractView() {

			@Override
			protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				out.println(page_content);
				out.flush();

			}
		};

		// 가상의 뷰를 리턴한다
		return new ModelAndView(view);
	}

	/**
	 * 파라미터를 전달받아서 리턴한다.
	 *
	 * @param fieldName    - 파라미터 이름
	 * @param defaultValue - 값이 없을 경우 사용될 기본값
	 * @return String
	 */
	public String getString(String fieldName, String defaultValue) {
		// 리턴을 위한 값을 두 번째 파라미터(기본값)로 설정해 둔다.
		String result = defaultValue;
		// GET,POST 파라미터를 받는다.
		String param = this.request.getParameter(fieldName);

		if (param != null) { // 값이 null이 아니라면?
			param = param.trim(); // 앞뒤 공백을 제거한다.
			if (!param.equals("")) { // 공백제거 결과가 빈 문자열이 아니라면?
				result = param; // 리턴을 위해서 준비한 변수에 수신한 값을 복사한다.
			}
		}

		// 값을 리턴. param값이 존재하지 않을 경우 미리 준비한 기본값이 그대로 리턴된다.
		return result;
	}

	/**
	 * 파라미터를 전달받아서 int로 형변환 하여 리턴한다.
	 *
	 * @param fieldName    - 파라미터 이름
	 * @param defaultValue - 값이 없을 경우 사용될 기본값
	 * @return int
	 */
	public int getInt(String fieldName, int defaultValue) {
		// 리턴을 위한 값을 두 번째 파라미터(기본값)로 설정해 둔다.
		int result = defaultValue;
		// getString()메서드를 통해서 파라미터를 문자열 형태로 받는다.
		// 파라미터가 존재하지 않는다면 두 번째로 전달한 값이 리턴된다.
		String param = this.getString(fieldName, null);

		// 파라미터로 전달된 값을 숫자로 형변환 한다.
		try {
			result = Integer.parseInt(param);
		} catch (NumberFormatException e) {
			// 형변환에 실패할 경우 catch블록으로 제어가 이동하고,result값은 미리 설정해 둔
			// defaultValue인 상태를 유지한다.
		}

		return result;
	}

	/**
	 * 배열 형태의 파라미터를 리턴한다. 체크박스 전용 기능
	 *
	 * @param fieldName    - 파라미터 이름
	 * @param defaultValue - 값이 없거나 배열의 길이가 0인 경우 사용될 기본값
	 * @return String[]
	 */
	public String[] getStringArray(String fieldName, String[] defaultValue) {
		// 리턴을 위한 값을 두 번째 파라미터(기본값)로 설정해 둔다.
		String[] result = defaultValue;
		// 배열 형태의 GET,POST 파라미터를 받는다.
		String[] param = this.request.getParameterValues(fieldName);

		if (param != null) { // 수신된 파라미터가 존재한다면?
			if (param.length > 0) { // 배열의 길이가 0보다 크다면?
				result = param; // 리턴을 위해서 준비한 변수에 수신한 값을 복사한다.
			}
		}

		// 값을 리턴. param값이 존재하지 않을 경우 미리 준비한 기본값이 그대로 리턴된다.
		return result;
	}

	public String getString(String fieldName) {
		return this.getString(fieldName, null);
	}

	public int getInt(String fieldName) {
		return this.getInt(fieldName, 0);
	}

	public String[] getStringArray(String fieldName) {
		return this.getStringArray(fieldName, null);
	}

	/**
	 * 쿠키값을 저장한다.
	 *
	 * @param key     - 쿠키이름
	 * @param value   - 값
	 * @param timeout - 설정시간. 브라우저를 닫으면 즉시 삭제할 경우 -1
	 */
	public void setCookie(String key, String value, int timeout) {
		/** 전달된 값을 URLEncoding 처리 한다. */
		if (value != null) {
			try {
				// import java.net.URLEncoder
				value = URLEncoder.encode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		/** 쿠키 객체 생성 및 기본 설정 */
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setDomain(DOMAIN);

		/** 유효시간 설정 */
		// 시간값이 0보다 작은 경우는 이 메서드를 설정하지 않도록 한다. (브라우저를 닫으면 삭제)
		// 0으로 설정할 경우 setMaxAge(0)이라고 설정되므로 즉시 삭제된다.
		if (timeout > -1) {
			cookie.setMaxAge(timeout);
		}

		/** 쿠키 저장하기 */
		this.response.addCookie(cookie);
	}

	/**
	 * 쿠키값을 조회한다.
	 *
	 * @param key          - 쿠키이름
	 * @param defaultValue - 값이 없을 경우 사용될 기본값
	 * @return String
	 */
	public String getCookie(String key, String defaultValue) {
		/** 리턴할 값을 설정 */
		String result = defaultValue;

		/** 쿠키 배열 가져오기 */
		// import javax.servlet.http.Cookie
		Cookie[] cookies = this.request.getCookies();

		/** 쿠키가 있다면? 추출된 배열의 항목 수 만큼 반복하면서 원하는 이름의 값을 검색 */
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				// 쿠키의 이름 얻기
				String cookieName = cookies[i].getName();
				// 원하는 이름이라면?
				if (cookieName.equals(key)) {
					// 값을 추출 --> 이 값이 리턴된다.
					result = cookies[i].getValue();
					try {
						// import java.net.URLDecoder
						result = URLDecoder.decode(result, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					break;
				} // end if
			} // end for
		} // end if

		return result;
	}

	/**
	 * 쿠키값을 조회한다. 값이 없을 경우 Null을 리턴한다.
	 *
	 * @param key - 쿠키이름
	 * @return String
	 */
	public String getCookie(String key) {
		return this.getCookie(key, null);
	}

	/**
	 * 지정된 키에 대한 쿠키를 삭제한다.
	 * 
	 * @param key
	 */
	public void removeCookie(String key) {
		this.setCookie(key, null, 0);
	}

	/**
	 * 세션값을 저장한다.
	 * 
	 * @param key   - 세션이름
	 * @param value - 저장할 데이터
	 */
	public void setSession(String key, Object value) {
		this.session.setAttribute(key, value);
	}

	/**
	 * 세션값을 조회한다.
	 * 
	 * @param key          - 조회할 세션의 이름
	 * @param defaultValue - 값이 없을 경우 대체할 기본값
	 * @return Object이므로 명시적 형변환 필요함
	 */
	public Object getSession(String key, Object defaultValue) {
		Object value = this.session.getAttribute(key);

		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * 세션값을 조회한다. 값이 없을 경우에 대한 기본값을 null로 설정
	 * 
	 * @param key - 세션 이름
	 * @return Object이므로 명시적 형변환 필요함
	 */
	public Object getSession(String key) {
		return this.getSession(key, null);
	}

	/**
	 * 특정 세션값을 삭제한다.
	 * 
	 * @param key - 세션 이름
	 */
	public void removeSession(String key) {
		this.session.removeAttribute(key);
	}

	/**
	 * 현재 사용자에 대한 모든 세션값을 일괄 삭제한다.
	 */
	public void removeAllSession() {
		this.session.invalidate();
	}

	/**
	 * 현재 프로젝트의 최상위 경로값을 "/프로젝트명" 형식으로 리턴한다.
	 * 
	 * @return
	 */
	public String getRootPath() {
		return this.request.getContextPath();
	}

	public String getClientIP() {
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		return ip;

	}

	/**
	 * 결과 메시지를 JSON으로 출력한다. JSONApi에서 web.redirect() 기능을 대체할 용도.
	 * 
	 * @param rt - JSON에 포함할 메시지 내용
	 */
	public void printJsonRt(String rt) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("rt", rt);

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String convertHtmlTag(String content) {
		// 변경된 결과를 저장할 객체
		StringBuilder builder = new StringBuilder();

		// 문자열에 포함된 한 글자
		char charBuff;

		for (int i = 0; i < content.length(); i++) {
			// 한 글자를 추출
			charBuff = (char) content.charAt(i);

			// 특수문자 형태에 부합할 경우 변환하여 builder에 추가
			// 그렇지 않을 경우 원본 그대로 builder에 추가
			switch (charBuff) {
			case '<':
				builder.append("&lt;");
				break;
			case '>':
				builder.append("&gt;");
				break;
			case '&':
				builder.append("&amp;");
				break;
			case '\n':
				builder.append("&lt;br/&gt;");
				break;
			default:
				builder.append(charBuff);
			}
		}

		// 조립된 결과를 문자열로 변환해서 리턴
		return builder.toString();

	}
}
