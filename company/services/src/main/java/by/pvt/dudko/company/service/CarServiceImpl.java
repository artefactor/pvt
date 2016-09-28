package by.pvt.dudko.company.service;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import by.pvt.dudko.company.dao.IDao;
import by.pvt.dudko.company.dao.impl.MySqlCarDao;
import by.pvt.dudko.company.entities.Car;
import by.pvt.dudko.company.entities.Order;
import by.pvt.dudko.company.entities.Trip;
import by.pvt.dudko.company.exception.ServiceException;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CarServiceImpl {

	@Autowired
	@Qualifier("mySqlCarDao")
	private IDao mySqlCarDao;
	private static final Logger log = Logger.getLogger(CarServiceImpl.class);

	public CarServiceImpl() {
	}

	/**
	 * All cars
	 * 
	 * @return collection all car
	 *
	 */
	//XXX getAllCars
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Car> allCar() {
		List<Car> list = mySqlCarDao.getAll();

		return list;

	}

	/**
	 * equals date which car do and date of order
	 * 
	 * @param object
	 *            car and order
	 * @return boolean(true if car free,else false)
	 * 
	 */
//XXX method should be renamed. smth like checkTripList...WithOrder
	public boolean equalsDateOrderCarTrips(Set<Trip> list, Order order) {
		// XXX boolean result;
		boolean q = false;
		int amount = 0;
		for (Trip trip : list) {
			if (trip.getDateBegin().after(order.getPropertiesOrder().getDateFinish())
					|| trip.getDateFinish().before(order.getPropertiesOrder().getDateBegin())) {
				amount = amount + 1;
			}
		}
		if (amount == list.size()) {
			q = true;
		}
		
		return q;

	}

	/**
	 * definition of key condition machines
	 * 
	 * @param key
	 *            - id of Car
	 * @return condition Car
	 * 
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	// XXX getCarConditionByCarId
	public int conditionCar(int key) {
		Car car = (Car) mySqlCarDao.get(key);
		
		return car.getCondition();

	}

	/**
	 * definition of key machines
	 * 
	 * @param key
	 *            - id of Car
	 * @return entity Car
	 * 
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Car getCar(int key) {
		Car car = (Car) mySqlCarDao.get(key);
		
		return car;

	}

	/**
	 * definition of machines condition car
	 * 
	 * @param object
	 *            Car
	 * @return condition Car(-1,0,1)
	 *
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	//XXX getCarCondionByCar
	public int condCar(Car car) {
		
		return conditionCar(car.getIdCar());
	}

	/**
	 * change condition car
	 * 
	 * @param object
	 *            Car and condition,that should be
	 * 
	 */
//XXX changeCarCondition
	public void changeConditionCar(Car car, int condition) {
		car.setCondition(condition);
		mySqlCarDao.update(car);
	}
}
