
/**
 * PrintedItem - Abstract Super Class of LibraryItems that are delievered in a physical manner
 * Has Super: LibraryItem
 * 
 * @author Jack Travis
 */
import java.util.*;
public abstract class PrintedItem extends LibraryItem
{
    //
    //Global Variables
    //
    private int noOfPages;
    private String publisher;
    /**
     * Default Constructor
     */
    public PrintedItem()
    {
        super();
    }

    //
    //Print Details
    //
    /**
     * Prints supers details, then prints fields of PrintedItem
     */
    protected void printDetails()
    {
        super.printDetails();
        System.out.println("The publisher of this book is " + publisher + " and contains " + noOfPages + " pages.");                 
    }
    //
    //Read function
    //
    /**
     * Uses passed scanner to get field information and passes scanner to super
     */
    protected boolean readData(Scanner itemData)
    {
        try
        {
            String noOfPagesAsString; //Holds number of pages as a string, so it can be checked to ensure there is no non-numerical characters that would otherwise cause an error
            noOfPages = itemData.nextInt();
            publisher = itemData.next();
            boolean wasSuccessful; //States the success of superclass adding data.
            wasSuccessful = super.readData(itemData); //Sends scanner in currect state to superclass
            return wasSuccessful;
        }
        catch(NoSuchElementException ex1)
        {
            System.out.println("An error whilst reading has occured. \nThis could be caused by missing data or by incorrect data (eg. A characters where a number was expected)");
            return false;
        }
    }
    //
    //Accessors - String
    //
    public String getPublisher()
    {
        return publisher;
    }
    //
    //Accessors - int
    //
    public int getNoOfPages()
    {
        return noOfPages;
    }
}
