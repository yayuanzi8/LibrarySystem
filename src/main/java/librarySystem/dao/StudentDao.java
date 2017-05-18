package librarySystem.dao;

import librarySystem.domain.Book;
import librarySystem.domain.Student;

import java.util.List;

public interface StudentDao {
    Student find(String sno);
}
