package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AbstractAirline;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void main(String[] args) {
    Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    Airline Airline_info;

    /*loops string and displays*/
    for (String arg : args) {
      System.out.println(arg);
    }

    // too many args
    if (args.length > 8) {

        System.out.println("too many");
        return;
    }

    // print and leave
    else if(args[0].equals("-README")){
      System.out.println("Welcome to airline");
      return;
    }

    /*
    //first index is print
    else if(args[0].equals("-Print")){

        // index of array
        int index = 0;

      // check if read me is next
      if(args[1].equals("-README")){

          // check to see if input is correct format
          if (Bad_Input(args,index))
      }

      // read me is not next
      else{

      }

    }

    // no read me or print
    else
      Airline_info = new Airline(args);

    System.exit(1);
    */
  }

  public static boolean Bad_Input(String[] Check_Err,int index){

      return  false;
  }

}