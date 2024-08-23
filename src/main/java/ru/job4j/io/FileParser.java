package ru.job4j.io;

import java.io.*;
import java.util.Arrays;
import java.util.function.Predicate;

public class FileParser {
    private final File file;

    public FileParser(final File file) {
        this.file = file;
    }

    public String getContent(Predicate<Integer> predicate) throws IOException {
        StringBuilder content = new StringBuilder();
        String buffer = "";
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            buffer = Arrays.toString(input.readAllBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        buffer.chars()
                .filter(predicate::test)
                .forEach(content::append);
        return content.toString();
    }
}
