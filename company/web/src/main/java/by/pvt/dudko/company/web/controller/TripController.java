package by.pvt.dudko.company.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import by.pvt.dudko.company.dto.FilterTripClientDto;
import by.pvt.dudko.company.dto.PaginationDto;
import by.pvt.dudko.company.dto.SortTripDto;
import by.pvt.dudko.company.entities.Client;
import by.pvt.dudko.company.entities.Trip;
import by.pvt.dudko.company.exception.ServiceException;
import by.pvt.dudko.company.service.TripServiceImpl;
import by.pvt.dudko.company.util.UtilDate;
import by.pvt.dudko.company.web.constant.ConstantsPages;

@Controller
@RequestMapping("/trip")
public class TripController {
	private static final Logger log = Logger.getLogger(TripController.class);

	@Autowired
	private TripServiceImpl tripServiceImpl;

	@RequestMapping(value = "/filtr", method = RequestMethod.POST)
	public String filtrTrip(Model model, HttpServletRequest request){
		String page = null;
		FilterTripClientDto filtrTripClientDto = new FilterTripClientDto();
		String dateBegin = request.getParameter("dateBegin").trim();
		String dateFinish = request.getParameter("dateFinish").trim();
		if (!dateBegin.equals("")) {
			filtrTripClientDto.setDateBegin(UtilDate.date(dateBegin));
		}
		if (!dateFinish.equals("")) {
			filtrTripClientDto.setDateFinish(UtilDate.date(dateFinish));
		}
		filtrTripClientDto.setTarget(request.getParameter("target").trim());
		Client client = (Client) request.getSession().getAttribute("USER");
		SortTripDto sortTripDto = (SortTripDto) request.getSession().getAttribute("source");
		List<Trip> tripsDefine = tripServiceImpl.filtrTrip(client, filtrTripClientDto, sortTripDto);
		PaginationDto paginationDto = new PaginationDto(tripsDefine.size(), 1, tripsDefine.size(), 1, 0);
		request.getSession().setAttribute("filtr", filtrTripClientDto);
		request.getSession().setAttribute("pagination", paginationDto);
		request.setAttribute("trips", tripsDefine);
		page = ConstantsPages.ORDERS;
		return page;
	}

	@RequestMapping(value = "/sort", method = RequestMethod.POST)
	public String sortTrip(Model model, HttpServletRequest request) {
		String page = null;
		SortTripDto sortTripDto = new SortTripDto();
		sortTripDto.setOrderColumn(request.getParameter("order").trim());
		sortTripDto.setColumn(request.getParameter("source").trim());
		FilterTripClientDto filtrTripClientDto = (FilterTripClientDto) request.getSession().getAttribute("filtr");
		Client client = (Client) request.getSession().getAttribute("USER");
		PaginationDto paginationDto=(PaginationDto) request.getSession().getAttribute("pagination");
		List<Trip> nextTrips = tripServiceImpl.sortTrip(sortTripDto, filtrTripClientDto, client,paginationDto.getCount());
		request.setAttribute("trips", nextTrips);
		request.getSession().setAttribute("source", sortTripDto);
		return page = ConstantsPages.ORDERS;
	}

	@RequestMapping(value = "/amount", method = RequestMethod.GET)
	public String amountElementPage(Model model, HttpServletRequest request){
		String page = null;
		int amount = Integer.parseInt(request.getParameter("element").trim());
		Client client = (Client) request.getSession().getAttribute("USER");
		SortTripDto sortTripDto = (SortTripDto) request.getSession().getAttribute("source");
		FilterTripClientDto filtrTripClientDto = (FilterTripClientDto) request.getSession().getAttribute("filtr");
		PaginationDto paginationDto = (PaginationDto) request.getSession().getAttribute("pagination");
		int allPage = tripServiceImpl.amountPages(paginationDto, amount);
		PaginationDto paginationDtoTwo = new PaginationDto(paginationDto.getAllCount(), allPage, amount, 1, 0);
		List<Trip> tripsDefine = tripServiceImpl.pagination(client, sortTripDto, filtrTripClientDto, paginationDtoTwo);
		request.setAttribute("trips", tripsDefine);
		request.getSession().setAttribute("pagination", paginationDtoTwo);
		return page = ConstantsPages.ORDERS;

	}

	@RequestMapping(value = "/next_page", method = RequestMethod.GET)
	public String nextPage(Model model, HttpServletRequest request) {
		String page = null;
		int number = Integer.parseInt(request.getParameter("next_page").trim());
		PaginationDto paginationDto = (PaginationDto) request.getSession().getAttribute("pagination");
		Client client = (Client) request.getSession().getAttribute("USER");
		FilterTripClientDto filtrTripClientDto = (FilterTripClientDto) request.getSession().getAttribute("filtr");
		SortTripDto sortTripDto = (SortTripDto) request.getSession().getAttribute("source");
		paginationDto = tripServiceImpl.nextPage(paginationDto, number);
		List<Trip> tripsDefine = tripServiceImpl.pagination(client, sortTripDto, filtrTripClientDto, paginationDto);
		request.getSession().setAttribute("pagination", paginationDto);
		request.setAttribute("trips", tripsDefine);
		return page = ConstantsPages.ORDERS;

	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView allException(Exception e) {
		log.error("error", e);
		ModelAndView model = new ModelAndView("error_two");
		model.addObject("ERROR", "errorText");
		return model;

	}
	
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView serviceException(Exception e) {
		log.error("error", e);
		ModelAndView model = new ModelAndView("error_two");
		model.addObject("ERROR", "dataBaseError");
		
		return model;
	}

}
