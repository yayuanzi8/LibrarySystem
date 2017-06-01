package librarySystem.service.impl;

import librarySystem.dao.BookCLCDao;
import librarySystem.domain.BookCLC;
import librarySystem.service.BookCLCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 阿越 on 2017/5/21.
 */
@Service
public class BookCLCServiceImpl implements BookCLCService {
    @Autowired
    BookCLCDao bookCLCDao;

    public List<BookCLC> findByBookNO(String bookNO) {
        bookNO = bookNO.substring(1);
        return bookCLCDao.findByBookNO(bookNO);
    }

    public List<BookCLC> findByStatus(String bookNO) {
        return bookCLCDao.findByStatus(bookNO);
    }

    public void updateStatus(String barCode) {
        bookCLCDao.updateStatus(barCode);
    }

    @Override
    public BookCLC findByBarCode(String barCode) {
        return bookCLCDao.findByBarCode(barCode);
    }
}
