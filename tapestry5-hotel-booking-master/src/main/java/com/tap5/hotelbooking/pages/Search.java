package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.hibernate.HibernateGridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.tap5.hotelbooking.data.SearchCriteria;
import com.tap5.hotelbooking.entities.User;

/**
 * Lookup for Hotels.
 * 
 * @author ccordenier
 */
public class Search
{
    @Inject
    private Session session;

    @InjectComponent
    private Zone result;

    @SessionState
    @Property
    private SearchCriteria criteria;

    /*
    @SuppressWarnings("unused")
    @Property
    private GridDataSource source = new HotelDataSource(session, Hotel.class);

    @SuppressWarnings("unused")
    @Property
    private Hotel currentHotel;
    */
    
    @SuppressWarnings("unused")
    @Property
    private GridDataSource source = new UserDataSource(session, User.class);

    @SuppressWarnings("unused")
    @Property
    private User currentUser;
    
    private Signin newUser;

    /**
     * This datasource is used by Tapestry 5 Grid to search and paginate
     * 
     * @author ccordenier
     */
    /*
    private final class HotelDataSource extends HibernateGridDataSource
    {
        private HotelDataSource(Session session, @SuppressWarnings("rawtypes") 
        Class entityType)
        {
            super(session, entityType);
        }

        @Override
        public int getAvailableRows()
        {
            return criteria.getSearchPattern() == null ? 0 : super.getAvailableRows();
        }

        @Override
        protected void applyAdditionalConstraints(Criteria crit)
        {
            crit.add(Restrictions.ilike("name", criteria.getSearchPattern()));
        }
    }
    */
    
    private final class UserDataSource extends HibernateGridDataSource
    {
    	private UserDataSource(Session session, @SuppressWarnings("rawtypes") 
    	Class entityType )
    	{
    		super(session, entityType);
    	}
    	
    	@Override
    	public int getAvailableRows()
    	{
    		return criteria.getSearchPattern() == null ? 0 : super.getAvailableRows();
    	}
    	
    	@Override
        protected void applyAdditionalConstraints(Criteria crit)
        {
            crit.add(Restrictions.ilike("fullname", criteria.getSearchPattern()));
        }
    }

    /**
     * Move this into a hotel query service
     */
    @OnEvent(value = EventConstants.SUCCESS)
    Object searchHotels()
    {
        return result.getBody();
    }
    
    Object onAction()
    {
        return result.getBody();
    }

}
