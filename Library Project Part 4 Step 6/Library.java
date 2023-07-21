
/**
 * The Library class - Designed for user usage to perform operations
 * 
 * NOTE: Code used when itemMap & userMap were of type Arrays was deleted before being informed that the code should remained in a commented state
 * Said code has been re-added (upto Part 4 Step 2), and will be at the top of the corresponding section it was located at, with header //***OLD CODE*** and footer //***END OF OLD CODE***
 * 
 * Please read README.TXT
 * 
 * @author Jack Travis 
 */
import java.util.*;
import java.awt.Frame;
import java.awt.FileDialog;
import java.io.*;
public class Library
{
    //
    //Global Fields
    //
    //***OLD CODE***
    //private List<LibraryItem> itemList; //ID, object
    //private List<LibraryUser> userList; //User ID, object
    //private HashMap<String, LibraryUser> userIDs; //Creates HashMap of userIDs to ensure no duplicate values for ID were created
    //***END OF OLD CODE***
    
    
    
    private Diary diary;
    private Map<String, ItemReservation> itemReservationMap; //Holds item reservations
    private Map<String, LibraryItem> itemMap; //ID, object
    private Map<String, LibraryUser> userMap; //User ID, object
    private ArrayList<LibraryUser> newUserList; //Holds users with 'unknown' ID. IDs will be allocated once all users with assigned ID's have already been added. List created & destoyed in readItemData() to save memory (only exists
                                                //when needed. Reason why doing it this way: All old user ID's are required to be known before new users are added, to prevent duplicates.
    //
    //Constructors
    //
    /**
     * Default Constructor for objects of class Library. 
     */
    public Library()
    {
        //***OLD CODE***
        //itemList = new ArrayList<LibraryItem>();
        //userList = new ArrayList<LibraryUser>();
        // userIDs = new HashMap<String, LibraryUser>();
        //***END OF OLD CODE***
        itemMap = new HashMap<String, LibraryItem>();
        userMap = new HashMap<String, LibraryUser>();
        itemReservationMap = new HashMap<String, ItemReservation>();
        diary = new Diary();
    }
    //
    //Public sub procedures
    //
    /**
     * Reads text document and turns items in text into LibraryItems
     */    
    public void readItemAndUserData() throws IOException
    {
       String chosenFile;
       chosenFile = chooseFile(); //Calls function, which uses a fileDialog to choose a file. Returns file path. 
       if(chosenFile.equals("nullnull") == true) //"nullnull" is the String chooseFile() returns if no file was chosen 
       {
           System.out.println("\nFile Not Found: Did you select a file?");   
       }
       else
       {
           newUserList = new ArrayList<LibraryUser>(); //Creates newUserList, to add all users with 'unknown' id, to be dealt with later
           getEachLineOfText(chosenFile); //Call subprocedure, which uses a scanner to get data from previously selected text file
           addNewUsers(); //Assigns new users with unique IDs and adds said users to userMap & userIDs
           newUserList = null;
           System.out.println("\n***File has been read***");
       }
    }
    /**
     * Same as readItemAndUserData(), but from passed file
     * Purpose: For faster testing via the Test class. 
     * Difference: Skips the need for a fileDialog by stating which file to read. If file isnt present, nothing happens.
     * 
     * Test class allows for testing using both user selected & code defined files, to test the fileDialog and for ease of testing respectively.
     */
    public void readTestData(String chosenFile) throws IOException 
    {
       File file;
       file = new File(chosenFile);
       if(file.isFile() == true)
       {
           newUserList = new ArrayList<LibraryUser>(); //Creates newUserList, to add all users with 'unknown' id, to be dealt with later
           getEachLineOfText(chosenFile); //Call subprocedure, which uses a scanner to get data from previously selected text file
           addNewUsers(); //Assigns new users with unique IDs and adds said users to userMap & userIDs
           newUserList = null;
           System.out.println("\n***File has been read***");
       }
       else
       {
           System.out.println("\nError: File not found. If Testing, rectify this, or use readItemAndUserData() to select a file manualy instead");
           System.out.println("Expected file: " + chosenFile);
       }
    }
    /**
     * Reads text document and extracts item reservations
     */
    public void readItemReservationData() throws IOException  //EXPECTS TO SEE [Item Reservations Data] BEFORE RESERVATIONS
    {
        String chosenFile; //Uses a fileDialog to allow user to select file to read
        chosenFile = chooseFile();
        if(chosenFile.equals("nullnull") == true) //"nullnull" is the String chooseFile() returns if no file was chosen 
        {
           System.out.println("\nFile Not Found: Did you select a file?");   
        }
        else
        {
            getEachLineOfText(chosenFile); //Call subprocedure, which uses a scanner to get data from previously selected text file
            System.out.println("\n***File has been read***");
        }
    }
    //
    // Scanners & file reading/choosing
    //
    /**
     * Creates a file dialog. Returns chosen file
     */
    private String chooseFile()
    {
       //Creates Frame for fileBox
       Frame dialogFrame; 
       dialogFrame = new Frame();
       //Creates File Dialog
       FileDialog fileBox;        
       fileBox = new FileDialog(dialogFrame, "Open", FileDialog.LOAD);  
       fileBox.setDirectory(System.getProperty("user.dir"));
       fileBox.setVisible(true);        
       return fileBox.getDirectory() + fileBox.getFile();
    }
    /**
     * Uses scanner to get and display each useful line of text from selected text document
     */
    private void getEachLineOfText(String chosenFile) throws IOException
    {
       try
       {
           //Creates scanner
           Scanner readFile = new Scanner(new File(chosenFile));
           String lineOfText; //Holds next line of text
           int typeOfData; //States which 'section' in the file the scanner is upto
           typeOfData = 0; //0 = Not yet at relevant lines. 1 = At Books. 2 = At Periodical            
           while(readFile.hasNext() == true) //Loops if there is more lines to read
           {
               lineOfText = readFile.nextLine();
               lineOfText = lineOfText.trim(); //removes leading a trailing whitespace
               if(lineOfText.startsWith("/") != true && lineOfText.isEmpty() != true) //Omits useless data
               {
                   if(lineOfText.equals("[Book data]")) //Selects a number, based on the last identifier in said text document
                   {
                       typeOfData = 1;
                   }
                   else if(lineOfText.equals("[periodical data]"))
                   {
                       typeOfData = 2;
                   }
                   else if(lineOfText.equals("[CD data]"))
                   {
                       typeOfData = 3;
                   }
                   else if(lineOfText.equals("[DVD data]"))
                   {
                       typeOfData = 4;
                   }
                   else if(lineOfText.equals("[Library User Data]"))
                   {
                       typeOfData = 5;
                   }
                   else if(lineOfText.equals("[Item Reservation Data]"))
                   {
                       typeOfData = 6;
                   }
                   else
                   {
                       if(typeOfData == 0) //Occurs when 'useful' text is reached before an identifier, like [Book data]
                       {
                           System.out.println("\nError: Useful data reached before type declaration");
                           System.out.println("Please reposition before adding to Library");
                           System.out.println("Data: " + lineOfText);
                       }
                       else
                       {
                           splitLineOfText(lineOfText, typeOfData); //Calls subprocedure, which uses a second scanner to tokenise and parse each line of text.                      
                       }
                    }
               }
           }
           readFile.close();
       }
       catch(FileNotFoundException ex1) //Occurs if no data is sent to the scanner. This shouldn't occur, due to the corresponding IF loop in readItemData()
       {
            ex1.printStackTrace();
            System.out.println("\nFile Not Found: Did you select a file?");
       }
       catch(Exception ex3) //Occurs if a type of error occurs, which hasn't yet been caught.
       {
           ex3.printStackTrace();
           System.out.println("\nAn unexpected error occured");
       }
    }
    /**
     * Passed a String, lineOfText, and an int, typeOfData
     * Uses typeOfData to determine what lineOfText is: e.g. A user, or an item (if item, it determines what type of item)
     * 
     */
    private void splitLineOfText(String lineOfText, int typeOfData)
    {
        Scanner splitLine; //Creates a new scanner
        splitLine = new Scanner(lineOfText).useDelimiter("[ ]*+,[ ]*+");
        LibraryItem item; //Creates a pointer to a LibrayItem object
        LibraryUser user;
        ItemReservation reservation;
        item = null;
        user = null;
        reservation = null;
        if(typeOfData == 1)
        {
            item = new Book(); //Book item created
        }
        else if(typeOfData == 2)
        {
            item = new Periodical(); //Periodical item created         
        }
        else if(typeOfData == 3)
        {
            item = new CD(); //Periodical item created         
        }
        else if(typeOfData == 4)
        {
            item = new DVD(); //Periodical item created         
        }
        else if(typeOfData == 5)
        {
            user = new LibraryUser();   //User created
        }
        else if(typeOfData == 6)
        {
            reservation = new ItemReservation(); //itemReservation created
        }
        boolean wasSuccessful; //False - Object 'self creation' was unsuccessful. True - it was
        if(item != null) //Runs if lineOfText contained an item, denoted by the value of typeOfData
        {
            wasSuccessful = item.readData(splitLine); //Object is sent the scanner for 'self creation'
            if(wasSuccessful == true)
            {
                wasSuccessful  = storeItem(item); //Stores object in Array itemMap
                if(wasSuccessful == false) //Happens if item with same itemCode has already been added
                {
                    System.out.println("Line with duplicate ID: " + lineOfText + "\n");
                }
            }
            else
            {
                System.out.println("Data: " + lineOfText);     
                item = null; //Unsuccessfully created object is disposed of
            }
        }
        else if(user != null) //Runs if lineOfText contained an User, denoted by the value of typeOfData
        {  
            wasSuccessful = user.readData(splitLine);
            if(wasSuccessful == true)
            {
                wasSuccessful = storeUser(user); //Stores object in Array itemMap
                if(wasSuccessful == false) //Happens if User with same ID has already been added
                {
                    System.out.println("Line with duplicate ID: " + lineOfText + "\n");
                }
            }
            else
            {
                System.out.println("Data: " + lineOfText);     
                item = null; //Unsuccessfully created object is disposed of               
            }
        }
        else if(reservation != null) //Runs if lineOfText contained a reservation, denoted by the value of typeOfData
        {
            wasSuccessful = reservation.readData(splitLine); //Scanner sent to reservation to read the Date. Reservations success outcome is returned
            if(wasSuccessful == true)
            {
                wasSuccessful = storeItemReservation(reservation); //Stores object in Array itemMap
                if(wasSuccessful == false) //Happens if User with same ID has already been added
                {
                    System.out.println("Line with duplicate ID: " + lineOfText + "\n");
                }
            }
            else
            {
                System.out.println("Data: " + lineOfText);     
                reservation = null; //Unsuccessfully created object is disposed of               
            }
        }
        splitLine.close();
    }
    /**
     * Writes every users data to a text file, of users choosing
     */
    public void writeUserDataUserSelectedFile() throws FileNotFoundException
    {
        String chosenFile;
        chosenFile = chooseFile(); //Calls function, which uses a fileDialog to choose a file. Returns file path. 
        File file;        
        if(chosenFile.equals("nullnull") == true) //"nullnull" is the String chooseFile() returns if no file was chosen 
        {
           System.out.println("\nFile Not Found: Did you select a file?");   
        }
        else
        {
            //NOTE: writeUserData(), at the point of the 'old code', only operated by a user selecting a file. With the current code, the system supports both user selected and code defined text file, via 
            // //writeUserDataUserSelectedFile() and writeUserData() respectively. 
            //***OLD CODE*** 
            //  PrintWriter pWriter; 
            //  pWriter = new PrintWriter(new File(chosenFile)); //Initiates a PrintWriter to write to a file. NOTE: DOES NOT APPEND, DATA IN FILE PREVIOUSLY WILL BE REMOVED
            //  addUserIdentifier(pWriter); //Adds first 2 lines to text file: The identifier [Library User Data] and the key for the layout of said data being entered. 
            //  for(LibraryUser currentUser : userList)
            //  {
            //      currentUser.writeData(pWriter);
            //  }
            //  pWriter.close();
            //***END OF OLD CODE***
            file = new File(chosenFile); //Creates a File object with path of chosen file
            userDataToFile(file);
        }    
    }
    /**
     * Writes data to default output text file, \**\Text files\UsersWithIDs.txt, starting from the active directory
     * Note: This operation overwrites any text that is current in this file
     */
    public void writeUserData() throws FileNotFoundException, IOException
    {
        //See writeUserDataUserSelectedFile() for 'OLD CODE'
        File file; 
        String filePath;
        file = new File((System.getProperty("user.dir"))); //Gets current working directory
        filePath = file.getParent() + "\\Text files\\UsersWithIDs.txt"; //Gets file path where lastReservationNo.txt is expected to be found
        //This is one directory up, in a file called Text Files
        file = new File(filePath); //Creates a file with expected filePath
        if(file.isFile() == false) //If file doesnt exist (including directories), they're created
        {
            createExpectedFile(file);
        }
        userDataToFile(file);
    }    
    /**
     * Common code for writeUserData() & writeUserDataUserSelectedFile()
     * FileNotFoundException not caught because file is either:
     * 1. User selected file (which has been confirmed as chosen).
     * 2. System selected file (via code) (which is created if not found)
     */
    private void userDataToFile(File file) throws FileNotFoundException
    {
        PrintWriter pWriter;
        pWriter = new PrintWriter(file); //Initiates a PrintWriter to write to a file. NOTE: DOES NOT APPEND, DATA IN FILE PREVIOUSLY WILL BE REMOVED
        addUserIdentifier(pWriter); //Adds first 2 lines to text file: The identifier [Library User Data] and the key for the layout of said data being entered. 
        for(LibraryUser user: userMap.values())
        {
            user.writeData(pWriter);
        }
        pWriter.close();
        System.out.println("User data successfully saved in text file " + file.getPath());
    }
    /**
     * Gets all item Reservations for hashMap and writes them to user selected text file
     */
    public void writeItemReservationDataUserSelectedFile() throws FileNotFoundException
    {
        String chosenFile;
        File file;
        chosenFile = chooseFile(); //Calls function, which uses a fileDialog to choose a file. Returns file path. 
        if(chosenFile.equals("nullnull") == true) //"nullnull" is the String chooseFile() returns if no file was chosen 
        {
           System.out.println("\nFile Not Found: Did you select a file?");   
        }
        else
        {
            file = new File(chosenFile); //Creates a File object with path of chosen file
            itemReservationToFile(file);
        }    
    }
    /**
     * Gets all item Reservations for hashMap and writes them to default file (\..\Text files\activeReservations.txt) starting from the active directory
     * NOTE: This operation overwrites any text already in said file
     */
    public void writeItemReservationData() throws IOException
    {
        //See writeUserDataUserSelectedFile() for 'OLD CODE'
        File file; 
        String filePath;
        file = new File((System.getProperty("user.dir"))); //Gets current working directory
        filePath = file.getParent() + "\\Text files\\activeReservations.txt"; //Gets file path where lastReservationNo.txt is expected to be found
        //This is one directory up, in a file called Text Files
        file = new File(filePath); //Creates a file with expected filePath
        if(file.isFile() == false) //If file doesnt exist (including directories), they're created
        {
            createExpectedFile(file);
        }   
        itemReservationToFile(file);
    }
    /**
     * Writes all item reservations to selected file
     * Common code for writeItemReservationData() & writeItemReservationDataUserSelectedFile()
     */
    public void itemReservationToFile(File file) throws FileNotFoundException
    {
        PrintWriter pWriter;
        pWriter = new PrintWriter(file); //Initiates a PrintWriter to write to a file. NOTE: DOES NOT APPEND. AND DATA IN FILE PREVIOUSLY WILL BE REMOVED
        addReservationIdentifier(pWriter); //Adds first 2 lines to text file: The identifier [Library User Data] and the key for the layout of said data being entered. 
        for(ItemReservation reservation: itemReservationMap.values())
        {
            reservation.writeData(pWriter);
        }
        pWriter.close();
        System.out.println("Reservation data successfully saved in text file " + file.getPath());
    }
    /**
     * Writes the last Resrvation Number to be assigned to a text file. Said text file is hard codeded. 
     * The file (from current working directory) is \..\Text file\lastResrvationNo.txt
     * 2 Lines added:
     * 1. Identifier for lastReservationNo ([lastReservationNo])
     * 2. The last reservation Number
     */
    private void writeLastReservationNumber(String reservationNumber) throws FileNotFoundException
    {
        PrintWriter pWriter; 
        File file;
        String filePath;
        file = new File((System.getProperty("user.dir"))); //Gets current working directory
        filePath = file.getParent() + "\\Text files\\lastReservationNo.txt"; //Gets file path where lastReservationNo.txt is expected to be found
        file = new File(filePath); //file given new file - The file of interest
        pWriter = new PrintWriter(file); //pWriter is initialized 
        pWriter.println("[lastReservationNo]"); //Identifier added to top line
        pWriter.println(reservationNumber); //last reservation number added to file
        pWriter.close();
    }
    /**
     * Adds 2 lines to beginning of a file (said file is passed via PrintWriter), which will contain user information:
     * Line 1: User identifier ([Library User Data])
     * Line 2: Key for anyone who reads said file
     */
    private void addUserIdentifier(PrintWriter pWriter) //Adds first 2 lines to text file, so read methods can use said file
    {
        pWriter.println("[Library User Data]");
        pWriter.println("// data is userID, surname, firstName, otherInitials, title");
    }
    /**
     * Adds 2 lines to beginning of a file (said file is passed via PrintWriter), which will contain reservtion information:
     * Line 1: resrvation identifier ([Item Reservation Data])
     * Line 2: Key for anyone who reads said file
     */
    private void addReservationIdentifier(PrintWriter pWriter) //Adds first 2 lines to text file, so read methods can use said file
    {
        pWriter.println("[Item Reservation Data]");
        pWriter.println("// data is reservationNo, itemCode, userID, startDate, noOfDays");
    }
    //
    //Accessors
    //
    /**
     * Displays all details of all items & users in itemMap & userMap respectively
     */
    public void printAllItemsAndUsers()
    {
        //***OLD CODE***
        //  int listPosition;
        //  listPosition = 0;
        //  for(LibraryItem libraryItem : itemList)
        //  {
        //      libraryItem.printDetails();
        //      System.out.println();
        //  }
        //  for(LibraryUser libraryUser : userList)
        //  {
        //      libraryUser.printDetails();
        //      System.out.println();
        //  }
        //***END OF OLD CODE***
        String keySet;
        Scanner splitLine; //Creates a new scanner to split keySet
        for(int x = 0; x<2 ; x++) //Loops twice, once for Users and once for Items
        {
            keySet = getKeySet(x); //Gets a string of all keys in HashMap
            keySet = keySet.substring(1, (keySet.length() -1)); //Removes first and last character, [ & ] respectively
            splitLine = new Scanner(keySet).useDelimiter("[ ]*+,[ ]*+");
            if(x==0)
            {
                while(splitLine.hasNext() == true) 
                {
                    keySet = splitLine.next();
                    itemMap.get(keySet).printDetails(); //Uses KeySet to get object, which then has its details shown
                }
            }
            else
            {
                while(splitLine.hasNext() == true) //Same as above, but with Users instead
                {
                    keySet = splitLine.next();
                    userMap.get(keySet).printDetails(); //Uses KeySet to get object, which then has its details shown
                }
            }
            splitLine.close();
        }
        System.out.println("\n***End of Operation***");
    }
    /**
     * Outputs all details of all item Reservations in itemReservationMap
     */
    public void printItemReservations()
    {
        String keySet;
        keySet = itemReservationMap.keySet().toString(); //Gets all keys in KeyMap, to be passed to a scanner.
        keySet = keySet.substring(1, (keySet.length() -1)); //Removes first and last character, [ & ] respectively
        Scanner splitLine; //Creates a new scanner to split keySet
        splitLine = new Scanner(keySet).useDelimiter("[ ]*+,[ ]*+");
        while(splitLine.hasNext() == true)
        {
            keySet = splitLine.next();
            itemReservationMap.get(keySet).printDetails(); //Uses KeySet to get object, which then has its details shown
        }
        splitLine.close();
        System.out.println("\n***End of Operation***");
    }
    /**
     * Prints loaned items between the timeframe given
     */
    public void printDiaryEntries(String startDate, String endDate)
    {
        DateUtil makeDate; //Creates a DateUtil to convert String inputs to type Date
        Date startDateAsDate, endDateAsDate; //Fields to hold Dates
        makeDate = new DateUtil();
        try
        {
            startDateAsDate = makeDate.convertStringToDate(startDate); //turns String startDate to type Date
            endDateAsDate = makeDate.convertStringToDate(endDate); //turns String endDate to type Date
            diary.printEntries(startDateAsDate, endDateAsDate); 
        }
        catch(Exception ex1) //Occurs if inputs are not in the expected format of dd-mm-yyyy, or the date selected doesnt exist
        {
            System.out.println("\nError: Please ensure inputted dates are valid and in the format 'dd-mm-yyyy'");
        }       
        System.out.println("\n***End of Operation***");     
    }
    /**
     * Retrieves key Set from selected hashMap, based on inputted int value
     * x=0 - Return Key Set of itemMap. 
     * x=1 - Return Key Set of userMap
     */
    private String getKeySet(int x)
    {
        if(x==0)
        {
            return itemMap.keySet().toString();
        }
        else if(x==1)
        {
            return userMap.keySet().toString();
        }
        return "[]"; //Returned if x is given any other value that what is expected. This represents an empty keySet
    }
    /**
     * Retrieves itemReservation from hashMap, which has a matching key.
     */
    private ItemReservation getItemReservation(String key)
    {
        return itemReservationMap.get(key);
    }
    //
    //Mutators
    //
    /**
     * Stores item to itemMap
     */
    public boolean storeItem(LibraryItem item)
    {
        //***OLD CODE***
        //  itemList.add(item);
        //***END OF OLD CODE***
        if(checkIfItemCodeInUse(item) == false) //Ensures code isnt already in use before storing
        {
            itemMap.put(item.getItemCode(), item); //item is stored in itemMap
            return true; //Indicates successful storing of the item
        }
        else
        {

            System.out.println("\nERROR: Item with the Item Code " + item.getItemCode() + " has already been added. Item WILL NOT be added.");
            return false; //Indicated unsucessful storing of the item
        }
    }
    /**
     * Stores user to users
     */
    public boolean storeUser(LibraryUser user)
    {
        //***OLD CODE
        //  if(user.getUserID().equals("unknown"))
        //  {
        //      newuserList.add(user);
        //  }
        //  else
        //  {
        //      userList.add(user);
        //      userIDs.put(user.getUserID(), user);
        //  }
        //***END OF OLD CODE***       
        if(user.getUserID().equals("unknown")) //If user is new, they are not immediately stored
        {
            newUserList.add(user); //Users added to ArrayList, to be assigned an ID once all users with IDs from selected file are added first. This reduces the chance that a new user is assinged an ID the same to an older user
            //whom had previously been assigned said id, but hadn't yet been re-added to the system. Note: If users with pre-assigned IDs are located in a seperate file, which is read AFTER some new users are added to the system,
            //it is isnt possible to prevent their ID from being assigned earlier (as the system is unaware of their existance). In the case of this happening, the old user is required to receive a new id. The likelihood of this
            //happening is low with few users, but exponentially increases as more 'new' users are added to they system).
            //To sum up: All users with previously assigned IDs should be added to the system before any new Users are added, to prevent problematic situations.
            return true;
        }
        else
        {
            if(checkIfUserIDInUse(user) == false) //Ensures a user with the same ID hasnt already been added to the system. Likely causes: Input file had information for the same user twice 
            {
                userMap.put(user.getUserID(), user);  
                return true;
            }
            else //ID is already takem. This occurs if: 1. File where data is being read has 2 users with the same IDs  2. Two files contains the same user (or users with same IDs) are read
            {                                         //3. A second file of Users is being read, but their ID has been assinged to a new user in the first file. 
                System.out.println("\nERROR: A user with ID of " + user.getUserID() + " has already been added to HashMap.");
                System.out.println("Checking for similarities...");
                if(checkUserSimilarities(user) == true) //Checks user & user in HashMap to see if they're identical. True, they are. False, they differ
                {
                    System.out.println("Cause of error: User has previously been added. User will not be added again.");
                    return false;
                }
                else //User that have the same IDs appear to be differnt users. Problem is averted by assigning user with a new ID
                {    
                    System.out.println("Users with same IDs have different data. Assigning new ID to User from file...");
                    assignUserNewID(user); //Gives User, whos ID has already been taken, a newID, before saving to hashMap
                    return false;
                }
            }   
        }
    }
    /**
     * Store reservations in HashMap
     */
    public boolean storeItemReservation(ItemReservation reservation)
    {        
        if(checkIfItemReservationCodeInUse(reservation) == false) //Makes sure reservation with same ID hasnt already been added to HashMap
        {
            itemReservationMap.put(reservation.getReservationNo(), reservation); //reservation added to HashMap
            diary.addReservation(reservation); //reservation added to diary
            return true;
        }
        else
        {
            System.out.println("\nERROR: Reservation with Reservation Number " + reservation.getReservationNo() + " has already been added. Reservation WILL NOT be added.");
            return false;
        }
    }
    /**
     * Sorts 'new' users that were put in newUserList (assigns IDs, saves to userMap)
     * A 'new' user is a user with id of "unknown"
     */
    private void addNewUsers()
    {
        for(LibraryUser newUser : newUserList) //Goes through array list, extracting each newUser
        {
            assignUserNewID(newUser); //In charge of assinging new ID
        }
    }
    /**
     * Creates and stores a reservation
     */
    public boolean makeItemReservation(String userID, String itemCode, String startDate, int noOfDays) throws FileNotFoundException, IOException
    {
        if(userMap.containsKey(userID) == true && itemMap.containsKey(itemCode) == true)
        {
            //check getReservations for each day
            if(noOfDays >0)
            {
                DateUtil getDate; //Allows for String to be turned to type Date
                Date startDateAsDate , currentDate; //Turns String startDate into type Date, date which is being passed to getReservations
                ItemReservation[] reservationArray; //Holds the aray getResrvations returns
                getDate = new DateUtil();
                startDateAsDate = getDate.convertStringToDate(startDate);
                for(int x = 0; x < noOfDays; x++) 
                {
                    currentDate = getDate.incrementDate(startDateAsDate, x); //Increments startDate by the loop counter
                    reservationArray = diary.getReservations(currentDate); //Gets reservations for incremented date
                    if(reservationArray != null) //Ensures there was at least 1 reservation made on selected day. If there wasnt, nothing else needs doing and the for loop counter is incremented
                    {
                        for(ItemReservation reservation: reservationArray)
                        {
                            if(reservation.getItemCode().equals(itemCode) == true) //Checks to see if item is already reserved on a day in which another user wants to reserve it
                            {
                                System.out.println("\nItem is already reserved for selected period");
                                return false;
                            }
                        }
                    }
                }
                String reservationNo;
                reservationNo = generateReservationNo(); //Creates a reservation number
                ItemReservation reservation; //Creates a reservation
                reservation = new ItemReservation(reservationNo, itemCode, userID, startDate, noOfDays);
                return storeItemReservation(reservation);
            }
            else
            {
                System.out.println("\nItem must be reserved for at least 1 day");
                return false;
            }
        }
        else
        {
            System.out.println("\nError: User or Item not valid. Reservation has NOT been created");
            return false;
        }        
    }
    /**
     * Deletes selected ItemReservation
     */
    public void deleteItemReservation(String reservationNo)
    {
        if(itemReservationMap.containsKey(reservationNo) == true) //Ensures reservation actually exists
        {
            ItemReservation reservation;
            reservation = itemReservationMap.get(reservationNo); //gets reservation using reservation key
            diary.deleteReservation(reservation); //Deletes reservation from diary
            itemReservationMap.remove(reservation); //Removes reservation from hashMap
            System.out.println("\nReservation has been removed");
        }
        else
        {
            System.out.println("\nError: No such reservation");
        }
    }
    //
    //ID Generator
    //
    /**
     * Produces an ID for a user.
     * An ID is in the format [Prefix]-[Numbers]
     * Where the prefix is the first letter of the Users firstname and surname (Eg. Joe Smith has the prefix JS)
     * Where Numbers consists of 6 randomly chosen numerical characters
     */
    private String generateUserID(String prefix, int length)
    {
        Random rand;
        String userID;
        rand = new Random(); //Creates a randomiser
        userID = prefix;
        for(int x = 0; x<6 ; x++)
        {
           userID += rand.nextInt(10); //Picks a random int between 0 & 9, and appends it to the String userID
        }
        return userID;
    }
    /**
     * Reassigns User ID, in the case that users with same id has already been added, but their data differs.
     */
    private void assignUserNewID(LibraryUser user)
    {
        String prefix, userNewID;
        int length, count; //Length: Number of numbers to be added to end of ID, Count: Counts number of failed attempts to assign a new ID
        length  = 6;
        count = 0;
        prefix = user.getFirstName().substring(0, 1) + user.getSurname().substring(0 , 1) + "-"; //Creates prefix for userID, by taking users first letter of First and surname.
        userNewID = generateUserID(prefix, length); //Calls generateUserID to produce random ID (prefix + 6 random numbers)
        while(userMap.containsKey(userNewID) == true) //Checks to see if ID is already in use. If it is, a new ID is formed
        {
            count += 1;
            if(count == 500)
            {
                length += 1; //If 500 failed attempts are made to assign a new userID, the quantity of numbers in said ID is incremented (Should only occur if a large proportion of IDs are already taken). 
                //500 attempts was chosen after significant testing (via a standalone project, called Test), whereby it adds items to a hashmap until it fails x amount of times to assign a new unique ID. 
                //This process is done 1000 times, and the average number of items added to said hashmap before x amount of fails occur is worked out. I decided that 500 attempts offered the best tradeoff of
                //time taken and how full the hashMap is before having to increase the length of IDs.
                //On average, 500 failed attempts occurs when the hashmap is 98% full, with 500 attempts taking <2ms to execute (based on my PC, whilst running in the background). Therefore, a 7th digit for
                //a users ID, on average, will only be assigned once 980 000 users have already been added to the hashMap.
                count = 0;
            }             
            userNewID = generateUserID(prefix, length);
        }
        user.setUserID(userNewID); //Sets Users new id        
        if(storeUser(user) == true) //Stores user and ensures the process was successful (Shouldnt fail, as usersID should be unique)
        {
            System.out.println("User: " + user.getFirstName() + " " + user.getSurname() + " has been assigned an ID of " + userNewID);
        }
        else //storeUser return false if user with matching ID is found. This should never occur, as the ID has just been randomized and checked to ensure it is unique
        {
            System.out.println("\nError: Something went wrong.");
        }
    }
    /**
     * Generates a reservation number by retreiving previous reservation number and incrementing it by 1.
     */
    private String generateReservationNo() throws FileNotFoundException, IOException
    {
        int lastReservationNo; //Holds the last reservation No, as type int. 
        lastReservationNo = findLastReservationNumber();
        String reservationNumber; //Will hold the 6 digit reservation number
        reservationNumber = new String();
        reservationNumber += (lastReservationNo +1); //Gets next reservation number by finding how many reservations have previously been added.
        int zerosNeeded; //States how many zeros needed to make reservations 6 digits long
        zerosNeeded = 6 - reservationNumber.length();
        for(int x = 0; x < zerosNeeded; x++)  //Adds the necessary amount of 0's to beginning of reservationNo to make it 6 characters long
        {
            reservationNumber = "0" + reservationNumber;
        }
        writeLastReservationNumber(reservationNumber); //Writes to text file to update lastReservationNo
        return reservationNumber;
        
    }
    //
    //Code relevant to selecting the last reservation number
    //
    /**
     * Function that finds and returns lastReservationNo as int. e.g., if lastReservationNo was 000009, 9 would be returned
     * 
     * Has 3 main steps, with each subsequent step being executed on failure of pervious step. 
     * 
     * Step 1: Read file that is expected to be present, which should contain lastReservationNo. If it isnt present, or file doesnt contain
     * lastReservationNo, run Step 2
     * 
     * Step 2: Request user to select file where lastResrvationNo is found. If selected file doesnt contain lastReservatioNo, or no file was
     * selected, run Step 3
     * 
     * Step 3: Worst Case Scenario reached: Assumes no such file exists and that all present reservations have already been added. This looks
     * through itemReservationMap for reservation with highest reservationNo and increments said value for next reservation. If no reservations
     * are found in itemReservationMap, it is assumed that no reservations have yet been made and starts at "000001". NOTE: If a reservation is
     * later added with the same reservationNo as what has been assigned, that reservation will be required to receive a new reservationNo to
     * prevent data loss. Such an issue cannot be solved without forcing all previous reservations to be added first.
     * 
     */
    private int findLastReservationNumber() throws FileNotFoundException, IOException
    {
        File file;
        String filePath;
        int lastReservationNo;
        file = new File((System.getProperty("user.dir"))); //Gets current working directory
        filePath = file.getParent() + "\\Text files\\lastReservationNo.txt"; //Gets file path where lastReservationNo.txt is expected to be found
        //This is one directory up, in a file called Text Files
        file = new File(filePath); //Creates a file with expected filePath
        if(file.isFile() == false) //Checks to see if file exists as expected. If not, the code below is run
        {
            System.out.println("\nFile lastReservationNo.txt was not found where it was expected");
            System.out.println("Please select lastReservationNo.txt manually. If such a file doesnt exist, please click 'cancel'");
            lastReservationNo = findLastReservationNumberUserSelectedFile(); //Calls function that executes step 2
            if(lastReservationNo == -1)
            {
                lastReservationNo = findLastResrvationNumberViaHashMap(); //Calls function that executes step 3
            }
            createExpectedFile(file); //Creates directories and file that was expected for future use.            
        }
        else
        {
            lastReservationNo = getLastReservationNumber(file);
            if(lastReservationNo == -1)
            {
                System.out.println("\nFile lastReservationNo.txt did not contain lastReservationNo");
                System.out.println("If the file containing said information exists in a differnt location, please select it.");
                System.out.println("If such a file doesnt exist, please click 'cancel'");
                lastReservationNo = findLastReservationNumberUserSelectedFile(); //Calls function that executes step 2
                if(lastReservationNo == -1)
                {
                    lastReservationNo = findLastResrvationNumberViaHashMap(); //Calls function that executes step 3
                }
                //Code that attempts to work out which number is next using hashMap
            }
        }
        return lastReservationNo;
    }
    /**
     * Gets last reservation number from selected file
     */
    private int getLastReservationNumber(File file) throws FileNotFoundException
    {
        Scanner sr1; //Creates scanner with selected file
        String lastReservationNo;
        boolean found; //States if lastReservationNo has been found
        sr1 = new Scanner(file);
        while(sr1.hasNextLine() == true) //Goes through file, looking for line denoting reservation number ([lastReservationNo])
        {
            lastReservationNo = sr1.nextLine(); //lastReservationNo given the next line of the file
            lastReservationNo = lastReservationNo.trim(); //Removes leading & trailing whitespace
            if(lastReservationNo.equals("[lastReservationNo]") == true) //Checks to see if line is denoting line
            {
                while(sr1.hasNextLine() == true) //Inner while loop. Checks every subsequence line, until end of file is reached
                {                                                  //or until lastReservation number is found
                    lastReservationNo = sr1.nextLine(); //lastReservationNo given the next line of the file
                    lastReservationNo = lastReservationNo.trim(); //Removes leading & trailing whitespace
                    found = checkLine(lastReservationNo); //Function which checks to see if line contains value in the same fomat as reservationNo
                                                         //If it does, it is used. Else, its ignored 
                    if(found == true)
                    {
                        sr1.close();
                        int lastReservationNoAsInt;                    
                        lastReservationNoAsInt = Integer.parseInt(lastReservationNo) ;                
                        return lastReservationNoAsInt;
                    }
                }
            }
        }
        System.out.println("\nSelected file did not contain lastResevationNo."); //Reached if: 1.Denoting line [lastReservationNo] is not present
        sr1.close();                                                                                   //2. lastReservationNo wasn't found after denoting line.
        return -1;            
    }
    /**
     * Checks selected String to see if it could be the reservationNo that is expected
     */
    private boolean checkLine(String text)
    {
        return text.matches("[0-9]{6}"); //Checks to see if field 'text' consists of 6 numerical characters
    }
    /**
     * Primary use: Step 2 for findLastReservationNumber()
     * Finds last reservation number by prompting user to select said file.
     */    
    private int findLastReservationNumberUserSelectedFile() throws FileNotFoundException
    {
        String filePath;
        int lastReservationNo;
        filePath = chooseFile(); //Prompts user to select file
        if(filePath.equals("nullnull")) //Runs if no file was selected
        {
            return -1; //value which represents failure
        }
        else //Proceeds to check user selected file for last ItemReservationNo
        {            
            lastReservationNo = getLastReservationNumber(new File(filePath)); //Calls function which reads selected file. String is returned
            if(lastReservationNo == -1)
            {
                return -1; //value which represents failure
            }
            else
            {
                return lastReservationNo;
            }                       
        }     
    }
    /**
     * Primary use: Step 3 for findLastReservationNumber()
     * Finds last reservation number by incrementing highest reservationNo in itemReservationMap
     * NOTE: This is the worst case scenario for finding lastResrvationNo, and should only be used if Step 1 & 2 (see findLastReservationNumber())
     * fail to find lastReservationNo
     */
    private int findLastResrvationNumberViaHashMap()
    {
        String keys, currentKey; //keys - Holds keySet of HashMap  currentKey - Holds key that the scanner last red
        int currentKeyAsInt; //Holds currentKey, but as an integer
        int biggestKey; //Holds the integer value of the largest key in the HashMap
        biggestKey = 0;
        if(itemReservationMap.size() == 0)
        {
            return 0;
        }
        else
        {
            keys = itemReservationMap.keySet().toString(); //Retrieves keySet of HashMap
            keys = keys.substring(1, (keys.length() -1)); //Removes [ & ] from beginning & end
            
            Scanner sr1;
            sr1 = new Scanner(keys).useDelimiter("[ ]*+,[ ]*+"); //Scanner to seperate keys 
            while(sr1.hasNext() == true)
            {
                currentKey = sr1.next();
                try
                {
                    currentKeyAsInt = Integer.parseInt(currentKey);
                    if(biggestKey <= currentKeyAsInt)
                    {
                        biggestKey = currentKeyAsInt;
                    }
                }
                catch(Exception ex1) //This occurs if a key has been added to HashMap that doesnt abide by itemReservation reservationNo assigning
                {                    //standard. This Shouldn't occur, but would crash the program if it did (hence the try-catch). Catch is empty
                                     //because, if this does occur, nothing (other than ignoring it) needs to be done.
                }            
            }
            sr1.close();
            return biggestKey;
        }
    }
    /**
     * Creates directories and files that were expected, but no present, so they're present for future use
     */    
    private void createExpectedFile(File file) throws IOException
    {
        File createDirs; //Holds filepath of directory path needed to get to expected text file
        createDirs = new File(file.getParent()); //Gets filepath of where the text file was expected to be found
        createDirs.mkdirs(); //Creates directories for where lastReservationNo.txt should be. If they're present, this does nothing.
        createDirs = null; 
        file.createNewFile(); //Creates text file, lastReservationNo.txt, where it was expected.
     }
    //
    //Functions
    //
    /**
      *Ensures that user that is to be added to HashMap has a unique ID 
      *
      */
    private boolean checkIfUserIDInUse(LibraryUser user)
    {
        return userMap.containsKey(user.getUserID());
    }
    /**
      *Ensures that item that is to be added to HashMap has a unique ID 
      *
      */
    private boolean checkIfItemCodeInUse(LibraryItem item)
    {
        return itemMap.containsKey(item.getItemCode());
    }
    /**
      *Ensures that reservation that is to be added to HashMap has a unique ID 
      *
      */
    private boolean checkIfItemReservationCodeInUse(ItemReservation reservation)
    {
        return itemReservationMap.containsKey(reservation.getReservationNo());
    }
    /**
     * Check if users with same ID are identical
     */
    private boolean checkUserSimilarities(LibraryUser user)
    {
        String commonID; //Holds ID that the 2 users share
        commonID = user.getUserID();
        if(user.getSurname().equals(userMap.get(commonID).getSurname()) == true)  //If statements checking each field of User. If all are identical, true is returned. If any differ, false is returned
        {
            if(user.getFirstName().equals(userMap.get(commonID).getFirstName()) == true)
            {
                if(user.getOtherInitials().equals(userMap.get(commonID).getOtherInitials()) == true)
                {
                    if(user.getTitle().equals(userMap.get(commonID).getTitle()) == true)
                    {
                        return true;
                    }
                }
            }
        }
        return false; //Returned if Users are not identical(occurs if most inner if statement isnt reached)
    }        
}