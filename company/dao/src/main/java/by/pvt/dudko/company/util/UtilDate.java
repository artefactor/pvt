package by.pvt.dudko.company.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
@Component
// XXX better DateUtil. Better CompanyDateUtil
public class UtilDate {
	
	private static final Logger log = Logger.getLogger(UtilDate.class);
	
	public static Date date(String date) {
		log.info("(attempt) change type string on the date(my method)");
		Date Date = new Date();
		// XXX shoud be extracted to constant at least. Better to load it from property file
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date = sdf.parse(date.trim());
			log.info(" change type string on the date(my method) succesfully");
		} catch (ParseException e) {
			// XXX not the approach. Need to throw your own bisiness exception.
			log.error("Error change type string on the date(my method) "+e);
		}
		return Date;
	}
	
}
