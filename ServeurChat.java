import java.net.ServerSocket;

public class ServeurChat
{
	private String nomServeur;

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
			ServerSocket serverSocket = new ServerSocket(12345);
			System.out.println("Le serveur {" + this.nomServeur + "} à été démarré");

			while (true)
			{
				Socket clientSocket = serverSocket.accept();

			}
		}
	}

}
