package librarySystem.dao;

import librarySystem.domain.ReaderBook;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ReaderBookDao {

    List<ReaderBook> findByCredNum(@Param("credNum") Integer credNum, @Param("start") Integer start, @Param("itemNumEveryPage") Integer itemNumEveryPage);
    /**
     * 查找指定用户的借阅数目
     * @param credNum
     * @return
     */
    Integer findCountByCredNum(@Param("credNum") Integer credNum);

    List<ReaderBook> findBorrowing(@Param("credNum") Integer credNum);


    void update(@Param("readerBook") ReaderBook readerBook);

    ReaderBook findByCredNumAndBarCode(@Param("credNum") Integer credNum, @Param("barCode") String barCode);

    Integer findOverTimeBorrowedBooksCount(@Param("today") Date today);

    List<ReaderBook> findOverTimeBooks(@Param("start") Integer start, @Param("itemNumEveryPage") Integer itemNumEveryPage, @Param("today") Date today);

    Integer findCountByBookNO(@Param("bookNOS") String[] bookNOS);

    List<ReaderBook> findByBookNOS(@Param("bookNOS") List<String> bookNOS);

    List<ReaderBook> findOverTimeBorrowedBooksByBookNO(@Param("bookNO") String bookNO);

    ReaderBook findByCredNumAndBarCodeAndStatus(@Param("credNum") Integer credNum, @Param("barCode") String barCode, @Param("status") String status);

    Integer getSpecifyBookBorrowCount(@Param("bookNO") String bookNO);

    List<ReaderBook> findSpecifyBookBorrowHistory(@Param("bookNO") String bookNO, @Param("start") Integer start, @Param("itemCountEveryPage") Integer itemCountEveryPage);

    Integer findBorrowingCountByBookNO(@Param("bookNO") String bookNO);

    void deleteByBookNO(@Param("bookNO") String bookNO);

    Integer findBorrowingCountByBarCode(@Param("barCode") String barCode);

    void deleteByBarCode(@Param("barCode") String barCode);

    Integer getSpecifyBookBorrowCountByBarCode(@Param("barCode") String barCode);

    List<ReaderBook> findSpecifyBookBorrowHistoryByBarCode(@Param("barCode") String barCode, @Param("start") Integer start, @Param("itemCountEveryPage") Integer itemCountEveryPage);

    //蔡越
    void add(@Param("credNum") Integer credNum, @Param("barCode") String barCode, @Param("bookNO") String bookNO, @Param("returnDate") Date returnDate,@Param("borrowDate") Date borrowDate, @Param("status") String status);
}
