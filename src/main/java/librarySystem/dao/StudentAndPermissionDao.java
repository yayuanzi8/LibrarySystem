package librarySystem.dao;

import librarySystem.domain.Student_Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentAndPermissionDao {
    List<Student_Permission> findBySno(@Param("sno") String sno);
}
