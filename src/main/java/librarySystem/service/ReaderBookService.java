package librarySystem.service;

public interface ReaderBookService {
    Integer findPageNumByCredNum(String credNum,Integer itemNumEveryPage);

    void renew(String credNum, String bookNO);
}
