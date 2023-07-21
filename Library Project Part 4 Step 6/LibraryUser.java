
/**
 * Library User class - Holds necessary user information 
 * 
 * @author Jack Travis
 */
import java.util.*;
import java.io.*;
public class LibraryUser
{
    //
    //Global Fields
    //
    private String userID, surname, firstName, otherInitials, title;
    //
    //Constructor
    //
    /**
     * Constructor for objects of class LibraryUser
     */
    public LibraryUser()
    {
     
    }
    //
    //Read Function
    //
    /**
     * Passed a scanner. Uses said scanner to fill in its fields
     */
    public boolean readData(Scanner itemData)
    {
        try
        {
            userID = itemData.next();
            surname = itemData.next();
            firstName = itemData.next();
            otherInitials = itemData.next();
            title = itemData.next();
        }
        catch(NoSuchElementException ex2) //Occurs if line containing User Data is missing some of said data
        {
            ex2.printStackTrace();
            System.out.println("\nError: Data missing from entry");
            System.out.println("Item will not be added to Library");
            return false;
        }
        catch(Exception ex3)
        {
            ex3.printStackTrace();
            System.out.println("\nAn unexpected error has occured");
            return false;
        }
        return true;
    }
    //
    //Print Details
    //
    /**
     * Prints the details of the user
     */
    public void printDetails()
    {
        System.out.println("\nUserID: " + userID);
        System.out.println("Surname: " + surname);
        System.out.println("First Name: " + firstName);
        System.out.println("Other Initials: " + otherInitials);
        System.out.println("Title: " + title);
    }   
 
    //
    //Write Details
    //
    /**
     * Uses the passed PrintWriter to write the Users info into the given text file
     */
    public void writeData(PrintWriter pWriter)
    {
        String lineToInput; //Creates String, which will hold the text pWriter will write
        lineToInput = userID + "," + surname + "," + firstName + "," + otherInitials + "," + title;
        pWriter.println(lineToInput);
    }

    //
    //Accessors - String
    //
    public String getUserID()
    {
        return userID;
    }
    public String getSurname()
    {
        return surname;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public String getOtherInitials()
    {
        return otherInitials;
    }
    public String getTitle()
    {
        return title;
    }
    //
    //Mutators
    //
    protected void setUserID(String newID)
    {
        userID = newID;
    }
}
