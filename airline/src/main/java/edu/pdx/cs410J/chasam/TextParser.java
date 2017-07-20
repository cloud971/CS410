package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AirlineParser;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yoda on 7/16/2017.
 */
public class TextParser implements AirlineParser<Airline> {

    private File checkFile;
    private Airline airlines;
    private FileReader reader;
    private String fName;

    public TextParser(){

    }

    public TextParser(String airline){

    }

    @Override
    public Airline parse(){

        return airlines;
    }


    public boolean checkFile(String fileName){


            checkFile = new File(fileName);

            // the file doesn't exist
            if (!checkFile.exists())
                return false;

            fName = fileName;

        return true;
    }

    //parsing the array
    public boolean parseFile(){


        String [] array; // args of array
        String error ; // checks end of file;

        // creates filereader
        try{

            reader = new FileReader(fName);

        }catch (FileNotFoundException e){
            return false;
        }

        // creates bufferdreader

        // gets the size of the file
        if (checkFile.length() == 0)
            return false;

        BufferedReader reading = new BufferedReader(reader);
        airlines = new Airline();

        // open file and reads
        try {

            while ((error = reading.readLine()) != null){

                array = error.split(",");

                if(array.length !=8){

                    System.out.println("Error parsing the file");
                    return false;
                }

                // checks to see if i can add the thing in
                else{

                    if(wrong_Input(array)){

                        System.out.println("File was not formatted correct");
                        return false;
                    }

                    else{

                        airlines.toAdd(array);
                    }
                }
            }

            reader.close();

        }catch (IOException e){
        }

        return true;
    }

    // makes the file if it doesnt exist
    public void makeFile(){

        try {

            checkFile.createNewFile();

        }catch (IOException e){

        }
    }

    public boolean wrong_Input(String[] Check_Err){

        String regex ="[0-9]+";
        String dateFormat = "MM/dd/yyyy";


        if(!Check_Err[1].matches(regex)) { // Check for valid flightnumber

            return true;
        }

        // Src greater than length and contains numbers
        else if(Check_Err[2].length() != 3 || !Check_Err[2].matches("[a-zA-Z]+")) {

            return true;
        }


        else if(!Check_date(Check_Err[3],dateFormat)){ // check for valid departure date format

            return true;
        }

        else if (!Check_time(Check_Err[4])){ // check for valid departure time format

            System.out.println("Your departure time is invalid, ex: 5:23");
            return true;
        }

        // check for valid arrival airport code
        else if(Check_Err[5].length() != 3 || !Check_Err[5].matches("[a-zA-Z]+")) {

            return true;
        }

        // check for valid arrival date
        else if(!Check_date(Check_Err[6],dateFormat)){ // check for valid departure date format

            return true;
        }

        else if (!Check_time(Check_Err[7])){ // check for valid departure time format

            return true;
        }

        return false;
    }

    // checks for incorrect time format
    public boolean Check_date(String theDate, String theFormat){

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

}
