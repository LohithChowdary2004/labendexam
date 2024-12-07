package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ClientDemo {
    public static void main(String[] args) {
        ClientDemo demo = new ClientDemo();
        demo.updateDepartment(1, "Information Technology", "Queens");
    }


    public void updateDepartment(int id, String newName, String newLocation) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Department.class); 

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();


            int updatedRows = session.createQuery("update Department set name = ?1, location = ?2 where id = ?3")
                .setParameter(1, newName)    
                .setParameter(2, newLocation) 
                .setParameter(3, id)        
                .executeUpdate();

            if (updatedRows > 0) {
                System.out.println("Department updated successfully.");
            } else {
                System.out.println("No Department found with the given ID.");
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
