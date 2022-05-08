import java.util.*;
import java.io.*;
import java.net.*;

public class serverC {
    public static void main(String args[]) throws Exception {
    	
    	ServerSocket MyServer = new ServerSocket(4444);
    	Socket ss = null;
    	while (true) {
    		ss = null;
    		try {
    			ss = MyServer.accept();
    			DataInputStream din =new DataInputStream(ss.getInputStream());  
				DataOutputStream dout=new DataOutputStream(ss.getOutputStream());
				
				 Thread t = new ClientHandler(ss, din, dout);
				 t.start();
				
    		}
    		catch(Exception E){
    			continue;
    		}
    	}
    }
}

class ClientHandler extends Thread {

	DataInputStream in;
	DataOutputStream out;
	Socket socket;
	int sum;
	float res;
	boolean conn;
	
	public ClientHandler (Socket s, DataInputStream din, DataOutputStream dout) {
		this.socket = s;
		this.in = din;
		this.out = dout;
		this.conn = true;

		try {
			this.out.writeUTF("Service:\nAdd: + num num\nSubtract: - num num\nMultiply: * num num\nDivision: / num num");
			this.out.flush();
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	public void run() {
		while (conn == true) {

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String input[] = this.in.readUTF().split(" ");
				
				switch (input[0]) {
					case "+":
						sum = Integer.parseInt(input[1]) + Integer.parseInt(input[2]);
						this.out.writeUTF(Integer.toString(sum));
						break;
					case "-":
						sum = Integer.parseInt(input[1]) - Integer.parseInt(input[2]);
						this.out.writeUTF(Integer.toString(sum));
						break;
					case "*":
						sum = Integer.parseInt(input[1]) * Integer.parseInt(input[2]);
						this.out.writeUTF(Integer.toString(sum));
						break;
					case "/":
						res = Float.parseFloat(input[1]) / Integer.parseInt(input[2]);
						this.out.writeUTF(Float.toString(res));
						break;
					default:
						this.out.writeUTF("Terminating");
						conn = false;
				}
				
				this.out.flush();
				System.out.println("Response to " + this.socket + ": " + sum);			
			}

			catch (Exception E) {
				System.out.println(E);
			}
		}
		closeConn();
	}

	public void closeConn() {

		try {
			this.out.close();
			this.in.close();
			this.socket.close();
		}

		catch (Exception E) {
			System.out.println(E);
		}
	}
}
