package librarySystem.controller;

import librarySystem.util.CodeUtil;
import librarySystem.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class LoginController {

    private final CodeUtil codeUtil;

    @Autowired
    public LoginController(CodeUtil codeUtil) {
        this.codeUtil = codeUtil;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLoginForm() {
        return "user/login";
    }

    @RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> loginSuccess() {
        return Result.ok();
    }

    @RequestMapping(value = "/loginFail", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> loginFail(HttpSession session) {
        AuthenticationException exception = (AuthenticationException) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        Map<String,Object> map = Result.error();
        String message = exception.getMessage();
        if (message.equals("Bad credentials")){
            message = "用户名或密码错误";
        }
        map.put("details",message);
        System.out.println(message);
        return map;
    }

    @RequestMapping(value = "/captcha.jpg", method = RequestMethod.GET)
    public ModelAndView generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        codeUtil.makeValidateImage(request, response);
        return null;
    }
}
