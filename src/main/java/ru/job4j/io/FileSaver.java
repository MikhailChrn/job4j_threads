package ru.job4j.io;

import java.io.*;

public class FileSaver {
    private final File file;

    public FileSaver(final File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file))) {
            output.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
