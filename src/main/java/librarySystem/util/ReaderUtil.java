package librarySystem.util;

import librarySystem.domain.Book;
import librarySystem.domain.Reader;
import librarySystem.domain.ReaderBook;
import librarySystem.webDomain.ReaderBorrowHistory;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.http.HttpSession;

/**
 * Created by yayuanzi8 on 2017/5/19 0019.
 */
public class ReaderUtil {
    public static Reader getUserFromSecurityContext(HttpSession httpSession) {
        SecurityContext context = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        return (Reader) context.getAuthentication().getPrincipal();
    }

    public static ReaderBorrowHistory extractBorrowHistory(ReaderBook readerBook, Book book) {
        ReaderBorrowHistory history = new ReaderBorrowHistory();
        history.setBookNO(readerBook.getBookNO());
        history.setBarCode(readerBook.getBarCode());
        history.setCredNum(readerBook.getCredNum());
        history.setReturnDate(readerBook.getReturnDate());
        history.setBorrowDate(readerBook.getBorrowDate());
        history.setStatus(readerBook.getStatus());
        history.setBookName(book.getBookName());
        history.setAuthor(book.getAuthor());
        history.setCnum(book.getCnum());
        history.setStoreAddress(book.getStoreAddress());
        return history;
    }
}
