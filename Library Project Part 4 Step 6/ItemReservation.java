
/**
 * ItemReservation - Holds resevations made by users
 * 
 * @author Jack Travis
 */
import java.util.*;
import java.io.*;
public class ItemReservation
{
    //
    //Global Fields
    //
    private String reservationNo, itemCode, userID;
    private Date startDate;
    private int noOfDays;
    //
    //Constructors
    //
    /**
     * Constructor for objects of class ItemReservation
     */
    public ItemReservation(String reservationNo, String itemCode, String userID, String startDate, int noOfDays)
    {
       this.reservationNo = reservationNo;
       this.itemCode = itemCode;
       this.userID = userID;
       this.noOfDays = noOfDays;
       DateUtil setDate;
       setDate = new DateUtil();
       this.startDate = setDate.convertStringToDate(startDate);
    }
    /**
     * Default Constructor
     */
    public ItemReservation()
    {
    }
    //
    //Accessors - String
    //
    public String getReservationNo()
    {
        return reservationNo;
    }
    public String getItemCode()
    {
        return itemCode;
    }
    public String getUserID()
    {
        return userID;
    }
    //
    //Accessors - int
    //
    public int getNoOfDays()
    {
        return noOfDays;
    }
    //
    //Accessors - Date
    //
    public Date getStartDate()
    {
        return startDate;
    }
    //
    //Print Details
    //
    /**
     * Prints reservation details.
     */
    public void printDetails()
    {
        DateUtil getDate;
        getDate = new DateUtil();
        System.out.println("\nReservation Number: " + reservationNo);
        System.out.println("The item with item code, " + itemCode + ", was rented by user with ID, " + userID + ".");
        System.out.println("The book was rented on " + getDate.convertDateToShortString(startDate) + ", for " + noOfDays + " days.");
    }
    //
    //Files - Write & Read
    //
    /**
     * Writes reservation details to selected text file, using passed PrintWriter
     */
    public void writeData(PrintWriter pWriter)
    {
        DateUtil getDate;
        getDate = new DateUtil();
        String lineToInput; //Creates String, which will hold the text pWriter will write
        lineToInput = reservationNo + "," + itemCode + "," + userID + "," + getDate.convertDateToShortString(startDate) + "," + noOfDays;
        pWriter.println(lineToInput);
    }
    /**
     * Uses passed scanner to get information for ItemReservation feilds
     */
    public boolean readData(Scanner itemData)
    {
        try
        {
            reservationNo = itemData.next();
            itemCode = itemData.next();
            userID = itemData.next();
            DateUtil setDate;
            setDate = new DateUtil();            
            startDate = setDate.convertStringToDate(itemData.next()); //Takes next item in the line, and turns the String to type Date
            noOfDays = itemData.nextInt();
        }
        catch(NoSuchElementException ex2)
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
    public String toString()
    {
        return "\nReservation Number: " + reservationNo + "\nCustomer ID: " + userID + "\nItem Code: " + itemCode;
    }
}
