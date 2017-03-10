package es.ucm.fdi.tp.practica6.bgame.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {

	private Socket s;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public Connection(Socket s) throws IOException{
		this.s = s;
		this.out = new ObjectOutputStream(s.getOutputStream());
		this.in = new ObjectInputStream(s.getInputStream());
	}
	
	public void sendObject(Object r) throws IOException{
		try{
		out.reset();
		out.writeObject(r);
		out.flush();
		}
		catch(Exception e)
		{
			System.err.println("Error sending object. Connection may be closed.");
		}
	}
	
	public Object getObject() throws ClassNotFoundException, IOException{
		return in.readObject();
	}
	
	public void stop() throws IOException{
		s.close();
	}
	
	public boolean equals(Connection c){
		return c.s.equals(this.s) && c.in.equals(this.in) && c.out.equals(this.out);
	}
}
