package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AirlineParser;

/**
 * Created by Yoda on 7/16/2017.
 */
public class TextParser implements AirlineParser<Airline> {

    public TextParser(){

    }

    @Override
    public Airline parse(){

        return new Airline();
    }
}
