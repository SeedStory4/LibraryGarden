package egovframework.com.cmm.interceptor;

import libraryGarden.domain.UserVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

/**
 * 인증여부 체크 인터셉터
 * @author 공통서비스 개발팀 서준식
 * @since 2011.07.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011.07.01  서준식          최초 생성
 *  2011.09.07  서준식          인증이 필요없는 URL을 패스하는 로직 추가
 *  2014.06.11  이기하          인증이 필요없는 URL을 패스하는 로직 삭제(xml로 대체)
 *  </pre>
 */

public class AuthenticInterceptor extends WebContentInterceptor {

	/**
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {

//		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
//
//		if (loginVO.getId() != null) {
//			return true;
//		} else {
//			ModelAndView modelAndView = new ModelAndView("redirect:/uat/uia/egovLoginUsr.do");
//			throw new ModelAndViewDefiningException(modelAndView);
//		}
		
		
		// 세션에 저장된 UserVo를 가져오기
		UserVo loginUser = (UserVo)request.getSession().getAttribute("loginUser");
		
		if(loginUser == null) {
			// 이동할 경로를 저장
			saveUrl(request);
			
			// 로그인 페이지로 이동
			ModelAndView modelAndView = new ModelAndView("redirect:/user/user/userLogin.do");
			throw new ModelAndViewDefiningException(modelAndView);
			
		} else {
			// 세션에 저장된 UserVo가 있으면 요청 진행
			return true;
		}
	}
	
	public void saveUrl(HttpServletRequest request) {
		
		String uri = request.getRequestURI();  // 전체경로주소
		String param = request.getQueryString();  // 파라미터를 가져온다
		
		if(param == null || param.equals("null") || param.equals("")) {
			param = "";
		} else {
			param = "?" + param;
		}
		
		// 이동할 페이지
		String locationUrl = uri + param; 
		
		HttpSession session = request.getSession();
		if(request.getMethod().equals("GET")) {  // 대문자 GET
			session.setAttribute("saveUrl", locationUrl);
		}
	}

}
