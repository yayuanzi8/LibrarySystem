package librarySystem.dao;

import librarySystem.domain.BookCLC;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookCLCDao {

    BookCLC findByBarCode(@Param("barCode") String barCode);

    void update(@Param("bookCLC") BookCLC bookCLC);

    String getLatestBarCode();

    void save(@Param("bookCLC") BookCLC bookCLC);

    void deleteByBookNO(@Param("bookNO") String bookNO);

    void deleteByBarCode(@Param("barCode") String barCode);

    //蔡越
    List<BookCLC> findByBookNO(@Param("bookNO") String bookNO);

    List<BookCLC> findByStatus(@Param("bookNO") String bookNO);

    void updateStatus(@Param("barCode") String barCode);
}
