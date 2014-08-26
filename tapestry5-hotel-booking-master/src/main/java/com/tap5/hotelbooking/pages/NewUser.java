package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.hotelbooking.dal.CrudServiceDAO;
import com.tap5.hotelbooking.dal.QueryParameters;
import com.tap5.hotelbooking.entities.Track;
import com.tap5.hotelbooking.entities.User;
import com.tap5.hotelbooking.services.Authenticator;

public class NewUser 
{
	@Property
    @Validate("username")
    private String username;

    @Property
    @Validate("required, minlength=3, maxlength=50")
    private String fullName;

    @Property
    @Validate("required,email")
    private String email;

    @Property
    @Validate("password")
    private String password;

    @Property
    @Validate("password")
    private String verifyPassword;
    
    @Property
    @Persist
    @Validate("required")
    private Track track;
    
    @Inject
    private CrudServiceDAO crudServiceDAO;

    @Component
    private Form registerForm;

    @Inject
    private Messages messages;

    @Inject
    private Authenticator authenticator;
    
    @OnEvent(value = EventConstants.VALIDATE, component = "RegisterForm")
    public void checkForm()
    {
        if (!verifyPassword.equals(password))
        {
            registerForm.recordError(messages.get("error.verifypassword"));
        }
    }
    
    @OnEvent(value = EventConstants.SUCCESS, component = "RegisterForm")
    public Object proceedSignup()
    {

        User userVerif = crudServiceDAO.findUniqueWithNamedQuery(
                User.BY_USERNAME_OR_EMAIL,
                QueryParameters.with("username", username).and("email", email).parameters());

        if (userVerif != null)
        {
            registerForm.recordError(messages.get("error.userexists"));

            return null;
        }

        User user = new User(fullName, username, email, password);

        //crudServiceDAO.update(user);
        crudServiceDAO.create(user);
        
        return Search.class;
    }
}
