package by.pvt.dudko.company.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CAR")
public class Car implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idCar")
	private int idCar;
	 @Column(name="mark")
	private String mark;
	 @Column(name="seatCount")
	private int seatCount;
	 @Column(name="mass")
	private int mass;
	//XXX valume or volume?
	 @Column(name="valume")
	private int volume;
	//XXX conditions or condition?
	@Column(name="conditions")
	private int condition;
	@ManyToMany(fetch = FetchType.EAGER,mappedBy="cars", cascade={
			CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
	private Set<Driver> driver=new HashSet();
	@OneToMany(mappedBy="car")
	private Set<Trip> trips=new HashSet() ;

	public Car() {
	}

	public Car(String mark, int seatCount, int mass, int volume, int condition) {
		this.mark = mark;
		this.seatCount = seatCount;
		this.mass = mass;
		this.volume = volume;
		this.condition = condition;
				
		
	}
	
	public void setTrips(Set<Trip> trips) {
		this.trips = trips;
	}
	public Set<Trip> getTrips() {
		return trips;
	}
	
	public void setDriver(Set<Driver> driver) {
		this.driver = driver;
	}
	
		
	public void setMark(String mark) {
		this.mark = mark;
	}

	public void setIdCar(int idCar) {
		this.idCar = idCar;
	}

	

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}



	public void setCondition(int condition) {
		this.condition = condition;
	}

	public String getMark() {
		return mark;
	}

	public int getIdCar() {
		return idCar;
	}

	public Set<Driver> getDriver() {
		return driver;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public int getMass() {
		return mass;
	}

	public int getVolume() {
		return volume;
	}

	public int getCondition() {
		return condition;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Car))
			return false;
		Car car = (Car) o;
		// XXX what a strange implementation?
		if (idCar != 0 ? idCar != car.idCar : car.idCar != 0)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = idCar != 0 ? new Integer(idCar).hashCode() : 0;
	return result;
	}

	@Override
	public String toString() {
		return "Car : id: " + idCar + " Mark: " + mark+",Driver : "+driver+",Trips : "+trips;
	}
}
