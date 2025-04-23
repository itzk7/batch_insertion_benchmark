package org.example.service;

import org.example.entity.UserEntityPermission;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.List;

@Component("csv-upload")
@Order(6)
public class CsvUploadInsertStrategy implements UserEntityPermissionBulkInsertStrategy {

	private final DataSource dataSource;

	public CsvUploadInsertStrategy(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void bulkInsert(List<UserEntityPermission> permissions) {
		File tempFile = null;
		try {
			// Step 1: Create temp CSV file
			tempFile = File.createTempFile("permissions-", ".csv");
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
				for (UserEntityPermission permission : permissions) {
					writer.write(permission.getUserId() + "," + permission.getEntityId());
					writer.newLine();
				}
			}

			// Step 2: Perform COPY operation
			try (Connection conn = dataSource.getConnection();
				 FileReader reader = new FileReader(tempFile)) {

				CopyManager copyManager = new CopyManager((BaseConnection) conn.unwrap(BaseConnection.class));
				copyManager.copyIn("COPY user_entity_permission (user_id, entity_id) FROM STDIN WITH (FORMAT csv)", reader);
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed to perform CSV upload", e);
		} finally {
			// Step 3: Delete temp CSV file
			if (tempFile != null && tempFile.exists()) {
				boolean deleted = tempFile.delete();
				if (!deleted) {
					System.err.println("Warning: Failed to delete temp CSV file: " + tempFile.getAbsolutePath());
				}
			}
		}
	}

	@Override
	public String getStrategyName() {
		return "csv-upload";
	}
}
