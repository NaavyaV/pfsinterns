// Java Program to Illustrate Record Class

import java.io.Serializable;

public class Record implements Serializable {

    // Instance variables
    private String name;
    private int idNumber;
    private int gpa;

    // Default Constructor
    public Record(){}
    public Record(String name, int idNumber, int contactNumber)
    {
        this.name = name;
        this.idNumber = idNumber;
        this.gpa = contactNumber;
    }


    public int getGpa() { return gpa; }
    public void setGpa(int gpa) { this.gpa = gpa;}
    public int getIdNumber() { return idNumber; }
    public void setIdNumber(int idNumber) { this.idNumber = idNumber;}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // toString() Method
    // @return
    @Override public String toString()
    {
        // Returning the record
        return "Records {"
                + "Name: " + name + ", ID: " + idNumber
                + ", GPA: " + gpa + '}';
    }
}
