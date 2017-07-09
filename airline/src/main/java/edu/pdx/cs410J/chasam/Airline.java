package edu.pdx.cs410J.chasam;

import com.sun.org.apache.bcel.internal.generic.NEW;
import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Yoda on 7/8/2017.
 */
public class Airline extends AbstractAirline<Flight>{

    private Collection<Flight> Flight_List = new ArrayList<>();

    public Airline(){

    }

    public Airline(String[] Info){

        Flight Users_Flight = new Flight(Info);
    }

    @Override
    public String getName(){

        return "hi";
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
