package org.example.service;

import org.example.entity.UserEntityPermission;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component("entityManagerStrategy")
@Order(2)
public class HibernateEntityManagerStrategy implements UserEntityPermissionBulkInsertStrategy {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void bulkInsert(List<UserEntityPermission> permissions) {
		int batchSize = 1000;

		for (int i = 0; i < permissions.size(); i++) {
			entityManager.persist(permissions.get(i));
			if (i % batchSize == 0 && i > 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}

		entityManager.flush();
		entityManager.clear();
	}

	@Override
	public String getStrategyName() {
		return "entity-manager";
	}
}
