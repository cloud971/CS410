package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AbstractAirline;
import java.util.*;
import java.text.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project2 {

  public static void main(String[] args) {
    Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    Airline Airline_info = null;
    String readMe = new String("Sam Cha\nProject 1\n7/12/17\n\nThis is the first project for CS410p Advanced Java.\n" +
            "The purpose of this program is to create an airline and add a flight to the airline.\n" +
            "The program takes arguments from the command line and error checks for bad inputs.\nAfter this step a flight is created\n" +
            "and stored inside an arraylist data structure." + "\nThere are four features that this program has.\n" +
            "The first feature is adding flights/airlines, second is displaying the airline,\nThe third feature displays the flight informationan\n" +
            "and the last feature displays a read me to the user.\n");

    TextParser checkName; // checks for same airline
    TextDumper addMe; // adds flight to text file
    String theName;
    int error = 0;


    // tracker
    int index = 0;

    // too many args
    if (args.length > 12) {

        System.out.println("You have entered too much information");
        return;
    }

    // no args
    else if(args.length == 0) {

        System.out.println("You have not entered any information");
        return;
    }

    //prints readme
    else if (args.length == 1 && args[0].equals("-README")){

        System.out.println(readMe);
        return;
    }

    // user wants to print flight but no information
    else if(args.length == 1 && args[0].equals("-print")) {

        System.out.println("There is no flight information");
        return;
    }

    // the user wants to add to file
    else if(args.length == 1 && args[0].equals("-textFile")){

        System.out.println("You have not entered anything to add to the file");
    }

    //not enough args
    else if(args.length < 8){

        System.out.println("You are missing information about your flight");
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

        // check if read me is next and not write to file, prints the flight and readme
        if(args[1].equals("-README") && !args[2].equals("-textFile ") && args.length == 10){

            index = 2;

            if (!Bad_Input(args,index)){

                Airline_info = new Airline(args,index);
                flightInfo = Airline_info.toString();
                System.out.println(flightInfo);
                Airline_info.display();
            }

            System.out.println(readMe);
        }

        // user only wants to print and the next thing is a print file
        else if(args[1].equals("-textFile")){

            boolean showRead = false; // shows read me

            // check if the user wants to display readme
            if(args[3].equals("-README"))
                showRead = true;

            // check to see of the use has enough info
            if (showRead == true && args.length != 12){

                System.out.println("Number of arguments is invalid");
                return;
            }

            // the user doesnt want to display readme, must have 11 args
            if (showRead == false && args.length != 11){

                System.out.println("Number of arguments is invalid");
                return;
            }

            if (showRead == true) {

                theName = args[2];
                index = 4;
            }
            else {

                theName = args[2];
                index = 3;
            }

            // checks for bad inputs
            if (!Bad_Input(args,index)){

                checkName = new TextParser();

                // checks if the file can be found
                if(checkName.checkFile(theName)){

                    // the file is parsed
                    if (checkName.parseFile()){

                        Airline_info = checkName.parse();

                        // airline has the same name
                        if(Airline_info.sameName(args[index])){

                            Airline_info.mainAdd(args, index+1);

                            TextDumper dumping = new TextDumper(theName);
                            dumping.dump(Airline_info);
                            Airline_info.printnew();
                        }

                        else
                            System.out.println("Airline names are not the same");
                    }

                    else {

                        System.out.println("Your airline name doesnt match the file or the file isn't formatted correct");
                    }
                }

                // the file isnt found and create the file and add to begining
                else{

                    TextDumper dumping = new TextDumper(theName,1);
                    checkName.makeFile();
                    Airline_info = new Airline(args,index);
                    dumping.dump(Airline_info);

                    System.out.println(Airline_info.toString());
                    Airline_info.printnew();
                }

                if (showRead)
                    System.out.println(readMe);
            }
        }

        // user wants to read second
        else if(args[1].equals("-README")) {

            if (args.length != 10){
                System.out.println("Missing arguments or too many");
                return;
            }

            index = 2;
            Airline_info = new Airline(args,index);
            System.out.println(Airline_info.toString());
            Airline_info.display();
            System.out.println(readMe);

        }

        // user wants to print the flight and add
        else if(args.length == 9){

            index= 1;
            Airline_info = new Airline(args,index);
            System.out.println(Airline_info.toString());
            Airline_info.display();
        }
    }

    // user wants to add to text file
    else if (args[0].equals("-textFile"))
        fileFirst(readMe,Airline_info,index,args);


    // no readme, print or file read
    else if(args.length == 8){

        if (!Bad_Input(args, index)) {

            System.out.println("Your flight has been added");
            Airline_info = new Airline(args, index);
        }
    }

  }

  // checks for bad inputs
  public static boolean Bad_Input(String[] Check_Err,int index){

      String regex ="[0-9]+";
      String dateFormat = "MM/dd/yyyy";

/*
      // checks for valid airline name
      if(Check_Err[index].matches(regex) || !Check_Err[index].matches("[a-zA-Z]+")){

          System.out.println("Invalid Airline");
          return true;
      }
*/
      if(!Check_Err[index+1].matches(regex)) { // Check for valid flightnumber

          System.out.println("Your flight number needs to contain only numbers ex: 123");
          return true;
      }

      // Src greater than length and contains numbers
      else if(Check_Err[index+2].length() != 3 || !Check_Err[index+2].matches("[a-zA-Z]+")) {

          System.out.println("Your code of departure needs to be a three letter code ex:abc");
          return true;
      }


      else if(!Check_date(Check_Err[index+3],dateFormat)){ // check for valid departure date format

          System.out.println("Your departure date needs to be in one of these formats, mm/dd/yyyy, m/dd/yyyy, mm/d/yyyy ");
            return true;
      }

      else if (!Check_time(Check_Err[index+4])){ // check for valid departure time format

          System.out.println("Your departure time is invalid, ex: 5:23");
          return true;
      }

      // check for valid arrival airport code
      else if(Check_Err[index+5].length() != 3 || !Check_Err[index+5].matches("[a-zA-Z]+")) {

          System.out.println("Your code of arrival needs to be three letters ex:abc");
          return true;
      }

      // check for valid arrival date
      else if(!Check_date(Check_Err[index+6],dateFormat)){ // check for valid departure date format

          System.out.println("Your arrival date needs to be in one of these formats, mm/dd/yyyy, m/dd/yyyy, mm/d/yyyy ");
            return true;
      }

      else if (!Check_time(Check_Err[index+7])){ // check for valid departure time format

          System.out.println("Your arrival time is invalid ex: 10:04");
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

      String timeFormat ="HH:mm";
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


  public static void fileFirst(String readme, Airline theList, int theIndex,String [] info){

      String flightInfo;

      // check if read me is next and not write to file, prints the flight and readme
      if(info[2].equals("-README") && info.length == 11){

          theIndex = 3;

          if (!Bad_Input(info,theIndex)) {

              TextParser parseMe = new TextParser();

              if (parseMe.checkFile(info[1])) {

                  // the file is parsed
                  if (parseMe.parseFile()) {

                      theList = parseMe.parse();

                      // airline has the same name
                      if (theList.sameName(info[theIndex])) {

                          theList.mainAdd(info, theIndex + 1);
                          TextDumper dumping = new TextDumper(info[1]);
                          dumping.dump(theList);
                      }

                      else
                          System.out.println("Error Airline names are different");
                  }

                  else
                      System.out.println("File can't be parsed");
              }

              // the file isnt found and create the file and add to begining
              else {

                  TextDumper dumping = new TextDumper(info[1], 1);
                  parseMe.makeFile();
                  theList = new Airline(info, theIndex);
                  dumping.dump(theList);
              }
          }

          System.out.println(readme);
      }

      // user wants to print and add to file
      else if(info[2].equals("-print")){

          boolean reads = false;

          if (info[3].equals("-README") && info.length != 12) {

              System.out.println("Missing arguments");
              return;
          }

          else if(info[3].equals("-README"))
              reads = true;

          if (reads)
              theIndex = 4;

          if (!reads)
              theIndex = 3;

          if(!Bad_Input(info,theIndex)){

              TextParser parseMe = new TextParser();

              if (parseMe.checkFile(info[1])) {

                  // the file is parsed
                  if (parseMe.parseFile()) {

                      theList = parseMe.parse();

                      // airline has the same name
                      if (theList.sameName(info[theIndex])) {

                          theList.mainAdd(info, theIndex + 1);
                          TextDumper dumping = new TextDumper(info[1]);
                          dumping.dump(theList);
                          theList.printnew();
                      }

                      else
                          System.out.println("Error Airline names are different");
                  }

                  else
                      System.out.println("File can't be parsed");
              }

              // the file isnt found and create the file and add to begining
              else {

                  TextDumper dumping = new TextDumper(info[1], 1);
                  parseMe.makeFile();
                  theList = new Airline(info, theIndex);
                  dumping.dump(theList);
                  theList.printnew();
              }
          }

          if(reads)
              System.out.println();
      }

      // user only wants to add to file
      else if(info.length == 10){

          theIndex = 2;

          if (!Bad_Input(info,theIndex)) {

              TextParser parseMe = new TextParser();

              if (parseMe.checkFile(info[1])) {

                  // the file is parsed
                  if (parseMe.parseFile()) {

                      theList = parseMe.parse();

                      // airline has the same name
                      if (theList.sameName(info[theIndex])) {

                          theList.mainAdd(info, theIndex + 1);
                          TextDumper dumping = new TextDumper(info[1]);
                          dumping.dump(theList);
                      }

                      else
                          System.out.println("Error Airline names are different");
                  }

                  else
                      System.out.println("File can't be parsed");
              }

              // the file isnt found and create the file and add to begining
              else {

                  TextDumper dumping = new TextDumper(info[1], 1);
                  parseMe.makeFile();
                  theList = new Airline(info, theIndex);
                  dumping.dump(theList);
              }
          }
      }

      else
          System.out.println("Error with arguments");

      return;
  }
}