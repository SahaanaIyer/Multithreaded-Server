import java.io.*;
import java.util.*;
import java.net.*;
public class clientC
{
	public static void main(String args[])throws Exception
	{
		String send="", r="";
		
		Socket MyClient = new Socket("localhost",4444);
		DataInputStream din=new DataInputStream(MyClient.getInputStream());
		DataOutputStream dout = new DataOutputStream(MyClient.getOutputStream());
		Scanner sc = new Scanner(System.in);
		r=din.readUTF();
		System.out.println(r);
		while(!send.equals("stop"))	
		{ 
			
			System.out.print("Task: ");
			send = sc.nextLine();
			dout.writeUTF(send);
			dout.flush();
			r=din.readUTF();
			System.out.println("Answer: "+ r);
		}
		dout.close();
		din.close();
		MyClient.close();	
	}
}
