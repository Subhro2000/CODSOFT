/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Subhro Ghosh
 */
public class ATM_Interface
{
    public static void main(String[] args) throws IOException
    {        
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Subhro's Payment Bank ATM Service!");
        System.out.print("Enter your ac no : ");
        String ac = sc.next();
        long acno = checkAcNoLength(ac, sc);
        if(acno>0)            
            atmFunctions(acno);
        else
            System.out.println("Try again later!!!");
    }
    
    //Function for displaying menu to the user
     private static int menu(Scanner inp)
    {
        System.out.print("1: DEPOSIT ||  ");
        System.out.print("2: WITHDRAW ||  ");
        System.out.print("3: BALANCE ||  ");
        System.out.print("4: SET PIN ||  ");
        System.out.print("5: MONEY TRANSFER ||  ");
        System.out.print("6: TRANSCTION HISTORY ||  ");
        System.out.print("7: EXIT ||  ");
        System.out.println("0: CANCLE ANY PROCESS");
        System.out.print("Enter your choice : ");
        int n = inp.nextInt();
        return n;
    }
     
     //Function that performs withdraw money.    
     private static void atmWithdraw(ATM a, float amt, char ch)
     {
         atmWithdraw(a, amt, ch, a);
     }
     
     //Function that performs transfer money. 
     private static void atmWithdraw(ATM a, float amt, char ch, ATM b)
     {
        int c =a.withdraw(amt);
        switch (c)
        {
            case 3 -> 
            {
                if(ch=='W')
                {
                    System.out.println("\nWithdraw sccessfull.\n");
                    String out = String.format("%n%s Money Debited %44s %.2f",getDt(),"Rs.",amt);
                    a.setTransction(out);
                }
                else
                {
                    System.out.println("\nMoney Transfer sccessfull.\n");
                    String out = String.format("%n%s Money Transfer To   Ac. %-30s %s %.2f",getDt(),b.getAcNo(),"Rs.",amt);
                    a.setTransction(out);
                    
                }
            }
            case 2 -> System.out.println("\nInsufficient Balance!!\n");
            default -> System.out.println("\nFirst deposit some money!!\n");
        }
     }
     
     //Function for checking the pin if the user give the correct one or not
     private static boolean checkPin(ATM a, int p)
     {
         Scanner sc = new Scanner(System.in);
         for(int i=0; i<3; i++)
         {
             if(p==a.getPin())
                 return true;
             else
             {
                System.out.println("Wrong pin!!! \nNo of attempt left "+(3-i));
                 System.out.print("Please enter correct pin : ");
                 p = sc.nextInt();
             }
         }
         return false;
     }
     
     // Function for checking the pin is 4 digit or not
     private static int checkPinLength(String p, Scanner sc)
     {
         for(int i=0; i<4; i++)
         {
            if(p.length()==4)
            {
                return Integer.parseInt(p);
            }
            else
            {
                System.out.println("Pin should have 4 digits!\nAttempt left "+(4-i));
                System.out.print("Enter again : ");
                p = sc.next();
            }
         }
         return -1;
     }
     
     private static long checkAcNoLength(String a, Scanner sc)
     {
         for(int i=0; i<4; i++)
         {
            if(a.length()>=10)
            {
                return Long.parseLong(a);
            }
            else
            {
                System.out.println("Ac No should have minimum 10 digits!\nAttempt left "+(4-i));
                System.out.print("Enter again : ");
                a = sc.next();
            }
         }
         return -1;
     }
     
