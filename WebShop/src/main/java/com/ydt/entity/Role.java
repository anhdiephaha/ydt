package com.ydt.entity;

import javax.persistence.*;

@Entity
@Table(name = "roles", catalog = "webshop1")
public class role implements java.io.Serializable{
    private int roleId;
    private String roleName;
    public role() {
    }
    public role(int roleId) {
        this.roleId = roleId;
    }
    public role(int roleId, String roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false)
    public int getRoleId() {
        return this.roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Column(name = "role_name", length = 200)
    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
