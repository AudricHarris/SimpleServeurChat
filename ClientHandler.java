import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable
{
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	private Client c;

}
