package librarySystem.dao;

import librarySystem.domain.Reader;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ReaderDao {
    Reader findByCredNum(@Param("cred_num") Integer cred_num);

    Reader findByEmail(@Param("email") String email);

    void update(@Param("reader") Reader reader);

    Integer getReaderNum();

    List<Reader> findAll(@Param("start") Integer start, @Param("itemCountEveryPage") Integer itemCountEveryPage);

    Integer getReaderNumByCredNum(@Param("credNum") Integer credNum);

    List<Reader> findByCredNumHazily(@Param("credNum") Integer credNum, @Param("start") Integer start, @Param("itemCountEveryPage") Integer itemCountEveryPage);

    Integer getReaderNumByName(@Param("name") String name);

    List<Reader> findByNameHazily(@Param("name") String name, @Param("start") Integer start, @Param("itemCountEveryPage") Integer itemCountEveryPage);

    Integer getReaderNumByEntryDate(Date entryDate);

    List<Reader> findByStartTime(@Param("entryDate") Date entryDate, @Param("start") Integer start, @Param("itemCountEveryPage") Integer itemCountEveryPage);

    Integer getMaxCredNum();

    void add(@Param("reader") Reader reader);
}
