package librarySystem.controller;

import librarySystem.service.ReaderBookService;
import librarySystem.util.Result;
import librarySystem.webDomain.ReaderBorrowHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
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

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/returnBook", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> returnBook(@RequestParam("barCode") String barCode, @RequestParam("credNum") Integer credNum) {
        try {
            readerBookService.returnBook(credNum, barCode);
            return Result.ok();
        } catch (Exception e) {
            return Result.error();
        }
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/loadBookBorrowHistoryPageNum", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> loadBookBorrowHistoryPageNum(@RequestParam("bookNO") String bookNO) {
        try {
            Integer totalPage = readerBookService.getSpecifyBookBorrowPageNum(bookNO, 20);
            Map<String, Object> map = Result.ok();
            map.put("totalPage", totalPage);
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/loadBookBorrowHistory", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> loadBookBorrowHistory(@RequestParam("bookNO") String bookNO, @RequestParam("pageNum") Integer pageNum) {
        try {
            List<ReaderBorrowHistory> historyList = readerBookService.findSpecifyBookBorrowHistory(bookNO, pageNum, 20);
            Map<String, Object> map = Result.ok();
            map.put("historyList", historyList);
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/loadBookBorrowHistoryPageNumByBarCode",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> loadBookBorrowHistoryPageNumByBarCode(@RequestParam("barCode") String barCode){
        try {
            Integer totalPage = readerBookService.getSpecifyBookBorrowPageNumByBarCode(barCode, 20);
            Map<String, Object> map = Result.ok();
            map.put("totalPage", totalPage);
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/loadBookBorrowHistoryByBarCode", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> loadBookBorrowHistoryByBarCode(@RequestParam("barCode") String barCode, @RequestParam("pageNum") Integer pageNum) {
        try {
            List<ReaderBorrowHistory> historyList = readerBookService.findSpecifyBookBorrowHistoryByBarCode(barCode, pageNum, 20);
            Map<String, Object> map = Result.ok();
            map.put("historyList", historyList);
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }

}
