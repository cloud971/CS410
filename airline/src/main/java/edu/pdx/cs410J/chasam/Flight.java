package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

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
    private Date appear;
    private Date leave;

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

      DateFormat b = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      String bob;

      try {

          leave = b.parse(dep+" "+depTime+" "+dAMPM);
          bob = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM,Locale.US).format(leave);


      }catch (ParseException e){

          System.out.println("cant parse");
          return null;
      }

      return leave;
  }

  @Override
  public Date getArrival(){

      DateFormat b = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      String bob;

      try {

          appear = b.parse(dep+" "+depTime+" "+dAMPM);
          bob = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM,Locale.US).format(appear);


      }catch (ParseException e){

          System.out.println("cant parse");
          return null;
      }

      return appear;
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

      int catchMe = this.src.compareTo(newTrip.src);

      if (catchMe == 0)
          catchMe = compareDate(newTrip);

      return catchMe;
  }

  // original
  public String getD(){

        return dep+" "+depTime+" "+dAMPM;
  }

  public String getA(){

      return arrival+" "+aTime+" "+aAMPM;
  }

  public int compareDate(Flight someF) {

      DateFormat b = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      Date myDate;
      Date compareD;
      int catchThis = 0;

      try {

          myDate = b.parse(getD());
          compareD = b.parse(someF.getD());

          catchThis = myDate.compareTo(compareD);

      } catch (ParseException e) {
          System.out.println("cant parse");
      }

      return catchThis;
  }

  public boolean compareEvery(Flight sameOne){

      if (src.equals(sameOne.src) && compareDate(sameOne) == 0 && flightNumber.equals(sameOne.flightNumber) && dest.equals(sameOne.dest)){


          DateFormat c = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
          Date myDate;
          Date compareD;
          int catchThis = 0;

          try {

              myDate = c.parse(getA());
              compareD = c.parse(sameOne.getA());
              catchThis = myDate.compareTo(compareD);

          } catch (ParseException e) {
              System.out.println("cant parse");
          }

          if (catchThis == 0)
              return true;
      }

      return false;
  }

  public void makePretty(){

      DateFormat b = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      long duration = 0;

      try {

          String bye = arrival+" "+aTime+" "+aAMPM;
          String imHere = dep+" "+depTime+" "+dAMPM;

          Date m = b.parse(bye);
          Date a = b.parse(imHere);


          long hi = m.getTime() - a.getTime();
          duration = hi/60000;

      }catch (ParseException e){
          System.out.println("cant parse");
      }

      System.out.println("Flight " +getNumber() + " departs "+ AirportNames.getName(src) + " at " + getDepartureString() +
              " arrives at "+AirportNames.getName(dest)+" on "+getArrivalString()+" in "+duration+" minutes");
  }

  public String returnPretty(){

      return "Flight " +getNumber() + " departs "+ AirportNames.getName(src) + " at " + getDepartureString() + " arrives at "+AirportNames.getName(dest)+" on "+getArrivalString();
  }
}

