package com.nexera.common.dao.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nexera.common.compositekey.QuoteCompositeKey;
import com.nexera.common.dao.QuoteDao;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.QuoteDetails;
import com.nexera.common.vo.QuoteDetailsVO;

@Component
public class QuoteDaoImpl extends GenericDaoImpl implements QuoteDao{

	private static final Logger LOG = LoggerFactory
	        .getLogger(QuoteDaoImpl.class);
	
	public boolean addQuoteDetails(QuoteDetails quoteDetais) {
		boolean status = true;
		try{
			this.saveOrUpdate(quoteDetais);
		}
		catch(Exception e){
			LOG.error("Error in inserting/updating quote details: "+e);
			status = false;
		}
		finally{
			return status;
		}

	}
	
	public QuoteDetails getUserDetails(QuoteCompositeKey compKey){
		QuoteDetails quoteDetails = (QuoteDetails)this.load(QuoteDetails.class, compKey);
		return quoteDetails;
	}
	
	public String  getUniqueIdFromQuoteDetails(QuoteCompositeKey compKey){
		String id = null ;
		try{
				Session session = sessionFactory.getCurrentSession();
				SQLQuery qry = session.createSQLQuery("select id from quotedetails where prospect_username = '"+compKey.getUserName()+"' and internal_user_id = "+compKey.getInternalUserId());
				// Here PRODUCTS is the table in the database...
				List list = qry.list();
				Iterator it = list.iterator();
				
				if (it.hasNext())	{
					id = ""+it.next();
				}
		}
		catch(Exception e){
			LOG.error("Error in fetching id from quotedetail table: "+e);
		}
				return id;
	}

	@Override
	public void updateCreatedUser(QuoteCompositeKey compKey) {
		
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE QuoteDetails quote set quote.isCreated = :isCreated WHERE quote.quoteCompositeKey = :quoteCompositeKey";
		Query query = session.createQuery(hql);
		query.setParameter("isCreated", true);
		query.setParameter("quoteCompositeKey", compKey);
		query.executeUpdate();	
	}
	
	
	@Override
	public void updateDeletedUser(QuoteCompositeKey compKey) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE QuoteDetails quote set quote.isDeleted = :isDeleted WHERE quote.quoteCompositeKey = :quoteCompositeKey";
		Query query = session.createQuery(hql);
		query.setParameter("isDeleted", true);
		query.setParameter("quoteCompositeKey", compKey);
		query.executeUpdate();	
	}
	
	
	
}
