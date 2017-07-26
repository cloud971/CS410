package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AbstractFlight;
import javafx.scene.input.DataFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Flight extends AbstractFlight implements Comparable<Flight> {

    private String flightNumber;
    private String src;
    private String arrival;
    private String dest;
    private String dep;
    private String depTime;
    private String aTime;
    private String dAMPM;
    private String aAMPM;
    private Date startDate;
    private Date leaveDate;

    public Flight(){

    }

  public Flight(String [] data, int index){

        flightNumber = data[index];
        src = data[index+1];
        dep = data[index+2];
        depTime = data[index+3];
        dAMPM = data[index+4];
        dest = data[index+5];
        arrival = data[index+6];
        aTime = data[index+7];
        aAMPM = data[index+8];
  }

  public Flight(String [] data){

      flightNumber = data[1];
      src = data[2];
      dep = data[3];
      depTime = data[4];
      dAMPM = data[5];
      dest = data[6];
      arrival = data[7];
      aTime = data[8];
      aAMPM = data[9];
  }

  public Flight(Flight mainFlight){


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

     String catchLeave = setDateTimeFormat(1);

     return catchLeave;
  }

  @Override
  public String getDestination() {

      return dest;
  }

  @Override
  public String getArrivalString() {

      String Catch = setDateTimeFormat(0);

      return Catch;
  }

  @Override
  public Date getDeparture(){


      return new Date();
  }

  @Override
  public Date getArrival(){

      return new Date();
  }

  public void setNum(int set){

      flightNumber = Integer.toString(set);
  }

  // formats the date and time
  public String setDateTimeFormat(int num){

      DateFormat b = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      String bob;
      Date m;

      try {

          if(num == 1)
              m = b.parse(dep+" "+depTime+" "+dAMPM);

          else
              m = b.parse(arrival+" "+aTime+" "+aAMPM);

          bob = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,Locale.US).format(m);


      }catch (ParseException e){
          System.out.println("cant parse");
          return null;
      }

      return bob;
  }

  @Override
    public int compareTo(Flight newTrip){

      return 1;
  }

  // original
  public String getD(){

        return dep+" "+depTime+" "+dAMPM;
  }

  public String getA(){

      return arrival+" "+aTime+" "+aAMPM;
  }
}
