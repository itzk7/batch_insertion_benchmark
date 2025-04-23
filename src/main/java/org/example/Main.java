package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Main.class, args);

		BulkInsertBenchmarkRunner runner = context.getBean(BulkInsertBenchmarkRunner.class);

		Map<Integer, Map<String, Long>> result = new HashMap<>();
		for(int i=100;i<=100;i*=10) {
			result.put(i, runner.runBenchmark(i));
		}


		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File("result.json"), result);
			System.out.println("Map saved to map.json");
		} catch (IOException e) {
			System.err.println("something went wrong " +e);
		}
	}
}
