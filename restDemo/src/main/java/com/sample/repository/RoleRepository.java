package com.sample.repository;

import com.sample.models.Role;
import com.sample.models.Role;
import com.sample.repository.SessionUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Stateless
public class RoleRepository {
  
    public List<Role> getAllRoles() {
    	Session session = SessionUtil.getSession();
    	Query query = session.createQuery("FROM Role");
    	List<Role> list = query.list();
    	session.close();
        return list;
    }

    public Role findById(int id){
    	Session session = SessionUtil.getSession();
    	Query query = session.createQuery("FROM Role where id= :id");
    	query.setInteger("id", id);
    	Object raw = query.uniqueResult();
    	
    	if(raw == null) {
    		return null;
    	}
    	else {
    		return (Role) raw;
    	}
    }
    
    public Role findByName(String name){
    	Session session = SessionUtil.getSession();
    	System.out.println(name);
    	Query query = session.createQuery("FROM Role where name= :name");
    	query.setString("name", name);
    	Object raw = query.uniqueResult();
    	
    	if(raw == null) {
    		return null;
    	}
    	else {
    		return (Role) raw;
    	}
    }

    public Role save(Role bean) {
    	Session session = SessionUtil.getSession();    
        Transaction tx = session.beginTransaction();
        Role temp = addRole(session,bean);    
        tx.commit();
        session.close();
        return temp;
    }
    
    private Role addRole(Session session, Role bean){
    	Role Role = new Role();
        
    	Role.setName("ROLE_" + bean.getName());
    	Role.setUser_id(bean.getUser_id());
        session.save(Role);
        return Role;
      }

    public boolean delete(Integer id) {
    	Session session = SessionUtil.getSession();
    	Transaction tx = session.beginTransaction();
    	String queryStr =  "DELETE FROM Role where id = :id";
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

    public boolean update(Role Role) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.beginTransaction();
        
        String hql = "UPDATE FROM Role set name= :name, user_id = :user_id where id= :id";
        Query query = session.createQuery(hql);
        query.setInteger("id", Role.getId());
        query.setString("name", Role.getName());
        query.setInteger("user_id", Role.getUser_id());
        
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