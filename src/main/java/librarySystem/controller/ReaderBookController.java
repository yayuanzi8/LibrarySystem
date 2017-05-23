package librarySystem.controller;

import librarySystem.service.ReaderBookService;
import librarySystem.util.Result;
import librarySystem.webDomain.ReaderBorrowHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/readerBook")
public class ReaderBookController {

    private final ReaderBookService readerBookService;
    private final SimpleDateFormat formatter;

    @Autowired
    public ReaderBookController(ReaderBookService readerBookService) {
        this.readerBookService = readerBookService;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    @RequestMapping(value = "/searchOverTimeBooks", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> searchOverTimeBooks(@RequestParam("searchType") String searchType, @RequestParam("searchValue") String searchValue, @RequestParam("pageNum") Integer pageNum) {
        Integer itemCountEveryPage = 20;
        try {
            List<ReaderBorrowHistory> historyList = null;
            Integer totalPage = 0;
            switch (searchType) {
                case "bookName":
                    totalPage = readerBookService.findOverTimeBorrowedBooksCountByBookName(searchValue, itemCountEveryPage);
                    if (totalPage != 0)
                        historyList = readerBookService.getOverTimeBooksByBookName(searchValue, pageNum, itemCountEveryPage);
                    break;
                case "bookNO":
                    totalPage = readerBookService.findOverTimeBorrowedBooksCountByBookNO(searchValue, itemCountEveryPage);
                    if (totalPage != 0)
                        historyList = readerBookService.getOverTimeBooksByBookNO(searchValue, pageNum, itemCountEveryPage);
                    break;
                case "returnDate":
                    Date returnDate = formatter.parse(searchValue);
                    totalPage = readerBookService.findOverTimeBorrowedBooksCountByReturnDate(returnDate, itemCountEveryPage);
                    if (totalPage != 0)
                        historyList = readerBookService.getOverTimeBooksByReturnDate(returnDate, pageNum, itemCountEveryPage);
                    break;
            }
            Map<String, Object> map = Result.ok();
            map.put("totalPage", totalPage);
            map.put("historyList", historyList);
            return map;
        } catch (Exception e) {
            return Result.error();
        }

    }
}
