package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.ControllerImpl;
import java.io.IOException;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests the controller class.
 */
public class ControllerTest {

  /**
   * Sets up the controller.
   */
  @Test
  public void testRun() {
    String testInput =
        """
            6 6
            1 1 1 1
            0 0
            0 1
            0 2
            0 3
            0 4
            0 5
            1 0
            1 1
            1 2
            1 3
            1 4
            1 5
            2 0
            2 1
            2 2
            2 3
            2 4
            2 5
            3 0
            3 1
            3 2
            3 3
            3 4
            3 5
            4 0
            4 1
            4 2
            4 3
            4 4
            4 5
            5 0
            5 1
            5 2
            5 3
            5 4
            5 5""";
    StringBuilder testString = new StringBuilder();
    Controller testController =
        new ControllerImpl(new StringReader(testInput), testString, new Random(1));
    try {
      testController.run();
    } catch (IOException e) {
      System.out.println("Error has occurred");

    }
    assertEquals("""
        enter valid height and width
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        AI's board
        - - - - - -\s
        - - - - - -\s
        - - - - - -\s
        - - - - - -\s
        - - - - - -\s
        - - - - - -\s
        -----------------------------------------
        your board
        S - C B - -\s
        S - C B - -\s
        S D C B - -\s
        - D C B - -\s
        - D C B - -\s
        - D C - - -\s
        -----------------------------------------
        Please enter 4 shots:\s
        -----------------------------------------
        AI's board
        M - - - - -\s
        H - - - - -\s
        H - - - - -\s
        H - - - - -\s
        - - - - - -\s
        - - - - - -\s
        -----------------------------------------
        your board
        S M C H - -\s
        S - C B - -\s
        S D C B - -\s
        - D C B - -\s
        - D C B - -\s
        - D C M - M\s
        -----------------------------------------
        Please enter 4 shots:\s
        -----------------------------------------
        AI's board
        M M - - - -\s
        H M - - - -\s
        H - - - - -\s
        H - - - - -\s
        H - - - - -\s
        H - - - - -\s
        -----------------------------------------
        your board
        S M H H - -\s
        S M C B - -\s
        S D C B - -\s
        - D C B - -\s
        M D H B - -\s
        - D C M - M\s
        -----------------------------------------
        Please enter 4 shots:\s
        -----------------------------------------
        AI's board
        M M - - - -\s
        H M - - - -\s
        H M - - - -\s
        H M - - - -\s
        H M - - - -\s
        H M - - - -\s
        -----------------------------------------
        your board
        H M H H - -\s
        H M C B - -\s
        S D C B - -\s
        M D C B - -\s
        M D H B - -\s
        - D C M - M\s
        -----------------------------------------
        Please enter 4 shots:\s
        -----------------------------------------
        AI's board
        M M H - - -\s
        H M H - - -\s
        H M H - - -\s
        H M H - - -\s
        H M - - - -\s
        H M - - - -\s
        -----------------------------------------
        your board
        H M H H - -\s
        H M C H - -\s
        S D C B - -\s
        M D C B - -\s
        M H H H - -\s
        - D C M - M\s
        -----------------------------------------
        Please enter 4 shots:\s
        -----------------------------------------
        AI's board
        M M H M - -\s
        H M H M - -\s
        H M H - - -\s
        H M H - - -\s
        H M M - - -\s
        H M M - - -\s
        -----------------------------------------
        your board
        H M H H - -\s
        H M C H M -\s
        S D C B - -\s
        M D C B - -\s
        M H H H - -\s
        - D C M M M\s
        -----------------------------------------
        Please enter 4 shots:\s
        -----------------------------------------
        AI's board
        M M H M - -\s
        H M H M - -\s
        H M H M - -\s
        H M H M - -\s
        H M M M - -\s
        H M M M - -\s
        -----------------------------------------
        your board
        H M H H - -\s
        H M C H M -\s
        S D C B - -\s
        M D H B - -\s
        M H H H - -\s
        - D H M M M\s
        -----------------------------------------
        Please enter 4 shots:\s
        -----------------------------------------
        AI's board
        M M H M H -\s
        H M H M H -\s
        H M H M H -\s
        H M H M H -\s
        H M M M - -\s
        H M M M - -\s
        -----------------------------------------
        your board
        H M H H - -\s
        H M H H M -\s
        S D C B M -\s
        M D H B - -\s
        M H H H - -\s
        - D H M M M\s
        -----------------------------------------
        Please enter 4 shots:\s
        -----------------------------------------
        AI's board
        M M H M H H\s
        H M H M H H\s
        H M H M H -\s
        H M H M H -\s
        H M M M H -\s
        H M M M H -\s
        -----------------------------------------
        your board
        H M H H - -\s
        H M H H M -\s
        S D C B M -\s
        M D H H - -\s
        M H H H - M\s
        - D H M M M\s
        -----------------------------------------
        Please enter 4 shots:\s
        -----------------------------------------
        you won!
        """, testString.toString());
  }

}
