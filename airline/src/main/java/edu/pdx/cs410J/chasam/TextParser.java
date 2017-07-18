package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.AirlineParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Yoda on 7/16/2017.
 */
public class TextParser implements AirlineParser<Airline> {

    private FileReader reader;
    private Airline airlines;
    private String [] toAdd;

    public TextParser(){

    }

    public TextParser(String airline){

    }

    @Override
    public Airline parse(){

        return new Airline();
    }

    public boolean checkFile(String fileName){

        try {

            reader = new FileReader(fileName);

        }catch (FileNotFoundException e){

            return false;
        }

        return true;
    }
}
