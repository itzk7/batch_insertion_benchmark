package org.example.util;

import org.example.entity.UserEntityPermission;

import java.util.*;
import java.util.stream.IntStream;

public class PermissionDataGenerator {

//	public static List<UserEntityPermission> generatePermissions(int count) {
//		List<UserEntityPermission> permissions = new ArrayList<>(count);
//		for (int i = 0; i < count; i++) {
//			String userId = "user-" + (i % 1000); // reuse user ids
//			String entityId = "entity-" + UUID.randomUUID();
//			permissions.add(new UserEntityPermission(userId, entityId));
//		}
//		return permissions;
//	}
public static List<UserEntityPermission> generatePermissions(int count) {
	long startTime = System.currentTimeMillis();
	List<UserEntityPermission> permissions = Collections.synchronizedList(new ArrayList<>(count));

	IntStream.range(0, count).parallel().forEach(i -> {
		String userId = "user-" + (i % count); // reuse user ids
		String entityId = "entity-" + UUID.randomUUID();
		permissions.add(new UserEntityPermission(userId, entityId));
	});
	long endTime = System.currentTimeMillis();

	System.out.println("time taken to generate data " + (endTime - startTime));
	return permissions;
}

}
