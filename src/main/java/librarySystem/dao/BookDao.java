package librarySystem.dao;

import librarySystem.domain.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookDao {
    List<Book> getAllBooks();

    Book findByBookNO(@Param("bookNO") String bookNO);
}
