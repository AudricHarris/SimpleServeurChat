import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
	private String nom;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public Client(String nom)
	{
		this.nom = nom;

		// Connection au serveur 
		try
		{
			this.socket = new Socket("localhost", 12345);
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(this.socket.getOutputStream()));
		}

	}

	public void ClientLogic()
	{
		System.out.println(this.nom + "est connecté au chat");
		while (true)
		{
			String msg = in.read();
			out.println(msg);
		}
	}

	public static void main(String[] args)
	{
		Socket toServer;
		PrintWriter out;
		BufferedReader in;

		Client c = new Client("Test Client");
		
		c.ClientLogic();
	}
	
}
