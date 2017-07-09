package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

    private String [] Flight_data = new String[8];

  public Flight(String [] data){

    // no print or readme
    if(data.length == Flight_data.length){

      this.Flight_data[0] = new String(data[0]); // name
      this.Flight_data[1] = new String(data[1]); // flight number
      this.Flight_data[2] = new String(data[2]); // 3 symbol
      this.Flight_data[3] = new String(data[3] + data[4]); // data and time arrival
      this.Flight_data[4] = new String(data[5]); // destination
      this.Flight_data[5] = new String(data[6] + data[7]); // data and time of dep

    }

    // there was a print or read
    else if(data[0] == "-Print"){

    }

  }

  @Override
  public int getNumber() {

    int flight_num = Integer.parseInt(Flight_data[1]);

    return flight_num;
  }

  @Override
  public String getSource() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDepartureString() {



    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDestination() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getArrivalString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }
}
