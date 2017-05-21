package librarySystem.service.impl;

import librarySystem.dao.BookDao;
import librarySystem.dao.ReaderBookDao;
import librarySystem.dao.ReaderDao;
import librarySystem.domain.Book;
import librarySystem.domain.Reader;
import librarySystem.domain.ReaderBook;
import librarySystem.service.ReaderService;
import librarySystem.webDomain.ReaderBorrowHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class
ReaderServiceImpl implements ReaderService {

    private final ReaderDao readerDao;
    private final ReaderBookDao readerBookDao;
    private final BookDao bookDao;

    @Autowired
    public ReaderServiceImpl(ReaderDao readerDao, ReaderBookDao readerBookDao, BookDao bookDao) {
        this.readerDao = readerDao;
        this.readerBookDao = readerBookDao;
        this.bookDao = bookDao;
    }

    @Override
    public Reader findByEmail(String email) {
        return readerDao.findByEmail(email);
    }

    @Override
    public Reader findByCredNum(String credNum) {
        return readerDao.findByCredNum(credNum);
    }

    @Override
    public void update(Reader reader) {
        readerDao.update(reader);
    }

    @Override
    public List<ReaderBorrowHistory> getReaderBorrowHistory(String credNum, Integer pageNum, Integer itemNumEveryPage) {
        Integer start = (pageNum - 1) * itemNumEveryPage;
        List<ReaderBook> readerBooks = readerBookDao.findByCredNum(credNum, start, itemNumEveryPage);
        List<ReaderBorrowHistory> histories = new ArrayList<>();
        for (ReaderBook readerBook : readerBooks) {
            Book book = bookDao.findByBookNO(readerBook.getBookNO());
            ReaderBorrowHistory history = extractBorrowHistory(readerBook, book);
            histories.add(history);
        }
        return histories;
    }

    @Override
    public List<ReaderBorrowHistory> findSpecifyReaderBorrowingBooks(String credNum) {
        List<ReaderBook> readerBookList = readerBookDao.findBorrowing(credNum);
        List<ReaderBorrowHistory> histories = new ArrayList<>();
        for (ReaderBook readerBook : readerBookList) {
            Book book = bookDao.findByBookNO(readerBook.getBookNO());
            ReaderBorrowHistory history = extractBorrowHistory(readerBook,book);
            histories.add(history);
        }
        return histories;
    }

    private ReaderBorrowHistory extractBorrowHistory(ReaderBook readerBook, Book book) {
        ReaderBorrowHistory history = new ReaderBorrowHistory();
        history.setBookNO(readerBook.getBookNO());
        history.setCredNum(readerBook.getCredNum());
        history.setReturnDate(readerBook.getReturnDate());
        history.setBorrowDate(readerBook.getBorrowDate());
        history.setStatus(readerBook.getStatus());
        history.setBookName(book.getBookName());
        history.setAuthor(book.getAuthor());
        history.setCnum(book.getCnum());
        history.setStoreAddress(book.getStoreAddress());
        return history;
    }
}
