package edu.pdx.cs410J.chasam;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project3} main class.
 */
public class Project3IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */

  @Test
  public void testNoCommandLineArguments() {
  }

}