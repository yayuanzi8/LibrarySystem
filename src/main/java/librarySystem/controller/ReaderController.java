package librarySystem.controller;

import librarySystem.domain.Reader;
import librarySystem.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/reader")
public class ReaderController {

    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }


    @RequestMapping(value = "/{cred_num}", method = RequestMethod.GET)
    public String toReaderHomePage(@PathVariable("cred_num") String credNum, HttpSession session,Model model) {
        System.out.println(credNum);
        Reader reader = readerService.findByCredNum(credNum);
        model.addAttribute("reader",reader);
        return "user/readerDetails";
    }
}
