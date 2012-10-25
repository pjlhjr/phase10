/**
 * CS2003 Lab Project Fall 2012
 * Paul Harris, Matt Hruz, Evan Forbes
 * 
 */
package phase10;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class contains and manages the information for each Phase 10 game
 * 
 * @author Evan Forbes
 */
public class Phase10 implements Serializable{
	
	private static final long serialVersionUID = 20121L;
	
	ArrayList<Player> players;
	Round round;
	int roundNumber;
	int dealer;

	Phase10() {
		players = new ArrayList<Player>();
		dealer = -1;
		roundNumber = 0;
	}
	
	public Round getRound(){
		return round;
	}
	
	public int getRoundNumber(){
		return roundNumber;
	}
	
	public int getNumberOfPlayers() {
		return players.size();
	}
	
	public Player getPlayer(int index) {
		return players.get(index);
	}
	
	void addPlayer(Player p) {
		players.add(p);
	}
	
	public int getDealer() {
		return dealer;
	}
	
	public void startGame() {
		nextRound();
	}
	
	void nextRound() {
		roundNumber++;
		nextDealer();
		round = new Round(this);
		//TODO Call Gui- say new round has started
	}
	
	private void nextDealer()
	{
		dealer++;
		if (dealer>=getNumberOfPlayers())
		{
			dealer=0;
		}		
	}

}
