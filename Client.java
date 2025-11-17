import java.io.BufferedReader;
import java.io.IOException;
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
	
	public void ecouterServeur()
	{
		try
		{
			while (true)
			{
				String messageRecu = this.in.read();
				System.out.println("messageRecu : " + messageRecu);
			}
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void ClientLogic()
	{
		System.out.println(this.nom + "est connecté au chat");
		while (true)
		{
			try
			{
				String msg = in.read();
				out.write(msg);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
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
