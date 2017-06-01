package librarySystem.dao;

import librarySystem.domain.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookDao {
    List<Book> getAllBooks();

    Book findByBookNO(@Param("bookNO") String bookNO);

    List<Book> findByBookNameHazily(@Param("bookName") String bookName);

    Integer getAllBookCount();

    List<Book> findAllBookInPagination(@Param("start") Integer start, @Param("itemCountEveryPage") Integer itemCountEveryPage);

    Integer getBookCountByBookName(@Param("bookName") String bookName);

    Integer getBookCountByBookNO(@Param("bookNO") String bookNO);

    Integer getBookCountByAuthor(@Param("author") String author);

    Integer getBookCountByPress(@Param("press") String press);

    List<Book> findByBookNameInPagination(@Param("bookName") String bookName, @Param("start") Integer start, @Param("itemCountEveryPage") Integer itemCountEveryPage);

    List<Book> findByAuthorInPagination(@Param("author") String author, @Param("start") Integer start, @Param("itemCountEveryPage") Integer itemCountEveryPage);

    List<Book> findByPressInPagination(@Param("press") String press, @Param("start") Integer start, @Param("itemCountEveryPage") Integer itemCountEveryPage);

    void save(@Param("book") Book book);

    void update(@Param("book") Book book);

    void deleteByBookNO(@Param("bookNO") String bookNO);

    //蔡越
    List<Book> findBooksByName(@Param("searchWords") String searchWords);

    List<Book> findBooksByNameAndType(@Param("searchWords") String searchWords, @Param("type") String type);

    List<Book> findBooksByAuthor(@Param("searchWords") String searchWords);

    List<Book> findBooksByAuthorAndType(@Param("searchWords") String searchWords, @Param("type") String type);

    List<Book> findBooksByNO(@Param("searchWords") String searchWords);

    List<Book> findBooksByNOAndType(@Param("searchWords") String searchWords, @Param("type") String type);

    List<Book> findBooksByPress(@Param("searchWords") String searchWords);

    List<Book> findBooksByPressAndType(@Param("searchWords") String searchWords, @Param("type") String type);

    List<Book> findBorrowNumAndOrder();

    List<Book> findSearchNumAndOrder();

    List<Book> orderAddTime();

    List<Book> orderByBorrowNum();

    List<Book> orderByScore();

    List<Book> findOtherBooksByAuthor(@Param("author") String author);

    List<Book> findSimilarBooksByCnum(@Param("cnum") String cnum);

    Book findBookByBookNO(@Param("bookNO") String bookNO);

    Book findBookByBookId(@Param("id") Integer bookId);
}
