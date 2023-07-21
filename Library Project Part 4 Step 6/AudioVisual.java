
/**
 * AudioVisual - Abstract class for any LibraryItems that are obtainable in an Audio or Visual format
 * Has super: LibraryItem
 * 
 * @author Jack Travis
 */
import java.util.*;
public abstract class AudioVisual extends LibraryItem
{
    //
    //Global Fields
    //
    private int playingTime;
    //
    //Constructor
    //
    /**
     * Default Constructor
     */
    public AudioVisual()
    {
        super();
    }
    //
    //Print Details
    //
    /**
     * Prints supers details, then prints fields of AudioVisual
     */
    protected void printDetails()
    {
        super.printDetails();
        System.out.println("The play time of this item is " + playingTime + " minutes.");
    }
    //
    //Read Function
    //
    /**
     * Uses passed scanner to get field information and passes scanner to super
     */
    protected boolean readData(Scanner itemData)
    {
        try
        {
            String playingTimeAsString;
            playingTime = itemData.nextInt();
            boolean wasSuccessful; //boolean to hold the return value given by the Super class
            wasSuccessful = super.readData(itemData); //Sends scanner is current state to superclass
            return wasSuccessful; //If LibraryItem failed to get all info (ie. File had elements missing (incomplete line), or characters found where
                                  //a number should be
        }
        catch(NoSuchElementException ex1)
        {
            System.out.println("An error whilst reading has occured. \nThis could be caused by missing data or by incorrect data (eg. A characters where a number was expected)");
            return false;
        }
                            
    }
    //
    //Accessor - int
    //
    public int getPlayingTime()
    {
        return playingTime;
    }

}
