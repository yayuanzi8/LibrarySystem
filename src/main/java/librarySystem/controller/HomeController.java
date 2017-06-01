package librarySystem.controller;

import librarySystem.domain.Reader;
import librarySystem.service.ReaderService;
import librarySystem.util.CodeUtil;
import librarySystem.util.ReaderUtil;
import librarySystem.util.Result;
import librarySystem.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class HomeController {

    private final ReaderService readerService;
    private final MailSender mailSender;
    private final CodeUtil codeUtil;

    @Autowired
    public HomeController(ReaderService readerService, MailSender mailSender, CodeUtil codeUtil) {
        this.readerService = readerService;
        this.mailSender = mailSender;
        this.codeUtil = codeUtil;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index() {
        return "index";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(method = RequestMethod.GET, value = "/admin")
    public String toAdmin(Model model, HttpSession session) {
        Reader reader = readerService.findByCredNum(ReaderUtil.getUserFromSecurityContext(session).getCredNum());
        model.addAttribute(reader);
        return "user/admin";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendEmail(@RequestParam("email") String email, HttpSession session) {
        if (!email.matches("\\w+@\\w+\\.\\w+")) {
            return Result.emailPatternError();
        }
        Reader reader = readerService.findByEmail(email);
        if (reader != null) {
            return Result.emailExistedError();
        }
        String captcha = new String(codeUtil.getValidateString());
        session.setAttribute(email, captcha);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yayuanzi8@163.com");
        message.setTo(email);
        message.setSubject("您的注册验证码是" + captcha);
        message.setText("您的邮箱在仲恺农业图书馆注册了账号!\r\n如果这并非您本人操作！不必理会该邮件！您的验证码是" + captcha);
        mailSender.send(message);
        return Result.ok();
    }

    @RequestMapping(value = "/receiveImage", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> receiveImage(@RequestPart MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
            String url = UploadUtil.dealUpload(file, request);
            url = request.getContextPath() + "/uploads/" + url;
            Map<String, Object> map = Result.ok();
            System.out.println(url);
            map.put("url", url);
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }
}
