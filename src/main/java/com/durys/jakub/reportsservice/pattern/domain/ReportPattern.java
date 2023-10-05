package com.durys.jakub.reportsservice.pattern.domain;

import com.durys.jakub.reportsservice.common.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "REP_REPORT_PATTERN")
@Slf4j
public class ReportPattern {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private ReportPatternInfo informations;

    @Embedded
    private PatternFile patternFile;

    @OneToMany(
            mappedBy = "pattern",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ReportPatternParameter> parameters = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    public ReportPattern(ReportPatternInfo patternInfo, MultipartFile file) {
        this.informations = patternInfo;
        this.parameters = Set.of();
        this.status = Status.ACTIVE;
        withPatternFile(file);
    }

    public void markAsDeleted() {
        this.status = Status.DELETED;
    }

    public ReportPattern withFile(byte[] content) {
        patternFile.setFile(content);
        return this;
    }

    public ReportPattern withPatternFile(MultipartFile file) {
        try {
            this.patternFile = new PatternFile(file.getBytes(), file.getOriginalFilename());
        } catch (IOException e) {
            log.error("error saving file {}", e);
        }
        return this;
    }

    public String name() {
        return informations.getName();
    }

    public String subsystem() {
        return informations.getSubsystem();
    }

    public String description() {
        return informations.getDescription();
    }

    public void addParameter(String name, String type) {
        parameters.add(new ReportPatternParameter(name, type));
    }




}
