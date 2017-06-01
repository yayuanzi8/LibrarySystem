package librarySystem.domain;

import java.util.Date;

public class Book {
    private Integer bookId;
    private String bookNO;
    private String ISBN;//
    private String bookName;
    private String subHead;//
    private String author;
    private String authorDesc;//
    private String press;
    private String pressYear;//
    private String translator;
    private String bookDesc;
    private Integer pageNum;
    private String bookImage;
    private String bookType;
    private String cnum;
    private Double price;
    private int searchNum;//0
    private int borrowNum;//浏览数
    private String score;//0
    private int scoreNum;//default 0
    private int storeNumber;//
    private int borrowNumber;//
    private Date addTime;
    private String shortCatalog;
    private String longCatalog;
    private String storeAddress;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getSubHead() {
        return subHead;
    }

    public void setSubHead(String subHead) {
        this.subHead = subHead;
    }

    public String getAuthorDesc() {
        return authorDesc;
    }

    public void setAuthorDesc(String authorDesc) {
        this.authorDesc = authorDesc;
    }

    public String getPressYear() {
        return pressYear;
    }

    public void setPressYear(String pressYear) {
        this.pressYear = pressYear;
    }

    public int getSearchNum() {
        return searchNum;
    }

    public void setSearchNum(int searchNum) {
        this.searchNum = searchNum;
    }

    public int getBorrowNum() {
        return borrowNum;
    }

    public void setBorrowNum(int borrowNum) {
        this.borrowNum = borrowNum;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(int scoreNum) {
        this.scoreNum = scoreNum;
    }

    public int getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(int storeNumber) {
        this.storeNumber = storeNumber;
    }

    public int getBorrowNumber() {
        return borrowNumber;
    }

    public void setBorrowNumber(int borrowNumber) {
        this.borrowNumber = borrowNumber;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getShortCatalog() {
        return shortCatalog;
    }

    public void setShortCatalog(String shortCatalog) {
        this.shortCatalog = shortCatalog;
    }

    public String getLongCatalog() {
        return longCatalog;
    }

    public void setLongCatalog(String longCatalog) {
        this.longCatalog = longCatalog;
    }

    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getBookNO() {
        return bookNO;
    }

    public void setBookNO(String bookNO) {
        this.bookNO = bookNO;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookNO='" + bookNO + '\'' +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", translator='" + translator + '\'' +
                ", bookDesc='" + bookDesc + '\'' +
                ", pageNum=" + pageNum +
                ", bookImage='" + bookImage + '\'' +
                ", bookType='" + bookType + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", cnum='" + cnum + '\'' +
                ", price=" + price +
                '}';
    }
}
