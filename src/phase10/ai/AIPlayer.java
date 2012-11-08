/*
 * Difficulty Ranges:
 * drawOrPickUp(): 0-29, 30-69, 70-100
 */
package phase10.ai;

import java.util.ArrayList;
import java.util.Random;

import phase10.Card;
import phase10.Hand;
import phase10.Phase10;
import phase10.PhaseGroup;
import phase10.Player;

public class AIPlayer extends Player {
	private static final long serialVersionUID = 20121L;
	private int difficulty;
	private Phase10 game;
	private ArrayList<Integer> opponentCard;
	private ArrayList<Integer> discardedCards;
	
	public AIPlayer(Phase10 game, int difficulty, String name){
		super(name);
		this.game = game;
		this.difficulty = difficulty;
	}
	
	public void playTurn(){
		drawOrPickUp();
		if(!hasLaidDownPhase())
			layDownPhase();
		if(hasLaidDownPhase())
			playOffPhases();
		discardCards();
	}
	
	int[] setsNeeded(){
		int[] need;
		if(getPhase() == 1){
			need = new int[2];
			need[0] = 3;
			need[1] = 3;
		}
		else if(getPhase() == 2){
			need = new int[1];
			need[0] = 3;
		}
		else if(getPhase() == 3){
			need = new int[1];
			need[0] = 4;
		}
		else if(getPhase() == 7){
			need = new int[2];
			need[0] = 4;
			need[1] = 4;
		}
		else if(getPhase() == 9){
			need = new int[2];
			need[0] = 5;
			need[1] = 2;
		}
		else if(getPhase() == 10){
			need = new int[2];
			need[0] = 5;
			need[1] = 3;
		}
		else{
			need = null;
		}
		return need;
	}
	
	int numLengthRun(){
		if(getPhase() == 2 || getPhase() == 3)
			return 4;
		else if(getPhase() == 4)
			return 7;
		else if(getPhase() == 5)
			return 8;
		else if(getPhase() == 6)
			return 9;
		return 0;
	}
	
	boolean colorPhase(){
		if(getPhase() == 7)
			return true;
		return false;
	}
	
	private boolean drawOrPickUp(){//true to pick up, false to draw

		Card cardOnTopOfPile = game.getRound().getTopOfDiscardStack();
		if(addedPointValue(cardOnTopOfPile.getValue()) < currentPointValue()){
			if(difficulty < 30)
				return true;
			
			int[] values = possiblePointValues();
			int avgValue = 0;
			for(int value : values)
				avgValue += value;
			avgValue /= values.length;//possibly change this implementation later
			
			if(values[cardOnTopOfPile.getValue()] < avgValue){
				if (difficulty < 70)
					return true;
				
				int[] sortedValuesIndex = new int[values.length];
				for(int x = 1; x < values.length; x++){
					int y = 0;
					for(; y <= x; y++)
						if(values[sortedValuesIndex[x]] < values[sortedValuesIndex[y]])
							break;
					//TODO 
				}
			}
		}
		return false;
	}

	private boolean layDownPhase(){
		
	}
	
	private boolean playOffPhases(){
		
	}
	
	private void discardCards(){
		
	}
	
	private int currentPointValue(){
		Groups g = new Groups(getHand(), this);
		return g.getPointValue();
	}
	
	private int[] possiblePointValues(){
		int[] totalPointValues;
		if(!colorPhase()){
			totalPointValues = new int[14];
			for(int x = 0; x < 12; x++){		
				totalPointValues[x] = addedPointValue(x);
			}
			//TODO wild skip
		}else{
			totalPointValues = new int[6];
			for(int x = 0; x < 4; x++){
				totalPointValues[x] = addedPointValue(x);
			}
		}
		return totalPointValues;
	}
	
	private int addedPointValue(int c) {
		Groups tempGroup = new Groups(getHand(), c, this);
		return tempGroup.getPointValue();
	}

	public int getDifficulty(){
		return difficulty;
	}

	public void setDifficulty(int d){
		difficulty = d;
	}
	
