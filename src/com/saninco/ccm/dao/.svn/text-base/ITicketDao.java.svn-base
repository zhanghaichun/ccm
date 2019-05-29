/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Priority;
import com.saninco.ccm.model.Severity;
import com.saninco.ccm.model.Ticket;
import com.saninco.ccm.model.TicketStatus;
import com.saninco.ccm.model.User;
import com.saninco.ccm.vo.SearchTicketVO;

/**
 * Spring Hibernate DAO interface for Ticket and ticket details based on MyEclipse generated 
 * DAO class with reverse engineering.
 * 
 * @author xinyu.Liu (Optimization of complete by xinyu.Liu)
 *
 */
@SuppressWarnings("unchecked")
public interface ITicketDao {
	
	public AttachmentPoint findAttachmentPointById(Integer id);
	
	public List<String> searchTicket(SearchTicketVO searchTicketVO, int userId);
	
	public long getNumberOfTickets(SearchTicketVO searchTicketVO, int userId);
	
	public List<String> searchTicketDetail(SearchTicketVO searchTicketVO);
	
	public long getNumberOfTicketDetail(String ticketId);
	
	public abstract void save(Ticket transientInstance);
	
	public Priority findPriorityById(Integer id);
	
	public Ticket findTicketById(Integer id);
	
	public TicketStatus findTicketStatusById(Integer id);
	
	public Severity findSeverityById(Integer id);

	public abstract void delete(Ticket persistentInstance);

	public abstract Ticket findById(java.lang.Integer id);

	public abstract List findByExample(Ticket instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByTitle(Object title);

	public abstract List findByContent(Object content);

	public abstract List findByCreatedBy(Object createdBy);

	public abstract List findByModifiedBy(Object modifiedBy);

	public abstract List findByRecActiveFlag(Object recActiveFlag);

	public abstract List findAll();

	public abstract Ticket merge(Ticket detachedInstance);

	public abstract void attachDirty(Ticket instance);

	public abstract void attachClean(Ticket instance);

	public List<Object[]> searchTicketByObject(SearchTicketVO sio, int userId);
	
	public User findUserById(Integer id);

	public Object get(Class c,Integer id);
}
