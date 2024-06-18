package cs3500.pa04;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.ControllerImpl;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.model.AiForServerPlayer;
import cs3500.pa04.model.Player;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;


/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - can take 2 command line arguments or no command line args required
   */
  public static void main(String[] args) {


    if (args.length == 2) {
      playAgainstServer(args);
    } else if (args.length == 0) {
      playAgainstLocalAi();
    } else {
      throw new IllegalArgumentException("invalid number of arguments");
    }
  }

  /**
   * Plays against the server
   *
   * @param args - takes in the ip address and port number
   */
  private static void playAgainstServer(String[] args) {
    Controller session;
    int port;
    try {
      port = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("did not give a valid port");
    }
    try {
      Player player = new AiForServerPlayer(new Random());
      session = new ProxyController(new Socket(args[0], port),
          player);
    } catch (IOException e) {
      throw new RuntimeException("controller could not be instantiated");
    }
    try {
      session.run();
    } catch (IOException e) {
      System.err.println("error occurred");

    }
  }

  /**
   * Plays against the local ai
   */
  private static void playAgainstLocalAi() {
    Controller session = new ControllerImpl(new InputStreamReader(System.in),
        System.out, new Random());
    try {
      session.run();
    } catch (IOException e) {
      System.err.println("either input or output was invalid");

    }
  }
}
