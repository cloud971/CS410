package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AirlineDumper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Yoda on 7/16/2017.
 */
public class TextDumper implements AirlineDumper<Airline> {

    private StringBuilder writeMe;
    private String fName;
    private int make;

    public TextDumper(){

    }

    public TextDumper(String fName){

        this.fName = fName;
    }

    public TextDumper(String fName, int make){

        this.fName = fName;
        this.make = make;
    }

    @Override
    public void dump(Airline dumping){

        Collection<Flight> the_flight = dumping.getFlights();

        Flight [] convertF = new Flight[the_flight.size()];
        convertF = the_flight.toArray(convertF);

        writeMe = new StringBuilder();
        String [] the_dates;

        if(make != 1)
        writeMe.append("\n");

        writeMe.append(dumping.getName()+","); // airline name
        writeMe.append(convertF[convertF.length-1].getNumber()+","); // flight number
        writeMe.append(convertF[convertF.length-1].getSource()+","); // get src

        the_dates = convertF[convertF.length-1].getD().split(" ");

        writeMe.append(the_dates[0]+","+the_dates[1]+","+the_dates[2]+","); // gets depature time and date
        writeMe.append(convertF[convertF.length-1].getDestination()+",");
        the_dates = convertF[convertF.length-1].getA().split(" "); // gets arrival time and date
        writeMe.append(the_dates[0]+","+the_dates[1]+","+the_dates[2]); // append


        try {

                BufferedWriter out = new BufferedWriter(new FileWriter(fName,true));
                out.write(writeMe.toString());
                out.close();

        }catch (IOException e){

        }
    }
}
