package by.pvt.dudko.company.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import by.pvt.dudko.company.dao.IDao;
import by.pvt.dudko.company.dao.IOrderDao;
import by.pvt.dudko.company.dao.impl.MySqlOrderDao;
import by.pvt.dudko.company.entities.Car;
import by.pvt.dudko.company.entities.Client;
import by.pvt.dudko.company.entities.Order;
import by.pvt.dudko.company.entities.Trip;
import by.pvt.dudko.company.exception.ServiceException;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AdminServiceImpl {
	@Autowired
	private CarServiceImpl carServise;
	@Autowired
	private IOrderDao mySqlOrderDao;
	private static final Logger log = Logger.getLogger(AdminServiceImpl.class);

	public AdminServiceImpl() {
	}
// XXX getBusyCar
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Car> busyCar() {
		Date date = new Date();
		List<Car> cars = carServise.allCar();
		List<Car> list = new ArrayList();
		for (Car car : cars) {
			car.getDriver();
			//XXX magic numbers. 1?
			if (car.getCondition() == 1) {
				list.add(car);
			} else {
				for (Trip trip : car.getTrips()) {
					if (date.after(trip.getDateBegin()) && date.before(trip.getDateFinish())) {
						list.add(car);
						break;
					}
				}
			}

		}

		return list;
	}

	/**
	 * method choose car for order
	 * 
	 * @return object car
	 * @param object
	 *            order
	 * @throws IllegalArgumentException
	 * @throws ServiceException
	 */
	public Car selectCar(Order order) throws ServiceException {
		List<Car> list = new ArrayList<Car>();
		Car car = new Car();
		List<Car> cars = carServise.allCar();
		for (Car car1 : cars) {
			// XXX magic number 0
			if (car1.getCondition() == 0) {
				if (order.getPropertiesOrder().getMass() <= car1.getMass()
						&& order.getPropertiesOrder().getSeatCount() <= car1.getSeatCount()
						&& order.getPropertiesOrder().getVolume() <= car1.getVolume()) {
					Set<Trip> trips = (Set<Trip>) car1.getTrips();
					if (carServise.equalsDateOrderCarTrips(trips, order)) {
						list.add(car1);
					}
				}
			}
		}
		if (!list.isEmpty()) {
			//XXX what if more than 1 car in list?
			car = list.get(0);
		} else {
			String message = "Car is not found";
			log.info(message);
			throw new ServiceException(message);
		}

		return car;
	}

	/**
	 * method generates an trip
	 * 
	 * @return object trip
	 * @param objects
	 *            order,car,client
	 * 
	 */
	public Trip formTrip(Order order, Car car, Client client) {
		Trip trip = new Trip();
		trip.setTarget(order.getPropertiesOrder().getTarget());
		trip.setDateBegin(order.getPropertiesOrder().getDateBegin());
		trip.setDateFinish(order.getPropertiesOrder().getDateFinish());
		trip.setDictanse(order.getPropertiesOrder().getDictanse());
		trip.setCar(car);
		trip.setOrder(order);
		trip.setIdClient(client.getIdClient());
		trip.setIdTrip(mySqlOrderDao.getMaxIdOrder() + 1);
		trip.setConditionTrip(-1);
		String message = "new trip is fill";
		log.info(message);

		return trip;
	}

	/**
	 * method define broken car
	 * 
	 * @return collection car
	 * 
	 */
	//XXX getBrokenCars
	public List<Car> brokenCars() {
		List<Car> list = new ArrayList<Car>();
		for (Car car : carServise.allCar()) {
			//XXX magic numbers. -1?
			if (carServise.condCar(car) == (-1)) {
				list.add(car);
			}
		}

		return list;

	}

}
