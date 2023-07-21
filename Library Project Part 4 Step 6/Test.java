
/**
 * Write a description of class Test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;
public class Test
{
    private Date date1, date2;

    /**
     * Test the program
     */
    public Test() //SELECT FILE testData.txt (Located in Text files)
    {

    }
    /**
     * Tests to see if Items and users are successfully read from user selected files, checks print methods for users and items, checks write method for users (user selected file) 
     */
    public void libraryTestingUserSelectedFiles() throws IOException
    {
        Library l1, l2; //Create a library
        l1 = new Library();
        System.out.println("Calling readItemAndUserData()");
        System.out.println("Please select file to read (Users and Items))"); //Prompts user what file to select
        l1.readItemAndUserData(); //Reads user selected file
        System.out.println("Calling printAllItems()");
        l1.printAllItemsAndUsers(); //Outputs what was read
        System.out.println("Calling writeUserData()");
        System.out.println("Please select file to write to (Users)"); //Prompts user what file to select
        l1.writeUserDataUserSelectedFile(); //Writes what was read to user selected file
       
        l2 = new Library(); //Creates a second Library to read and print details of file that has just been
        //written to, to ensure it worked
        System.out.println("Calling readItemAndUserData()");
        System.out.println("Please re-select the file that has just been written to");        
        l2.readItemAndUserData(); //Prompts user to select a file. User should select files they have just written to
        System.out.println("Calling printAllItems(). Users should be the same as what should have been written to the selected file");
        l2.printAllItemsAndUsers();
    }
    /**
     * libraryTest - No user input required.
     * Requires file 'testData.txt' located \..\Text Files\, from the working directory 
     */
    public void libraryTesting() throws IOException
    {
        Library l1, l2; //Create a library
        l1 = new Library();
        
        String filePath, parentPath;
        File file;
        file = new File((System.getProperty("user.dir"))); //Gets current working directory
        filePath = file.getParent() + "\\Text files\\testData.txt"; //Gets file path where lastReservationNo.txt is expected to be found
        parentPath = file.getParent() + "\\Text files";
       
        
        System.out.println("Calling readItemAndUserData()");
        l1.readTestData(filePath); //Reads user selected file
        System.out.println("Calling printAllItems()");
        l1.printAllItemsAndUsers(); //Outputs what was read
        System.out.println("Calling writeUserData()");
        l1.writeUserData(); //Writes what was read to user selected file
       
        l2 = new Library(); //Creates a second Library to read and print details of file that has just been
        //written to, to ensure it worked
        System.out.println("Calling readItemAndUserData()");
        filePath = parentPath + "\\UsersWithIDs.txt";
        l2.readTestData(filePath); //Prompts user to select a file. User should select files they have just written to
        System.out.println("Calling printAllItems(). Users should be the same as what should have been written to the selected file");
        l2.printAllItemsAndUsers();
    }
    public void dateUtilTesting()
    {
        DateUtil dateUtil;
        dateUtil = new DateUtil();
        
        date1 = dateUtil.convertStringToDate("22-08-2002");
        date2 = dateUtil.convertStringToDate("10-02-1994");
        System.out.println(dateUtil.daysBetween(date2, date1));
    }
    public void itemReservationTesting() throws IOException
    {
        Library library;
        library = new Library();
        System.out.println("SELECT RESERVATIONS");
        library.readItemReservationData(); //SELECT Reservations
        System.out.println("Item Reservations from File");
        library.printItemReservations(); //Displays reservations added.
        LibraryItem item; //Creates item
        LibraryUser  user; //Creates user
        Scanner sr1; //Creates scannner to pass to user and item
        sr1 = new Scanner("TE-157462,Travers,Eric, ,Dr").useDelimiter("[ ]*+,[ ]*+");
        user = new LibraryUser();
        user.readData(sr1);
        library.storeUser(user);
        sr1 = new Scanner("Thomee, 9783540331216,370, Springer Verlag, Galerkin Finite Element Methods for Parabolic Problems, LM002153,4554,543,FALSE").useDelimiter("[ ]*+,[ ]*+");
        item = new Book();
        item.readData(sr1);
        library.storeItem(item);
        System.out.println("Add Invalid Reservation - Incorrect User");
        library.makeItemReservation("A-3", item.getItemCode(), "01-04-2021", 10);
        System.out.println("Add Invalid Reservation - Incorrect Item");
        library.makeItemReservation(user.getUserID(), "item", "01-04-2021", 10);
        System.out.println("Add Reservation - Valid");
        library.makeItemReservation(user.getUserID(), item.getItemCode(), "01-04-2021", 10);
        System.out.println("printItemReservations");
        library.printItemReservations(); //Displays reservations added.
        System.out.println("****printItemReservations end***");
        library.writeItemReservationData();
        System.out.println("\nAttempt to reserve already reserved item");
        library.makeItemReservation(user.getUserID(), item.getItemCode(), "29-03-2021", 10);
        System.out.println("\nAttempt to delete reservation");
        library.deleteItemReservation("000001");
        System.out.println("\nAttempt to delete non-existent reservation");
        library.deleteItemReservation("002");
        
        System.out.println("\nSave to user selected file");
        library.writeItemReservationDataUserSelectedFile();
        System.out.println("\nSave to default file");
        library.writeItemReservationData();
    }

}
