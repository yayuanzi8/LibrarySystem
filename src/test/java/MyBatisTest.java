import librarySystem.config.RootConfig;
import librarySystem.dao.BookCLCDao;
import librarySystem.dao.BookDao;
import librarySystem.domain.Book;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class MyBatisTest {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BookCLCDao bookCLCDao;

    @Test
    public void testConn() {
        SqlSession session = sqlSessionFactory.openSession();
        session.selectList("librarySystem.dao.BookDao.getAllBooks");
    }

    @Test
    public void testFindBooks() {
        List<Book> bookList = bookDao.getAllBooks();
        System.out.println(bookList.get(0));
    }

    @Test
    public void generateBarCode() {
        String latestBarCode = bookCLCDao.getLatestBarCode();
        latestBarCode = latestBarCode.substring(1);
        int length = latestBarCode.length();
        Integer latestBarCodeInteger = Integer.parseInt(latestBarCode);
        String barCode = (latestBarCodeInteger + 1) + "";
        StringBuilder sb = new StringBuilder();
        if (barCode.length() < length) {
            for (int i = 0; i < length - barCode.length(); i++) {
                sb.append(0);
            }
        }
        barCode = sb.toString() + barCode;

        System.out.println(barCode);
    }
}
