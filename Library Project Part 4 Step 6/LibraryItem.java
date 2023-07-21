
/**
 * LibraryItem - Superclass of all items the Library offers
 * 
 * @author Jack Travis
 */
import java.util.*;
public abstract class LibraryItem
{
    //
    //Global Fields
    //
    //Title of book, Book id, publisher
    private String title, itemCode;
    //Cost of borrowing item (in pence), total times borrowed, total pages
    private int cost, timesBorrowed;
    //States if item is currently being loaned
    private boolean onLoan;
    //
    //Constructors
    //
    public LibraryItem()
    {
    }
    //
    //Print Details
    //
    /**
     * Prints details of item
     */
    protected void printDetails()
    {
        System.out.println("\n" + title + " with item code " + itemCode + " has been borrowed " + timesBorrowed + " times.");
        System.out.println("This item at present is" + isOnLoan() + "will cost " + cost + " pence.");
    }
    //
    //Functions
    //
    /**
     * Called by printDetails(). Returns the correct text for the output regarding item availability.
     */
    protected String isOnLoan() 
    {
        if(onLoan == true)
        {
            return " on loan and, when free, ";
        }
        else
        {
            return " available, and ";
        }
    }
    //
    //Scanners
    //
    /**
     * Uses passed scanner to get field information.
     */
    protected boolean readData(Scanner itemData)
    {
        try
        {
            title = itemData.next();
            itemCode = itemData.next();
            cost = itemData.nextInt();
            timesBorrowed = itemData.nextInt();
            onLoan = itemData.nextBoolean();
            return true;
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
    public String getTitle()
    {
        return title;
    }
    public String getItemCode()
    {
        return itemCode;
    }
  
    //
    //Accessors - int
    //
    public int getCost()
    {
        return cost;
    }
    public int getTimesBorrowed()
    {
        return timesBorrowed;
    }
    //
    //Accessors - boolean
    //
    public boolean getOnLoan()
    {
        return onLoan;
    }
    //
    //Mutators
    //
}
