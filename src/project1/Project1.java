package project1;

import java.util.Scanner;
import java.io.*;
import java.util.InputMismatchException;

/**
 *
 * @author Haseeb Ahmad ---- FA14-BSE-081 --- Section 1B
 */
public class Project1 {

    public static void main(String[] args) throws IOException {     //Main Method that runs when Program is started...
        ClearCommand.Clear();                                       //Clears the screen....
        System.out.println("*****  WELCOME TO STUDENT MANAGMENT SYSTEM   *****\n-------------------------------------------------------"); //prints start screen
        MainMenuPrinter();                                                  //Print Menus...
        menu();                                                     //that method ask user to input and redirect to the choice of user
    }

    /*
     That Method Ask user to input numbers , and on the basis of input that redirects to the s[ecific class...
     */
    public static void menu() throws IOException {
        /*
        Declaration and Initialization of essential Variables and objects...
        */
        RandomAccessFile studentdata = new RandomAccessFile("studentdata.txt", "rw");   //
        RecordManagment RecordManagmentobject = new RecordManagment();
        GradingSystem GradingSystemObject = new GradingSystem();
        AdmissionSystem AdmissionSystemObject = new AdmissionSystem();
        Scanner ScanInput = new Scanner(System.in);
        int UserInput = 0;
        boolean AskAgain;
        String input = "\nEnter Input: \n>>>> ";
        
        /*
        Ask User to input and make sure that input is sutable or right using exception handling...
        */
        do {
            try {
                System.out.print(input);
                UserInput = ScanInput.nextInt();
                AskAgain = false;
            } catch (InputMismatchException | NullPointerException ex) {
                ScanInput.nextLine();
                System.out.println("****ERROR!... wrong input, try again****");
                AskAgain = true;
            }
            if ((UserInput != 1 && UserInput != 2 && UserInput != 3 && UserInput != 4 && UserInput != 5 && UserInput != 6)) {
                input = "Enter Input Again: \n>>>> ";
                AskAgain = true;
            }
        } while (AskAgain);
        
        ClearCommand.Clear();       //Clears Screen after asking user to input...
        
        
        /*
        if else statements used to check the user input to redirect program somewhere else
        */
        
        if (UserInput == 1) {
            GradingSystemObject.Calculator();
        } else if (UserInput == 2) {
            AdmissionSystem.NewAdmission(studentdata.length(), AdmissionSystem.lastID() + 1);
        } else if (UserInput == 3) {
            RecordManagmentobject.RecordManagmentMenu();
        } else if (UserInput == 4) {
            studentdata.setLength(0);
            Project1.MainMenuPrinter();
            Project1.menu();
        } else if (UserInput == 5) {
            RecordManagmentobject.DisplayAllRecord();
        } else if (UserInput == 6){
            System.exit(0);
        }
    }

    /**
     * That method Prints the main menu for user to choose between it...
    **/
    public static void MainMenuPrinter() {
        System.out.print("\nEnter \n"
                + "'1' for grading system\n"
                + "'2' for new admission...\n"
                + "'3' for RecordManagment\n"
                + "'4' for clear all student data\n"
                + "'5' for displaying all\n"
                + "'6 for exiting'");
    }
}
