package org.example.service;

import org.example.entity.UserEntityPermission;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("entityManagerWithConcurrencyStrategy")
@Order(3)
public class HibernateEntityManagerWithConcurrency implements UserEntityPermissionBulkInsertStrategy {

	private static final int THREADS = 5;
	private static final int BATCH_SIZE = 1000;

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	private final PlatformTransactionManager transactionManager;

	public HibernateEntityManagerWithConcurrency(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Override
	public void bulkInsert(List<UserEntityPermission> permissions) {
		int chunkSize = (int) Math.ceil((double) permissions.size() / THREADS);
		ExecutorService executor = Executors.newFixedThreadPool(THREADS);

		for (int i = 0; i < THREADS; i++) {
			int start = i * chunkSize;
			int end = Math.min(start + chunkSize, permissions.size());
			List<UserEntityPermission> subList = permissions.subList(start, end);

			executor.submit(() -> {
				TransactionTemplate template = new TransactionTemplate(transactionManager);
				template.execute(status -> {
					EntityManager em = entityManagerFactory.createEntityManager();
					try {
						em.getTransaction().begin();
						for (int j = 0; j < subList.size(); j++) {
							em.persist(subList.get(j));
							if (j > 0 && j % BATCH_SIZE == 0) {
								em.flush();
								em.clear();
							}
						}
						em.flush();
						em.clear();
						em.getTransaction().commit();
					} catch (Exception e) {
						System.err.println("something went wrong " + e);
						e.printStackTrace();
						if (em.getTransaction().isActive()) {
							em.getTransaction().rollback();
						}
					} finally {
						em.close();
					}
					return null;
				});
			});
		}

		executor.shutdown();
		try {
			if (!executor.awaitTermination(15, java.util.concurrent.TimeUnit.MINUTES)) {
				executor.shutdownNow();
				System.err.println("Executor did not terminate in time. terminate state: " + executor.isTerminated());
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public String getStrategyName() {
		return "entity-manager-concurrent";
	}
}
