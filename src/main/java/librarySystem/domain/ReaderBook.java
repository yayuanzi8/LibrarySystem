package librarySystem.domain;

import java.util.Date;

public class ReaderBook {
    private Integer rbId;
    private Integer credNum;
    private String barCode;
    private Date returnDate;
    private Date borrowDate;
    private String status;
    private String bookNO;

    public Integer getRbId() {
        return rbId;
    }

    public void setRbId(Integer rbId) {
        this.rbId = rbId;
    }

    public String getBookNO() {
        return bookNO;
    }

    public void setBookNO(String bookNO) {
        this.bookNO = bookNO;
    }

    public Integer getCredNum() {
        return credNum;
    }

    public void setCredNum(Integer credNum) {
        this.credNum = credNum;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
