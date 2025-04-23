package org.example.service;

import org.example.entity.UserEntityPermission;
import org.example.repository.UserEntityPermissionRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("repositorySaveAllStrategy")
@Order(1)
public class RepositorySaveAllInsertStrategy implements UserEntityPermissionBulkInsertStrategy {

	private final UserEntityPermissionRepository repository;

	public RepositorySaveAllInsertStrategy(UserEntityPermissionRepository repository) {
		this.repository = repository;
	}

	@Override
	public void bulkInsert(List<UserEntityPermission> permissions) {
		repository.saveAll(permissions);
	}

	@Override
	public String getStrategyName() {
		return "repository-saveAll";
	}
}
