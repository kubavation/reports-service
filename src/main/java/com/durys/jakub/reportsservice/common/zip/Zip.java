package com.durys.jakub.reportsservice.common.zip;

import com.durys.jakub.reportsservice.sharedkernel.model.GeneratedFile;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Zip {

    public static GeneratedFile pack(List<GeneratedFile> generatedFiles) throws Exception {

        var byteArrayOutputStream = new ByteArrayOutputStream();
        var zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

        for (GeneratedFile file: generatedFiles) {
            ZipEntry entry = new ZipEntry(file.fileName() + UUID.randomUUID());
            entry.setSize(file.file().length);
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write(file.file());
        }

        zipOutputStream.closeEntry();
        zipOutputStream.close();
        return new GeneratedFile(byteArrayOutputStream.toByteArray(), "reports.zip", null);
    }
}
