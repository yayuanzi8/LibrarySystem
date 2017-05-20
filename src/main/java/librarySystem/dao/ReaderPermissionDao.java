package librarySystem.dao;

import librarySystem.domain.ReaderPermission;

import java.util.List;

public interface ReaderPermissionDao {
    List<ReaderPermission> findByCredNum(String credNum);
}
