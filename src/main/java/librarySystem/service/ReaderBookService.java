package librarySystem.service;

import librarySystem.webDomain.ReaderBorrowHistory;

import java.util.Date;
import java.util.List;

public interface ReaderBookService {
    Integer findPageNumByCredNum(String credNum,Integer itemNumEveryPage);

    void renew(String credNum, String barCode);

    Integer findOverTimeBorrowedBooks(Integer itemNumEveryPage);

    List<ReaderBorrowHistory> getOverTimeBooks(Integer pageNum, Integer itemNumEveryPage);

    Integer findOverTimeBorrowedBooksCountByBookName(String bookName, Integer itemCountEveryPage);

    List<ReaderBorrowHistory> getOverTimeBooksByBookName(String bookName, Integer pageNum, Integer itemCountEveryPage);

    Integer findOverTimeBorrowedBooksCountByBookNO(String bookNO, Integer itemCountEveryPage);

    List<ReaderBorrowHistory> getOverTimeBooksByBookNO(String bookNO, Integer pageNum, Integer itemCountEveryPage);

    Integer findOverTimeBorrowedBooksCountByReturnDate(Date returnDate, Integer itemCountEveryPage);

    List<ReaderBorrowHistory> getOverTimeBooksByReturnDate(Date returnDate, Integer pageNum, Integer itemCountEveryPage);
}
