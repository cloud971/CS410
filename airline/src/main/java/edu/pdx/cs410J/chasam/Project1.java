package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AbstractAirline;
import java.util.*;
import java.text.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void main(String[] args) {
    Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    Airline Airline_info;
    String readMe = new String("Sam Cha\nProject 1\n7/12/17\n\nThis is the first project for CS410p Advanced Java.\n" +
            "The purpose of this program is to create an airline and add a flight to the airline.\n" +
            "The program takes arguments from the command line and error checks for bad inputs.\nAfter this step a flight is created\n" +
            "and stored inside an arraylist data structure." + "\nThere are four features that this program has.\n" +
            "The first feature is adding flights/airlines, second is displaying the airline,\nThe third feature displays the flight informationan\n" +
            "and the last feature displays a read me to the user.\n");

    /*loops string and displays
    for (String arg : args) {
      System.out.println(arg);
    }

*/
    // tracker
    int index = 0;

    // too many args
    if (args.length > 10) {

        System.out.println("too many arguments");
        return;
    }

    // no args
    else if(args.length == 0) {

        System.out.println("No arguments");
        return;
    }

    // not enough args
    else if(args.length < 8){

        System.out.println("Not enough arguments");
        return;
    }

    // print readme and leave
    else if(args[0].equals("-README")){

      System.out.println(readMe);
      return;
    }


    //first index is print
    else if(args[0].equals("-print")){

        String flightInfo;
      // check if read me is next
      if(args[1].equals("-README")){

          index = 2;
          // check to see if input is correct format
          if (!Bad_Input(args,index)){
              Airline_info = new Airline(args,index);
              flightInfo = Airline_info.toString();
              System.out.println(flightInfo);
          }

          // prints readme
          System.out.println(readMe);
      }

      else{

          index = 1;

          if (!Bad_Input(args,index)){

              Airline_info = new Airline(args,index);
              flightInfo = Airline_info.toString();
              System.out.println(flightInfo);
          }
      }
    }

    // no read me or print
    else {

        if (!Bad_Input(args, index)) {

            System.out.println("adding");
            Airline_info = new Airline(args, index);
        }
    }
  }

  // checks for bad inputs
  public static boolean Bad_Input(String[] Check_Err,int index){

      String regex ="[0-9]+";
      String dateFormat = "MM/dd/yyyy";


      // checks for valid airline name
      if(Check_Err[index].matches(regex) || !Check_Err[index].matches("[a-zA-Z]+")){

          System.out.println("Ivalid Airline");
          return true;
      }

      else if(!Check_Err[index+1].matches(regex)) { // Check for valid flightnumber

          System.out.println("Invalid flightNumber");
          return true;
      }

      // Src greater than length and contains numbers
      else if(Check_Err[index+2].length() != 3 || !Check_Err[index+2].matches("[a-zA-Z]+")) {

          System.out.println("Invalid departure code");
          return true;
      }


      else if(!Check_date(Check_Err[index+3],dateFormat)){ // check for valid departure date format

          System.out.println("Invalid departure date");
            return true;
      }

      else if (!Check_time(Check_Err[index+4])){ // check for valid departure time format

          System.out.println("Invalid depature time");
          return true;
      }

      // check for valid arrival airport code
      else if(Check_Err[index+5].length() != 3 || !Check_Err[index+5].matches("[a-zA-Z]+")) {

          System.out.println("Invalid arrival airport code");
          return true;
      }

      // check for valid arrival date
      else if(!Check_date(Check_Err[index+6],dateFormat)){ // check for valid departure date format

          System.out.println("Invalid arrival date");
            return true;
      }

      else if (!Check_time(Check_Err[index+7])){ // check for valid departure time format

          System.out.println("Invalid arrival time");
          return true;
      }

      return false;
  }

  // checks for incorrect time format
  public static boolean Check_date(String theDate, String theFormat){

      Date aDate = null;

      try {

          SimpleDateFormat cFormat = new SimpleDateFormat(theFormat);
          aDate = cFormat.parse(theDate);

          if(theDate.equals(cFormat.format(aDate))){

              return true;
          }

          // adding a zero to account for missing zero on single month
          // also accounts for m/dd/yyyy
          else if (("0"+theDate).equals(cFormat.format(aDate))){

              return true;
          }

          cFormat = new SimpleDateFormat("M/d/yyyy");
          aDate = cFormat.parse(theDate);

          // checking for only one digit day and month, no leading zeros
          if (theDate.equals(cFormat.format(aDate)))

              return true;


          cFormat = new SimpleDateFormat("MM/d/yyyy");
          aDate = cFormat.parse(theDate);

          // checking for month with leading zero and single digit day
          if (theDate.equals(cFormat.format(aDate)))

              return true;


      } catch (ParseException ex) {
          System.out.println("Can't parse");
      }

      return false;
  }

  // checks for correct time format
  public static boolean Check_time(String timeString){

      String timeFormat ="hh:mm";
      Date timeDate;

      try {

          SimpleDateFormat sFormat = new SimpleDateFormat(timeFormat);
          timeDate = sFormat.parse(timeString);

          if (timeString.equals(sFormat.format(timeDate)))

              return true;

          // accounts for single time
          else if (("0"+timeString).equals(sFormat.format(timeDate)))

              return true;

      }catch (ParseException ex){

      }

      return false;
  }

}