package com.camping.app.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.app.domain.Member;

@WebFilter("/app/*")
public class AuthFilter implements Filter {
  
  @Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

FilterConfig filterConfig;
  String contextRootPath;
  
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
    contextRootPath = filterConfig.getServletContext().getContextPath();
  }
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpReq = (HttpServletRequest) request;
    HttpServletResponse httpResp = (HttpServletResponse) response;
    
//    String servletPath = httpReq.getPathInfo();
//    if (servletPath.endsWith("add")
//        || servletPath.endsWith("update")
//        || servletPath.endsWith("delete")
//        || !(servletPath.endsWith("/auth/form")) && servletPath.endsWith("form")) {
//      
//      Member loginUser = (Member) httpReq.getSession().getAttribute("loginUser");
//      
//      if (loginUser == null) {
//        if (servletPath.startsWith("/json")) {
//          response.setContentType("text/plain;charset=UTF-8");
//          PrintWriter out = response.getWriter();
//          out.println("{\"status\":\"loginfail\", \"message\":\"로그인 하지 않았습니다.\"}");
//          
//        } else {
//          httpResp.sendRedirect(contextRootPath + "/app/auth/form");
//        }
//        return;
//      }
      
//    }
    
    chain.doFilter(request, response);
  }
}
