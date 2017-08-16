package edu.pdx.cs410J.chasam.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.chasam.client.Airline;
import edu.pdx.cs410J.chasam.client.Flight;
import edu.pdx.cs410J.chasam.client.AirlineService;

public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService
{
  private Airline airlineInfo = new Airline();

  @Override
  public Airline getAirline(String info) {

      return airlineInfo;
  }

  @Override
  public String add(String theName, String flightDigit,String sourceCode, String dateOfLeave,String destinCode,String dateArrival){


    Flight newFlight = new Flight(flightDigit,sourceCode,dateOfLeave,destinCode,dateArrival);

    if(airlineInfo.retrieveNUM() == 0 || airlineInfo.getName() == null){

      airlineInfo.setName(theName);
      airlineInfo.addFlight(newFlight);

      return "true";
    }

    if(airlineInfo.getName().equals(theName)){

      airlineInfo.newAdd(newFlight);
      return "true";

    }

    return "false";
  }

  @Override
  public void throwUndeclaredException() {
    throw new IllegalStateException("Expected undeclared exception");
  }

  @Override
  public void throwDeclaredException() throws IllegalStateException {
    throw new IllegalStateException("Expected declared exception");
  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }
}
