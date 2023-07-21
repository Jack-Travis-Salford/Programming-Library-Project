
/**
 * CD - Used to define item as type CD
 * Has supers: AudioVisual & LibraryItem
 * 
 * @author Jack Travis
 */
import java.util.*;
public class CD extends AudioVisual
{
    //
    //Global Variables
    //
    private String artist;
    private int noOfTracks;
    //
    //Constructor
    //
    /**
     * Default Constructor
     */
    public CD()
    {
        super();
    }
    //
    //Print details
    //
    /**
     * Prints supers details, then prints fields of CD
     */
    protected void printDetails()
    {
        super.printDetails();
        System.out.println("The Artist of the CD is " + artist + " and contains " + noOfTracks + " tracks.");
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
            artist = itemData.next();
            noOfTracks = itemData.nextInt(); 
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
    //Accessor - String
    //
    public String getArtist()
    {
        return artist;
    }
    //
    //Accessor - int
    //
    public int getNoOfTracks()
    {
        return noOfTracks;
    }
    
}
