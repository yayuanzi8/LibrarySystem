package librarySystem.controller;

import librarySystem.domain.Book;
import librarySystem.service.BookService;
import librarySystem.service.ReaderBookService;
import librarySystem.util.Result;
import librarySystem.webDomain.ReaderBorrowHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class BookController {

    private final ReaderBookService readerBookService;
    private final BookService bookService;

    @Autowired
    public BookController(ReaderBookService readerBookService, BookService bookService) {
        this.readerBookService = readerBookService;
        this.bookService = bookService;
    }

    @RequestMapping(value = "/overTimeBooks", method = RequestMethod.GET)
    public String toOverTimeBooksPage(Model model) {
        //20表示每页显示数据的条数
        Integer pageNum = readerBookService.findOverTimeBorrowedBooks(20);
        model.addAttribute("pageNum", pageNum);
        return "book/overTimeBooks";
    }

    @RequestMapping(value = "/overTimeBooksInPagination/{pageNum}", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getOverTimeBooksInPagination(@PathVariable("pageNum") Integer pageNum) {
        try {
            List<ReaderBorrowHistory> historyList = readerBookService.getOverTimeBooks(pageNum, 20);
            Map<String, Object> map = Result.ok();
            map.put("historyList", historyList);
            return map;
        } catch (Throwable e) {
            return Result.error();
        }
    }

    @RequestMapping(value = "/allBook", method = RequestMethod.GET)
    public String getAllBookPageNum(Model model) {
        Integer pageNum = bookService.getAllBookPageNum(20);
        model.addAttribute("pageNum", pageNum);
        return "book/allBook";
    }

    @RequestMapping(value = "/allBookInPagination/{pageNum}", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getAllBookInPagination(@PathVariable("pageNum") Integer pageNum) {
        try {
            List<Book> bookList = bookService.findAllBookInPagination(pageNum, 20);
            Map<String, Object> map = Result.ok();
            map.put("bookList", bookList);
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }

    @RequestMapping(value = "/newBook", method = RequestMethod.GET)
    public String addNewBook() {
        return "book/newBook";
    }
}
