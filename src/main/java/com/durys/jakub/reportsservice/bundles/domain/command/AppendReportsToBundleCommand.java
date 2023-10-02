package com.durys.jakub.reportsservice.bundles.domain.command;

import com.durys.jakub.reportsservice.cqrs.command.Command;

import java.util.Set;

public record AppendReportsToBundleCommand(Long bundleId, Set<Long> reportIds) implements Command<Void> {
}
