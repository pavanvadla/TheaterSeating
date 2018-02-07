package main.com.theaterseating;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import main.com.theaterseating.model.TheaterLayout;
import main.com.theaterseating.model.TheaterRequest;
import main.com.theaterseating.service.TheaterSeatingService;
import main.com.theaterseating.service.TheaterSeatingServiceImpl;
import main.com.theaterseating.util.Constants;

public class TheaterSeating
{
	public static void main(String[] args)
	{
		StringBuilder layout = new StringBuilder();
		StringBuilder requests = new StringBuilder();
		String line;
		boolean isValid = true;;
		Scanner input = new Scanner(System.in);
        while (!(line = input.nextLine()).isEmpty())
        {
        	if(Character.isDigit(line.charAt(0)))
        	{
        		layout.append(line + Constants.NEWLINE);
        	}
        	else
        	{
        		isValid = false;
        		System.out.println("Theater Layout input is invalid. Please refer README for the input data format.");
        	}
        }
        while (!(line = input.nextLine()).isEmpty())
        {
        	if(Pattern.compile("^[A-Z]").matcher(line).find())
        	{
        		requests.append(line + Constants.NEWLINE);
        	}
        	else
        	{
        		isValid = false;
        		System.out.println("Ticket Request input is invalid. Please refer README for the input data format.");
        	}
        }
        input.close();
        if(isValid)
        {
	        TheaterSeatingService service = new TheaterSeatingServiceImpl();
	        try
	        {
		        TheaterLayout theaterLayout = new TheaterLayout();
				List<TheaterRequest> ticketRequests = new ArrayList<TheaterRequest>();
				theaterLayout = service.getTheaterLayout(layout.toString());
				ticketRequests = service.getTicketRequests(requests.toString());
		        service.processTicketRequests(ticketRequests, theaterLayout);
	        }
	        catch(NumberFormatException nfe)
	        {
	            System.out.println(nfe.getMessage());
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
        }
	}
	
}
