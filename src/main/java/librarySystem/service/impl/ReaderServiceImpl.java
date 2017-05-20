package librarySystem.service.impl;

import librarySystem.dao.ReaderDao;
import librarySystem.domain.Reader;
import librarySystem.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderServiceImpl implements ReaderService {

    private final ReaderDao readerDao;

    @Autowired
    public ReaderServiceImpl(ReaderDao readerDao) {
        this.readerDao = readerDao;
    }

    @Override
    public Reader findByEmail(String email) {
        return readerDao.findByEmail(email);
    }

    @Override
    public Reader findByCredNum(String credNum) {
        return readerDao.findByCredNum(credNum);
    }
}
