package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yoda on 7/25/2017.
 */
public class PrettyPrinter implements AirlineDumper<Airline> {


    private File checkFile;
    private String fName;
    private FileReader writeMe;
    private int sumNum;

    public PrettyPrinter(){

    }

    @Override
    public void dump(Airline dumpMe) {

        Collection<Flight> myList = dumpMe.getFlights(); //get collection
        List<Flight> convertF = new ArrayList<>(myList); // make it list
        Collections.sort(convertF); // sort

        try {

            BufferedWriter out = new BufferedWriter(new FileWriter(fName,true));

            for (int i = 0; i < convertF.size(); ++i){

                if (sumNum ==1) {

                    out.write(convertF.get(i).returnPretty());
                    sumNum = 0;
                }

                else
                    out.write("\n"+convertF.get(i).returnPretty());
            }

            out.close();

        }catch (IOException e){
            return;
        }


    }

    public boolean fileCheck(String theName){

        checkFile = new File(theName);

        // the file doesn't exist
        if (!checkFile.exists())
            return false;

        fName = theName;
        return true;
    }

    // makes file
    public void makeDir(String theName){

        checkFile = new File(theName);
        fName = theName;
        sumNum = 1;

        try {

            checkFile.createNewFile();

        }catch (IOException e){

        }

    }
}
