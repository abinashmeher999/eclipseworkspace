
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Plib implements Serializable {

    /**
     *
     */
    private static int bookIDCounter = 0;
    private static int friendIDCounter = 0;
    private static double totalCostOfBooks;
    static ArrayList<Book> books = new ArrayList<Book>();
    ;
	static ArrayList<Friend> currentUsers = new ArrayList<Friend>();
    static String fileName = "Data.dat";
    static String friendFileName = "Users.dat";

    public static void readData(String fileName, String friendFileName) {
        try (FileInputStream fileIn = new FileInputStream(fileName); ObjectInputStream objectInput = new ObjectInputStream(fileIn)) {
            bookIDCounter = objectInput.readInt();
            totalCostOfBooks = objectInput.readDouble();
            books = (ArrayList<Book>) objectInput.readObject();

        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }
        try (FileInputStream friendFileIn = new FileInputStream(friendFileName); ObjectInputStream friendInput = new ObjectInputStream(friendFileIn)) {
            friendIDCounter = friendInput.readInt();
            currentUsers = (ArrayList<Friend>) friendInput.readObject();
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }
    }

    public static void writeAndExit(String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName); ObjectOutputStream objectOutput = new ObjectOutputStream(fileOut);) {
            objectOutput.writeInt(bookIDCounter);
            objectOutput.writeDouble(totalCostOfBooks);
            objectOutput.writeObject(books);
            System.out.println("Succesfully exited!");
        } catch (IOException ex) {
            System.out.println("Couldn't be written!");
        }
    }

    public static boolean writeFriend(String friendFileName) {
        boolean result = false;
        try (FileOutputStream fileOut = new FileOutputStream(friendFileName); ObjectOutputStream friendOutput = new ObjectOutputStream(fileOut);) {
            friendOutput.writeInt(friendIDCounter);
            friendOutput.writeObject(currentUsers);
            System.out.println("Succesfully written!");
            result = true;
        } catch (IOException ex) {
            System.out.println("Couldn't be written!");
            result = false;
        }
        return result;
    }

    public static void printFriend(Friend friend) {
        System.out.println("Name : " + friend.getName());
        System.out.println("ID : " + friend.getID());
        System.out.println("LandLine : " + friend.getLnumber());
        System.out.println("Mobile : " + friend.getMnumber());
        System.out.println("Address : " + friend.getAddress());
        return;
    }

    public static void createBook() {
        IssueBook newBook = new IssueBook();
        Scanner input = new Scanner(System.in);

        newBook.setID(++bookIDCounter);
        System.out.println("Please note, the ID is " + newBook.getID());

        System.out.println("Enter the Title :");
        newBook.setTitle(input.nextLine());

        System.out.print("Enter the Year of Publication\t");
        newBook.setYearOfPublication(input.nextLine());

        String expectedPattern = "dd/MM/yyyy";
        System.out.println("Enter date of purchase in " + expectedPattern + " format :");
        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
        try {
            Date date = formatter.parse(input.nextLine());
            newBook.setDateOfPurchase(date);
        } catch (ParseException e) {
            System.out.println("Error in date format :");
            return;
        }

        System.out.println("Enter the price :");
        newBook.setPrice(input.nextDouble());
        input.nextLine();

        System.out.println("Enter the Publisher :");
        newBook.setPublisher(input.nextLine());

        books.add(newBook);
        totalCostOfBooks += newBook.getPrice();
        return;
    }

    public static boolean deleteBook(int bookID) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getID() == bookID) {
                Book tempBook = books.remove(i);
                totalCostOfBooks -= tempBook.getPrice();
                System.out.println("Book ID " + bookID + " has been deleted!");
                return true;
            }
        }
        System.out.println("Book not found!");
        return false;
    }

    public static Boolean makeUnissuable(int bookID) {
        int i;
        for (i = 0; i < books.size(); i++) {
            if (books.get(i).getID() == bookID) {
                IssueBook tempIssueBook = new IssueBook();
                tempIssueBook = (IssueBook) books.remove(i);
                books.add(tempIssueBook.makeRefBook());
                System.out.println("Book ID " + bookID + " is now unissuable.");
                return true;
            }
        }
        if (i == books.size()) {
            System.out.println("Book not found!");
        }

        return false;
    }

    public static boolean search(String keyword) {
        int i = 0;
        int count = 0;
        boolean found = false;
        for (i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("\t" + (++count) + ". " + books.get(i).getTitle() + " ID " + books.get(i).getID());
                found = true;
            }
        }

        return found;
    }

    public static void displayBooks() {
        int n = books.size();
        int count = 0;

        System.out.println("Issuable Books :");
        for (int i = 0; i < n; i++) {
            if (books.get(i).isIssuable()) {
                System.out.println("\t" + (++count) + ". " + books.get(i).getTitle() + " ID " + books.get(i).getID());
            }
        }
        if (count == 0) {
            System.out.println("\tNone");
        }
        count = 0;

        System.out.println("Reference Books :");
        for (int j = 0; j < n; j++) {
            if (!books.get(j).isIssuable()) {
                System.out.println("\t" + (++count) + ". " + books.get(j).getTitle() + " ID " + books.get(j).getID());
            }
        }
        if (count == 0) {
            System.out.println("\tNone");
        }
        count = 0;
    }

    public static double displayTotalCost() {
        System.out.println("The total cost of books in the library is Rs." + totalCostOfBooks + ".");
        return totalCostOfBooks;
    }

    public static void createFriend() {
        Friend newFriend = new Friend();
        Scanner input = new Scanner(System.in);

        newFriend.setID(++friendIDCounter);
        System.out.println("Please note, the ID is " + newFriend.getID());

        System.out.println("Please enter the name :");
        newFriend.setName(input.nextLine());

        System.out.println("Please enter the address (separated by commas) :");
        newFriend.setAddress(input.nextLine());

        System.out.println("Please enter the LandLine Number :");
        newFriend.setLnumber(input.nextLine());

        System.out.println("Please enter the Mobile Number :");
        newFriend.setMnumber(input.nextLine());

        currentUsers.add(newFriend);
        Thread displayThread = new Thread("Display") {
            public void run() {
                System.out.println("--Thread: " + getName() + " running--");
                printFriend(currentUsers.get(currentUsers.size() - 1));
                System.out.println("--Thread: " + getName() + " finished--");
            }
        };

        Thread writeThread = new Thread("Write") {
            public void run() {
                System.out.println("--Thread: " + getName() + " running--");
                writeFriend(friendFileName);
                System.out.println("--Thread: " + getName() + " finished--");
            }
        };

        displayThread.start();
        writeThread.start();
        return;
    }

    public static boolean deleteFriend(int friendID) {
        for (int i = 0; i < currentUsers.size(); i++) {
            if (currentUsers.get(i).getID() == friendID) {
                Friend tempFriend = currentUsers.remove(i);
                System.out.println("Friend " + tempFriend.getName() + " with ID " + friendID + " has been deleted!");
                writeFriend(friendFileName);
                return true;
            }
        }
        System.out.println("Friend not found!");
        return false;
    }

    public static boolean searchFriend(String keyword) {
        int count = 0;
        boolean found = false;
        for (int i = 0; i < currentUsers.size(); i++) {
            if (currentUsers.get(i).getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("\t" + (++count) + ". " + currentUsers.get(i).getName() + " ID " + currentUsers.get(i).getID());
                found = true;
            }
        }

        return found;
    }

    public static void queryIssuedBooks() {
        for (int i = 0; i < books.size(); i++) {
            Book tempBook = books.get(i);
            if (tempBook.isIssued()) {
                System.out.println("Book : " + tempBook.getTitle() + " ID " + tempBook.getID());
                System.out.println("Issued to :" + tempBook.getIssueHistory().get(tempBook.getIssueHistory().size() - 1).getPerson());
                System.out.println("");
            }
        }
        return;
    }

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        readData(fileName, friendFileName);
        int option = 0;
        int secondOption = 0;
        while (option != -1) {
            System.out.println("Part 1 of Assignment : 1");
            System.out.println("Part 2 of Assignment : 2");
            System.out.println("Exit : 3");
            option = input.nextInt();
            input.nextLine();
            switch (option) {
                case 1:
                    while (secondOption != -1) {
                        System.out.println("Create Book: 1");
                        System.out.println("Delete Book: 2");
                        System.out.println("Display Books: 3");
                        System.out.println("Make Unissuable: 4");
                        System.out.println("Search for a Book: 5");
                        System.out.println("View total cost: 6");
                        System.out.println("Exit library: 7");
                        secondOption = input.nextInt();
                        input.nextLine();
                        switch (secondOption) {
                            case 1:
                                createBook();
                                secondOption = 0;
                                break;
                            case 2:
                                System.out.println("Enter the ID (-1 to cancel)");
                                int k = input.nextInt();
                                input.nextLine();
                                if (k == -1) {
                                    System.out.println("Delete cancelled!");
                                } else {
                                    deleteBook(k);
                                }
                                secondOption = 0;
                                break;
                            case 3:
                                displayBooks();
                                secondOption = 0;
                                break;
                            case 4:
                                System.out.println("Enter the ID (-1 to cancel)");
                                int j = input.nextInt();
                                input.nextLine();
                                if (j == -1) {
                                    System.out.println("Making Referrence cancelled!");
                                } else {
                                    makeUnissuable(j);
                                }
                                secondOption = 0;
                                break;
                            case 5:
                                System.out.println("Enter the keyword :");
                                String key = input.nextLine();
                                search(key);
                                secondOption = 0;
                                break;
                            case 6:
                                displayTotalCost();
                                secondOption = 0;
                                break;
                            case 7:
                                secondOption = -1;
                                break;

                            default:
                                System.out.println("Please Enter a VALID INPUT!");
                                secondOption = 0;
                                break;
                        }
                    }
                    option = 0;
                    secondOption = 0;
                    break;
                case 2:
                    while (secondOption != -1) {
                        System.out.println("Create friend record : 1");
                        System.out.println("Delete friend record : 2");
                        System.out.println("Modify friend record: 3");
                        System.out.println("Query friend : 4");
                        System.out.println("Issue book : 5");
                        System.out.println("Return book : 6");
                        System.out.println("Query Issued books : 7");
                        System.out.println("Query book : 8");
                        System.out.println("Display statistics : 9");
                        System.out.println("Trace book issue history : 10");
                        System.out.println("Exit : 11");
                        secondOption = input.nextInt();
                        input.nextLine();
                        switch (secondOption) {
                            case 1:
                                createFriend();
                                secondOption = 0;
                                break;
                            case 2:
                                System.out.println("Enter the ID (-1 to cancel)");
                                int k = input.nextInt();
                                input.nextLine();
                                if (k == -1) {
                                    System.out.println("Delete cancelled!");
                                } else {
                                    deleteFriend(k);
                                }
                                secondOption = 0;
                                break;
                            case 3:
                                System.out.println("Enter the ID of the friend you want to modify (-1 to cancel)");
                                int m = input.nextInt();
                                input.nextLine();
                                if (m == -1) {
                                    System.out.println("Delete cancelled!");
                                } else {
                                    for (int z = 0; z < currentUsers.size(); z++) {
                                        if (currentUsers.get(z).getID() == m) {
                                            Friend tempFriend = currentUsers.get(z);
                                            int mod = 0;
                                            while (mod != -1) {
                                                System.out.println("1 : LandLine");
                                                System.out.println("2 : Mobile");
                                                System.out.println("3 : Address");
                                                System.out.println("4 : Exit");
                                                mod = input.nextInt();
                                                input.nextLine();
                                                switch (mod) {
                                                    case 1:
                                                        System.out.println("Enter Landline");
                                                        tempFriend.setLnumber(input.nextLine());
                                                        mod = 0;
                                                        break;
                                                    case 2:
                                                        System.out.println("Enter Mobile");
                                                        tempFriend.setMnumber(input.nextLine());
                                                        mod = 0;
                                                        break;
                                                    case 3:
                                                        System.out.println("Enter Address");
                                                        tempFriend.setAddress(input.nextLine());
                                                        mod = 0;
                                                        break;
                                                    case 4:
                                                        mod = -1;
                                                        break;
                                                    default:
                                                        System.out.println("Invalid Input");
                                                        mod = 0;
                                                        break;
                                                }
                                            }
                                        }
                                    }
                                }
                                secondOption = 0;
                                break;
                            case 4:
                                System.out.println("Enter the keyword :");
                                String key = input.nextLine();
                                searchFriend(key);
                                secondOption = 0;
                                break;
                            case 5:
                                int book,
                                 friend;
                                System.out.println("Enter Book ID");
                                book = input.nextInt();
                                input.nextLine();
                                System.out.println("Enter Person ID");
                                friend = input.nextInt();
                                input.nextLine();
                                IssueBook tempBook = null;
                                int f;
                                for (f = 0; f < books.size(); f++) {
                                    if (books.get(f).getID() == book) {
                                    	//System.out.println("fon");
                                        tempBook = (IssueBook) books.get(f);
                                        break;
                                    }
                                }
                                if (f == books.size()) {
                                    System.out.println("Book Not Found!");
                                    break;
                                }
                                Friend tempFriend = null;
                                int v;
                                for (v = 0; v < currentUsers.size(); v++) {
                                    if (currentUsers.get(v).getID() == friend) {
                                    	//System.out.println("fon");
                                        tempFriend = (Friend) currentUsers.get(v);
                                        break;
                                    }
                                }
                                if (v == currentUsers.size()) {
                                    System.out.println("Friend Not Found!");
                                    break;
                                }
                                tempBook.issue(tempFriend);
                                secondOption = 0;
                                break;
                            case 6:
                                int book2,
                                 friend2;
                                System.out.println("Enter Book ID");
                                book2 = input.nextInt();
                                input.nextLine();
                                System.out.println("Enter Person ID");
                                friend2 = input.nextInt();
                                input.nextLine();
                                IssueBook tempBook2 = null;
                                int f2;
                                for (f2 = 0; f2 < books.size(); f2++) {
                                    if (books.get(f2).getID() == book2) {
                                        tempBook2 = (IssueBook) books.get(f2);
                                        break;
                                    }
                                }
                                if (f2 == books.size()) {
                                    System.out.println("Book Not Found!");
                                    break;
                                }
                                Friend tempFriend2 = null;
                                int v2;
                                for (v2 = 0; v2 < currentUsers.size(); v2++) {
                                    if (currentUsers.get(v2).getID() == friend2) {
                                        tempFriend2 = currentUsers.get(v2);
                                        break;
                                    }
                                }
                                if (v2 == currentUsers.size()) {
                                    System.out.println("Friend Not Found!");
                                    break;
                                }
                                tempBook2.returnBack(tempFriend2);
                                secondOption = 0;
                                break;
                            case 7:
                                queryIssuedBooks();
                                secondOption = 0;
                                break;
                            case 8:
                                System.out.println("Enter book ID");
                                int temb = input.nextInt();
                                input.nextLine();
                                int a;
                                for (a = 0; a < books.size(); a++) {
                                    if (books.get(a).getID() == temb && books.get(a).isIssuable()) {
                                        ((IssueBook) books.get(a)).issueQuery();
                                        break;
                                    }
                                }
                                if (a == books.size()) {
                                    System.out.println("Book Not Found!");
                                    break;
                                }
                                secondOption = 0;
                                break;
                            case 9:
                                System.out.println("Enter book ID");
                                int tem = input.nextInt();
                                input.nextLine();
                                int a2;
                                for (a2 = 0; a2 < books.size(); a2++) {
                                    if (books.get(a2).getID() == tem) {
                                        System.out.println("This has been issued " + books.get(a2).getNumIssues() + " times.");
                                        break;
                                    }
                                }
                                if (a2 == books.size()) {
                                    System.out.println("Book Not Found!");
                                    break;
                                }
                                secondOption = 0;
                                break;
                            case 10:
                                System.out.println("Enter book ID");
                                int t = input.nextInt();
                                input.nextLine();
                                int b;
                                for (b = 0; b < books.size(); b++) {
                                    if (books.get(b).getID() == t) {
                                        books.get(b).traceIssueHistory();
                                        break;
                                    }
                                }
                                if (b == books.size()) {
                                    System.out.println("Book Not Found!");
                                    break;
                                }
                                secondOption = 0;
                                break;
                            case 11:
                                secondOption = -1;
                                break;
                            default:
                                System.out.println("Please Enter a VALID INPUT!");
                                secondOption = 0;
                                break;
                        }
                    }
                    option = 0;
                    secondOption = 0;
                    break;
                case 3:
                    option = -1;
                    secondOption = 0;
                    break;
                default:
                    System.out.println("Invalid INPUT");
                    option = 0;
                    secondOption = 0;
                    break;
            }

        }
        writeAndExit(fileName);
    }

}
