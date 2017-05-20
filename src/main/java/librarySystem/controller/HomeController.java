package librarySystem.controller;

import librarySystem.domain.Reader;
import librarySystem.service.ReaderService;
import librarySystem.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class HomeController {

    private final ReaderService readerService;

    @Autowired
    public HomeController(ReaderService readerService) {
        this.readerService = readerService;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendEmail(@RequestParam("email") String email) {
        if (!email.matches("\\w+@\\w+\\.\\w+")) {
            return Result.emailPatternError();
        }
        Reader reader = readerService.findByEmail(email);
        if (reader != null) {
            return Result.emailExistedError();
        }

        return Result.ok();
    }
}
