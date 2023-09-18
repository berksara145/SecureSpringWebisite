package com.sample.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionUtil {
  
  private static SessionUtil instance=new SessionUtil();
  private SessionFactory sessionFactory;
  
  public static SessionUtil getInstance(){
      return instance;
  }
  
  private SessionUtil(){
    Configuration configuration = new Configuration();
    configuration.configure("hibernate.cfg.xml");
        
    StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties())
            .build();
    
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
  }
  
  public static Session getSession(){
    Session session =  getInstance().sessionFactory.openSession();
    
    return session;
  }
}
