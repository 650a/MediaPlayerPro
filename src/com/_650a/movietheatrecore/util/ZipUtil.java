package com._650a.movietheatrecore.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipUtil {

    private ZipUtil() {
    }

    public static void zipDirectory(Path sourceDir, Path zipFile) throws IOException {
        Path root = sourceDir.toAbsolutePath();
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(zipFile)))) {
            Files.walk(root)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        String entryName = root.relativize(path).toString().replace("\\", "/");
                        ZipEntry entry = new ZipEntry(entryName);
                        try {
                            entry.setTime(Files.getLastModifiedTime(path).toMillis());
                            zos.putNextEntry(entry);
                            Files.copy(path, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to zip " + path + ": " + e.getMessage(), e);
                        }
                    });
        } catch (RuntimeException e) {
            if (e.getCause() instanceof IOException io) {
                throw io;
            }
            throw e;
        }
    }
}
