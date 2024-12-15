package com.danal.publicdataprocessor.batch.partitioner;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvPartitioner implements Partitioner {

    private final String filePath;
    private final int partitionCount;

    public CsvPartitioner(String filePath, int partitionCount) {
        this.filePath = filePath;
        this.partitionCount = partitionCount;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> partitions = new HashMap<>();

        int totalLines = getTotalLines(filePath); // 파일 총 라인 수 계산 (헤더 포함)
        if (totalLines <= 1) { // 파일이 빈 경우 또는 헤더만 있는 경우 처리
            throw new IllegalArgumentException("The file is empty or only contains a header: " + filePath);
        }

        int dataLines = totalLines - 1; // 헤더를 제외한 데이터 라인 수
        int linesPerPartition = dataLines / partitionCount;
        int remainder = dataLines % partitionCount;

        for (int i = 0; i < partitionCount; i++) {
            int startLine = i * linesPerPartition + 2; // 시작 라인 (헤더 + 1)
            int endLine = (i + 1) * linesPerPartition + 1;

            // 마지막 파티션에 남은 라인을 추가
            if (i == partitionCount - 1) {
                endLine += remainder;
            }

            ExecutionContext context = new ExecutionContext();
            context.putString("filePath", filePath);
            context.putInt("startLine", startLine);
            context.putInt("endLine", endLine);

            partitions.put("partition" + i, context);

            // 디버깅 로그
            System.out.printf("Partition %s: startLine=%d, endLine=%d, filePath=%s%n",
                    "partition" + i, startLine, endLine, filePath);
        }

        return partitions;
    }

    private int getTotalLines(String filePath) {
        // 파일 총 라인 수를 읽어오는 로직
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return (int) reader.lines().count();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read total lines from file: " + filePath, e);
        }
    }
}