	private static boolean bestChoice(double shape){
		Random g = new Random(System.currentTimeMillis());
		int difficulty = 50;
		final double c = 50.0;
		double 	a = (c-(2*shape))/5000.0,
				b = ((4*shape)-(3*c))/100.0,
				threshold = (a*difficulty*difficulty) + (b*difficulty) + c;
		if(g.nextInt(100)>threshold)
			return true;
		else
			return false;
	}

	public static void main(String[] args){
		bestChoice(0);
		bestChoice(50);
		bestChoice(75);
		bestChoice(100);
	}
	
	private class Groups{
		private ArrayList<ArrayList<Integer>> complete,
					partial,
					excess,
					connected,
					conflicting;
		private ArrayList<Integer> single;
		private int[] cardValues;
		private AIPlayer player;
		
		private Groups(AIPlayer p){
			player = p;
			complete = new ArrayList<ArrayList<Integer>>();
			if(!p.colorPhase()){
				partial = new ArrayList<ArrayList<Integer>>();
				excess = new ArrayList<ArrayList<Integer>>();
				connected = new ArrayList<ArrayList<Integer>>();
				conflicting = new ArrayList<ArrayList<Integer>>();
				single = new ArrayList<Integer>();
			}
		}
		
		public Groups(Hand h, AIPlayer p){
			this(p);
			cardValues = new int[h.getNumberOfCards()];
			for(int x = 0; x < h.getNumberOfCards(); x++){
				cardValues[x] = h.getCard(x).getValue();
			}
			group();
		}
		
		public Groups(Hand h, int c, AIPlayer p){
			this(p);
			cardValues = new int[h.getNumberOfCards()+1];
			for(int x = 0; x < h.getNumberOfCards(); x++){
				cardValues[x] = h.getCard(x).getValue();
			}
			cardValues[h.getNumberOfCards()] = c;
			group();
		}
		
		public int getPointValue(){}
		
		private void group(){
			int[] setsNeeded = player.setsNeeded();
			if(setsNeeded != null){
				for(int x = 1; x < cardValues.length && cardValues[x] < Card.WILD_VALUE; x++){
					if(cardValues[x] == cardValues[x-1]){
						ArrayList<Integer> setGroup = new ArrayList<Integer>();
						setGroup.add(x-1);
						while(x < cardValues.length && cardValues[x] == cardValues[x-1]){
							setGroup.add(x++);
						}
						if(setGroup.size() >= setsNeeded[setsNeeded.length])
							complete.add(setGroup);
						else
							partial.add(setGroup);
					}
				}
			}
			if(player.numLengthRun() > 0){
				for(int x = 1; x < cardValues.length && cardValues[x] < Card.WILD_VALUE; x++){
					if(cardValues[x] == 1 + cardValues[x-1]){
						ArrayList<Integer> runGroup = new ArrayList<Integer>();
						runGroup.add(x-1);
						while(x < cardValues.length 
								&& (cardValues[x] == 1 + cardValues[x-1] || cardValues[x] == cardValues[x-1]) 
								&& cardValues[x] < Card.WILD_VALUE){
							if(cardValues[x] == 1 + cardValues[x-1])
								runGroup.add(x);
							x++;
						}
						if(runGroup.size() >= player.numLengthRun())
							complete.add(runGroup);
						else
							partial.add(runGroup);
					}
				}
			}
			if(player.colorPhase()){
				int color = 0;
				for(int x = 0; x < cardValues.length && cardValues[x] < Card.WILD_VALUE; x++){
					ArrayList<Integer> colorGroup = new ArrayList<Integer>();
					while(cardValues[x] == color && x < cardValues.length && cardValues[x] < Card.WILD_VALUE){
						colorGroup.add(x++);
					}
					complete.add(colorGroup);
					color++;
				}
			}
			
			
		}
		
		public int[] cardsForConnectedGroups(){
			
		}
		
		public PhaseGroup[] getCompletePhaseGroups(){
			
		}
	}
}

