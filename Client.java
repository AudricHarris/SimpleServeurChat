import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private String nom;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public Client(String nom)
	{
		this.nom = nom;
		connectToServer();
	}

	public String getNom() { return this.getNom(); }

	private void connectToServer()
	{
		try
		{
			this.socket = new Socket("localhost", 12345);
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

			this.out.println(nom);
		}
		catch (IOException e)
		{
			System.err.println("Erreur de connexion au serveur: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void ecouterServeur() 
	{
		try
		{
			String messageRecu;
			while ((messageRecu = in.readLine()) != null)
			{
				System.out.println(messageRecu);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void envoyerMessages()
	{
		try
		{
			BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
			String msg;
			while ((msg = consoleIn.readLine()) != null)
			{
				if ("quitter".equalsIgnoreCase(msg)) {
					this.out.println("quitter");
					break;
				}
				this.out.println(msg);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				socket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		String nom = "Test";
		if (args.length >= 1) nom = args[0];
		Client c = new Client(nom);
		System.out.println(c.nom + " est connecté au chat");

		Thread ecouteur = new Thread(() -> c.ecouterServeur());
		ecouteur.start();

		c.envoyerMessages();

		try
		{
			ecouteur.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
