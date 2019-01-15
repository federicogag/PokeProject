package it.unibs.pajc.pokeproject;
import java.io.*;
import java.util.*;

public class Pokemon {
	private String name;
	private String type;
	public PKMove[] moves = new PKMove[4];
	public HashMap<String, Integer> stats = new HashMap<>();
	
	
	
	public Pokemon(String name,  String type) {
		this.name = name;
		this.type = type;
		fillStats(name);
		fillMoves();
	}
	
	/*
	 * TODO LIST
	 * fix percorso file, fix nome file, controlli file
	 * 
	 */
	
	private void fillStats(String name) {
		String filename = name + ".pk";
		File poke = new File(filename);
		if(poke.isFile())
		{
			BufferedReader br=null;
			try {
				br = new BufferedReader(new FileReader(poke));
				String text;
				while((text=br.readLine())!=null) {
					StringTokenizer st = new StringTokenizer(text, ":");
					String key = st.nextToken();
					String value = st.nextToken();
					int intvalue = Integer.parseInt(value);
					stats.put(key, intvalue);
				}	
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}	
	
	private void fillMoves() {
		moves[0] = new PKMove("Azione", 10);
		moves[1] = new PKMove("Forza", 20);
		moves[2] = new PKMove("Ruggito", 0);
		switch(type) {
		case "Erba":
			moves[3] = new PKMove("Foglielama", 30);
			break;
		case "Fuoco":
			moves[3] = new PKMove("Lanciafiamme", 40);
			break;
		case "Acqua":
			moves[3] = new PKMove("Idropulsar", 35);
			break;
		}
	}
}
