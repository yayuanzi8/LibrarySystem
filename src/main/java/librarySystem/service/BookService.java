package librarySystem.service;

import librarySystem.domain.Book;
import librarySystem.webDomain.BookWithBarCode;

import java.util.List;

public interface BookService {
    Integer getAllBookPageNum(Integer itemCountEveryPage);

    List<Book> findAllBookInPagination(Integer pageNum, Integer itemCountEveryPage);

    Integer getBookPageNumByBookName(String bookName, Integer itemCountEveryPage);

    Integer getBookPageNumByBookNO(String bookNO, Integer itemCountEveryPage);

    Integer getBookPageNumByAuthor(String author, Integer itemCountEveryPage);

    Integer getBookPageNumByPress(String press, Integer itemCountEveryPage);

    List<Book> findBookByBookName(String bookName, Integer pageNum, Integer itemCountEveryPage);

    List<Book> findBookByBookNO(String bookNO,Integer pageNum, Integer itemCountEveryPage);

    List<Book> findBookByAuthor(String searchValue, Integer pageNum, Integer itemCountEveryPage);

    List<Book> findBookByPress(String press, Integer pageNum, Integer itemCountEveryPage);

    void addNewBook(Book book);

    Book findOneBookByBookNO(String bookNO);

    void update(Book book);

    void delete(String bookNO);

    BookWithBarCode findByBarCode(String barCode);

    void deleteByBarCode(String barCode);

    //蔡越
    List<Book> findBooksByNameAndType(String searchWords, String book_type);

    List<Book> findBooksByAuthorAndType(String searchWords, String book_type);

    List<Book> findBooksByNOAndType(String searchWords, String book_type);

    List<Book> findBooksByPressAndType(String searchWords, String book_type);

    List<Book> findBorrowNumAndOrder();

    List<Book> findSearchNumAndOrder();

    List<Book> orderAddTime();

    List<Book> orderByBorrowNum();

    List<Book> orderByScore();

    List<Book> findOtherBooksByAuthor(String author);

    List<Book> findSimilarBooksByCnum(String cnum);

    Book findBookByBookNO(String bookNO);

    Book findBookByBookId(Integer bookId);
}
