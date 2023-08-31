import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> roomHistory;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        createRooms();
        parser = new Parser();
        roomHistory = new Stack<Room>();
    }



    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room did1, did2, did3, did4, did5,did6;

        // create the rooms
        did1 = new Room(" na didatica 1, um predio velho caindo aos pedaços, porém tem muitas aulas por aqui");
        did2 = new Room(" na didatica 2, não costumo passar por aqui ");
        did3 = new Room(" na didatica 3, me traz boas memorias");
        did4 = new Room(" na didatica 4, já perdi muito tempo por aqui");
        did5 = new Room(" na didatica 5, ela é bem grande");
        did6 = new Room(" na didatica 6 e me parece o mesmo da didatica 5");

        // create the itens
        Item page1 = new Item("Página 1",3.0);
        Item sandubaSaboroso  =  new Item("Um sanduba muito delicioso", 5.0);
        Item page2 = new Item("Pagina 3", 3.0);
        Item page3 = new Item("Pagina 3", 3.0);
        Item page4 = new Item("Pagina 4", 3.0);
        Item page5 = new Item("Pagina 5", 3.0);
        Item page6 = new Item("Pagina 6", 3.0);



        // initialise room exits
        did1.setExit("north",did6);
        did1.setExit("east",did2);

        did1.setItem("Página 1",page1 );
        did1.setItem("Sanduba Saboroso",sandubaSaboroso);


        did2.setExit("west",did1);
        did2.setExit("east",did3);

        did2.setItem("Pagina 2", page2 );

        did3.setExit("north",did5);
        did3.setExit("west",did2);
        did3.setExit("east",did4);
        did3.setItem("Pagina 3", page3);

        did4.setExit("west",did3);
        did4.setItem("Pagina 4", page4);

        did5.setExit("south",did3);
        did5.setItem("Pagina 5", page5);

        did6.setExit("south",did1);
        did6.setItem("Pagina 6", page6);

        currentRoom = did1;  // start game did1
    }

    /**
     *  Main play routine.  Loops until end of play.
     */

    public void play()
    {
        printWelcome();
        printLocationInfo();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printLocationInfo(){
        currentRoom.getLongDescription();
        System.out.print("Exits: ");
        currentRoom.getExit();
        System.out.println();
    }
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();

    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }else if (commandWord.equals("info")) {
            printInf();
        }else if (commandWord.equals("look")){
            currentRoom.getLongDescription();
            currentRoom.additem();
        }else if (commandWord.equals("eat")) {
            printEat();
        }else if (commandWord.equals("drink")){
            System.out.println("Você não está com sede!!");
        } else if (commandWord.equals("back")){
            goBack();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp( )
    {
        parser.showCommands();
    }
    private void printEat(){
        System.out.println("Você comeu agora e não está mais com fome");
    }
    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave the current room and move to the next room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            roomHistory.push(currentRoom); // adiciona a sala atual de registro
            currentRoom = nextRoom;
            printLocationInfo(); // Print information about the new room

        }
    }
    private void goBack(){
        if (!roomHistory.empty()) {
            currentRoom = roomHistory.pop(); // pega a ultima sala visitada
            printLocationInfo();
        }else{
            System.out.println("Você não tem mais para onde voltar");
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    private void printInf() {
        printLocationInfo();
    }

}

