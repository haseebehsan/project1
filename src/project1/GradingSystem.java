package project1;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Haseeb
 */
public class GradingSystem {

    public void Calculator() throws IOException {
        char grade;
        float GPA;
        int[] subs = new int[5];
        int i;
        boolean again;
        Scanner in = new Scanner(System.in);

        for (i = 0; i < 5; i++) {

            System.out.println("Enter marks in subject " + (i + 1) + " out of 100");
            String input = "\nEnter Input: \n>>>> ";
            do {

                try {

                    System.out.print(input);
                    //in.nextLine();
                    subs[i] = in.nextInt();
                    again = false;
                } catch (InputMismatchException | NullPointerException ex) {
                    in.nextLine();
                    System.out.println("****ERROR!... wrong input, try again****");
                    again = true;
                }
                if (!(subs[i] <= 100 && subs[i] >= 0)) {
                    input = "ERROR!, please enter marks out of 100\nEnter Input Again: \n>>>> ";
                    again = true;
                }
            } while (again);

        }

        grade = grade(subs);
        GPA = GPA(subs);
        ClearCommand.Clear();
        System.out.println("your grade is " + grade + "\tand GPA is " + GPA);

        Project1.MainMenuPrinter();
        Project1.menu();
    }

    public float GPA(int[] subs) {
        float GPA;
        GPA = ((float) percentage(subs) * 4) / 100;
        return GPA;
    }

    public char grade(int[] subs) {
        char grade;
        int perc = percentage(subs);
        if (perc >= 90) {
            grade = 'A';
        } else if (perc >= 80) {
            grade = 'B';
        } else if (perc >= 70) {
            grade = 'C';
        } else if (perc >= 60) {
            grade = 'D';
        } else if (perc >= 50) {
            grade = 'E';
        } else {
            grade = 'F';
        }

        return grade;
    }

    public int percentage(int[] subs) {
        int percentage;
        percentage = sum(subs[0], subs[1], subs[2], subs[3], subs[4]) / 5;
        return percentage;

    }

    public int sum(int sub1, int sub2, int sub3, int sub4, int sub5) {
        int sum;
        sum = sub1 + sub2 + sub3 + sub4 + sub5;
        return sum;
    }

}
