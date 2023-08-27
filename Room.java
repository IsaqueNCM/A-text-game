import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class  Room
{
    private String description;
    private HashMap<String, Room>exits;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east exit.
     * @param south The south exit.
     * @param west The west exit.
     */

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */


    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */

    /**
     * criou long description
     * */
    public void getLongDescription()
    {
        System.out.println("Você está aqui" + description);
    }

    public void getExit() {
        if (exits != null && !exits.isEmpty()) {
            System.out.print("Saídas disponíveis: ");
            for (String direction : exits.keySet()) {
                System.out.print(direction + " ");
            }
            System.out.println(); // Pula uma linha no final da lista de saídas
        } else {
            System.out.println("Não há saídas disponíveis.");
        }
    }
    public Room getExit(String direction) {
        return exits.get(direction);
    }
}