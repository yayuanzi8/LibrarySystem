package librarySystem.service;

import librarySystem.domain.Reader;
import librarySystem.webDomain.ReaderBorrowHistory;

import java.util.Date;
import java.util.List;

public interface ReaderService {
    Reader findByEmail(String email);

    Reader findByCredNum(String credNum);

    void update(Reader reader);

    List<ReaderBorrowHistory> getReaderBorrowHistory(String credNum,Integer pageNum,Integer itemNumEveryPage);

    List<ReaderBorrowHistory> findSpecifyReaderBorrowingBooks(String credNum);

    Integer getReaderPageNum(Integer itemCountEveryPage);

    List<Reader> findAllReaderInPagination(Integer pageNum, Integer itemCountEveryPage);

    Integer getReaderPageNumByCredNum(String credNum,Integer itemCountEveryPage);

    List<Reader> findReadersByCredNumInPagination(String credNum, Integer pageNum, Integer itemCountEveryPage);

    Integer getReaderPageNumByName(String name, Integer itemCountEveryPage);

    List<Reader> findReadersByNameInPagination(String name, Integer pageNum, Integer itemCountEveryPage);

    Integer getReaderPageNumByEntryDate(Date entryDate, Integer itemCountEveryPage);

    List<Reader> findReadersByEntryDateInPagination(Date entryDate, Integer pageNum, Integer itemCountEveryPage);
}
