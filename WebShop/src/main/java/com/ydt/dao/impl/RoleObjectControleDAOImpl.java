package com.ydt.dao.impl;

import com.ydt.dao.RoleObjectControleDAO;
import com.ydt.entity.ObjectControl;
import com.ydt.entity.RoleUser;
import com.ydt.entity.RoleUserId;
import com.ydt.entity.Roles;
import com.ydt.payload.Payload;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class RoleObjectControleDAOImpl implements RoleObjectControleDAO {
    private static final Logger logger = LoggerFactory.getLogger(RoleObjectControleDAOImpl.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<ObjectControl> getAllObjectControlOfRole(int idUser) {
        List<Integer> lstObjectControl = new ArrayList<>();
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        //lấy list role theo id
        String hq = "select ru.id.roleId from RoleUser ru where ru.id.userId = :userId";
        Query qr = session.createQuery(hq);
        qr.setParameter("userId",idUser);
        List<Integer> idRole = qr.list();
        //lấy list object control id theo role
        String hql = "select distinct roc.id.objectControlId  from RoleObjectControl roc where roc.id.roleId in (:roleId)";
        Query query = session.createQuery(hql);
        query.setParameter("roleId",idRole);
        List<Integer> lst = query.list();
        for(Integer l: lst){
            lstObjectControl.add(l);
        }

        //Lấy list object control theo user
        String hql1 = "select ocu.id.objectControlId FROM ObjectControlUser ocu where ocu.id.userId = :idUser";
        Query query1 = session.createQuery(hql1);
        query1.setParameter("idUser",idUser);
        List<Integer> lstOC1 = query1.list();
        for(Integer l: lstOC1){
            lstObjectControl.add(l);
        }

        //lấy object control theo id object control
        String hql2 = "FROM ObjectControl oc where oc.objectControlId in (:idOC)";
        Query query2 = session.createQuery(hql2);
        query2.setParameter("idOC",lstObjectControl);
        List<ObjectControl> lstOC = query2.list();


        session.close();
        return lstOC;
    }

    @Override
    public List<Roles> getRoleByUser(int userId) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        String hql = "select ru.roles FROM RoleUser ru where ru.id.userId = :userId";
        Query query = session.createQuery(hql);
        query.setParameter("userId",userId);
        List<Roles> roles = query.list();
        session.close();
        return roles;
    }

    @Override
    public boolean saveRoleUser(RoleUserId roleUser) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        boolean rs = false;
        try {
            session.saveOrUpdate(roleUser);
            rs = true;
        }catch (Exception e){
            logger.error(e.getMessage());
            rs = false;
        }finally {
            session.close();
        }
        return rs;
    }
}
