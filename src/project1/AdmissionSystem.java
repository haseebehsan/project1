package project1;


import java.util.*;
import java.io.*;

/**
 *
 * @author Haseeb
 */
public class AdmissionSystem {

    
    public static void NewAdmission(long Starting_ByteValue, int ID) throws IOException {
        String FathersName, StudentsName;
        int MarksInFSC = 0, MarksInMatric = 0, MarksInNTS = 0;
        boolean addstudent = false, again;
        RandomAccessFile studentdata = new RandomAccessFile("studentdata.txt", "rw");
        Scanner in = new Scanner(System.in);
        long initial = studentdata.length();
        double Aggregate;

        studentdata.seek(Starting_ByteValue);

        do {
            ClearCommand.Clear();

            System.out.print("Enter first Name : ");
            StudentsName = in.nextLine();
            StudentsName = AddSpaces(15 - StudentsName.length(), StudentsName);

            System.out.print("Enter Father's name: ");
            FathersName = in.nextLine();
            FathersName = AddSpaces(15 - FathersName.length(), FathersName);

            System.out.print("Enter Marks in FSC: ");
            String AskingString = "\nEnter Input: \n>>>> ";
            do {
                try {
                    System.out.print(AskingString);
                    MarksInFSC = in.nextInt();
                    again = false;
                } catch (InputMismatchException | NullPointerException ex) {
                    in.nextLine();
                    System.out.println("****ERROR!... wrong input, try again****");
                    again = true;
                }
                if (!(MarksInFSC <= 1100 && MarksInFSC >= 0)) {
                    AskingString = "Error, MarksInFSC can't be out of that range (0, 1100)\nEnter Input Again: \n>>>> ";
                    again = true;
                }
            } while (again);

            System.out.print("Enter Marks in Matric: ");
            AskingString = "\nEnter Input: \n>>>> ";
            do {
                try {
                    System.out.print(AskingString);
                    MarksInMatric = in.nextInt();
                    again = false;
                } catch (InputMismatchException | NullPointerException ex) {
                    in.nextLine();
                    System.out.println("****ERROR!... wrong input, try again****");
                    again = true;
                }
                if (!(MarksInMatric >= 0 && MarksInMatric <= 1050)) {
                    AskingString = "Error! MarksInFSC can only be in range (0, 1050)\nEnter Input Again: \n>>>> ";
                    again = true;
                }
            } while (again);

            System.out.print("Enter Marks in NTS test: ");
            AskingString = "\nEnter Input: \n>>>> ";
            do {
                try {
                    System.out.print(AskingString);
                    MarksInNTS = in.nextInt();
                    again = false;
                } catch (InputMismatchException | NullPointerException ex) {
                    in.nextLine();
                    System.out.println("****ERROR!... wrong input, try again****");
                    again = true;
                }
                if (!(MarksInNTS >= 0 && MarksInNTS <= 100)) {
                    AskingString = "ERROR!, MarksInNTS can only be in range (0, 100)\nEnter Input Again: \n>>>> ";
                    again = true;
                }
            } while (again);

            Aggregate = Aggregate(MarksInFSC, MarksInMatric, MarksInNTS);

            if (Aggregate(MarksInFSC, MarksInMatric, MarksInNTS) > 60) {
                studentdata.writeInt(ID++);
                studentdata.writeUTF(StudentsName);
                studentdata.writeUTF(FathersName);
                studentdata.writeDouble(Aggregate);

                if (initial == studentdata.length()) {          //if new student /...
                    ClearCommand.Clear();
                    studentdata.seek(Starting_ByteValue);
                    System.out.println("Your New Data has been stored to the student, "
                            + "now new data of student is :" + "\nID: " + studentdata.readInt() + "\n"
                            + "Name: " + studentdata.readUTF() + "\n"
                            + "Father name : " + studentdata.readUTF() + "\n"
                            + "Aggregate" + studentdata.readDouble());
                    break;
                } else {
                    System.out.println("Admission Granted, your ID is " + (ID - 1) + "");
                    System.out.print("Your aggregate is : " + Aggregate);
                }
            } else {
                System.out.println("You are not eligible to get admission.... get out of here rightnow...");
            }

            System.out.println("\nTo Enter another Student enter 1, else enter 2 : ");
            int UserInput = 0;
            AskingString = "\nEnter Input: \n>>>> ";
            do {
                try {
                    System.out.print(AskingString);
                    UserInput = in.nextInt();
                    again = false;
                } catch (InputMismatchException | NullPointerException ex) {
                    in.nextLine();
                    System.out.println("****ERROR!... wrong input, try again****");
                    again = true;
                }
                if ((UserInput != 1 && UserInput != 2)) {
                    AskingString = "ERROR!, \n To Enter another Student Please  enter 1, else enter 2 : \nEnter Input Again: \n>>>> ";
                    again = true;
                }
            } while (again);

            in.nextLine();
            if (UserInput == 1) {
                addstudent = true;
            } else if (UserInput == 2) {
                ClearCommand.Clear();
                break;
            }

        } while (addstudent);

        Project1.MainMenuPrinter();
        Project1.menu();
    }

    public static double Aggregate(int FscMarks, int MatricMarks, int NtsMarks) {
        double Aggregate;
        Aggregate = ((FscMarks * 50) / 1100) + ((MatricMarks * 10) / 1050) + ((NtsMarks * 40) / 100);
        return Aggregate;

    }

    public static String AddSpaces(int n, String inp) {
        for (int i = 0; i < n; i++) {
            inp += " ";
        }
        return inp;
    }

    public static int lastID() throws FileNotFoundException, IOException {
        RandomAccessFile studentdata = new RandomAccessFile("studentdata.txt", "rw");
        int ID = 0;
        if (studentdata.length() != 0) {
            studentdata.seek(studentdata.length() - 46);
            ID = studentdata.readInt();
        }
        return ID;
    }
}
