package librarySystem.service;

import librarySystem.domain.Reader;
import librarySystem.webDomain.ReaderBorrowHistory;

import java.util.List;

public interface ReaderService {
    Reader findByEmail(String email);

    Reader findByCredNum(String credNum);

    void update(Reader reader);

    List<ReaderBorrowHistory> getReaderBorrowHistory(String credNum,Integer pageNum,Integer itemNumEveryPage);

    List<ReaderBorrowHistory> findSpecifyReaderBorrowingBooks(String credNum);
}
