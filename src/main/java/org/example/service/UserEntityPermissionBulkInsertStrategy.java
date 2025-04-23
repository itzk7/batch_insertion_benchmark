package org.example.service;


import org.example.entity.UserEntityPermission;
import java.util.List;

public interface UserEntityPermissionBulkInsertStrategy {
	void bulkInsert(List<UserEntityPermission> permissions);
	String getStrategyName();
}