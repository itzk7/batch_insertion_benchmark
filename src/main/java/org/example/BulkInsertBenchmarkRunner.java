package org.example;

import org.example.entity.UserEntityPermission;
import org.example.service.UserEntityPermissionBulkInsertStrategy;
import org.example.util.PermissionDataGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BulkInsertBenchmarkRunner {

    private final List<UserEntityPermissionBulkInsertStrategy> strategies;
    private final JdbcTemplate jdbcTemplate;
    private final int numberOfRecords;

    public BulkInsertBenchmarkRunner(
            List<UserEntityPermissionBulkInsertStrategy> strategies,
            JdbcTemplate jdbcTemplate,
            @Value("${benchmark.record.count}") int numerOfRecords) {
        this.strategies = strategies;
        this.jdbcTemplate = jdbcTemplate;
        this.numberOfRecords = numerOfRecords;
    }

    public void runBenchmark() {
        for (UserEntityPermissionBulkInsertStrategy strategy : strategies) {
            List<UserEntityPermission> permissions = PermissionDataGenerator.generatePermissions(numberOfRecords);

            System.out.println("Running strategy: " + strategy.getStrategyName());

            clearTable();

            long startTime = System.currentTimeMillis();
            strategy.bulkInsert(permissions);
            long endTime = System.currentTimeMillis();

            long duration = endTime - startTime;

            long count = countRecords();

            System.out.println("Inserted: " + count + " records using " +
                    strategy.getStrategyName() + " in " + duration + " ms");
            System.out.println("--------------------------------------------------");
        }
    }

    private void clearTable() {
        jdbcTemplate.execute("TRUNCATE TABLE user_entity_permission");
    }

    private long countRecords() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_entity_permission", Long.class);
    }
}
