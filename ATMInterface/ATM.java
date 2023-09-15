/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Subhro Ghosh
 */
public class ATM
{
    private int sig;
    private float balance;
    private int pin;
    private long accNo;
    private String userName;
    private String transction;
    
    public ATM(long acNo)
    {
        this.accNo=acNo;
    }

    public ATM(int pin, long accNo, String userName)
    {
        this.sig = 0x4041544D;  // @ATM
        this.pin = pin;
        this.accNo = accNo;
        this.userName = userName;
        this.transction = String.format("%-10s %-8s %-19s %-34s %s","DATE","TIME","ACTION","ACCOUNT","AMMOUNT");
    }
    
    
    
    public void write(OutputStream out) throws IOException
    {
        var dos = new DataOutputStream(out);
        dos.writeInt(sig);
        dos.writeLong(accNo);
        dos.writeInt(pin);
        dos.writeFloat(balance);
        dos.writeUTF(userName);
        dos.writeUTF(transction);
    }
    
    public void read(InputStream in) throws IOException
    {
        var dis = new DataInputStream(in);
        this.sig = dis.readInt();
        this.accNo=dis.readLong();
        this.pin = dis.readInt();
        this.balance = dis.readFloat();
        this.userName = dis.readUTF();
        this.transction = dis.readUTF();
    }
    
    public void deposit(float amt)
    {
        this.balance+=amt;
    }
    
    public int withdraw(float amt)
    {
        if(this.balance==0)
            return 1;
        else if(amt>this.balance)
            return 2;
        else
        {
            this.balance-=amt;
            return 3;
        }
    }
    
    public void setTransction(String s)
    {
        this.transction+=s;        
    }
    
    public String getTransction()
    {
        if(this.transction.endsWith("AMMOUNT"))
        {
            return "No Transction to show!";
        }
        else
            return this.transction;
    }
    
    public float getBalance()
    {
        return this.balance;
    }
    
    public int getPin()
    {
        return this.pin;
    }
    
    public void setPin(int pin)
    {
        this.pin=pin;
    }
    
    public String getUserName()
    {
        return this.userName;
    }
    
    public String getAcNo()
    {
        return accNo+"";
    }
}
