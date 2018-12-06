package com.ydt.dao;

import com.ydt.entity.ObjectControl;
import com.ydt.entity.Roles;

import java.util.List;
import java.util.Set;

public interface RoleObjectControleDAO {
    public List<ObjectControl> getAllObjectControlOfRole(int idUser);
    public List<Roles> getRoleByUser(int userId);
}
