package ru.gbteam.lms.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
public class ReadData {
    public static Optional<byte[]> read(String path, String filename) {
        Optional<byte[]> data = Optional.empty();
        try {
            data = Optional
                    .of(Files.readAllBytes(Path.of(path, filename)));
        } catch (IOException e) {
            log.error("Can't read file {}", filename, e);
            throw new IllegalStateException(e);
        }
        return data;
    }
}
