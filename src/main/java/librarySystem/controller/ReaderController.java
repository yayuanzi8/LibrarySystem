package librarySystem.controller;

import librarySystem.domain.Reader;
import librarySystem.service.ReaderBookService;
import librarySystem.service.ReaderService;
import librarySystem.util.ReaderUtil;
import librarySystem.util.Result;
import librarySystem.webDomain.ReaderBorrowHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/reader")
public class ReaderController {

    private final ReaderService readerService;
    private final ReaderBookService readerBookService;
    private final MailSender mailSender;
    private final SimpleDateFormat formatter;

    @Autowired
    public ReaderController(ReaderService readerService, ReaderBookService readerBookService, MailSender mailSender) {
        this.readerService = readerService;
        this.readerBookService = readerBookService;
        this.mailSender = mailSender;
        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
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
        } catch (Throwable e) {
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

    @RequestMapping(value = "/renew/{barCode:.+}", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> renewBook(@PathVariable("barCode") String barCode, HttpSession session) {
        try {
            readerBookService.renew(ReaderUtil.getUserFromSecurityContext(session).getCredNum(), barCode);
            return Result.ok();
        } catch (Exception e) {
            return Result.error();
        }
    }

    @RequestMapping(value = "/reminder/{credNum}/{bookName:.+}", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> reminder(@PathVariable("credNum") String credNum, @PathVariable("bookName") String bookName) {
        try {
            Reader reader = readerService.findByCredNum(credNum);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("yayuanzi8@163.com");
            message.setTo(reader.getEmail());
            message.setSubject("仲恺农业工程学院超期借书催还通知");
            message.setText("您好！[" + reader.getName() + "]，您在ZHKU大学借阅的[" + bookName + "]已超期！请尽快到图书馆归还！");
            mailSender.send(message);
            return Result.ok();
        } catch (Exception e) {
            return Result.error();
        }
    }

    @RequestMapping(value = "/allReader", method = RequestMethod.GET)
    public String allReader(Model model) {
        //得到用户的数量的页数
        Integer pageNum = readerService.getReaderPageNum(20);//每页20条数据
        model.addAttribute("pageNum", pageNum);
        return "user/allReader";
    }

    @RequestMapping(value = "/getReaderInPagination/{pageNum}", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getReaderInPagination(@PathVariable("pageNum") Integer pageNum) {
        try {
            List<Reader> readerList = readerService.findAllReaderInPagination(pageNum, 20);
            Map<String, Object> map = Result.ok();
            map.put("readerList", readerList);
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }

    @RequestMapping(value = "/searchReader", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> searchReader(@RequestParam("searchType") String searchType,
                                     @RequestParam("searchValue") String searchValue, @RequestParam("pageNum") Integer pageNum) {
        Integer itemCountEveryPage = 20;
        try {
            List<Reader> readerList = null;
            Integer totalPage = 0;
            switch (searchType) {
                case "credNum":
                    totalPage = readerService.getReaderPageNumByCredNum(searchValue, itemCountEveryPage);
                    if (totalPage != 0)
                        readerList = readerService.findReadersByCredNumInPagination(searchValue, pageNum, itemCountEveryPage);
                    break;
                case "name":
                    totalPage = readerService.getReaderPageNumByName(searchValue, itemCountEveryPage);
                    if (totalPage != 0)
                        readerList = readerService.findReadersByNameInPagination(searchValue, pageNum, itemCountEveryPage);
                    break;
                case "entryDate":
                    Date entryDate = formatter.parse(searchValue);
                    totalPage = readerService.getReaderPageNumByEntryDate(entryDate, itemCountEveryPage);
                    if (totalPage != 0)
                        readerList = readerService.findReadersByEntryDateInPagination(entryDate, pageNum, itemCountEveryPage);
                    break;
            }
            Map<String, Object> map = Result.ok();
            map.put("totalPage", totalPage);
            map.put("readerList", readerList);
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }

    @RequestMapping(value = "/watchBorrowDetails/{credNum}", method = RequestMethod.GET)
    public String toDetailsPage(@PathVariable("credNum") String credNum, Model model) {
        Integer pageNum = readerBookService.findPageNumByCredNum(credNum, 20);
        model.addAttribute("page_num", pageNum);
        model.addAttribute("credNum", credNum);
        return "user/watchBorrowDetails";
    }

    @RequestMapping(value = "/adminGetReaderHistory/{page_num}/{cred_num}", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> adminGetReaderHistory(@PathVariable("page_num") Integer pageNum, @PathVariable("cred_num") String credNum) {
        try {
            Reader reader = readerService.findByCredNum(credNum);
            List<ReaderBorrowHistory> historyList = readerService.getReaderBorrowHistory(reader.getCredNum(), pageNum, 20);
            Map<String, Object> map = Result.ok();
            map.put("historyList", historyList);
            return map;
        } catch (Throwable e) {
            return Result.error();
        }
    }

}
