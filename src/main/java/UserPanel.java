import java.util.InputMismatchException;
import java.util.Scanner;

public class UserPanel {
    int choiceUser = 0; // Sing Up or Register
    Scanner scanner = new Scanner(System.in);

    public void LoginPanel() {
        System.out.println("Welcone in SchoolDairy by Dijkstra");
        System.out.println("                                  ");

        while (choiceUser != 1 && choiceUser !=2)
        {
            try
            {
                System.out.println("Please chose 1 of 2 possible choice 1 or 2 :)");
                System.out.println("1. Register new Account (User)");
                System.out.println("2. Log In");
                choiceUser = scanner.nextInt();
            }
            catch (InputMismatchException e)
            {
                scanner.nextLine();
            }
            switch (choiceUser)
            {
                case 1:
                    createAccount();
                case 2:
                   // logIn();
            }

        }
    }

    public void createAccount()
    {
        String login;
        String password1;
        String password2;

        System.out.println("Enter your new personal login. Remember letter size is matter.");
        login = scanner.next();
        System.out.println("Enter your password: ");
        password1 = scanner.next();
        System.out.println("Repeat your password: ");
        password2 = scanner.next();

        while (password1.equals(password2));
        {
            System.out.println("Yours passwords aren't the same, try again!");
            System.out.println("Enter your password: ");
            password1 = "";
            password2 = "";
            password1 = scanner.next();
            System.out.println("Repeat your password: ");
            password2 = scanner.next();
        }
        System.out.println("Your account has been created! Now you can log in to you school dairy!");
        LoginPanel();




    }

}