     //Function to ge the Date and Time of Transction
     private static String getDt()
     {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(d);
     }
     
     
     //Main Function for all necessary task of an ATM mechine
     private static void atmFunctions(Long ac) throws FileNotFoundException, IOException
     {
        File folder = new File("ATM_Database");
        folder.mkdir();  // Creating a separate dir on disk to store only atm database for one time
        
         Scanner sc = new Scanner(System.in);
        ATM a = new ATM(ac);
        String fName = ac+".atm"; // creating the file name
        File userDetails = new File(folder, fName);
        
        if(userDetails.exists()) //If ac already exists
        {
            var fis = new FileInputStream(userDetails);
            a.read(fis);
            fis.close();
        }
        else // If ac does not exists
        {
            System.out.println("You are not registered with our ATM service.");
            System.out.print("Do you want to register yourself? Press-(1) for 'YES' OR Press- (2) for 'NO' : ");
            int choice = sc.nextInt();
            if(choice == 1) // user choose to register himself
            {               
                System.out.print("Enter your name : ");
                Scanner inp = new Scanner(System.in);
                String s = inp.nextLine();
                System.out.print("Set your first pin : ");
                String p = sc.next();
                int pin = checkPinLength(p, sc);
                if(pin>0)
                {
                    userDetails.createNewFile(); // Creating a new database for new user
                    var fos = new FileOutputStream(userDetails);
                    a = new ATM(pin, ac,s); // First time database creation
                    a.write(fos);
                    System.out.println("Atm details successfully stored!");
                }
                else
                {
                    System.out.println("\nYou did not set the pin correctly!!\n");
                    return;
                }
            }
            else // If user choose not to register himself then choice is 2
            {
                System.out.println("\nThank You!!\n");
                return;
            }
        }
        
        System.out.print("To access your ac please enter your PIN : ");
        int p = sc.nextInt();
        if(checkPin(a, p))
        {
            System.out.println("Welcome Mr./Mrs. "+a.getUserName());
            int n;
            float amt;
            mainloop:
            while((n=menu(sc))>=0)
            {
                switch(n)
                {
                    case 1 -> // Deposit
                    {
                        System.out.print("Enter the amt you want to deposit : ");
                        amt = sc.nextFloat();
                        if(amt==0) //Cancelling process
                        {
                            System.out.println("\nDeposit Cancle!!\n");
                            break;
                        }
                        a.deposit(amt);
                        System.out.println("\nDeposit Successfull.\n");
                        String out = String.format("%n%s Money Credited %43s %.2f", getDt(),"Rs.",amt);
                        a.setTransction(out);
                        break;
                    }
                    case 2 -> //Withdraw
                    {
                        System.out.print("Enter the amt you want to withdraw : ");
                        amt = sc.nextFloat();
                        if(amt==0) //Cancelling process
                        {
                            System.out.println("\nWithdraw Cancle!!\n");
                            break;
                        }
                        System.out.print("Enter your pin : ");
                        p=sc.nextInt();
                        if(p==0) //Cancelling process
                        {
                            System.out.println("\nWithdraw Cancle!!\n");
                            break;
                        }
                        if(checkPin(a, p))
                        {
                            char ch = 'W';
                            atmWithdraw(a, amt,ch);                            
                        }
                        else
                            System.out.println("\nLimit done!!!\n");
                        break;
                    }

                    case 3 -> //Balance Check
                    {
                        amt = a.getBalance();
                        System.out.println("\nAvailable balance is Rs. : "+amt+"\n");
                        break;
                    }

                    case 4-> //Set  new pin
                    {
                        System.out.print("Enter your old pin : ");
                        p=sc.nextInt();
                        if(p==0) //Cancelling process
                        {
                            System.out.println("Set Pin Cancle!!");
                            break;
                        }
                        if(checkPin(a, p))
                        {
                            System.out.print("Enter your new pin : ");
                            String np = sc.next();
                            if(np.equals("0")) // Cancelling process
                            {
                                System.out.println("Set Pin Cancle!!");
                                break;
                            }
                            int pin = checkPinLength(np, sc);
                            if(pin!=a.getPin()) // New pin should not be the same as old pin
                            {
                                if(pin<0)
                                {
                                    System.out.println("New pin should have 4 digit!\nTry again later.");
                                    break;
                                }
                                System.out.print("Re-enter your new pin : ");
                                String rp = sc.next();
                                if(rp.equals("0")) // Cancelling process
                                {
                                    System.out.println("Set Pin Cancle!!");
                                    break;
                                }
                                int rpin = checkPinLength(rp, sc);
                                if(pin == rpin)
                                {
                                    a.setPin(pin);
                                    System.out.println("\nPin set successfully.\n");
                                }
                                else
                                    System.out.println("\nPin does not match!!\n");
                            }
                            else
                                System.out.println("\nNew pin cannot be same as old pin.\n");
                        }
                        else
                            System.out.println("\nSet pin is not possible, As you give wrong pin again and again!!!\n");
                        break;
                    }

                    case 5-> //Money transfer from one ac to another ac
                    {
                        System.out.print("Enter the other ac no : ");
                        String racno = sc.next();
                        long acc = Long.parseLong(racno);
                        if(acc==0) // Cancelling process
                        {
                            System.out.println("Money Transfer Cancle!!");
                            break;
                        }
                        String accno = racno+".atm"; // creating the file name
                        var ud = new File(folder, accno);
                        int count=3;
                        while(!ud.exists())
                        {
                            System.out.print("Enter the correct ac no : ");
                            racno = sc.next();
                            acc = Long.parseLong(racno);
                            if(acc==0) //Cancelling process
                            {
                                System.out.println("Money Transfer Cancle!!");
                                break;
                            }
                            accno = acc+".atm"; // creating the file name
                            ud = new File(folder, accno);
                            count--;
                            if(ud.exists() || count==0)
                                break;
                        }
                        if(count==0 || acc==0)
                        {
                            System.out.println("\nMoney Transfer cant possible as you enter wrong ac no!!!\n");
                            break;
                        }

                        ATM b = new ATM(acc); //Creating obj for receiver ac
                        var fis = new FileInputStream(ud);
                        b.read(fis);

                        System.out.print("Enter the amt : ");
                        amt = sc.nextFloat();
                        if(amt==0) // Cancelling process
                        {
                            System.out.println("Money Transfer Cancle!!");
                            break;
                        }
                        System.out.print("Enter you pin : ");
                        p=sc.nextInt();
                        if(p==0) // cancelling process
                        {
                            System.out.println("Money Transfer Cancle!!");
                            break;
                        }
                        if(checkPin(a, p))
                        {
                            if(amt<=a.getBalance())
                            {
                                char ch = 'T';
                                atmWithdraw(a, amt,ch,b);
                                b.deposit(amt);
                                String out = String.format("%n%s Money Received From Ac. %-30s %s %.2f",getDt(),a.getAcNo(),"Rs.",amt);
                                b.setTransction(out);
                            }
                            else
                                System.out.println("Money transfer cant possible as your ac balance is low-> "+a.getBalance());
                        }
                        else
                            System.out.println("\nLimit up!!!\n");
                        var foos = new FileOutputStream(ud);
                        b.write(foos);
                        foos.close();
                        break;
                    }

                    case 6 -> //Get Transction History
                    {
                            System.out.println("\n"+a.getTransction()+"\n");
                    }
                    
                    case 7 -> // Exit
                    {                        
                        var fos = new FileOutputStream(userDetails);
                        a.write(fos);                            
                        fos.close();
                        System.out.println("\nThank you! Visit again!\n");
                        break mainloop;
                    }
                    
                    default -> System.out.println("Enter a correct operation!");
                }
            }
        }
        else //If user give wrong pin
        {
            System.out.println("\nYou have cross your limit. Sorry Visit Again!!!\n");
        }
     }
}
