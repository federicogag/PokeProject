package it.unibs.pajc.pokeproject.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import it.unibs.pajc.pokeproject.util.PKMessage;

public class PKBattleEnvironment {

	private Pokemon ourPokemon;
	private Pokemon opponentPokemon;
	private ArrayList<PropertyChangeListener> listenerList;
	private int opponentID;
	
	public PKBattleEnvironment() {
		listenerList = new ArrayList<>();
	}
	public void executeCommand(PKMessage msg) {
		PropertyChangeEvent e;
		switch(msg.getCommandBody()) {
		case MSG_TEST_CONNECTION:
			e = new PropertyChangeEvent(this, "connection", false, true);
			firePropertyChanged(e);
			break;
		case MSG_WAITING:
			break;
		case MSG_WAKEUP:
			// this may not be needed, further analysis requested
			e = new PropertyChangeEvent(this, "wait", true, false);
			firePropertyChanged(e);
			break;
		case MSG_OPPONENT_POKEMON:
			opponentID = msg.getDataToCarry();
			e = new PropertyChangeEvent(this, "opponent", -1, opponentID);
			firePropertyChanged(e);
			break;
		case MSG_OPPONENT_MOVE:
			break;
		case MSG_RECEIVED_DAMAGE:
			break;
		case MSG_DONE_DAMAGE:
			break;
		case MSG_BATTLE_OVER:
			break;
		case MSG_REMATCH:
			break;
		default:
			break;
		}
	}
	
	public void addPropertyListener(PropertyChangeListener listener) {
		listenerList.add(listener);
	}
	
	public void firePropertyChanged(PropertyChangeEvent e) {
		for(PropertyChangeListener l : listenerList)
			l.propertyChange(e);
	}
	
	public void removeListener() {
		listenerList.remove(0);
	}
	public Pokemon getOurPokemon() {
		return ourPokemon;
	}
	public void setOurPokemon(Pokemon ourPokemon) {
		this.ourPokemon = ourPokemon;
	}
	public Pokemon getOpponentPokemon() {
		return opponentPokemon;
	}
	public void setOpponentPokemon(Pokemon opponentPokemon) {
		this.opponentPokemon = opponentPokemon;
	}
	
	/*public int getOpponentID() {
		return this.opponentID;
	}*/
	
}
