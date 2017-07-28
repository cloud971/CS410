package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirportNames;
import java.util.*;
import java.text.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project3 {

   private static String readMe;
   private static Airline airlineInfo;
   private static TextParser parseMe;
   private static TextDumper dumping;
   private static PrettyPrinter soPretty;
   private static int index = 0;


  public static void main(String[] args) {
      Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

      String Bob = AirportNames.getName("PDX");

      readMe = new String("Sam Cha\nProject 1\n7/12/17\n\nThis is the first project for CS410p Advanced Java.\n" +
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

      // too many args
      if (args.length > 16) {

          System.out.println("You have entered too much information");
          return;
      }

      // no args
      else if (args.length == 0) {

          System.out.println("You have not entered any information");
          return;
      }

      //prints readme
      else if (args[0].equals("-README")) {

          System.out.println(readMe);
          return;
      }

      int pretty = -1;
      int readThis = -1;
      int printNum = -1;
      int textRead = -1;
      int total = 10;

      for (int i = 0; i < args.length; ++i) {

          if (args[i].equals("-print"))
              printNum = i;
          if (args[i].equals("-textFile"))
              textRead = i;
          if (args[i].equals("-pretty"))
              pretty = i;
          if (args[i].equals("-README"))
              readThis = i;
      }

      if (pretty >= 0)
          total += 2;

      if (printNum >= 0)
          total += 1;

      if (readThis >= 0)
          total += 1;

      if (textRead >= 0)
          total += 2;

      if (total != args.length) {

          System.out.println("You have missing arguments");
          return;
      }

      // do everything
      else if (args.length == 16)
          index = 6;

      else if (args.length == 15)
          index = 5;

      else if (args.length == 14)
          index = 4;

      else if (args.length == 13)
          index = 3;

      else if (args.length == 12)
          index = 2;

      else if (args.length == 11)
          index = 1;

      else if (args.length == 10)
          index = 0;

      if (noZone(args[index + 2], args[index + 6]))
          return;

      args[index+5] = args[index+5].toUpperCase();
      args[index+9] = args[index+9].toUpperCase();

      System.out.println(args[index+5]);
      if (!Bad_Input(args, index)) {


          if (total == 10)
              createObj(args, index);

          if (total == 11) {  // prints and adds flight

              createObj(args, index);
              airlineInfo.printnew();
          }

          if (total == 12) { // 3 options print read, text,pretty

              if (textRead == 0) // user wants to print to textfile
                  parseWrapper(args, args[1], index);


              else if (pretty == 0) {// pretty print

                  if (args[1].equals("-")) { // pretty print

                      createObj(args,index);
                      System.out.println(airlineInfo.toString());
                      airlineInfo.displayPretty();
                  }

                  else
                      prettyFunc(args,args[pretty+1]);
              }

              else if (printNum == 0) { // print and read

                  createObj(args, index);
                  airlineInfo.printnew();
                  System.out.println(readMe);
                  return;
              }
          }

          else if (total == 13){

              // parse file then print we know that read me cant be first
              if(textRead == 0|| textRead == 1){

                  parseWrapper(args, args[textRead + 1], index);
                  airlineInfo.sortMe();

              }


              else{

                  if (pretty ==1 || pretty ==0){

                      if (args[pretty+1].equals("-")){

                          createObj(args,index);
                          System.out.println(airlineInfo.toString());
                          airlineInfo.displayPretty();
                      }

                      else
                          prettyFunc(args, args[pretty + 1]);
                  }
              }

              if (readThis == 2)
                  System.out.println(readMe);

              else{

                  createObj(args, index);
                  airlineInfo.printnew();
              }
          }

          else if (total == 14){

              // i am doing both
              if (textRead>=0&&pretty >= 0){ // read file and pretty

                  parseWrapper(args,args[textRead+1],index);

                  if (args[pretty+1].equals("-"))
                      airlineInfo.displayPretty();

                  // prints pretty to file
                  else
                      prettyFunc(args,args[pretty+1]);

                  return;
              }

              else if(pretty < 0) // only read text
                  parseWrapper(args,args[textRead+1],index);

              else{

                  if (args[pretty+1].equals("-"))
                      airlineInfo.displayPretty();

                      // prints pretty to file
                  else
                      prettyFunc(args,args[pretty+1]);
              }

              // ONLY PRINT IF SUCCESSFULL, this case means read is last
              if (readThis != 3){

                  createObj(args, index);
                  airlineInfo.printnew();
                  System.out.println(readMe);
              }

              else // takes care if read is 3rd
              System.out.println(readMe);
          }

          else if(total ==15){

              if (readThis < 0) { // there is no readme

                  // parse the file and pretty print then print the new flight
                  parseWrapper(args,args[textRead+1],index);


                  if (args[pretty+1].equals("-"))
                      airlineInfo.displayPretty();

                  else
                      prettyFunc(args,args[pretty+1]);

                  airlineInfo.printnew();

              }

              if (readThis == 4){ // readme is last parse file pretty print

                  parseWrapper(args,args[textRead+1],index);
                  // DO SOMETHING PRETTY
              }

              else if (readThis == 2){ // read is second

                  if (textRead == 0) // check if text is first
                      parseWrapper(args,args[textRead+1],index);

                  // do something pretty because text is not first
                  else{

                      if (args[pretty+1].equals("-"))
                          airlineInfo.displayPretty();

                      else
                          prettyFunc(args,args[pretty+1]);
                  }

                  System.out.println(readMe);
                  return;
              }

              airlineInfo.printnew();
              return;
          }

          else if (total == 16){

              // readme is second so we print first
              if(readThis == 1){

                  createObj(args, index);
                  airlineInfo.printnew();
                  System.out.println(readMe);
              }

              else if(readThis == 2){ // read me is third

                  if (pretty < textRead){ // pretty read

                      if (args[pretty+1].equals("-"))
                          airlineInfo.displayPretty();

                      else
                          prettyFunc(args,args[pretty+1]);

                  }

                  else{ // text read

                      parseWrapper(args,args[textRead+1],index);
                  }

                  System.out.println(readMe);
                  return;
              }

              else if (readThis == 3){

                  if (pretty < textRead){

                      // do something pretty

                      if (args[pretty+1].equals("-"))
                          airlineInfo.displayPretty();

                      else
                          prettyFunc(args,args[pretty+1]);

                      System.out.println(readMe);
                  }

                  else{

                      parseWrapper(args,args[textRead+1],index);
                  }

                  airlineInfo.printnew();
                  System.out.println(readMe);
              }

              else if (readThis == 4) { // there is no print

                  parseWrapper(args,args[textRead+1],index);

                  if (args[pretty+1].equals("-"))
                      airlineInfo.displayPretty();

                  else
                      prettyFunc(args,args[pretty+1]);

                  System.out.println(readMe);
              }

              else if (readThis == 5){

                  parseWrapper(args,args[textRead+1],index);

                  if (args[pretty+1].equals("-"))
                      airlineInfo.displayPretty();

                  else
                      prettyFunc(args,args[pretty+1]);

                  airlineInfo.printnew();
                  System.out.println(readMe);
              }

              return;
          }
      }
  }


  // writes to a file
  public static void prettyFunc(String [] args,String fileName){

      if (airlineInfo == null)
          airlineInfo = new Airline(args,index);

      soPretty = new PrettyPrinter();

      if(soPretty.fileCheck(fileName)) {// the file exist

          soPretty.dump(airlineInfo);
      }

      else {

          soPretty.makeDir(fileName);
          soPretty.dump(airlineInfo);
      }

  }


  public static boolean noZone(String src,String destiny){

      if(AirportNames.getName(src) == null && AirportNames.getName(destiny) == null) {

          System.out.println("Error unknown code of arrival and departure");
          return true;
      }

      else if(AirportNames.getName(src)!= null && AirportNames.getName(destiny) == null) {

          System.out.println("Error unknown code of arrival");
          return true;
      }

      else if(AirportNames.getName(src)== null && AirportNames.getName(destiny) != null) {

          System.out.println("Error unknown code of departure");
          return true;
      }

      return false;
  }

  // checks if file can be parsed
  public static boolean parseWrapper(String [] args, String folderName, int place){

      parseMe = new TextParser();

      // checks if the file can be found
      if(parseMe.checkFile(folderName)){

          // the file is parsed
          if (parseMe.parseFile()){

              airlineInfo = parseMe.parse();

              // airline has the same name
              if(airlineInfo.sameName(args[place])){

                  airlineInfo.mainAdd(args, place+1);
                  TextDumper dumping = new TextDumper(folderName);
                  dumping.dump(airlineInfo);
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

          TextDumper dumping = new TextDumper(folderName,1);
          parseMe.makeFile();
          airlineInfo = new Airline(args,place);
          dumping.dump(airlineInfo);
      }

      return true;
  }

  // creates new obj airline
  public static void createObj(String [] data, int placeholder){

      airlineInfo = new Airline(data,placeholder);
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


      else if(!Check_date(Check_Err[index+3])){ // check for valid departure date format

          System.out.println("Your departure date needs to be in one of th formats, mm/dd/yyyy, m/dd/yyyy, mm/d/yyyy");
            return true;
      }

      else if (!Check_time(Check_Err[index+4], Check_Err[index+5])){ // check for valid departure time format

          System.out.println("Your departure time is invalid, ex: 5:23 am");
          return true;
      }

      // check for valid arrival airport code
      else if(Check_Err[index+6].length() != 3 || !Check_Err[index+6].matches("[a-zA-Z]+")) {

          System.out.println("Your code of arrival needs to be three letters ex:abc");
          return true;
      }

      // check for valid arrival date
      else if(!Check_date(Check_Err[index+7])){ // check for valid departure date format

          System.out.println("Your arrival date needs to be in one of these formats, mm/dd/yyyy, m/dd/yyyy, mm/d/yyyy ");
            return true;
      }

      else if (!Check_time(Check_Err[index+8],Check_Err[index+9])){ // check for valid departure time format

          System.out.println("Your arrival time is invalid ex: 10:04 am");
          return true;
      }

      else if(noZone(Check_Err[index+2],Check_Err[index+6]))
          return true;

      return false;
  }


  // checks for incorrect time format
  public static boolean Check_date(String theDate){

      String [] theDateSplit;
      theDateSplit = theDate.split("/");

      if(theDateSplit.length != 3)
          return false;

      // this is the month
      if(((theDateSplit[0].length() !=2)^(theDateSplit[0].length() != 1)) == false)
          return false;

      // this is the year
      if(((theDateSplit[1].length() !=2)^(theDateSplit[1].length() != 1)) == false)
          return false;

      if (theDateSplit[2].length() != 4)
          return false;

      return true;
  }

    // checks for correct time format
    public static boolean Check_time(String timeString, String marker){

        String timeFormat ="hh:mm";
        Date timeDate;

        if (!marker.equals("AM") && !marker.equals("PM"))
            return false;

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
