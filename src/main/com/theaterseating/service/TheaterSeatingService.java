package main.com.theaterseating.service;

import java.util.List;

import main.com.theaterseating.model.TheaterLayout;
import main.com.theaterseating.model.TheaterRequest;

public interface TheaterSeatingService {

	/**
	 * Get Theater layout from given input
	 * 
	 * @param String
	 * @param TheaterLayout
	 */
	TheaterLayout getTheaterLayout(String theaterLayout);
    
	/**
	 * Get Ticket requests from given input
	 * 
	 * @param String
	 * @param List<TheaterRequest>
	 */
    List<TheaterRequest> getTicketRequests(String ticketRequests);
    
    /**
	 * Process Ticket requests provided from input.
	 * 
	 * @param List<TheaterRequest>
	 * @param TheaterLayout
	 */
    void processTicketRequests(List<TheaterRequest> requests, TheaterLayout layout);
	
}
