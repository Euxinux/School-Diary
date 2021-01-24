import java.util.Scanner;

public class Student {
    String name;
    String lastName;
    Scanner scanner = new Scanner(System.in);


    public void addStudent()
    {
        System.out.println("Student name: "); name = scanner.nextLine();
        System.out.println("Student lastname: "); lastName = scanner.nextLine();
        System.out.println(name + " " + lastName);
    }

}

