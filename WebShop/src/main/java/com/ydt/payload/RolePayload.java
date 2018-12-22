package com.ydt.payload;

import com.ydt.entity.Roles;

import java.util.List;

public class RolePayload {
    private Roles roles;
    private List<Integer> arrScreen;
    private List<Integer> arrControl;

    public RolePayload() {

    }

    public RolePayload(Roles roles, List<Integer> arrScreen, List<Integer> arrControl) {
        this.roles = roles;
        this.arrScreen = arrScreen;
        this.arrControl = arrControl;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public List<Integer> getArrScreen() {
        return arrScreen;
    }

    public void setArrScreen(List<Integer> arrScreen) {
        this.arrScreen = arrScreen;
    }

    public List<Integer> getArrControl() {
        return arrControl;
    }

    public void setArrControl(List<Integer> arrControl) {
        this.arrControl = arrControl;
    }
}
