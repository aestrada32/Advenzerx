package com.tap5.hotelbooking.pages;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.hotelbooking.entities.User;
import com.tap5.hotelbooking.services.Authenticator;

public class UserUpdate 
{
	
	@SuppressWarnings("unused")
    @Property
    @PageActivationContext
    private User user;
	
	@Persist(PersistenceConstants.FLASH)
    private Integer versionFlash;
	
	@Component(id = "userForm")
    private BeanEditForm form;
	
	@Inject
    private Authenticator authenticator;

    @Inject
    private Messages messages;
    
    @PersistenceContext(unitName = "hotelbooking")
	private EntityManager manager;
    
//    private String username;
//    
//    private String fullname;
//    
//    private String email;
//    
//    Object onSuccess()
//    {
//    	Class<? extends BeanEditForm> updated = form.getClass();
//    }
    
    
}
