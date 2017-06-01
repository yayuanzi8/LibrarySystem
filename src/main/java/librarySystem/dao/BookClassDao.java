package librarySystem.dao;

import librarySystem.domain.BookClass;
import org.apache.ibatis.annotations.Param;

public interface BookClassDao {
    //蔡越
    BookClass findByCNum(@Param("cnum")String cnum);
}
