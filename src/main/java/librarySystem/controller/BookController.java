package librarySystem.controller;

import librarySystem.domain.Book;
import librarySystem.domain.BookCLC;
import librarySystem.domain.BookClass;
import librarySystem.domain.SearchWords;
import librarySystem.service.*;
import librarySystem.util.Result;
import librarySystem.webDomain.BookWithBarCode;
import librarySystem.webDomain.ReaderBorrowHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/book")
public class BookController {

    private final ReaderBookService readerBookService;
    private final BookService bookService;
    private final SearchWordsService searchWordsService;
    private final BookCLCService bookCLCService;
    private final BookClassService bookClassService;

    @Autowired
    public BookController(ReaderBookService readerBookService, BookService bookService, SearchWordsService searchWordsService, BookCLCService bookCLCService, BookClassService bookClassService) {
        this.readerBookService = readerBookService;
        this.bookService = bookService;
        this.searchWordsService = searchWordsService;
        this.bookCLCService = bookCLCService;
        this.bookClassService = bookClassService;
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/overTimeBooks", method = RequestMethod.GET)
    public String toOverTimeBooksPage(Model model) {
        //20表示每页显示数据的条数
        Integer pageNum = readerBookService.findOverTimeBorrowedBooks(20);
        model.addAttribute("pageNum", pageNum);
        return "book/overTimeBooks";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
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

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/allBook", method = RequestMethod.GET)
    public String getAllBookPageNum(Model model) {
        Integer pageNum = bookService.getAllBookPageNum(20);
        model.addAttribute("pageNum", pageNum);
        return "book/allBook";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
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

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/newBook", method = RequestMethod.GET)
    public String addNewBook() {
        return "book/newBook";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/loadBookPageNum", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> loadBookPageNum(@RequestParam("searchType") String searchType,
                                        @RequestParam("searchValue") String searchValue, @RequestParam("pageNum") Integer pageNum) {
        try {
            Integer totalPage = 0;
            switch (searchType) {
                case "bookName":
                    totalPage = bookService.getBookPageNumByBookName(searchValue, 20);
                    break;
                case "bookNO":
                    totalPage = bookService.getBookPageNumByBookNO(searchValue, 20);
                    break;
                case "author":
                    totalPage = bookService.getBookPageNumByAuthor(searchValue, 20);
                    break;
                case "press":
                    totalPage = bookService.getBookPageNumByPress(searchValue, 20);
                    break;
            }
            Map<String, Object> map = Result.ok();
            map.put("totalPage", totalPage);
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/loadBooksBySearch", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> loadBooksBySearch(@RequestParam("searchType") String searchType,
                                          @RequestParam("searchValue") String searchValue, @RequestParam("pageNum") Integer pageNum) {
        try {
            List<Book> bookList = null;
            switch (searchType) {
                case "bookName":
                    bookList = bookService.findBookByBookName(searchValue, pageNum, 20);
                    break;
                case "bookNO":
                    bookList = bookService.findBookByBookNO(searchValue, pageNum, 20);
                    break;
                case "author":
                    bookList = bookService.findBookByAuthor(searchValue, pageNum, 20);
                    break;
                case "press":
                    bookList = bookService.findBookByPress(searchValue, pageNum, 20);
                    break;
            }
            Map<String, Object> map = Result.ok();
            map.put("bookList", bookList);
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/addNewBook", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addNewBook(Book book) {
        try {
            bookService.addNewBook(book);
            return Result.ok();
        } catch (Exception e) {
            if (e instanceof UnsupportedOperationException) {
                Map<String, Object> map = Result.error();
                map.put("message", "uniqueBookNO");
                return map;
            }
            return Result.error();
        }
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/editBookInfo", method = RequestMethod.GET)
    public String toEditForm(@RequestParam("bookNO") String bookNO, Model model) {
        model.addAttribute("book", bookService.findOneBookByBookNO(bookNO));
        return "book/editBookInfo";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateBook(Book book, HttpServletRequest request) {
        try {
            bookService.update(book);
            return Result.ok();
        } catch (Exception e) {
            return Result.error();
        }
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> deleteBook(@RequestParam("bookNO") String bookNO) {
        try {
            bookService.delete(bookNO);
            return Result.ok();
        } catch (Exception e) {
            if (e instanceof UnsupportedOperationException) {
                Map<String, Object> map = Result.error();
                map.put("message", "hasReaderBorrow");
                return map;
            }
            return Result.error();
        }
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/searchByBarCode", method = RequestMethod.GET)
    public String searchByBarCode() {
        return "book/searchByBarCode";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/searchBookByBarCode", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> searchBookByBarCode(@RequestParam("barCode") String barCode) {
        try {
            BookWithBarCode bookDetails = bookService.findByBarCode(barCode);
            Map<String, Object> map = Result.ok();
            if (bookDetails == null) {
                map.put("message", "noResult");
            } else {
                bookDetails.getBook().setStoreAddress(bookCLCService.findByBarCode(bookDetails.getBarCode()).getStoreAddress());
                map.put("bookDetails", bookDetails);
            }
            return map;
        } catch (Exception e) {
            return Result.error();
        }
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('reader:admin')")
    @RequestMapping(value = "/deleteBookByBarCode", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> deleteBookByBarCode(@RequestParam("barCode") String barCode) {
        try {
            bookService.deleteByBarCode(barCode);
            return Result.ok();
        } catch (Exception e) {
            if (e instanceof UnsupportedOperationException) {
                Map<String, Object> map = Result.error();
                map.put("message", "hasBorrow");
                return map;
            }
            return Result.error();
        }
    }

    //蔡越

    /**
     * 书籍检索
     *
     * @param searchTypes
     * @param searchWords
     * @param book_type
     * @param session
     * @return
     */
    @RequestMapping("/searchBooks")
    public String searchBooks(String searchTypes, String searchWords, String book_type, HttpSession session) {
        // 查找检索词（做热门检索功能）
        SearchWords object = searchWordsService.find(searchWords);
        if (object != null)
            searchWordsService.addSearchNum(searchWords);
        else
            searchWordsService.add(searchWords, searchTypes, book_type);

        // 检索功能
        List<Book> books = new ArrayList<>();
        // 书名
        if (searchTypes.equals("1")) {
            books = bookService.findBooksByNameAndType(searchWords, book_type);
            String bookName;
            for (Book book : books) {
                getStoreAndBorrowNum(book);
                bookName = book.getBookName();
                bookName = bookName.replaceAll(searchWords, "<font style='color:red;'>" + searchWords + "</font>");
                book.setBookName(bookName);
            }
        }
        // 作者
        if (searchTypes.equals("2")) {
            books = bookService.findBooksByAuthorAndType(searchWords, book_type);
            String author;
            for (Book book : books) {
                getStoreAndBorrowNum(book);
                author = book.getAuthor();
                author = author.replaceAll(searchWords, "<font style='color:red;'>" + searchWords + "</font>");
                book.setAuthor(author);
            }
        }
        // 出版社
        if (searchTypes.equals("3")) {
            books = bookService.findBooksByPressAndType(searchWords, book_type);
            String press;
            for (Book book : books) {
                getStoreAndBorrowNum(book);
                press = book.getPress();
                press = press.replaceAll(searchWords, "<font style='color:red;'>" + searchWords + "</font>");
                book.setPress(press);
            }
        }
        // 书籍编号
        if (searchTypes.equals("4")) {
            books = bookService.findBooksByNOAndType(searchWords, book_type);
            String bookNO;
            for (Book book : books) {
                getStoreAndBorrowNum(book);
                bookNO = book.getBookNO();
                bookNO = bookNO.replaceAll(searchWords, "<font style='color:red;'>" + searchWords + "</font>");
                book.setBookNO(bookNO);
            }
        }
        session.setAttribute("books", books);
        return "searchResult";
    }

    private void getStoreAndBorrowNum(Book book) {
        List<BookCLC> bookCLCList = bookCLCService.findByBookNO(book.getBookNO());
        book.setStoreNumber(bookCLCList.size());
        bookCLCList = bookCLCService.findByStatus(book.getBookNO());
        book.setBorrowNumber(bookCLCList.size());
    }

    // -----检索页面的推荐栏start------

    @RequestMapping("/hotSearch")
    @ResponseBody
    public Map<String, Object> hotSearch(String page) {
        Map<String, Object> map = new HashMap<>();
        int pageNum = 6;
        int pageI;
        pageI = Integer.parseInt(page) * pageNum - 5;
        List<SearchWords> searchWordsList = searchWordsService.findAllAndOrder(pageI - 1, pageNum);
        map.put("sw", searchWordsList);
        // 返回总数
        List<SearchWords> allList = searchWordsService.findAll();
        map.put("length", allList.size());
        return map;
    }

    @RequestMapping("/hotBorrow")
    @ResponseBody
    public Map<String, Object> hotBorrow(String page) {
        Map<String, Object> map = new HashMap<>();
        int pageNum = 4;
        int begin = Integer.parseInt(page) * pageNum - (pageNum - 1);
        int end = Integer.parseInt(page) * pageNum;
        List<Book> bookList = bookService.findBorrowNumAndOrder();
        List<Book> returnList = new LinkedList<>();
        map.put("length", bookList.size());
        for (int i = begin; i <= end; i++) {
            if (i - 1 < bookList.size()) {
                Book book = bookList.get(i - 1);
                returnList.add(book);
            }
        }
        map.put("bl", returnList);
        return map;
    }

    @RequestMapping("/hotBooks")
    @ResponseBody
    public Map<String, Object> hotBooks(String page) {
        Map<String, Object> map = new HashMap<>();
        int pageNum = 4;
        int begin = Integer.parseInt(page) * pageNum - (pageNum - 1);
        int end = Integer.parseInt(page) * pageNum;
        List<Book> bookList = bookService.findSearchNumAndOrder();
        List<Book> returnList = new LinkedList<Book>();
        map.put("length", bookList.size());
        for (int i = begin; i <= end; i++) {
            if (i - 1 < bookList.size()) {
                Book book = bookList.get(i - 1);
                returnList.add(book);
            }
        }
        map.put("bl", returnList);
        return map;
    }
    // -----检索页面的推荐栏end------


    /**
     * 检索结果展示
     *
     * @param page
     * @param session
     * @return
     */
    @RequestMapping("/searchResult")
    @ResponseBody
    public Map<String, Object> searchResult(String page, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        List<Book> books = (List<Book>) session.getAttribute("books");
        map.put("books", books);
        return map;
    }

    // -----检索结果的侧边栏start------

    /**
     * 总点击排行榜
     *
     * @return
     */
    @RequestMapping("/totalHits")
    @ResponseBody
    public Map<String, Object> totalHits() {
        Map<String, Object> map = new HashMap<>();
        List<Book> books = bookService.findSearchNumAndOrder();
        map.put("books", books);
        return map;
    }

    @RequestMapping("/newBooks")
    @ResponseBody
    public Map<String, Object> newBooks() {
        Map<String, Object> map = new HashMap<>();
        List<Book> books = bookService.orderAddTime();
        map.put("books", books);
        return map;
    }

    @RequestMapping("/hotBorrowInResult")
    @ResponseBody
    public Map<String, Object> hotBorrowInResult() {
        Map<String, Object> map = new HashMap<>();
        List<Book> books = bookService.orderByBorrowNum();
        map.put("books", books);
        return map;
    }

    @RequestMapping("/goodScore")
    @ResponseBody
    public Map<String, Object> goodScore() {
        Map<String, Object> map = new HashMap<>();
        List<Book> books = bookService.orderByScore();
        map.put("books", books);
        return map;
    }
    // -----检索结果的侧边栏end------

    // -----书籍详情的侧边栏 start------
    @RequestMapping("/authorBook")
    @ResponseBody
    public Map<String, Object> authorBook(String author) {
        Map<String, Object> map = new HashMap<>();
        List<Book> books = bookService.findOtherBooksByAuthor(author);
        map.put("books", books);
        return map;
    }

    @RequestMapping("/similarBooks")
    @ResponseBody
    public Map<String, Object> similarBooks(String cnum) {
        Map<String, Object> map = new HashMap<>();
        List<Book> books = bookService.findSimilarBooksByCnum(cnum);
        map.put("books", books);
        return map;
    }

    // -----书籍详情的侧边栏 end------

    /**
     * 书籍链接
     *
     * @param bookId
     * @param request
     * @return
     */
    @RequestMapping("/findBook")
    public String findBook(String bookId, HttpServletRequest request) {
        Book book = bookService.findBookByBookId(Integer.parseInt(bookId));
        if (book != null) {
            request.setAttribute("book", book);
            BookClass bookClass = bookClassService.findByCNum(book.getCnum());
            if (bookClass != null)
                request.setAttribute("cname", bookClass.getCname());
            List<BookCLC> bookCLCList = bookCLCService.findByBookNO(book.getBookNO());
            if (bookCLCList.size() > 0)
                request.setAttribute("bclcList", bookCLCList);
        }
        return "book";
    }

    @RequestMapping("/borrowBook")
    public void borrowBook(Integer credNum, String barCode, String bookNO, HttpServletResponse response) throws IOException {
        String status = "借出";
        Calendar cal = Calendar.getInstance();
        Date borrowDate = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date returnDate = cal.getTime();
        try {
            readerBookService.borrowBook(credNum, barCode, bookNO, returnDate, borrowDate, status);
            response.getWriter().write("success");
        } catch (Exception e) {
            if (e instanceof UnsupportedOperationException) {
                response.getWriter().write("overMax");
                return;
            }
            response.getWriter().write("error");
        }
    }
}
