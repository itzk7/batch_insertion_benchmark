package org.example.service;

import org.example.entity.UserEntityPermission;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("nativeSqlStrategy")
@Order(4)
public class NativeSqlInsertStrategy implements UserEntityPermissionBulkInsertStrategy {

	private final JdbcTemplate jdbcTemplate;

	public NativeSqlInsertStrategy(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void bulkInsert(List<UserEntityPermission> permissions) {
		String sql = "INSERT INTO user_entity_permission (user_id, entity_id) VALUES (?, ?)";

		jdbcTemplate.batchUpdate(sql, permissions, permissions.size(), (ps, permission) -> {
			ps.setString(1, permission.getUserId());
			ps.setString(2, permission.getEntityId());
		});
	}

	@Override
	public String getStrategyName() {
		return "native-sql";
	}
}
