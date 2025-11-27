import java.net.Socket;
import java.io.*;

public class ClientHandler implements Runnable
{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[36m";

	private Socket s;
	private ServeurChat serveur;
	private BufferedReader in;
	private PrintWriter out;
	private String nom;

	public ClientHandler(Socket s, ServeurChat serveurChat)
	{
		this.s = s;
		this.serveur = serveurChat;
	}

	public void run()
	{
		try
		{
			this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
			this.out = new PrintWriter(this.s.getOutputStream(), true);

			this.nom = in.readLine();
			if (this.nom != null)
			{
				serveur.connectionClient(this.nom, this.out);
				out.println("Bienvenue, " + this.nom);
			}

			String message;
			while ((message = in.readLine()) != null)
			{
				if ("quitter".equals(message))
					break;
				String formattedMessage = message.replace("\\n", "\n");
				serveur.envoyerMessage(ANSI_RED + this.nom + " : " + ANSI_RESET + formattedMessage, this.out);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			serveur.deconnectionClient(this.nom, this.out);
			try
			{
				this.s.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
