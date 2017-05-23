package librarySystem.dao;

import librarySystem.domain.ReaderBook;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ReaderBookDao {

    List<ReaderBook> findByCredNum(@Param("credNum") String credNum, @Param("start") Integer start, @Param("itemNumEveryPage") Integer itemNumEveryPage);
    /**
     * 查找指定用户的借阅数目
     * @param credNum
     * @return
     */
    Integer findCountByCredNum(@Param("credNum") String credNum);

    List<ReaderBook> findBorrowing(@Param("credNum") String credNum);


    void update(@Param("readerBook") ReaderBook readerBook);

    ReaderBook findByCredNumAndBarCode(@Param("credNum") String credNum, @Param("barCode") String barCode);

    Integer findOverTimeBorrowedBooksCount(@Param("today") Date today);

    List<ReaderBook> findOverTimeBooks(@Param("start") Integer start, @Param("itemNumEveryPage") Integer itemNumEveryPage, @Param("today") Date today);

    Integer findCountByBookNO(@Param("bookNOS") String[] bookNOS);

    List<ReaderBook> findByBookNOS(@Param("bookNOS") List<String> bookNOS);

    List<ReaderBook> findOverTimeBorrowedBooksByBookNO(@Param("bookNO") String bookNO);
}
