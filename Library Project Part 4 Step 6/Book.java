
/**
 * Book - Class used for the creation of Book items
 * Has supers: printedItem & LibraryItem
 * 
 * @author Jack Travis 
 */
import java.util.*;
public class Book extends PrintedItem
{
    //
    // Global fields
    //
    private String author, isbn; //isbn, althought all numerical, is too big to be type int. Thus, based on its purpose, 
                                 //it is most suited to being type String
    //
    // Constructors
    //
    /**
     * Default constructor for Book
     */
    public Book()
    {
    }
    //
    // Read function
    //
    /**
     * Uses passed scanner to get field information and passes scanner to super
     */
    public boolean readData(Scanner itemData)
    {
        try
        {
            author = itemData.next(); //First data item read is author
            //author = author.trim();
            isbn = itemData.next();//Second data item read is isbn number
            //isbn = isbn.trim();
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
    //Accessors - String
    //
    public String getAuthor()
    {
        return author;
    }
    public String getIsbn()
    {
        return isbn;
    }
 
    //
    //Print Details
    //
    /**
     * Prints supers details, then prints fields of Book
     */
    public void printDetails()
    {
        super.printDetails();
        System.out.println("This book was written by " + author + ". The ISBN number of this book is " + isbn);
    }
}
