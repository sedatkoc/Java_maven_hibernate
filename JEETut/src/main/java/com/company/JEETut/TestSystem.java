package com.company.JEETut;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class TestSystem {
	
	private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
			.createEntityManagerFactory("JEETut");
	
	public static void main(String[] args) {
		
		getCustomer(1);
		ENTITY_MANAGER_FACTORY.close();
	}
	
	public static void addCustomer(int id, String fname, String lname) {
		EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
		
		EntityTransaction et=null;
		
		
		try {
			et = em.getTransaction();
			et.begin();
			
			Customer cust= new Customer();
			cust.setID(id);
			cust.setFName(fname);
			cust.setLName(lname);
			em.persist(cust);
			
			et.commit();
			
		}
		catch (Exception ex) {
			if (et != null) {
				et.rollback();
			}
			ex.printStackTrace();
		}
		finally {
			em.close();
		}
	}
	
	 public static void getCustomer(int id) {
	    	EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
	    	
	    	// the lowercase c refers to the object
	    	// :custID is a parameterized query thats value is set below
	    	String query = "SELECT c FROM Customer c WHERE c.id = :custID";
	    	
	    	// Issue the query and get a matching Customer
	    	TypedQuery<Customer> tq = em.createQuery(query, Customer.class);
	    	tq.setParameter("custID", id);
	    	
	    	Customer cust = null;
	    	try {
	    		// Get matching customer object and output
	    		cust = tq.getSingleResult();
	    		System.out.println(cust.getFName() + " " + cust.getLName());
	    	}
	    	catch(NoResultException ex) {
	    		ex.printStackTrace();
	    	}
	    	finally {
	    		em.close();
	    	}
	    }
	public static void getCustomers() {
		EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
		
		String strQuery = "SELECT c FROM Customer c WHERE c.id IS NOT NULL";
		
		TypedQuery<Customer> tq = em.createQuery(strQuery,Customer.class);
		
		List<Customer> custs;
		
		try {
			custs = tq.getResultList();
			custs.forEach(cust->System.out.println(cust.getFName()+ 
					" "+ cust.getLName()));
		}
		catch (NoResultException e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
	}
	public static void changeFName(int id, String fname) {
		EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
		
		EntityTransaction et=null;
		Customer cust = null;
		
		
		try {
			et = em.getTransaction();
			et.begin();
			
			cust = em.find(Customer.class, id);
			cust.setFName(fname);
			
			em.persist(cust);
			
			et.commit();
			
		}
		catch (Exception ex) {
			if (et != null) {
				et.rollback();
			}
			ex.printStackTrace();
		}
		finally {
			em.close();
		}
	}
	public static void deleteCustomer(int id) {
		EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
		
		EntityTransaction et=null;
		Customer cust = null;
		
		
		try {
			et = em.getTransaction();
			et.begin();
			
			cust = em.find(Customer.class, id);
			em.remove(cust);
			
			em.persist(cust);
			
			et.commit();
			
		}
		catch (Exception ex) {
			if (et != null) {
				et.rollback();
			}
			ex.printStackTrace();
		}
		finally {
			em.close();
		}
	}	
}
