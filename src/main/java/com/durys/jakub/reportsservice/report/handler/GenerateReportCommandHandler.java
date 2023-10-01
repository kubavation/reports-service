package com.durys.jakub.reportsservice.report.handler;

import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandling;
import com.durys.jakub.reportsservice.report.command.GenerateReportCommand;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

@CommandHandling
public class GenerateReportCommandHandler implements CommandHandler<GenerateReportCommand, ResponseEntity<Resource>> {


    @Override
    public ResponseEntity<Resource> handle(GenerateReportCommand command) {
        return null;
    }
}
