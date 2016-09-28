package by.pvt.dudko.company.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

public class OrderDto {
	//XXX target..   it is similar to source-target..  Common technical word.  I would change for more specific, ex. orderTarget
	
	private String target;
        // XXX distance
	
	private int dictanse;
	@NotEmpty(message="wrong date.")
	private String dateBegin;
	
	// XXX begin-end.  or start-finish
	@NotEmpty(message="wrong date.")
	private String dateFinish;
	private int seatCount;
	private int mass;
	private int volume;
	
	public void setVolume(int volume) {
		this.volume = volume;
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

	public String getTarget() {
		return target;
	}

	public int getDictanse() {
		return dictanse;
	}

	public String getDateBegin() {
		return dateBegin;
	}

	public String getDateFinish() {
		return dateFinish;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}

	public void setDictanse(int dictanse) {
		this.dictanse = dictanse;
	}

	public void setDateBegin(String dateBegin) {
		 this.dateBegin= dateBegin;
		 
	}

	public void setDateFinish(String dateFinish) {
		this.dateFinish = dateFinish;
		 
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

}
