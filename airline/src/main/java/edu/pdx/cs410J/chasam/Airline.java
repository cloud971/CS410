package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AbstractAirline;

import java.util.*;

/**
 * Created by Yoda on 7/8/2017.
 */
public class Airline extends AbstractAirline<Flight>{

    private Collection<Flight> Flight_List = new ArrayList<>();

    String name;

    public Airline(){

    }

    public Airline(String[] info, int index){

        this.name = new String(info[index]);
        Flight Users_Flight = new Flight(info, index+1);
        Flight_List.add(Users_Flight);
    }

    @Override
    public String getName(){

        return name;
    }

    @Override
    public void addFlight(Flight New_Flight){

        this.Flight_List.add(New_Flight);
    }


    @Override
    public Collection<Flight> getFlights(){

        return this.Flight_List;
    }

    // adding a new flight from file
    public void toAdd(String [] newFlight){


        name = newFlight[0];
        Flight addFlight = new Flight(newFlight);

        for(Flight x:Flight_List){

            if (x.compareEvery(addFlight))
                return;

        }

        Flight_List.add(addFlight);
    }

    public void display(){

        for(Flight x:Flight_List){
            System.out.println(x.toString());
        }
    }

    public boolean sameName(String aName) {

        if (name.equals(aName))
            return true;

        return false;
    }

    public void mainAdd(String [] args, int index){

        Flight mainFlight = new Flight(args,index);

        for(Flight x:Flight_List){

            if (x.compareEvery(mainFlight))
                return;

        }

        Flight_List.add(mainFlight);
    }

    public void printnew() {


        Flight[] convertF = new Flight[Flight_List.size()];
        convertF = Flight_List.toArray(convertF);

        System.out.println("ADDED FLIGHT: " + convertF[convertF.length - 1].toString());
    }


    // sorts the list
    public void sortMe(){

        List<Flight>convertF = new ArrayList<>(Flight_List);
        Collections.sort(convertF);

    }

    public void displayPretty(){

        List<Flight>convertF = new ArrayList<>(Flight_List);
        Collections.sort(convertF);
        for(Flight x:convertF){

            x.makePretty();
        }
    }
}
