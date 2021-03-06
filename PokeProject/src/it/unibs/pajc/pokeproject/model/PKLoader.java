package it.unibs.pajc.pokeproject.model;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class PKLoader {
	
	private static final String BACK_GIF_DIR = "data\\gif\\back";
	private static final String FRONT_GIF_DIR = "data\\gif\\front";
	private static final String TYPE_DIR = "data\\type";
	private static final String POKEMON_DIR = "data\\pokemon";
	private static final String PK_DATABASE_LOCATION = "pkDatabase.dat";
	private static final String TYPE_DATABASE_LOCATION = "typeDatabase.dat";
	private static final String GIF_EXT = "gif";
	private static final String PKMN_EXT = "pk";
	private static final String TYPE_EXT = "tp";
	private static final String KEY_NAME = "Name";
	private static final String KEY_TYPE = "Type";
	private static final String GRASS = "Grass";
	private static final String WATER = "Water";
	private static final String FIRE = "Fire";
	private static final String NORMAL = "Normal";
	private static final String AZIONE = "Azione";
	private static final String RUGGITO = "Ruggito";
	private static final String FORZA = "Forza";
	private static final String FRUSTATA = "Frustata";
	private static final String PISTOLACQUA = "Pistolacqua";
	private static final String BRACIERE = "Braciere";
	
	private static final int AZIONE_PWR = 40;
	private static final int FORZA_PWR = 80;
	private static final int RUGGITO_PWR = 0;
	private static final int FRUSTATA_PWR = 45;
	private static final int BRACIERE_PWR = 40;
	private static final int PISTOLACQUA_PWR = 40;
	private static final int MOVE_1 = 0;
	private static final int MOVE_2 = 1;
	private static final int MOVE_3 = 2;
	private static final int MOVE_4 = 3;

	private ArrayList<PKType> typeDatabase;
	private TreeMap<Integer, Pokemon> pkDatabase;
	
	@SuppressWarnings("unchecked")
	public void loadPokemon() {
		File pkDbaseFile = new File(PK_DATABASE_LOCATION);
		if(pkDbaseFile.exists()) { // dobbiamo leggere il file solo se esiste
			try(ObjectInputStream databaseReader = new ObjectInputStream(new FileInputStream(pkDbaseFile))){
				pkDatabase = (TreeMap<Integer, Pokemon>)databaseReader.readObject(); // lettura treemap da file
			}
			catch(Exception e) {
				e.printStackTrace();

			}
		}
		else { // altrimenti lo creiamo noi
			pkDatabase = readPokemons();
			try(ObjectOutputStream databaseWriter = new ObjectOutputStream(new FileOutputStream(pkDbaseFile))){
				databaseWriter.writeObject(pkDatabase); // scrittura treemap su file
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadTypes() {
		File typeDbaseFile = new File(TYPE_DATABASE_LOCATION);
		if(typeDbaseFile.exists()) { // dobbiamo leggere il file solo se esiste
			try(ObjectInputStream databaseReader = new ObjectInputStream(new FileInputStream(typeDbaseFile))){
				typeDatabase = (ArrayList<PKType>)databaseReader.readObject(); // lettura arraylist da file
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else { // altrimenti lo creiamo noi
			typeDatabase = readTypes();
			try(ObjectOutputStream databaseWriter = new ObjectOutputStream(new FileOutputStream(typeDbaseFile))){
				databaseWriter.writeObject(typeDatabase); // scrittura arraylist su file
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Check for file extension, just to be sure
	 */
	private boolean checkExtension(File file, String extension) {
		String fileExtension = "";
		String name = file.getName();
		int i = name.lastIndexOf('.');
		if(i > 0) {
			fileExtension = name.substring(i+1);
		}
		if(fileExtension.compareToIgnoreCase(extension) == 0) 
			return true;
		return false;
	}
	
	private TreeMap<Integer, Pokemon> readPokemons() {	
		File pokemonDir = new File(POKEMON_DIR);
		TreeMap<Integer, Pokemon> pkDatabase = new TreeMap<>();
		if(pokemonDir.exists() && pokemonDir.isDirectory()) 
		{
			File[] theList = pokemonDir.listFiles();
			Pokemon toPut = null;
			for(int i = 0; i < theList.length; i++) {
				if(checkExtension(theList[i], PKMN_EXT)) {
					try (BufferedReader br = new BufferedReader(new FileReader(theList[i]))) {
						String line;
						String name = "";
						String typeName;
						while((line=br.readLine())!=null) {
							StringTokenizer st = new StringTokenizer(line, ":");
							String key = st.nextToken();
							if(key.compareTo(KEY_NAME) == 0) {
								name = st.nextToken();
							}
							else if(key.compareTo(KEY_TYPE) == 0) {
								typeName  = st.nextToken();
								PKType type = getType(typeName);
								toPut = new Pokemon(name, type);
							}
							else {
								int value = Integer.parseInt(st.nextToken());
								toPut.setStat(key, value);
							}
						}
						URL frontSprite = getFrontSpriteURL(name);
						toPut.setFrontSprite(frontSprite);
						URL backSprite = getBackSpriteURL(name);
						toPut.setBackSprite(backSprite);
					}
					catch(IOException e) {
						e.printStackTrace();
					}
				}
				fillMoves(toPut);
				if(toPut != null) pkDatabase.put(toPut.getID(), toPut);
			}		
		}
		return pkDatabase;
	}
	
	private ArrayList<PKType> readTypes() {
		File typeDir = new File(TYPE_DIR);
		ArrayList<PKType> typeDatabase = new ArrayList<>();
		if(typeDir.exists() && typeDir.isDirectory()) 
		{
			File[] theList = typeDir.listFiles();
			PKType toPut = null;
			for(int i = 0; i < theList.length; i++) {
				if(checkExtension(theList[i], TYPE_EXT)) {
					try (BufferedReader br = new BufferedReader(new FileReader(theList[i]))) {
						String line;
						String typeName;
						double value;
						while((line=br.readLine())!=null) {
							StringTokenizer st = new StringTokenizer(line, ":");
							String key = st.nextToken();
							if(key.compareTo(KEY_NAME) == 0) {
								typeName = st.nextToken();
								toPut = new PKType(typeName);
							}
							else {
								value = Double.parseDouble(st.nextToken());
								toPut.setEffectivenessEntry(key, value);
							}
						}
					}
					catch(IOException e) {
						e.printStackTrace();
					}
				}
				if(toPut != null) typeDatabase.add(toPut);
			}
		}
		return typeDatabase;
	}
	
	private PKType getType(String typeName) {
		for(int i = 0; i < typeDatabase.size(); i++)
			if(typeName.compareToIgnoreCase(typeDatabase.get(i).getTypeName()) == 0)
				return typeDatabase.get(i);
		return null;
	}
	
	private URL getFrontSpriteURL(String pokemonName) {
		File typeDir = new File(FRONT_GIF_DIR);
		if(typeDir.exists() && typeDir.isDirectory()) 
		{
			File[] theList = typeDir.listFiles();
			for(int i = 0; i < theList.length; i++) {
				if(checkExtension(theList[i], GIF_EXT)) {
					String fileName = "";
					String name = theList[i].getName();
					int j = name.lastIndexOf('.');
					fileName = name.substring(0, j);
					if(fileName.compareToIgnoreCase(pokemonName) == 0)
						try {
							return theList[i].toURI().toURL();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
				}
			}
		}
		return null;
	}
	
	private URL getBackSpriteURL(String pokemonName) {
		File typeDir = new File(BACK_GIF_DIR);
		if(typeDir.exists() && typeDir.isDirectory()) 
		{
			File[] theList = typeDir.listFiles();
			for(int i = 0; i < theList.length; i++) {
				if(checkExtension(theList[i], GIF_EXT)) {
					String fileName = "";
					String name = theList[i].getName();
					int j = name.lastIndexOf('.');
					fileName = name.substring(0, j);
					if(fileName.compareToIgnoreCase(pokemonName) == 0)
						try {
							return theList[i].toURI().toURL();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
				}
			}
		}
		return null;
	}
	
	private void fillMoves(Pokemon poke) {
		poke.setMove(MOVE_1, new PKMove(AZIONE, AZIONE_PWR, getType(NORMAL)));
		poke.setMove(MOVE_2, new PKMove(FORZA, FORZA_PWR, getType(NORMAL)));
		poke.setMove(MOVE_3, new PKMove(RUGGITO, RUGGITO_PWR, getType(NORMAL)));
		
		switch(poke.getType().getTypeName()) {
			case GRASS:
				poke.setMove(MOVE_4, new PKMove(FRUSTATA, FRUSTATA_PWR, getType(GRASS)));
				break;
			case FIRE:
				poke.setMove(MOVE_4, new PKMove(BRACIERE, BRACIERE_PWR, getType(FIRE)));
				break;
			case WATER:
				poke.setMove(MOVE_4, new PKMove(PISTOLACQUA, PISTOLACQUA_PWR, getType(WATER)));
				break;
		}
	}
	
	public boolean typeDatabaseExist() {
		if(typeDatabase != null)
		{
			return !typeDatabase.isEmpty();
		}
		else
		{
			return false;
		}
	}
	
	public boolean pkDatabaseExist() {
		if(pkDatabase != null)
		{
			return !pkDatabase.isEmpty();
		}
		else
		{
			return false;
		}
	}
	
	public Pokemon getPokemonFromDB (int id) {
		Pokemon toGet = pkDatabase.get(id);
		return (Pokemon)deepCopy(toGet);
	}

	public TreeMap<Integer, Pokemon> getPkDatabase() {
		return pkDatabase;
	}
	
	/*
	 * We create a copy of the object we need by creating a stream of it
	 * and then reading it. Obviously we can if the object is serializable. 
	 */
	private static Object deepCopy (Object object) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
			outputStrm.writeObject(object);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
			return objInputStream.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}