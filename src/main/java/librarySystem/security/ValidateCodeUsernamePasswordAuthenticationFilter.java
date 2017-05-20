package librarySystem.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidateCodeUsernamePasswordAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String requestCaptchaCode = request.getParameter("captcha");
        String sessionCaptchaCode = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(requestCaptchaCode) || !sessionCaptchaCode.equalsIgnoreCase(requestCaptchaCode)){
            throw new AuthenticationServiceException("验证码错误");
        }
        return super.attemptAuthentication(request, response);
    }


}
