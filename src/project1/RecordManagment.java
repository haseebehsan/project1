package project1;


import java.io.*;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;
import static project1.AdmissionSystem.AddSpaces;
import static project1.AdmissionSystem.NewAdmission;

/**
 *
 * @author Haseeb
 */
public class RecordManagment {

    /**
     *
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    public void RecordManagmentMenu() throws IOException, FileNotFoundException {

        Scanner in = new Scanner(System.in);
        boolean again;
        int inp = 0;
        String AskingInput = "\nEnter Input: \n>>>> ";

        System.out.println("\nEnter\n"
                + "1 to search by name\n"
                + "2 to search by ID\n"
                + "3 for displaying all the record\n"
                + "4 for main menu: \n");

        //ask user to input under exception handling...
        do {
            try {
                System.out.print(AskingInput);
                inp = in.nextInt();
                again = false;
            } catch (InputMismatchException | NullPointerException ex) {
                in.nextLine();
                System.out.println("****ERROR!... wrong input, try again****");
                again = true;
            }
            if ((inp != 1 && inp != 2 && inp != 3 && inp != 4)) {
                AskingInput = "Enter Input Again: \n>>>> ";
                again = true;
            }
        } while (again);

        ClearCommand.Clear();
        in.nextLine();
        //act according to user's input
        if (inp == 1) {
            
            System.out.println("Enter name: ");
            SearchByName(in.nextLine());
        }
        if (inp == 2) {
            
            searchByID();
        }
        if (inp == 3) {
            DisplayAllRecord();
        }
        if (inp == 4) {
            Project1.MainMenuPrinter();
            Project1.menu();
            ClearCommand.Clear();
        }

    }

    /**
     * Lat user search for a student from the student data/....
     **/
    public void SearchByName(String NameToSearch) throws IOException, FileNotFoundException {
        // searches student by name for user...
        ClearCommand.Clear();

        //declaring essential variables and objects
        RandomAccessFile studentdata = new RandomAccessFile("studentdata.txt", "rw");
        String FathersName, StudentName;
        double Aggregate;
        boolean Found = false;
        long InitialByte;
        int ID;

        NameToSearch = AddSpaces(15 - NameToSearch.length(), NameToSearch);     //making name a specific size by adding spaces
        studentdata.seek(0);

        while (studentdata.getFilePointer() < studentdata.length()) {
            InitialByte = studentdata.getFilePointer();
            ID = studentdata.readInt();
            StudentName = studentdata.readUTF();

            if (StudentName.equals(NameToSearch)) {     //if student found, it prints it and redirect to AfterSearchMenu()...
                FathersName = studentdata.readUTF();
                Aggregate = studentdata.readDouble();
                System.out.println("Student ID :" + ID + "\nStudents name : " + StudentName + "\nFather's name : " + FathersName + "\nAggregade: " + Aggregate);
                Found = true;
                AfterSearchMenu(InitialByte);
                break;
            } else {
                //skipping other data...
                studentdata.readUTF();
                studentdata.readDouble();
            }
        }
        if (!Found) {
            System.out.println("Not found...");
            RecordManagmentMenu();
        }

    }

    public void searchByID() throws IOException, FileNotFoundException {
        int ID_to_Search = 0;
        Scanner in = new Scanner(System.in);
        
        //Searvhes by ID , it could me student details what user is finding....
        ClearCommand.Clear();
        RandomAccessFile studentdata = new RandomAccessFile("studentdata.txt", "rw");
        String StudentsName, FathersName , AskingInput = "Enter Input: \n>>>> ";
        double Aggregate;
        
        int ID;
        boolean found = false, again;
        long InitialByteValue;

        studentdata.seek(0);
        
        System.out.println("Enter ID: ");
        //ID_to_Search = in.nextInt();
        do {
            try {
                System.out.print(AskingInput);
                ID_to_Search = in.nextInt();
                again = false;
            } catch (InputMismatchException | NullPointerException ex) {
                in.nextLine();
                System.out.println("****ERROR!... wrong input, try again****");
                again = true;
            }
            
        } while (again);

        while (studentdata.getFilePointer() < studentdata.length()) {

            ID = studentdata.readInt();

            if (ID == ID_to_Search) {
                InitialByteValue = studentdata.getFilePointer() - 4;
                StudentsName = studentdata.readUTF();
                FathersName = studentdata.readUTF();
                Aggregate = studentdata.readDouble();

                System.out.println("Students name : " + StudentsName + "\nFather's name : " + FathersName + "\nAggregade: " + Aggregate);
                found = true;
                AfterSearchMenu(InitialByteValue);
                break;
            } else {
                //skipping other data
                studentdata.readUTF();
                studentdata.readUTF();
                studentdata.readDouble();

            }

        }
        if (!found) {
            System.out.println("Student couldn't be found!!!");
        }
        Project1.MainMenuPrinter();
        Project1.menu();

    }

