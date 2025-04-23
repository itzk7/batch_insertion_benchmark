package org.example.entity;

import javax.persistence.*;

@Entity
@Table(
		name = "user_entity_permission",
		uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "entity_id"})
)
public class UserEntityPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "entity_id", nullable = false)
	private String entityId;

	public UserEntityPermission() {}

	public UserEntityPermission(String userId, String entityId) {
		this.userId = userId;
		this.entityId = entityId;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	public String getEntityId() { return entityId; }
	public void setEntityId(String entityId) { this.entityId = entityId; }
}
