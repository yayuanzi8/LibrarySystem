package librarySystem.service.impl;

import librarySystem.dao.BookDao;
import librarySystem.domain.Book;
import librarySystem.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
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
        return bookDao.findAllBookInPagination(start,itemCountEveryPage);
    }
}
