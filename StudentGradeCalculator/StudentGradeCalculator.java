/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gradecalculator;

import java.util.Scanner;

/**
 *
 * @author Subhro Ghosh
 */
public class StudentGradeCalculator
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter how many subjects : ");
        int n = sc.nextInt();
        String subName[] = new String[n];
        int subMarks[] = new int[n];
        
        int sum = 0;
        for(int i=0; i<n; i++)
        {
            System.out.println("");
            System.out.print("Enter the name  of subject "+(i+1)+": ");
            subName[i] = sc.next();
            System.out.print("Enter the marks of subject "+(i+1)+": ");
            subMarks[i] = sc.nextInt();
            sum+=subMarks[i];            
        }
        
        System.out.println("");
        System.out.println("Your result is as follows ->");
        for(int i=0; i<n; i++)
        {
            String out = String.format("%-15s %d", subName[i],subMarks[i]);
            System.out.println(out);
        }
        System.out.println("");
        
        System.out.println("The total marks is -> "+sum+" out of "+(n*100));
        
        float avg = sum/(float)n;
        System.out.println("The avegare mark is -> "+avg+"%");
        
        if(avg==100)
            System.out.println("The grade is -> O");
        else if(avg>=90)
            System.out.println("The grade is -> A+");
        else if(avg>=80)
            System.out.println("The grade is -> A");
        else if(avg>=70)
            System.out.println("The grade is -> B+");
        else if(avg>=60)
            System.out.println("The grade is -> B");
        else if(avg>=50)
            System.out.println("The grade is -> C");
        else if(avg>=40)
            System.out.println("The grade is -> P");
        else
            System.out.println("The grade is -> F");
    }
}
