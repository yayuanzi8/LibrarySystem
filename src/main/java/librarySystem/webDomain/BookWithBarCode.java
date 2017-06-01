package librarySystem.webDomain;

import librarySystem.domain.Book;

import java.io.Serializable;

public class BookWithBarCode implements Serializable {
    private String barCode;
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
