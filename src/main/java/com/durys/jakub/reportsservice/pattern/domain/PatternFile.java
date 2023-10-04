package com.durys.jakub.reportsservice.pattern.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class PatternFile {
    private byte[] file;
    private String fileName;
}
