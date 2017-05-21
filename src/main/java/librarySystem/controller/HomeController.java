package librarySystem.controller;

import librarySystem.domain.Reader;
import librarySystem.service.ReaderService;
import librarySystem.util.CodeUtil;
import librarySystem.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
