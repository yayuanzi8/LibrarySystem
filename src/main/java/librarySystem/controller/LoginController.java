package librarySystem.controller;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//@Controller
@RequestMapping("/user")
public class LoginController {

    @Resource(name = "studentAuthenticationProvider")
    private AuthenticationProvider studentAuthenticationProvider;
    @Resource(name = "teacherAuthenticationProvider")
    private AuthenticationProvider teacherAuthenticationProvider;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLoginForm() {
        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> login(String cred_num, String password, String role, HttpSession session) {
        System.out.println(cred_num + "=" + password + "=" + role);
        Map<String, String> msgMap = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(cred_num, password);
        try {
            if (role.equals("teacher")) {
                Authentication authentication = teacherAuthenticationProvider.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            } else {
                Authentication authentication = studentAuthenticationProvider.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            }
        } catch (AuthenticationException e) {
            msgMap.put("message", "error");
            return msgMap;
        }
        msgMap.put("message", "ok");
        return msgMap;
    }
}
