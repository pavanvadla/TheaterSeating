package main.com.theaterseating.service;

import java.util.ArrayList;
import java.util.List;

import main.com.theaterseating.model.TheaterLayout;
import main.com.theaterseating.model.TheaterRequest;
import main.com.theaterseating.model.TheaterSection;
import main.com.theaterseating.util.Constants;

public class TheaterSeatingServiceImpl implements TheaterSeatingService
{

	/**
	 * Get Theater layout from given input
	 * 
	 * @param String
	 * @param TheaterLayout
	 */
	public TheaterLayout getTheaterLayout(String layout) throws NumberFormatException
	{
		TheaterLayout theaterLayout = new TheaterLayout();
        TheaterSection section;
        List<TheaterSection> sectionsList = new ArrayList<TheaterSection>();
        int totalSeats = 0, sectionSeats;
        String[] seats = layout.split(Constants.NEWLINE);
        String[] sections;
        for(int i=0; i<seats.length; i++)
		{
        	sections = seats[i].split(Constants.SPACESTRING);
            for(int j=0 ; j<sections.length ; j++)
            {
            	try
            	{
            		sectionSeats = Integer.valueOf(sections[j]);
            		totalSeats+= sectionSeats;
	            	section = new TheaterSection();
	            	section.setRowNumber(i+1);
	            	section.setSectionNumber(j+1);
	            	section.setAvailableSeats(sectionSeats);
	            	sectionsList.add(section);
            	}
            	catch(NumberFormatException nfe)
            	{
            		throw new NumberFormatException("Section "+ sections[j] +" is invalid.");
            	}
            }
		}
        theaterLayout.setAvailableSeats(totalSeats);
        theaterLayout.setSections(sectionsList);
        return theaterLayout;
	}
	
	/**
	 * Get Ticket requests from given input
	 * 
	 * @param String
	 * @param List<TheaterRequest>
	 */
	public List<TheaterRequest> getTicketRequests(String requests) throws NumberFormatException
	{
		List<TheaterRequest> ticketRequests = new ArrayList<>();
        TheaterRequest ticketRequest;
        String[] groupTickets = requests.split(Constants.NEWLINE);
        for(String group : groupTickets)
        {
        	String[] groupData = group.split(Constants.SPACESTRING);
        	try
            {
	            ticketRequest = new TheaterRequest();
	            ticketRequest.setGroupName(groupData[0]);
            	ticketRequest.setGroupTickets(Integer.valueOf(groupData[1]));
            	ticketRequest.setProcessed(false);
                ticketRequests.add(ticketRequest);
            }
            catch(NumberFormatException nfe)
            {
            	throw new NumberFormatException(groupData[1] +" is invalid ticket request.");
            }
        }
        return ticketRequests;
	}
	
	/**
	 * Process Ticket requests provided from input.
	 * 
	 * @param List<TheaterRequest>
	 * @param TheaterLayout
	 */
	public void processTicketRequests(List<TheaterRequest> ticketRequests, TheaterLayout theaterLayout)
	{
		for(int i=0; i<ticketRequests.size();i++)
		{
			TheaterRequest request = ticketRequests.get(i);
			int num = request.getGroupTickets();
			if(request.isProcessed()) 
			{
				System.out.println(request.getGroupName()+" Row "+request.getRowNumber()+" Section "+request.getSectionNumber());
				continue;
			}
			if(num > theaterLayout.getAvailableSeats())
			{
				System.out.println(request.getGroupName()+" Sorry, we can't handle your party");
				continue;
			}
			for(int j=0;j<theaterLayout.getSections().size();j++)
			{
                TheaterSection section = theaterLayout.getSections().get(j);
                if(num == section.getAvailableSeats())
                {
                	setRequestValues(request, theaterLayout, section);
                    section.setAvailableSeats(section.getAvailableSeats() - num);
                    System.out.println(request.getGroupName()+" Row "+request.getRowNumber()+" Section "+request.getSectionNumber());
                    break;
                    
                }
                else if(num < section.getAvailableSeats())
                {
                	//find next theater request with remaining seats of current section
                    int tempNum = findTheaterRequest(ticketRequests, section.getAvailableSeats() - num, i+1); 
                    if(tempNum >= 0)
                    {
                    	TheaterRequest nextRequest = ticketRequests.get(tempNum);
                    	setRequestValues(request, theaterLayout, section);
                        section.setAvailableSeats(section.getAvailableSeats() - num);
                        System.out.println(request.getGroupName()+" Row "+request.getRowNumber()+" Section "+request.getSectionNumber());
                        
                        setRequestValues(nextRequest, theaterLayout, section);
                        section.setAvailableSeats(section.getAvailableSeats() - nextRequest.getGroupTickets());
                        break;
                    }
                    else
                    {
                        int sectionNo = findTheaterSection(theaterLayout.getSections(), num, j+1);
                        if(sectionNo >= 0)
                        {
                            TheaterSection tempSection = theaterLayout.getSections().get(sectionNo);
                            setRequestValues(request, theaterLayout, tempSection);
                            tempSection.setAvailableSeats(tempSection.getAvailableSeats() - num);
                            System.out.println(request.getGroupName()+" Row "+request.getRowNumber()+" Section "+request.getSectionNumber());
                            break;
                        }
                        else
                        {
                        	setRequestValues(request, theaterLayout, section);
                            section.setAvailableSeats(section.getAvailableSeats() - num);
                            System.out.println(request.getGroupName()+" Row "+request.getRowNumber()+" Section "+request.getSectionNumber());
                            break;
                        }
                    }
                }
            }
			if(!request.isProcessed())
			{
				System.out.println(request.getGroupName()+" Call to split party");
			}
		}
	}
	
	/**
	 * Find Theater section by it's available seats.
	 * 
	 */
	private int findTheaterSection(List<TheaterSection> sections, int num, int i)
	{
		for(int index =i; index<sections.size();index++)
		{
			TheaterSection s = sections.get(index);
			if(s.getAvailableSeats()==num)
			{
				return index;
			}
		}
		return -1;
	}
	
	/**
	 * Find Theater request by it's available seats.
	 * 
	 */
	private int findTheaterRequest(List<TheaterRequest> requests, int seats, int i)
	{
		for(int index =i; index<requests.size();index++)
		{
			TheaterRequest r = requests.get(index);
			if(!r.isProcessed() && r.getGroupTickets() == seats)
			{
				return index;
			}
		}
		return -1;
	}
	
	private void setRequestValues(TheaterRequest request, TheaterLayout layout, TheaterSection section)
	{
		request.setRowNumber(section.getRowNumber());
        request.setSectionNumber(section.getSectionNumber());
        layout.setAvailableSeats(layout.getAvailableSeats() - request.getGroupTickets());
        request.setProcessed(true);
	}
	
}
