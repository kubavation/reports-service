package com.durys.jakub.reportsservice.bundles.domain.command;

import com.durys.jakub.reportsservice.cqrs.command.Command;

public record DeleteReportBundleCommand(Long bundleId) implements Command<Void> {
}
