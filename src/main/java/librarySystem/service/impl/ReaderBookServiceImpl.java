package librarySystem.service.impl;

import librarySystem.dao.ReaderBookDao;
import librarySystem.domain.ReaderBook;
import librarySystem.service.ReaderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class ReaderBookServiceImpl implements ReaderBookService {

    private final ReaderBookDao readerBookDao;

    @Autowired
    public ReaderBookServiceImpl(ReaderBookDao readerBookDao) {
        this.readerBookDao = readerBookDao;
    }

    @Override
    public Integer findPageNumByCredNum(String credNum, Integer itemNumEveryPage) {
        Integer itemCount = readerBookDao.findCountByCredNum(credNum);
        Integer pageNum;
        if (itemCount % itemNumEveryPage == 0) {
            pageNum = itemCount / itemNumEveryPage;
        } else {
            pageNum = (itemCount / itemNumEveryPage) + 1;
        }
        return pageNum;
    }

    @Override
    public void renew(String credNum, String bookNO) {
        ReaderBook readerBook = readerBookDao.findByCredNumAndBookNO(credNum, bookNO);
        if (readerBook.getStatus().equals("续借中")){
            throw new RuntimeException("已经借阅过了！");
        }
        Long returnDateMillis = readerBook.getReturnDate().getTime();
        long oneMonth = 2592000000L;
        Date newReturnDate = new Date(returnDateMillis + oneMonth);
        readerBook.setReturnDate(newReturnDate);
        readerBook.setStatus("续借中");
        readerBookDao.update(readerBook);
    }
}
