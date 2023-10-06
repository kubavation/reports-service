package com.durys.jakub.reportsservice.common.zip;

import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedFile;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class Zip {

    public static GeneratedFile pack(List<GeneratedFile> generatedFiles) {

        try(var byteArrayOutputStream = new ByteArrayOutputStream();
            var zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

            generatedFiles.stream()
                    .forEach(file -> {
                        try {
                            ZipEntry entry = toEntry(file);
                            zipOutputStream.putNextEntry(entry);
                            zipOutputStream.write(file.file());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

            zipOutputStream.closeEntry();
            return new GeneratedFile(byteArrayOutputStream.toByteArray(), "reports.zip", null);
        } catch (Exception e) {
            log.info("Error generating zip", e);
            throw new RuntimeException(e);
        }
    }

    private static ZipEntry toEntry(GeneratedFile file) {
        return new ZipEntry("%s.%s".formatted(file.fileName(), UUID.randomUUID()));
    }
}
