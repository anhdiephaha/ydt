package com.ydt.entity;
// Generated Dec 5, 2018 2:49:26 PM by Hibernate Tools 5.0.6.Final

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * ObjectsScreen generated by hbm2java
 */
@Entity
@Table(name = "objects_screen", catalog = "webshop1")
@Proxy(lazy = false)
public class ObjectsScreen implements java.io.Serializable {

	private int objectId;
//	private Integer objectLevel;
	private String objectName;
	@JsonIgnore
	private Set<ObjectControl> objectControls = new HashSet<ObjectControl>(0);
	@JsonIgnore
	private Set<RoleObject> roleObjects = new HashSet<RoleObject>(0);

	public ObjectsScreen() {
	}

	public ObjectsScreen(int objectId) {
		this.objectId = objectId;
	}

	public ObjectsScreen(int objectId, String objectName, Set<ObjectControl> objectControls,
			Set<RoleObject> roleObjects) {
		this.objectId = objectId;
		this.objectName = objectName;
		this.objectControls = objectControls;
		this.roleObjects = roleObjects;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "object_id", unique = true, nullable = false)
	public int getObjectId() {
		return this.objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

//	@Column(name = "object_level")
//	public Integer getObjectLevel() {
//		return this.objectLevel;
//	}
//
//	public void setObjectLevel(Integer objectLevel) {
//		this.objectLevel = objectLevel;
//	}
//
	@Column(name = "object_name", length = 200)
	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "objectsScreen")
	public Set<ObjectControl> getObjectControls() {
		return this.objectControls;
	}

	public void setObjectControls(Set<ObjectControl> objectControls) {
		this.objectControls = objectControls;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "objectsScreen")
	public Set<RoleObject> getRoleObjects() {
		return this.roleObjects;
	}

	public void setRoleObjects(Set<RoleObject> roleObjects) {
		this.roleObjects = roleObjects;
	}

}