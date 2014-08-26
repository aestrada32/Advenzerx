package com.tap5.hotelbooking.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.tap5.hotelbooking.entities.User;

public class UserFinderService
{
	/**
	 * Entity Manager Library
	 */
	@PersistenceContext(unitName = "hotelbooking")
	private EntityManager manager;
	
	/**
	 * Find user within the Entities
	 * @param id
	 * @return
	 */
	public User findUser(Long id)
	{
		return manager.find(User.class, id);
	}
	
	/**
	 * Counts all users
	 * @return user count
	 */
	public long countUsers() 
	{
        return (Long) manager.createQuery("select count(u) from User u").getSingleResult();
    }
	
	/**
	 * 
	 * @param maxResults
	 * @return list of users
	 */
	@SuppressWarnings("unchecked")
    public List<User> findPersons(int maxResults)
    {
        return manager.createQuery("select u from Person u order by lower(u.username), lower(u.fullname)")
                .setMaxResults(maxResults).getResultList();
    }
	
	/**
	 * 
	 * @param partialName
	 * @param maxResults
	 * @return List of users using a string
	 */
	@SuppressWarnings("unchecked")
    public List<User> findUsers(String partialName, int maxResults) {
        String searchName = partialName == null ? "" : partialName.toLowerCase();

        StringBuilder buf = new StringBuilder();
        buf.append("select u from User u");
        buf.append(" where lower(username) like :username");
        buf.append(" or lower(lastName) like :lastName");
        buf.append(" order by lower(u.fullname), lower(u.username)");

        Query q = manager.createQuery(buf.toString());
        q.setParameter("firstName", "%" + searchName + "%");
        q.setParameter("lastName", "%" + searchName + "%");

        List<User> l = q.setMaxResults(maxResults).getResultList();
        return l;
    }
	
	/**
	 * Edit a single user
	 * @param user
	 */
	public void changeUser(User user)
	{
		User u = manager.merge(user);
		if(!u.getId().equals(user.getId()))
		{
			throw new EntityNotFoundException("Usuario no existente");
		}
	}
	
	/**
	 * Edit a set of users
	 * @param users
	 */
	public void changePersons(List<User> users)
	{
		for(User user : users)
		{
			User u = manager.merge(user);
			if(!u.getId().equals(user.getId()))
			{
				throw new EntityNotFoundException("Usuario no existente");
			}
		}
	}
}
