package edu.pdx.cs410J.chasam.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;

public class AirlineGwtTestSuite {
  public static Test suite() {
    GWTTestSuite suite = new GWTTestSuite("Airline GWT Integration Tests");

    suite.addTestSuite(AirlineGwtIT.class);

    return suite;
  }

}
