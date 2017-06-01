package librarySystem.service;

import librarySystem.domain.Reader;
import librarySystem.webDomain.ReaderBorrowHistory;

import java.util.Date;
import java.util.List;

public interface ReaderService {
    Reader findByEmail(String email);

    Reader findByCredNum(Integer credNum);

    void update(Reader reader);

    List<ReaderBorrowHistory> getReaderBorrowHistory(Integer credNum,Integer pageNum,Integer itemNumEveryPage);

    List<ReaderBorrowHistory> findSpecifyReaderBorrowingBooks(Integer credNum);

    Integer getReaderPageNum(Integer itemCountEveryPage);

    List<Reader> findAllReaderInPagination(Integer pageNum, Integer itemCountEveryPage);

    Integer getReaderPageNumByCredNum(Integer credNum,Integer itemCountEveryPage);

    List<Reader> findReadersByCredNumInPagination(Integer credNum, Integer pageNum, Integer itemCountEveryPage);

    Integer getReaderPageNumByName(String name, Integer itemCountEveryPage);

    List<Reader> findReadersByNameInPagination(String name, Integer pageNum, Integer itemCountEveryPage);

    Integer getReaderPageNumByEntryDate(Date entryDate, Integer itemCountEveryPage);

    List<Reader> findReadersByEntryDateInPagination(Date entryDate, Integer pageNum, Integer itemCountEveryPage);

    void addReader(String name, String email,String readerType);
}
