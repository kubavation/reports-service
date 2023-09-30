package com.durys.jakub.reportsservice.bundles.infrastructure.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
public class CreateReportBundleDTO {
    private String name;
    private Set<Long> reportIds;
}
