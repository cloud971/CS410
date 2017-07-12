package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

    private String flightNumber;
    private String src;
    private String arrival;
    private String dest;
    private String dep;

    public Flight(){

    }

  public Flight(String [] data, int index){


      this.flightNumber = new String(data[index]); // flight number
      this.src = new String(data[index+1]); // 3 symbol
      this.arrival = new String(data[index+2] + data[index+3]); // data and time arrival
      this.dest = new String(data[index+4]); // destination
      this.dep = new String(data[index+5] + data[index+6]); // data and time of dep

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

      return src;
  }

  @Override
  public String getDepartureString() {

      return dep;
  }

  @Override
  public String getDestination() {
      return dest;
  }

  @Override
  public String getArrivalString() {

      return arrival;
  }

  public void setNum(int set){

      flightNumber = Integer.toString(set);
  }
}
