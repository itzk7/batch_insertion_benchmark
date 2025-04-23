package org.example;

import org.example.entity.UserEntityPermission;
import org.example.service.UserEntityPermissionBulkInsertStrategy;
import org.example.util.PermissionDataGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class BulkInsertBenchmarkRunner {

    private final List<UserEntityPermissionBulkInsertStrategy> strategies;
    private final JdbcTemplate jdbcTemplate;

    public BulkInsertBenchmarkRunner(
            List<UserEntityPermissionBulkInsertStrategy> strategies,
            JdbcTemplate jdbcTemplate) {
        this.strategies = strategies;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Long> runBenchmark(int numberOfRecords) {
        Map<String, Long> results = new LinkedHashMap<>();
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
            results.put(strategy.getStrategyName(), duration);
        }

        return results;
    }

    private void clearTable() {
        jdbcTemplate.execute("TRUNCATE TABLE user_entity_permission");
    }

    private long countRecords() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_entity_permission", Long.class);
    }
}
