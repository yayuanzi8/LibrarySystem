package librarySystem.dao;

import librarySystem.domain.BookCLC;
import org.apache.ibatis.annotations.Param;

public interface BookCLCDao {

    BookCLC findByBarCode(@Param("barCode") String barCode);

    void update(@Param("bookCLC") BookCLC bookCLC);
}
