
/**
 * Periodical - Class for creating periodical items.
 * Has supers: printedItem & libraryItem
 * 
 * @author Jack Travis
 */
import java.util.*;
public class Periodical extends PrintedItem
{
    //
    //Global variables
    //
    private String publicationDate; //In form dd-mm-yy
    //
    //Constructors
    //
    /**
     * Constructor for objects of class Periodical
     */
    public Periodical()
    {
        super();   
    }
    //
    //Read function
    //
    /**
     * Uses passed scanner to get necessary information for fields, and passes scanner to super
     */
    protected boolean readData(Scanner itemData)
    {
        try
            {
            publicationDate = itemData.next();
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
    //Print Details
    //
    /**
     * Prints supers details, followed by periodical fields
     */
    protected void printDetails()
    {
        super.printDetails();
        System.out.println("The publication date of this periodical is: " + publicationDate);
    }
    //
    //Accessors - String
    //
    public String getPublicationDate()
    {
        return publicationDate;
    }
}
