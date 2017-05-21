package librarySystem.controller;

import librarySystem.domain.Reader;
import librarySystem.service.ReaderBookService;
import librarySystem.service.ReaderService;
import librarySystem.util.ReaderUtil;
import librarySystem.util.Result;
import librarySystem.webDomain.ReaderBorrowHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/reader")
public class ReaderController {

    private final ReaderService readerService;
    private final ReaderBookService readerBookService;

    @Autowired
    public ReaderController(ReaderService readerService, ReaderBookService readerBookService) {
        this.readerService = readerService;
        this.readerBookService = readerBookService;
    }


    @PreAuthorize(value = "isAuthenticated()")
    @RequestMapping(value = "/{cred_num}", method = RequestMethod.GET)
    public String toReaderHomePage(@PathVariable("cred_num") String credNum, Model model) {
        Reader reader = readerService.findByCredNum(credNum);
        model.addAttribute("reader", reader);
        return "user/readerDetails";
    }

    @RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateEmail(@RequestParam("newEmail") String newEmail, @RequestParam("captcha") String captcha, HttpSession session) {
        try {
            String sessionCaptcha = (String) session.getAttribute(newEmail);
            if (sessionCaptcha == null || !captcha.equals(sessionCaptcha)) {
                Map<String, Object> map = Result.error();
                map.put("message", "captchaError");
                return map;
            }
            Reader reader = readerService.findByCredNum(ReaderUtil.getUserFromSecurityContext(session).getCredNum());
            reader.setEmail(newEmail);
            readerService.update(reader);
            System.out.println(newEmail);
            System.out.println(captcha);
            return Result.ok();
        } catch (Exception e) {
            Map<String, Object> map = Result.error();
            map.put("message", "systemError");
            return map;
        }
    }

    @RequestMapping(value = "/history/{cred_num}", method = RequestMethod.GET)
    public String toReaderHistory(@PathVariable("cred_num") String credNum, Model model) {
        Integer pageNum = readerBookService.findPageNumByCredNum(credNum, 10);
        model.addAttribute("page_num", pageNum);
        model.addAttribute("cred_num", credNum);
        return "user/borrowHistory";
    }

    @RequestMapping(value = "/historyPagination/{page_num}", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getReaderHistory(@PathVariable("page_num") Integer pageNum, HttpSession session) {
        try {
            Reader reader = ReaderUtil.getUserFromSecurityContext(session);
            List<ReaderBorrowHistory> historyList = readerService.getReaderBorrowHistory(reader.getCredNum(), pageNum, 10);
            Map<String, Object> map = Result.ok();
            map.put("historyList", historyList);
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }

    @RequestMapping(value = "/currentBorrow/{cred_num}", method = RequestMethod.GET)
    public String toCurrentBorrowPage(@PathVariable("cred_num") String credNum, Model model) {
        model.addAttribute("cred_num", credNum);
        List<ReaderBorrowHistory> historyList = readerService.findSpecifyReaderBorrowingBooks(credNum);
        model.addAttribute("historyList", historyList);
        Reader reader = readerService.findByCredNum(credNum);
        model.addAttribute(reader);
        return "user/currentBorrow";
    }

    @RequestMapping(value = "/renew/{bookNO:.+}", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> renewBook(@PathVariable("bookNO") String bookNO,HttpSession session) {
        try {
            readerBookService.renew(ReaderUtil.getUserFromSecurityContext(session).getCredNum(),bookNO);
            return Result.ok();
        }catch (Exception e){
            return Result.error();
        }
    }
}
