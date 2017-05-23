package librarySystem.service.impl;

import librarySystem.dao.BookDao;
import librarySystem.dao.ReaderBookDao;
import librarySystem.domain.Book;
import librarySystem.domain.ReaderBook;
import librarySystem.service.ReaderBookService;
import librarySystem.util.ReaderUtil;
import librarySystem.webDomain.ReaderBorrowHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class ReaderBookServiceImpl implements ReaderBookService {

    private final ReaderBookDao readerBookDao;
    private final BookDao bookDao;

    @Autowired
    public ReaderBookServiceImpl(ReaderBookDao readerBookDao, BookDao bookDao) {
        this.readerBookDao = readerBookDao;
        this.bookDao = bookDao;
    }

    //查找特定用户的借阅记录数目
    @Override
    public Integer findPageNumByCredNum(String credNum, Integer itemNumEveryPage) {
        Integer itemCount = readerBookDao.findCountByCredNum(credNum);
        Integer pageNum;
        if (itemCount % itemNumEveryPage == 0) {
            pageNum = itemCount / itemNumEveryPage;
        } else {
            pageNum = (itemCount / itemNumEveryPage) + 1;
        }
        return pageNum;
    }

    //续借
    @Override
    public void renew(String credNum, String barCode) {
        ReaderBook readerBook = readerBookDao.findByCredNumAndBarCode(credNum, barCode);
        if (readerBook.getStatus().equals("续借中")) {
            throw new RuntimeException("已经借阅过了！");
        }
        Long returnDateMillis = readerBook.getReturnDate().getTime();
        long oneMonth = 2592000000L;
        Date newReturnDate = new Date(returnDateMillis + oneMonth);
        readerBook.setReturnDate(newReturnDate);
        readerBook.setStatus("续借中");
        readerBookDao.update(readerBook);
    }

    @Override
    public Integer findOverTimeBorrowedBooks(Integer itemNumEveryPage) {
        Date today = new Date(System.currentTimeMillis());
        Integer itemCount = readerBookDao.findOverTimeBorrowedBooksCount(today);
        Integer pageNum;
        if (itemCount % itemNumEveryPage == 0) {
            pageNum = itemCount / itemNumEveryPage;
        } else {
            pageNum = (itemCount / itemNumEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public List<ReaderBorrowHistory> getOverTimeBooks(Integer pageNum, Integer itemNumEveryPage) {
        Integer start = (pageNum - 1) * itemNumEveryPage;
        List<ReaderBook> readerBooks = readerBookDao.findOverTimeBooks(start, itemNumEveryPage, new Date(System.currentTimeMillis()));
        List<ReaderBorrowHistory> histories = new ArrayList<>();
        for (ReaderBook readerBook : readerBooks) {
            Book book = bookDao.findByBookNO(readerBook.getBookNO());
            ReaderBorrowHistory history = ReaderUtil.extractBorrowHistory(readerBook, book);
            histories.add(history);
        }
        return histories;
    }

    @Override
    public Integer findOverTimeBorrowedBooksCountByBookName(String bookName, Integer itemCountEveryPage) {
        List<Book> bookList = bookDao.findByBookNameHazily(bookName);//根据书名模糊查找
        String[] bookNOS = new String[bookList.size()];
        for (int i = 0; i < bookList.size(); i++) {
            bookNOS[i] = bookList.get(i).getBookNO();
        }
        if (bookList.size() != 0) {
            Integer itemCount = readerBookDao.findCountByBookNO(bookNOS);
            Integer pageNum;
            if (itemCount % itemCountEveryPage == 0) {
                pageNum = itemCount / itemCountEveryPage;
            } else {
                pageNum = (itemCount / itemCountEveryPage) + 1;
            }
            return pageNum;
        }
        return 0;
    }

    @Override
    public List<ReaderBorrowHistory> getOverTimeBooksByBookName(String bookName, Integer pageNum, Integer itemCountEveryPage) {
        List<Book> bookList = bookDao.findByBookNameHazily(bookName);//根据书名模糊查找

        List<String> bookNOS = new ArrayList<>();
        for (Book book : bookList) {
            bookNOS.add(book.getBookNO());
        }
        List<ReaderBook> readerBookList = readerBookDao.findByBookNOS(bookNOS);

        List<ReaderBorrowHistory> historyList = new ArrayList<>();
        for (ReaderBook readerBook : readerBookList) {
            Book book = bookDao.findByBookNO(readerBook.getBookNO());
            historyList.add(ReaderUtil.extractBorrowHistory(readerBook, book));
        }
        return historyList;
    }

    @Override
    public Integer findOverTimeBorrowedBooksCountByBookNO(String bookNO, Integer itemCountEveryPage) {
        String cnum = String.valueOf(bookNO.charAt(0));
        bookNO = bookNO.substring(1);
        List<ReaderBook> readerBookList = readerBookDao.findOverTimeBorrowedBooksByBookNO(bookNO);
        Integer itemCount = 0;
        for (ReaderBook readerBook : readerBookList) {
            Book book = bookDao.findByBookNO(readerBook.getBookNO());
            if (book.getCnum().equals(cnum)) {
                itemCount++;
            }
        }
        Integer pageNum;
        if (itemCount % itemCountEveryPage == 0) {
            pageNum = itemCount / itemCountEveryPage;
        } else {
            pageNum = (itemCount / itemCountEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public List<ReaderBorrowHistory> getOverTimeBooksByBookNO(String bookNO, Integer pageNum, Integer itemCountEveryPage) {
        String cnum = String.valueOf(bookNO.charAt(0));
        bookNO = bookNO.substring(1);
        List<ReaderBook> readerBookList = readerBookDao.findOverTimeBorrowedBooksByBookNO(bookNO);
        List<ReaderBorrowHistory> historyList = new ArrayList<>();
        for (ReaderBook readerBook : readerBookList) {
            Book book = bookDao.findByBookNO(readerBook.getBookNO());
            if (book.getCnum().equals(cnum)) {
                ReaderBorrowHistory history = ReaderUtil.extractBorrowHistory(readerBook, book);
                historyList.add(history);
            }
        }
        return historyList;
    }

    @Override
    public Integer findOverTimeBorrowedBooksCountByReturnDate(Date returnDate, Integer itemCountEveryPage) {
        Integer itemCount = readerBookDao.findOverTimeBorrowedBooksCount(returnDate);
        Integer pageNum;
        if (itemCount % itemCountEveryPage == 0) {
            pageNum = itemCount / itemCountEveryPage;
        } else {
            pageNum = (itemCount / itemCountEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public List<ReaderBorrowHistory> getOverTimeBooksByReturnDate(Date returnDate, Integer pageNum, Integer itemCountEveryPage) {
        Integer start = (pageNum - 1) * itemCountEveryPage;
        List<ReaderBook> readerBookList = readerBookDao.findOverTimeBooks(start, itemCountEveryPage, returnDate);
        List<ReaderBorrowHistory> historyList = new ArrayList<>();
        for (ReaderBook readerBook : readerBookList) {
            Book book = bookDao.findByBookNO(readerBook.getBookNO());
            ReaderBorrowHistory history = ReaderUtil.extractBorrowHistory(readerBook, book);
            historyList.add(history);
        }
        return historyList;
    }
}
