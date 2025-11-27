import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ServeurChat
{
	private String nomServeur;
	private ServerSocket serverSocket;
	private Map<String, PrintWriter> clients = new ConcurrentHashMap<>();

	public ServeurChat(String nomServeur)
	{
		this.nomServeur = nomServeur;
	}

	public String getNomServeur() { return nomServeur; }
	public void setNomServeur(String nomServeur) { this.nomServeur = nomServeur; }

	public void openServeur()
	{
		try
		{
			this.serverSocket = new ServerSocket(12345);
			System.out.println("Le serveur {" + this.nomServeur + "} à été démarré");

			while (true)
			{
				Socket clientSocket = serverSocket.accept();
				System.out.println("Nouveau client connecté "+ clientSocket.getInetAddress());
				
				ClientHandler clientHandler = new ClientHandler(clientSocket, this);
				new Thread(clientHandler).start();
			}
		}
		catch (Exception e) { e.printStackTrace();}
	}

	public void envoyerMessage(String message, PrintWriter  c)
	{
		for (PrintWriter out : this.clients.values())
			if ( out != c)
				out.println(message);
	}

	public void connectionClient(String clientNom, PrintWriter c)
	{
		this.clients.put(clientNom, c);
		envoyerMessage("System : " + clientNom + " s'est connecté au chat",null);
	}
	
	public void deconnectionClient(String clientNom, PrintWriter c)
	{
		if (!this.clients.containsKey(clientNom)) return;

		this.clients.remove(clientNom);
		c.close();
		envoyerMessage("System : " + clientNom + " s'est déconnecté au chat",null);
	}

	public static void main(String[] args)
	{
		ServeurChat server = new ServeurChat("Serveur de chat");
		Thread t = new Thread(() -> server.openServeur());
		t.start();

		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
