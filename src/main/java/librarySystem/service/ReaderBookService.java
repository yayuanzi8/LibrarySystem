package librarySystem.service;

import librarySystem.webDomain.ReaderBorrowHistory;

import java.util.Date;
import java.util.List;

public interface ReaderBookService {
    Integer findPageNumByCredNum(Integer credNum, Integer itemNumEveryPage);

    void renew(Integer credNum, String barCode);

    Integer findOverTimeBorrowedBooks(Integer itemNumEveryPage);

    List<ReaderBorrowHistory> getOverTimeBooks(Integer pageNum, Integer itemNumEveryPage);

    Integer findOverTimeBorrowedBooksCountByBookName(String bookName, Integer itemCountEveryPage);

    List<ReaderBorrowHistory> getOverTimeBooksByBookName(String bookName, Integer pageNum, Integer itemCountEveryPage);

    Integer findOverTimeBorrowedBooksCountByBookNO(String bookNO, Integer itemCountEveryPage);

    List<ReaderBorrowHistory> getOverTimeBooksByBookNO(String bookNO, Integer pageNum, Integer itemCountEveryPage);

    Integer findOverTimeBorrowedBooksCountByReturnDate(Date returnDate, Integer itemCountEveryPage);

    List<ReaderBorrowHistory> getOverTimeBooksByReturnDate(Date returnDate, Integer pageNum, Integer itemCountEveryPage);

    void returnBook(Integer credNum, String barCode) throws Exception;

    Integer getSpecifyBookBorrowPageNum(String bookNO, Integer itemCountEveryPage);

    List<ReaderBorrowHistory> findSpecifyBookBorrowHistory(String bookNO, Integer pageNum, Integer itemCountEveryPage);

    Integer getSpecifyBookBorrowPageNumByBarCode(String barCode, Integer itemCountEveryPage);

    List<ReaderBorrowHistory> findSpecifyBookBorrowHistoryByBarCode(String barCode, Integer pageNum, Integer itemCountEveryPage);

    //蔡越
    void add(Integer credNum, String barCode, String bookNO, Date borrowDate, Date borrowDate1, String status);

    void borrowBook(Integer credNum, String barCode, String bookNO, Date returnDate, Date borrowDate, String status);
}
