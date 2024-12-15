package com.danal.publicdataprocessor.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component("LoggingChunkListener")
public class LoggingChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("Starting a new chunk. Step: [{}]", context.getStepContext().getStepName());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("Chunk completed in Step: [{}]. Total Read: {}, Total Processed: {}, Total Written: {}",
                context.getStepContext().getStepName(),
                context.getStepContext().getStepExecution().getReadCount(),
                context.getStepContext().getStepExecution().getReadCount() - context.getStepContext().getStepExecution().getFilterCount(),
                context.getStepContext().getStepExecution().getWriteCount());
    }


    @Override
    public void afterChunkError(ChunkContext context) {
        log.error("Error occurred in chunk. Step: [{}]", context.getStepContext().getStepName());
    }
}
