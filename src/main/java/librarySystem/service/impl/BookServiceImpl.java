package librarySystem.service.impl;

import librarySystem.dao.BookCLCDao;
import librarySystem.dao.BookDao;
import librarySystem.dao.ReaderBookDao;
import librarySystem.domain.Book;
import librarySystem.domain.BookCLC;
import librarySystem.service.BookService;
import librarySystem.webDomain.BookWithBarCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final BookCLCDao bookCLCDao;
    private final ReaderBookDao readerBookDao;

    public BookServiceImpl(BookDao bookDao, BookCLCDao bookCLCDao, ReaderBookDao readerBookDao) {
        this.bookDao = bookDao;
        this.bookCLCDao = bookCLCDao;
        this.readerBookDao = readerBookDao;
    }

    @Override
    public Integer getAllBookPageNum(Integer itemCountEveryPage) {
        Integer itemCount = bookDao.getAllBookCount();
        Integer pageNum;
        if (itemCount % itemCountEveryPage == 0) {
            pageNum = itemCount / itemCountEveryPage;
        } else {
            pageNum = (itemCount / itemCountEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public List<Book> findAllBookInPagination(Integer pageNum, Integer itemCountEveryPage) {
        Integer start = (pageNum - 1) * itemCountEveryPage;
        return bookDao.findAllBookInPagination(start, itemCountEveryPage);
    }

    @Override
    public Integer getBookPageNumByBookName(String bookName, Integer itemCountEveryPage) {
        Integer itemCount = bookDao.getBookCountByBookName(bookName);
        Integer pageNum;
        if (itemCount % itemCountEveryPage == 0) {
            pageNum = itemCount / itemCountEveryPage;
        } else {
            pageNum = (itemCount / itemCountEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public Integer getBookPageNumByBookNO(String bookNO, Integer itemCountEveryPage) {
        bookNO = bookNO.substring(1);
        Integer itemCount = bookDao.getBookCountByBookNO(bookNO);
        Integer pageNum;
        if (itemCount % itemCountEveryPage == 0) {
            pageNum = itemCount / itemCountEveryPage;
        } else {
            pageNum = (itemCount / itemCountEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public Integer getBookPageNumByAuthor(String author, Integer itemCountEveryPage) {
        Integer itemCount = bookDao.getBookCountByAuthor(author);
        Integer pageNum;
        if (itemCount % itemCountEveryPage == 0) {
            pageNum = itemCount / itemCountEveryPage;
        } else {
            pageNum = (itemCount / itemCountEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public Integer getBookPageNumByPress(String press, Integer itemCountEveryPage) {
        Integer itemCount = bookDao.getBookCountByPress(press);
        Integer pageNum;
        if (itemCount % itemCountEveryPage == 0) {
            pageNum = itemCount / itemCountEveryPage;
        } else {
            pageNum = (itemCount / itemCountEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public List<Book> findBookByBookName(String bookName, Integer pageNum, Integer itemCountEveryPage) {
        Integer start = (pageNum - 1) * itemCountEveryPage;
        return bookDao.findByBookNameInPagination(bookName, start, itemCountEveryPage);
    }

    @Override
    public List<Book> findBookByBookNO(String bookNO, Integer pageNum, Integer itemCountEveryPage) {
        ArrayList<Book> arrayList = new ArrayList<>();
        bookNO = bookNO.substring(1);
        Book book = bookDao.findByBookNO(bookNO);
        arrayList.add(book);
        return arrayList;
    }

    @Override
    public List<Book> findBookByAuthor(String author, Integer pageNum, Integer itemCountEveryPage) {
        Integer start = (pageNum - 1) * itemCountEveryPage;
        return bookDao.findByAuthorInPagination(author, start, itemCountEveryPage);
    }

    @Override
    public List<Book> findBookByPress(String press, Integer pageNum, Integer itemCountEveryPage) {
        Integer start = (pageNum - 1) * itemCountEveryPage;
        return bookDao.findByPressInPagination(press, start, itemCountEveryPage);
    }

    @Override
    public void addNewBook(Book book) {
        Book book1 = bookDao.findByBookNO(book.getBookNO());
        if (book1 != null && !book.getBookName().equals(book1.getBookName())) {
            throw new UnsupportedOperationException("索书号必须唯一！");
        }
        if (book.getTranslator() == null || "".equals(book.getTranslator().trim())) {
            book.setTranslator("无");
        }
        BookCLC bookCLC = new BookCLC();
        bookCLC.setBarCode(generateBarCode(book));
        bookCLC.setBookNO(book.getBookNO());
        bookCLC.setStatus("可借阅");
        bookCLC.setEntryTime(new Date());
        bookCLC.setStoreAddress(book.getStoreAddress());
        if (book1 != null && book.getBookName().equals(book1.getBookName())) {
            book1.setStoreNumber(book1.getStoreNumber() + 1);
            bookDao.update(book1);
            bookCLCDao.save(bookCLC);
        } else {
            book.setStoreNumber(1);
            book.setBorrowNumber(0);
            book.setAddTime(new Date());
            book.setSearchNum(0);
            book.setBorrowNum(0);
            book.setScore("0");
            book.setScoreNum(0);
            bookDao.save(book);
            bookCLCDao.save(bookCLC);
        }
    }

    @Override
    public Book findOneBookByBookNO(String bookNO) {
        return bookDao.findByBookNO(bookNO);
    }

    @Override
    public void update(Book book) {
        if (book.getTranslator() == null || "".equals(book.getTranslator().trim())) {
            book.setTranslator("无");
        }
        bookDao.update(book);
    }

    @Override
    public void delete(String bookNO) {
        Integer bookCount = readerBookDao.findBorrowingCountByBookNO(bookNO);
        if (bookCount != 0) {
            throw new UnsupportedOperationException("当前还有用户借阅");
        }
        readerBookDao.deleteByBookNO(bookNO);
        bookCLCDao.deleteByBookNO(bookNO);
        bookDao.deleteByBookNO(bookNO);
    }

    @Override
    public BookWithBarCode findByBarCode(String barCode) {
        BookCLC bookCLC = bookCLCDao.findByBarCode(barCode);
        if (bookCLC == null) {
            return null;
        }
        Book book = bookDao.findByBookNO(bookCLC.getBookNO());
        book.setStoreAddress(bookCLC.getStoreAddress());
        BookWithBarCode bookWithBarCode = new BookWithBarCode();
        bookWithBarCode.setBarCode(barCode);
        bookWithBarCode.setBook(book);
        return bookWithBarCode;
    }

    @Override
    public void deleteByBarCode(String barCode) {
        Integer bookCount = readerBookDao.findBorrowingCountByBarCode(barCode);
        if (bookCount != 0) {
            throw new UnsupportedOperationException("当前还有用户借阅");
        }
        readerBookDao.deleteByBarCode(barCode);
        bookCLCDao.deleteByBarCode(barCode);
    }

    private String generateBarCode(Book book) {
        String latestBarCode = bookCLCDao.getLatestBarCode();
        if (latestBarCode == null) {
            latestBarCode = "A0000000";
        }
        latestBarCode = latestBarCode.substring(1);
        int length = latestBarCode.length();
        Integer latestBarCodeInteger = Integer.parseInt(latestBarCode);
        String barCode = (latestBarCodeInteger + 1) + "";
        StringBuilder sb = new StringBuilder();
        if (barCode.length() < length) {
            for (int i = 0; i < length - barCode.length(); i++) {
                sb.append(0);
            }
        }
        barCode = sb.toString() + barCode;
        String prefix = "A";
        if (book.getStoreAddress().equals("白云校区图书馆")) {
            prefix = "B";
        }
        return prefix + barCode;
    }

    //蔡越

    public List<Book> findBooksByNameAndType(String searchWords, String book_type) {
        if (book_type.equals("all"))
            return bookDao.findBooksByName(searchWords);
        else
            return bookDao.findBooksByNameAndType(searchWords, book_type);
    }

    public List<Book> findBooksByAuthorAndType(String searchWords, String book_type) {
        if (book_type.equals("all"))
            return bookDao.findBooksByAuthor(searchWords);
        else
            return bookDao.findBooksByAuthorAndType(searchWords, book_type);
    }

    public List<Book> findBooksByNOAndType(String searchWords, String book_type) {
        searchWords = searchWords.substring(1);
        if (book_type.equals("all"))
            return bookDao.findBooksByNO(searchWords);
        else
            return bookDao.findBooksByNOAndType(searchWords, book_type);
    }

    public List<Book> findBooksByPressAndType(String searchWords, String book_type) {
        if (book_type.equals("all"))
            return bookDao.findBooksByPress(searchWords);
        else
            return bookDao.findBooksByPressAndType(searchWords, book_type);
    }

    public List<Book> findBorrowNumAndOrder() {
        return bookDao.findBorrowNumAndOrder();
    }

    public List<Book> findSearchNumAndOrder() {
        return bookDao.findSearchNumAndOrder();
    }

    public List<Book> orderAddTime() {
        return bookDao.orderAddTime();
    }

    public List<Book> orderByBorrowNum() {
        return bookDao.orderByBorrowNum();
    }

    public List<Book> orderByScore() {
        return bookDao.orderByScore();
    }

    public List<Book> findOtherBooksByAuthor(String author) {
        return bookDao.findOtherBooksByAuthor(author);
    }

    public List<Book> findSimilarBooksByCnum(String cnum) {
        return bookDao.findSimilarBooksByCnum(cnum);
    }

    public Book findBookByBookNO(String bookNO) {
        bookNO = bookNO.substring(1);
        return bookDao.findBookByBookNO(bookNO);
    }

    public Book findBookByBookId(Integer bookId) {
        return bookDao.findBookByBookId(bookId);
    }
}
