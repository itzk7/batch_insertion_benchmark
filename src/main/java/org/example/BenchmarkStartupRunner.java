//package org.example;
//
//import org.example.entity.UserEntityPermission;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//import static org.example.util.PermissionDataGenerator.generatePermissions;
//
//@Component
//public class BenchmarkStartupRunner implements CommandLineRunner {
//
//    private final BulkInsertBenchmarkRunner benchmarkRunner;
//
//    public BenchmarkStartupRunner(BulkInsertBenchmarkRunner benchmarkRunner) {
//        this.benchmarkRunner = benchmarkRunner;
//    }
//
//    @Override
//    public void run(String... args) {
//        List<UserEntityPermission> permissions =generatePermissions(100_000);
//        benchmarkRunner.runBenchmark(permissions);
//    }
//}
