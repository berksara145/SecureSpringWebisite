package com.sample.repository;

import com.sample.models.Role;
import com.sample.models.User;
import com.sample.repository.SessionUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Stateless
public class UserRepository {
  
    public List<User> getAllUsers() {
    	Session session = SessionUtil.getSession();
    	Query query = session.createQuery("FROM User");
    	List<User> list = query.list();
    	session.close();
        return list;
    }

    public User findById(int id){
    	Session session = SessionUtil.getSession();
    	Query query = session.createQuery("FROM User where id= :id");
    	query.setInteger("id", id);
    	Object raw = query.uniqueResult();
    	
    	if(raw == null) {
    		return null;
    	}
    	else {
    		return (User) raw;
    	}
    }
    
    public User findByName(String name){
    	Session session = SessionUtil.getSession();
    	System.out.println(name);
    	Query query = session.createQuery("FROM User where name= :name");
    	query.setString("name", name);
    	Object raw = query.uniqueResult();
    	
    	if(raw == null) {
    		return null;
    	}
    	else {
    		return (User) raw;
    	}
    }

    public User save(User bean) {
    	Session session = SessionUtil.getSession();    
        Transaction tx = session.beginTransaction();
        User temp = addUser(session,bean);    
        tx.commit();
        session.close();
        return temp;
    }
    
    private User addUser(Session session, User bean){
    	User user = new User();
        
    	user.setName(bean.getName());
    	user.setSurname(bean.getSurname());
        user.setPassword(bean.getPassword());
        session.save(user);
        return user;
    }

    public boolean delete(Integer id) {
    	Session session = SessionUtil.getSession();
    	Transaction tx = session.beginTransaction();
    	String queryStr =  "DELETE FROM User where id = :id";
    	Query query = session.createQuery(queryStr);
    	query.setInteger("id", id);
    	
    	int rowCount = query.executeUpdate();
    	
    	tx.commit();
        session.close();
    	
        if(rowCount > 0) {
        	return true;
        }
        else{
        	return false;
        }
    	
    }

    public boolean update(User user) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        
        String hql = "UPDATE FROM User set name= :name, surname = :surname where id= :id";
        Query query = session.createQuery(hql);
        query.setInteger("id", user.getId());
        query.setString("name", user.getName());
        query.setString("surname", user.getSurname());
        
        int rowCount = query.executeUpdate();
        
        tx.commit();
        session.close();
        
        if(rowCount > 0) {
        	return true;
        }
        else{
        	return false;
        }
    }
}
