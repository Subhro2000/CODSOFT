/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.util.Scanner;

/**
 *
 * @author Subhro Ghosh
 */
public class NumberGuessing
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        int choice;
        int score=0;
        do
        {
            System.out.println("Lets start the game!\n");            
            System.out.print("Enter the lower bound: ");
            int lb = sc.nextInt();
            System.out.print("Enter the upper bound: ");
            int ub = sc.nextInt();
            int rand = (int)(lb+(ub-lb+1)*Math.random());
            int points=100;
            System.out.print("\nEnter a number between "+lb+" to "+ub+": ");        
            int c=10;
            while(c!=0)
            {
                int n = sc.nextInt();     
                if(n==rand)
                {
                    System.out.println("\nHorray Its a match!");
                    System.out.println("\nYour Point is : "+points);
                    score+=points;
                    break;
                }
                else if(n>rand)
                {
                    System.out.println("It is higher! Attempt left : "+(c-1));
                }
                else
                {
                    System.out.println("It is lower! Attempt left : "+(c-1));
                }

                points-=5;
                c--;
                if(c==0)
                    System.out.println("Better luck next time!!");
                else
                    System.out.print("Enter another no: ");
            }
            
            System.out.print("\nDo you want play another round, \nPress- (1) For 'YES', Press (2) For 'NO' : ");
            choice = sc.nextInt();
            System.out.println("");
            
        }while(choice==1);   
        
        System.out.println("\nYour total score is -> "+score);
        System.out.println("\nThanks For Playing!");
    }
}
