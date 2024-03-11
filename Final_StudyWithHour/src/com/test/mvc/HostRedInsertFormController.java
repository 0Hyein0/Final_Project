/*==================================
	HostRedInsertFormController.java
	- 사용자 정의 컨트롤러 클래스
===================================*/

package com.test.mvc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HostRedInsertFormController implements Controller
{
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 액션 코드		
		ModelAndView mav = new ModelAndView();
		
		// session 설정
		// 호스트코드가 있는 경우에만 접근 가능
		HttpSession session = request.getSession();
		
		if (session.getAttribute("hoCode")==null)
		{
			// 호스트코드가 없는 경우 로그인 폼으로 이동
			mav.setViewName("redirect:loginform.do");
			return mav;
		}
		
		CafeDAO dao = new CafeDAO();
		ArrayList<ReasonDTO> reason = new ArrayList<ReasonDTO>();
		CafeDTO reserve = new CafeDTO();
		
		try
		{
			String reCode = request.getParameter("reCode");
			dao.connection();
			reserve = dao.ReserveInfoLists(reCode);
			reason = dao.reasonLists();
			
			mav.addObject("reserve", reserve);
			mav.addObject("reason", reason);
			mav.setViewName("HostRedForm.jsp");
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
		
	}

}
