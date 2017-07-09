package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

    private String name;
    private String flightNumber;
    private String Src;
    private String arrival;
    private String dest;
    private String dep;

    public Flight(){

    }

  public Flight(String [] data){

    // no print or readme
    if(data.length == 8){

      this.name = new String(data[0]); // name
      this.flightNumber = new String(data[1]); // flight number
      this.Src = new String(data[2]); // 3 symbol
      this.arrival = new String(data[3] + data[4]); // data and time arrival
      this.dest = new String(data[5]); // destination
      this.dep = new String(data[6] + data[7]); // data and time of dep

    }

    // there was a print or read
    else if(data[0] == "-Print"){

    }

  }

  @Override
  public int getNumber() {

    int flightNum = Integer.parseInt(flightNumber);

    if(flightNumber.equals(null))
        return 0;

    return flightNum;
  }

  @Override
  public String getSource() {

      return "s";
  }

  @Override
  public String getDepartureString() {

      return "s";
  }

  @Override
  public String getDestination() {
      return "l";
  }

  @Override
  public String getArrivalString() {

      return "dadsa";
  }

  public void setNum(int set){

      flightNumber = Integer.toString(set);
  }
}
