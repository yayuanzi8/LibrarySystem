package librarySystem.dao;

import librarySystem.domain.ReaderBook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReaderBookDao {

    List<ReaderBook> findByCredNum(@Param("credNum") String credNum, @Param("start") Integer start, @Param("itemNumEveryPage") Integer itemNumEveryPage);
    /**
     * 查找指定用户的借阅数目
     * @param credNum
     * @return
     */
    Integer findCountByCredNum(@Param("credNum") String credNum);

    List<ReaderBook> findBorrowing(@Param("credNum") String credNum);

    ReaderBook findByCredNumAndBookNO(@Param("credNum") String credNum, @Param("bookNO") String bookNO);

    void update(@Param("readerBook") ReaderBook readerBook);
}
