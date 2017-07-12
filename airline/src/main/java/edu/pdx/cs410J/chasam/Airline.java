package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

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
}
