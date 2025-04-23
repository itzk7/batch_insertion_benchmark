package org.example.service;

import org.example.entity.UserEntityPermission;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("plSqlStrategy")
@Order(5)
public class PlSqlInsertStrategy implements UserEntityPermissionBulkInsertStrategy {

	private final JdbcTemplate jdbcTemplate;

	public PlSqlInsertStrategy(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void bulkInsert(List<UserEntityPermission> permissions) {
		String sql = "CALL insert_user_entity_permission(?, ?)";

		jdbcTemplate.batchUpdate(sql, permissions, permissions.size(), (ps, permission) -> {
			ps.setString(1, permission.getUserId());
			ps.setString(2, permission.getEntityId());
		});
	}

	@Override
	public String getStrategyName() {
		return "pl-sql";
	}
}
