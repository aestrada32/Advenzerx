package com.tap5.hotelbooking.pages;

import java.io.File;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.hibernate.Session;

import com.tap5.hotelbooking.dal.CrudServiceDAO;
import com.tap5.hotelbooking.services.Authenticator;

public class UploadFoto 
{
	@Property
	private UploadedFile file;
	
	@Persist(PersistenceConstants.FLASH)
	@Property
	private String message;
	
	@Inject
	private ApplicationGlobals globals;
	
	@Inject
    private Authenticator authenticator;

    @Inject
    private CrudServiceDAO dao;
    
    @Inject
    private Session session;
    
    @SuppressWarnings("unused")
	private Error errors;
	
	public void onSuccess()
    {
		String [] allowedExtensions = {"png", "jpg", "gif"};
		try
        {
        	File copied = new File("src/main/webapp/static/" + file.getFileName());
        	int i = copied.getName().lastIndexOf('.');
        	String fileExt = copied.getName().substring(i + 1);
        	if(fileExt.equals(allowedExtensions[0]) 
        			|| fileExt.equals(allowedExtensions[1])
        			|| fileExt.equals(allowedExtensions[2]))
        	{
        		file.write(copied);
        	}
        	else
        	{
        		throw new IllegalArgumentException("Se ha seleccionado una extensión Incorrecta");
        	}
        }
        catch(Exception ex)
        {
        	message = ex.getMessage();
        	System.out.println(message);
        }
    }
	
	Object onUploadException(FileUploadException ex)
	{
		message = "Upload exception: " + ex.getMessage();
		return this;
	}
}