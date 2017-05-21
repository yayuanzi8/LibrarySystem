package librarySystem.dao;

import librarySystem.domain.Reader;
import org.apache.ibatis.annotations.Param;

public interface ReaderDao {
    Reader findByCredNum(@Param("cred_num") String cred_num);

    Reader findByEmail(@Param("email") String email);

    void update(@Param("reader") Reader reader);
}
