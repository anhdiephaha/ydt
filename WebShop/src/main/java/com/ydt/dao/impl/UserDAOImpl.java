package com.ydt.dao.impl;

import com.ydt.dao.UserDAO;
import com.ydt.entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<Users> getAllUsers() {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        String hql = "FROM Users";
        Query query = session.createQuery(hql);
        List<Users> lst = query.list();
        session.close();
        return lst;
    }

    @Override
    public Users getUserById(int id) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        String hql = "FROM Users where userId=:userId";
        Query query = session.createQuery(hql);
        query.setParameter("userId",id);
        Users users = (Users) query.uniqueResult();
        return users;
    }
}
