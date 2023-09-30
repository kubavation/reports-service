package com.durys.jakub.reportsservice.bundles.infrastructure.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReportBundleDTO {
    private String name;

    public ReportBundleDTO(String name) {
        this.name = name;
    }
}
