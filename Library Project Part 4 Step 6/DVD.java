/**
 * DVD - Class that defined an item as type DVD
 * Has Supers: AudioVisual & LibraryItem
 * 
 * @author Jack Travis
 */
import java.util.*;
public class DVD extends AudioVisual
{
    //
    //Global fields
    //
    private String director;
    //
    //Constructor
    //
    /**
     * Default Constructor
     */
    public DVD()
    {
        super();        
    }
    //
    //Print Details
    //
    /**
     * Prints supers details, then prints fields of DVD
     */
    protected void printDetails()
    {
        super.printDetails();
        System.out.println("The director of this DVD is " + director);
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
        director = itemData.next();
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
    public String getDirector()
    {
        return director;
    }
}
