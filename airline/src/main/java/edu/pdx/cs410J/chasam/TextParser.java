package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AirlineParser;

import java.io.*;

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

                if(array.length !=10){

                    System.out.println("Error parsing file");
                    return false;
                }

                // checks to see if i can add the thing in
                else{

                    if(Project3.Bad_Input(array,0)){

                        System.out.println("File was not formatted correctly");
                        return false;
                    }

                    else{

                        // airlines from the file dont match

                        if(airlines.getName()!= null && !array[0].equals(airlines.getName())) {

                            System.out.println("File was not formatted correctly!");
                            return false;
                        }

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
}