    public void AfterSearchMenu(long ByteIndex) throws IOException, FileNotFoundException {
        Scanner ScannerInputObject = new Scanner(System.in);
        String AskingUser_String = "\nEnter Input: \n>>>> ";
        boolean AskAgain;
        int UserInput = 0;

        System.out.println("\nEnter 1 to edit:\nEnter 2 to delete:\nEnter 3 for back menu: ");

        do {
            try {
                System.out.print(AskingUser_String);
                UserInput = ScannerInputObject.nextInt();
                AskAgain = false;
            } catch (InputMismatchException | NullPointerException ex) {
                ScannerInputObject.nextLine();
                System.out.println("****ERROR!... wrong input, try again****");
                AskAgain = true;
            }
            if ((UserInput != 1 && UserInput != 2 && UserInput != 3)) {
                AskingUser_String = "You only can enter 1 or 2\n1 for editing student record\n2 for deleting student record:\n3 for back menu: \nEnter Input Again: \n>>>> ";
                AskAgain = true;
            }
        } while (AskAgain);

        if (UserInput == 1) {
            edit(ByteIndex);
        } else if (UserInput == 2) {
            delete(ByteIndex);
        } else if (UserInput == 3) {
            RecordManagmentMenu();
        }

    }

    public void edit(long byteIndex) throws FileNotFoundException, IOException {
        RandomAccessFile studentdata = new RandomAccessFile("studentdata.txt", "rw");

        studentdata.seek(byteIndex);
        int ID = studentdata.readInt();
        studentdata.seek(byteIndex);

        NewAdmission(byteIndex, ID);

    }

    public void delete(long byteindex) throws FileNotFoundException, IOException {
        RandomAccessFile DeletingStudent = new RandomAccessFile("studentdata.txt", "rw");
        long NextStudentsData_StartingByteValue = byteindex + 46;
        RandomAccessFile NextReplacingStudent = new RandomAccessFile("studentdata.txt", "rw");

        DeletingStudent.seek(byteindex);
        NextReplacingStudent.seek(NextStudentsData_StartingByteValue);

        while (DeletingStudent.getFilePointer() < DeletingStudent.length()) {

            if (NextReplacingStudent.getFilePointer() == DeletingStudent.length()) {
                break;
            }

            DeletingStudent.writeInt(NextReplacingStudent.readInt());
            DeletingStudent.writeUTF(NextReplacingStudent.readUTF());
            DeletingStudent.writeUTF(NextReplacingStudent.readUTF());
            DeletingStudent.writeDouble(NextReplacingStudent.readDouble());

        }
        DeletingStudent.setLength(DeletingStudent.getFilePointer());        //decreases the length of file because of deletion

        System.out.println("Student commanded is seccessfully deleted from the record...");
        RecordManagmentMenu();

    }

    /**
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public void DisplayAllRecord() throws FileNotFoundException, IOException {
        RandomAccessFile StudentData_Object = new RandomAccessFile("studentdata.txt", "rw");

        StudentData_Object.seek(0);
        while (StudentData_Object.getFilePointer() < StudentData_Object.length()) {
            System.out.println("------------------------------");
            System.out.println("Student ID: " + StudentData_Object.readInt() + "                  |");
            System.out.println("Student Name: " + StudentData_Object.readUTF() + "  |");

            System.out.println("Father's Name: " + StudentData_Object.readUTF() + " |");

            System.out.println("Aggregate: " + StudentData_Object.readDouble() + "               |");

        }
        System.out.println("------------------------------");
        RecordManagmentMenu();
    }

}
