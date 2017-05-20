package librarySystem.service;

import librarySystem.domain.Reader;

public interface ReaderService {
    Reader findByEmail(String email);

    Reader findByCredNum(String credNum);
}
