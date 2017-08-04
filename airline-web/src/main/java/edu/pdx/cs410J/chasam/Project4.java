package edu.pdx.cs410J.chasam;
import edu.pdx.cs410J.AirportNames;

import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";
    public static final String readMe = new String("Sam Cha\nProject 1\n7/12/17\n\nThis is the first project for CS410p Advanced Java.\n" +
            "The purpose of this program is to create an airline and add a flight to the airline.\n" +
            "The program takes arguments from the command line and error checks for bad inputs.\nAfter this step a flight is created\n" +
            "and stored inside an arraylist data structure." + "\nThere are four features that this program has.\n" +
            "The first feature is adding flights/airlines, second is displaying the airline,\nThe third feature displays the flight informationan\n" +
            "and the last feature displays a read me to the user.\n");

    private static int index = 0;
    private static String searchD;
    private static String searchSrc;
    private static String searchName;
    private static int searching = -1;
    private static int printMe = -1;
    private static int reading = -1;
    private static int theHost = -1;
    private static int thePort = -1;
    private static int total = 10;
    private static int port;
    private static Airline airlineInfo;
    private static AirlineRestClient client;

    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        String key = null;
        String value = null;

        //missing args
        if(args.length == 0) {

            System.out.println("You are missing arguments");
            return;
        }

        //exit first is read me
        if (args[0].equals("-README")){

            System.out.println(readMe);
            return;
        }

        // too many arguments
        if(args.length > 20) {

            System.out.println("Too many arguments");
        }


        int c =0;

        // getting the options
        while( c < args.length) {

            if (args[c].equals("-host")) { // getting hostname

                hostName = args[c + 1];
                theHost = c;
            }

            if (args[c].equals("-port")) { // getting port number

                portString = args[c + 1];

            }

            if (args[c].equals("-search")) { // getting things to search

                searchName = args[c+1];
                searchD = args[c+2];
                searchSrc = args[c+3];
                searching = c;
            }

            if (args[c].equals("-README"))
                reading = c;

            if (args[c].equals("-print"))
                printMe = c;

            if(args[c].equals("-port"))
                thePort = c;

            c++;
        }

        if (searching > -1)
            total += 4;

        if (theHost > -1)
            total += 2;

        if (thePort >-1)
            total+=2;

        if (printMe >-1)
            total+= 1;

        if (reading > -1)
            total += 1;

        if(searching > -1 && args.length < 8)
            return;

        // i want to search
        if (searching > -1) {

            // min length is 8 and 9 for search
            if (args.length == 8 || args.length == 9) {

                // if there is a port
                if (theHost > -1 && thePort > -1) {

                    setPort(args[thePort + 1]);
                    client = new AirlineRestClient(args[theHost + 1], port);
                    find(args);

                }

                // wrong args
                else {

                    System.out.println("Incorrect number of arguments");
                    return;
                }

                if (reading > -1)
                    System.out.println();

                return;
            }
        }

        //looking
        if(total != args.length) {

            System.out.println("incorrect number of arguments");
            return;
        }


        index = args.length -10; // setting index
        args[index+5] = args[index+5].toUpperCase();
        args[index+9] = args[index+9].toUpperCase();


        if(Bad_Input(args,index))
            return;

        // create airline obj
        if (args.length == 10) {

            createObj(args, index);
            return;
        }

        // print
        if (args.length == 11){

            createObj(args,index);
            airlineInfo.printnew();
            return;
        }

        // print and readme
        if (args.length == 12){

            if (printMe > -1 && reading > -1){

                createObj(args,index);
                airlineInfo.printnew();
                System.out.println(readMe);
            }

            else
                System.out.println("Missing arguments");

            return;
        }

        // not possible
        if(args.length == 13 || args.length == 17) {

            System.out.println("Missing arguments");
            return;
        }


        // host and port
        if (args.length == 14){

            // adding to server
            if (theHost > -1 && thePort > -1) {

                setPort(args[thePort + 1]);
                adding(args);
            }

            else
                System.out.println("Missing arguments");

            return;
        }


        // host port, print or read
        if (args.length == 15){

            // adding to server
            if (theHost > -1 && thePort > -1){

                if (printMe > -1) { // print

                    setPort(args[thePort + 1]);

                    if (adding(args)) {

                        createObj(args, index);
                        airlineInfo.printnew();
                    }

                    else {

                        System.out.println("Error adding flight");
                        return;
                    }
                }

                else // read
                    System.out.println(readMe);
            }

            else
                System.out.println("Missing arguments");

            return;
        }


        // add to server print and read
        if (args.length == 16){

            // doing all options but search
            if (searching < 0){

                setPort(args[thePort+1]);

                if (printMe > -1) { // print

                    setPort(args[thePort + 1]);

                    if (adding(args)) {

                        createObj(args, index);
                        airlineInfo.printnew();
                    }

                    else {

                        System.out.println("Error adding flight");
                        return;
                    }
                }

                System.out.println(readMe);
            }

            return;
        }

        // search and store
        if (args.length == 18){

            if (reading < 0 && printMe < 0){

                setPort(args[thePort+1]);
                if(!adding(args)) {

                    System.out.println("error adding flight");
                    return;
                }

                find(args);
            }

            else
                System.out.println("Missing args");

            return;
        }

        if (args.length == 19 || args.length == 20){

            setPort(args[thePort+1]);
            boolean added = false;

            added = adding(args);
            find(args);

            if (printMe > -1){

                if (added == true) {

                    createObj(args, index);
                    airlineInfo.printnew();
                }

                else {

                    System.out.println("Error adding flight");
                    return;
                }
            }

            if (reading > -1)
                System.out.println(readMe);
        }

        /*
        String message;
        try {

            if (key == null) {

                // Print all key/value pairs
                Map<String, String> keysAndValues = client.getAllKeysAndValues();
                StringWriter sw = new StringWriter();
                Messages.formatKeyValueMap(new PrintWriter(sw, true), keysAndValues);
                message = sw.toString();

            }

            else if (value == null) {

                // Print all values of key
                message = Messages.formatKeyValuePair(key, client.getValue(key));

            }

            else {

                // Post the key/value pair
                client.addKeyValuePair(key, value);
                message = Messages.mappedKeyValue(key, value);
            }

        } catch ( IOException ex ) {
            error("While contacting server: " + ex);
            return;
        }

        System.out.println(message);
*/

    }


    // find search
    public static void find(String [] treasure){


        if(AirportNames.getName(treasure[searching+2]) == null){

            System.out.println("invalid src");
            return;
        }


        if (AirportNames.getName(treasure[searching+3]) == null){

            System.out.println("invalid dest");
            return;
        }

        searchName = treasure[searching+1];
        searchD = treasure[searching+3];
        searchSrc = treasure[searching+2];
        client.getMe(searchName, searchSrc, searchD);

    }

    // adding flight to airline
    public static boolean adding(String [] args){

        client = new AirlineRestClient(args[theHost+1], port);
        String leave = args[index+3]+" "+args[index+4]+" "+args[index+5];
        String theArrival = args[index+7]+" "+args[index+8]+" "+args[index+9];

        if (client.addMe(args[index], args[index+1],args[index+2],leave,args[index+6],theArrival))
            return true;

        return false;
    }


    // setting up port
    public static void setPort(String portString){

        try {

            port = Integer.parseInt(portString);

        } catch (NumberFormatException ex) {

            usage("Port \"" + portString + "\" must be an integer");
            System.exit(1);
        }
    }


    // creating an object
    public static void createObj(String [] data, int placeholder){

        airlineInfo = new Airline(data,placeholder);
    }


    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        System.exit(1);
    }


    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [key] [value]");
        err.println("  host    Host of web server");
        err.println("  port    Port of web server");
        err.println("  key     Key to query");
        err.println("  value   Value to add to server");
        err.println();
        err.println("This simple program posts key/value pairs to the server");
        err.println("If no value is specified, then all values are printed");
        err.println("If no key is specified, all key/value pairs are printed");
        err.println();

        System.exit(1);
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

        for(int i = 0; i < theDateSplit.length; ++i){

            if(!theDateSplit[i].matches("[0-9]+"))
                return false;
        }

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

    public static boolean noZone(String src,String destiny){

        if(AirportNames.getName(src) == null && AirportNames.getName(destiny) == null) {

            System.out.println("Error unknown code of arrival and departure");
            return true;
        }

        else if(AirportNames.getName(destiny) == null) {

            System.out.println("Error unknown code of arrival");
            return true;
        }

        else if(AirportNames.getName(src)== null) {

            System.out.println("Error unknown code of departure");
            return true;
        }

        return false;
    }
}