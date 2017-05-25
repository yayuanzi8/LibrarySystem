package librarySystem.service;

import librarySystem.domain.Book;

import java.util.List;

public interface BookService {
    Integer getAllBookPageNum(Integer itemCountEveryPage);

    List<Book> findAllBookInPagination(Integer pageNum, Integer itemCountEveryPage);
}
