import java.io.IOException;
import java.util.Scanner;

public class Runner {

    // Main driver method
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Manager hr = new Manager();
        hr.loadFromFile("StudentData");
//        // Initial Student
//        Record record = new Record();
//        record.setIdNumber(6862);
//        record.setGpa(87);
//        record.setName("Ankit");
//
//        hr.add(record);

        // Creating Scanner Object to read input
        Scanner input = new Scanner(System.in);

        // Creating option integer variable
        int option = 0;

        // Do - While loop
        do {
            menu();
            option = input.nextInt();

            // Switch case
            switch (option) {
                case 1:
                    // Add student
                    System.out.print("What is the student's ID Number? ");
                    int idNumber = input.nextInt();

                    System.out.print("What is the student's GPA? ");
                    int contactNumber = input.nextInt();
                    input.nextLine();

                    System.out.print("What is the student's name? ");
                    String name = input.nextLine();

                    Record record = new Record(name, idNumber, contactNumber);
                    hr.add(record);
                    System.out.println(record);
                    System.out.println("\n--------------------------------------------\n");
                    break;

                case 2:
                    // Delete student
                    System.out.print("What is the student's ID Number? ");
                    int rId = input.nextInt();
                    hr.delete(rId);
                    System.out.println("\n--------------------------------------------\n");
                    break;

                case 3:
                    // Update Student
                    System.out.print("What is the student's ID number? ");
                    int rIdNo = input.nextInt();
                    hr.update(rIdNo, input);
                    System.out.println("\n--------------------------------------------\n");
                    break;

                case 4:
                    // Search Student
                    System.out.print("What is the student's ID? ");
                    int bookId = input.nextInt();
                    if (!hr.find(bookId)) {
                        System.out.println("Student id does not exist\n");
                    }
                    System.out.println("\n--------------------------------------------\n");
                    break;

                case 5:
                    // Display all students
                    hr.display();
                    System.out.println("--------------------------------------------\n");
                    break;

                case 9:
                    // Exit program
                    System.out.println("\nThank you for using the program. Save changes? (Y/N)");
                    if(input.next().equals("Y") || input.next().equals("y")) {
                        hr.saveToFile("StudentData");
                        System.out.println("\nStudent data saved successfully!\n");
                    }
                    System.exit(0);
                    break;

                default:
                    // Print statement
                    System.out.println("\nInvalid input\n");
                    System.out.println("\n--------------------------------------------\n");
                    break;
            }
        }
        while (true);
    }

    public static void menu()
    {
        System.out.println("MENU");
        System.out.println("1: Add Student");
        System.out.println("2: Delete Student");
        System.out.println("3: Update Student");
        System.out.println("4: Search Student");
        System.out.println("5: Display Students");
        System.out.println("9: Exit program");
        System.out.print("Enter your selection : ");
    }
}
