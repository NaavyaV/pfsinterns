// Java Program to Illustrate StudentRecordManagement Class

// Importing required classes
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

// Class
public class Manager {

    LinkedList<Record> list;

    public Manager()
    {
        list = new LinkedList<>();
    }

    public void add(Record record)
    {
        if (!find(record.getIdNumber())) {
            list.add(record);
        }
        else {
            System.out.println("Record already exists in the Record list");
        }
    }

    public boolean find(int idNumber)
    {
        for (Record l : list) {
            if (l.getIdNumber() == idNumber) {

                System.out.println(l);
                return true;
            }
        }
        return false;
    }

    public void delete(int recIdNumber)
    {
        Record recordDel = null;
        for (Record ll : list) {

            if (ll.getIdNumber() == recIdNumber) {
                recordDel = ll;
            }
        }

        if (recordDel == null) {
            System.out.println("Invalid record Id");
        }
        else {
            String recName = recordDel.getName();
            list.remove(recordDel);
            System.out.println("Successfully removed " + recName + " from the list");
        }
    }

    public Record findRecord(int idNumber)
    {
        for (Record l : list) {
            if (l.getIdNumber() == idNumber) {
                return l;
            }
        }

        return null;
    }

    public void update(int id, Scanner input)
    {

        if (find(id)) {
            Record rec = findRecord(id);

            System.out.print(
                    "What is the student's new ID Number? ");
            int idNumber = input.nextInt();

            System.out.print(
                    "What is the student's updated GPA? ");
            int gpa = input.nextInt();
            input.nextLine();

            System.out.print(
                    "What is the student's updated name? ");
            String name = input.nextLine();

            rec.setIdNumber(idNumber);
            rec.setName(name);
            rec.setGpa(gpa);
            System.out.println("Record Updated Successfully");
        }
        else {

            // Print statement
            System.out.println("Record Not Found in the Student list");
        }
    }

    public void display()
    {
        if (list.isEmpty()) {
            System.out.println("The list has no records\n");
        }

        for (Record record : list) {
            System.out.println(record.toString());
        }
    }

    public void saveToFile(String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(list);
            }
    }

    public void loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis))
        {
            list = (LinkedList<Record>) ois.readObject();
        }
        catch (Exception e) {
            list = new LinkedList<>();
        }
    }
}
