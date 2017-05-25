package librarySystem.service.impl;

import librarySystem.dao.BookDao;
import librarySystem.dao.ReaderBookDao;
import librarySystem.dao.ReaderDao;
import librarySystem.domain.Book;
import librarySystem.domain.Reader;
import librarySystem.domain.ReaderBook;
import librarySystem.service.ReaderService;
import librarySystem.util.ReaderUtil;
import librarySystem.webDomain.ReaderBorrowHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = {java.lang.Exception.class, java.lang.RuntimeException.class}, transactionManager = "transactionManager")
public class ReaderServiceImpl implements ReaderService {

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
            ReaderBorrowHistory history = ReaderUtil.extractBorrowHistory(readerBook, book);
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
            ReaderBorrowHistory history = ReaderUtil.extractBorrowHistory(readerBook, book);
            histories.add(history);
        }
        return histories;
    }

    @Override
    public Integer getReaderPageNum(Integer itemCountEveryPage) {
        Integer itemCount = readerDao.getReaderNum();
        Integer pageNum;
        if (itemCount % itemCountEveryPage == 0) {
            pageNum = itemCount / itemCountEveryPage;
        } else {
            pageNum = (itemCount / itemCountEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public List<Reader> findAllReaderInPagination(Integer pageNum, Integer itemCountEveryPage) {
        Integer start = (pageNum - 1) * itemCountEveryPage;
        List<Reader> readerList = readerDao.findAll(start, itemCountEveryPage);
        for (Reader reader : readerList) {
            reader.setPassword(null);
        }
        return readerList;
    }

    @Override
    public Integer getReaderPageNumByCredNum(String credNum, Integer itemCountEveryPage) {
        Integer itemCount = readerDao.getReaderNumByCredNum(credNum);
        Integer pageNum;
        if (itemCount % itemCountEveryPage == 0) {
            pageNum = itemCount / itemCountEveryPage;
        } else {
            pageNum = (itemCount / itemCountEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public List<Reader> findReadersByCredNumInPagination(String credNum, Integer pageNum, Integer itemCountEveryPage) {
        Integer start = (pageNum - 1) * itemCountEveryPage;
        List<Reader> readerList = readerDao.findByCredNumHazily(credNum, start, itemCountEveryPage);
        for (Reader reader : readerList) {
            reader.setPassword(null);
        }
        return readerList;
    }

    @Override
    public Integer getReaderPageNumByName(String name, Integer itemCountEveryPage) {
        Integer itemCount = readerDao.getReaderNumByName(name);
        Integer pageNum;
        if (itemCount % itemCountEveryPage == 0) {
            pageNum = itemCount / itemCountEveryPage;
        } else {
            pageNum = (itemCount / itemCountEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public List<Reader> findReadersByNameInPagination(String name, Integer pageNum, Integer itemCountEveryPage) {
        Integer start = (pageNum - 1) * itemCountEveryPage;
        List<Reader> readerList = readerDao.findByNameHazily(name, start, itemCountEveryPage);
        for (Reader reader : readerList) {
            reader.setPassword(null);
        }
        return readerList;
    }

    @Override
    public Integer getReaderPageNumByEntryDate(Date entryDate, Integer itemCountEveryPage) {
        Integer itemCount = readerDao.getReaderNumByEntryDate(entryDate);
        Integer pageNum;
        if (itemCount % itemCountEveryPage == 0) {
            pageNum = itemCount / itemCountEveryPage;
        } else {
            pageNum = (itemCount / itemCountEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public List<Reader> findReadersByEntryDateInPagination(Date entryDate, Integer pageNum, Integer itemCountEveryPage) {
        Integer start = (pageNum - 1) * itemCountEveryPage;
        List<Reader> readerList = readerDao.findByStartTime(entryDate, start, itemCountEveryPage);
        for (Reader reader : readerList) {
            reader.setPassword(null);
        }
        return readerList;
    }

}
