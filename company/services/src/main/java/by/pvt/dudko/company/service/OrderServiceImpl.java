package by.pvt.dudko.company.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import by.pvt.dudko.company.dao.IDao;
import by.pvt.dudko.company.dao.IOrderDao;
import by.pvt.dudko.company.dao.impl.MySqlOrderDao;
import by.pvt.dudko.company.dto.OrderDto;
import by.pvt.dudko.company.entities.Client;
import by.pvt.dudko.company.entities.Order;
import by.pvt.dudko.company.entities.PropertiesOrder;
import by.pvt.dudko.company.entities.Trip;
import by.pvt.dudko.company.exception.ServiceException;
import by.pvt.dudko.company.util.UtilDate;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OrderServiceImpl {

	@Autowired
	private IOrderDao mySqlOrderDao;
	@Autowired
	private ServiceImpl serviceImpl;
	private static final Logger log = Logger.getLogger(OrderServiceImpl.class);

	public OrderServiceImpl() {
	}

	/**
	 * all orders
	 * 
	 * @return collection all orders
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Order> allOrder() {
		List<Order> list = mySqlOrderDao.getAll();
		return list;
	}

	/**
	 * method for comparing dates of existing trips in the car with the date of
	 * the new trip
	 * 
	 * @param client,orderDto
	 * @return int 0-if car is find,1 - if data is incorrect,2-if don't suitable
	 *         car
	 * @throws ServiceException
	 */
	//XXX all magic numbers should extracted to constants
	public int estimateDateOrder(OrderDto orderDto, Client client) {
	// XXX not use i, i is for iteration: int result=DATA_INCORRECT;
		int i = 1;
		Date date = new Date();
		Date dateBegin=UtilDate.date(orderDto.getDateBegin());
		Date dateFinish=UtilDate.date(orderDto.getDateFinish());
		try {
			if (dateFinish.after(dateBegin) && dateBegin.after(date)) {
				serviceImpl.transactionSaveTrip(client, orderDto);
				i = 0;
			} else {
				log.info("INCORRECT DATA on the ORDER FORM");
			}
		} catch (ServiceException e) {
			i = 2;
		}
		return i;
	}

	/**
	 * method define id next order
	 * 
	 * @return id Order
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int numberTrip() {
		int idOrder = mySqlOrderDao.getMaxIdOrder() + 1;
		return idOrder;
	}

	/**
	 * method generates an order
	 * 
	 * @param client,orderDto
	 * @return object Order
	 * @throws ServiceException
	 */

	public Order formOrder(Client client, OrderDto orderDto) {
		PropertiesOrder propertiesOrder = new PropertiesOrder();
		propertiesOrder.setDateBegin(UtilDate.date(orderDto.getDateBegin()));
		propertiesOrder.setDateFinish(UtilDate.date(orderDto.getDateFinish()));
		propertiesOrder.setDictanse(orderDto.getDictanse());
		propertiesOrder.setMass(orderDto.getMass());
		propertiesOrder.setSeatCount(orderDto.getSeatCount());
		propertiesOrder.setTarget(orderDto.getTarget());
		propertiesOrder.setVolume(orderDto.getVolume());
		Order order = new Order();
		order.setClient(client);
		order.setPropertiesOrder(propertiesOrder);
		return order;

	}

	/**
	 * method write object order in database
	 * 
	 * @param order
	 * @throws ServiceException
	 */
	//XXX createOrder. USe simple names. Not need to be creative
	public void fixationOrder(Order order) {
		mySqlOrderDao.create(order);
	}

	/**
	 * method get one order
	 * 
	 * @return object order
	 * @param id
	 *            order
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Order getOrder(int idOrder) {
		Order order = (Order) mySqlOrderDao.get(idOrder);
		return order;
	}
}
