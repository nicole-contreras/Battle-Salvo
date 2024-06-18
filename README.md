## How to Run the Server
To run the BattleSalvo server, follow these steps:

Download the Server.jar file from the GitHub repository.
* Open a terminal and navigate to the directory where Server.jar is located.
* Run the following command to start the server:
    java -jar Server.jar
* The server should start and display a message indicating that it is accepting clients.

# Game Implementation Details
## ProxyController
The ProxyController class acts as an intermediary between the server and the local game implementation. It parses incoming messages from the server, delegates the messages to the correct player method, and sends the response back to the server. The ProxyController follows the Game Loop Protocol described in the Remote Interactions Protocols section of the assignment.

## Driver
The Driver class handles the main execution flow of the game. It runs the PA03 implementation (human vs. CPU) when no command-line arguments are provided and runs the PA04 version (local CPU vs. server CPU) when a host and port are provided. If no valid arguments are provided, the Driver throws an exception with a descriptive message.

## UML Diagram
The UML diagram for the BattleSalvo implementation shows the classes, interfaces, fields, and methods, along with their relationships. The diagram provides an overview of how the components of the game interact with each other.

# Running the Game
To play BattleSalvo, players must connect to the server using the provided host and port. Once connected, they can join a game and start playing against each other or against the AI player. Players take turns guessing coordinates to try and sink each other's ships. The game continues until one player wins by sinking all of the opponent's ships.
