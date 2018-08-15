package com.viewol.web.filter;

import com.viewol.base.ContextLoader;
import com.viewol.pojo.UserSession;
import com.viewol.service.IUserSessionService;
import com.youguu.core.logging.Log;
import com.youguu.core.logging.LogFactory;
import com.youguu.core.util.json.YouguuJsonHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 登陆状态认证
 */
public class SessionFilter implements Filter {
    private static Log log = LogFactory.getLog(SessionFilter.class);
    private static final String HEADER_NAME_USER_ID = "userId";
    private static final String HEADER_NAME_SESSION_ID = "sessionId";

    private static ApplicationContext ctx = new AnnotationConfigApplicationContext(ContextLoader.class);
    private IUserSessionService userSessionService = (IUserSessionService) ctx.getBean("userSessionService");

    public SessionFilter() {

    }

    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        HttpServletResponse response = (HttpServletResponse) rep;
        response.setCharacterEncoding("UTF-8");

        //不须加解密
        int userId = request.getIntHeader(HEADER_NAME_USER_ID);
        String sessionId = request.getHeader(HEADER_NAME_SESSION_ID);
        boolean success = false;

        String rtMsg = null;
        try {
            if (userId>0 && hasText(sessionId)) {
                log.debug("verifying header info userId={};sessionId={};", userId, sessionId);
                UserSession userSession = userSessionService.getSession(userId);
                if(userSession!=null && userSession.getSessionId() !=null && !"".equals(userSession.getSessionId())){
                    if(sessionId.equals(userSession.getSessionId())){
                        success = true;
                    } else {
                        success = false;
                    }
                } else {
                    success = false;
                }
            }

            if (success) {
                chain.doFilter(request, response);
                return;
            }

            //返回认证失败状态码
            rtMsg = YouguuJsonHelper.returnJSON("0101", "登录状态失效");
        } catch (Throwable e) {
            log.error("SessionFilter error", e);
            rtMsg = YouguuJsonHelper.returnJSON("0001", "接口权限校验异常");
        }

        log.warn("userId={},sessionId={},rtMsg={}", userId, sessionId, rtMsg);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(rtMsg);
            out.flush();
        } catch (Throwable e) {
            log.error("Error when convert base64 and write msg to client", e);
            throw new ServletException(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

    private boolean hasText(String str) {
        return str != null && str.length() > 0;
    }

    public void destroy() {

    }


}
