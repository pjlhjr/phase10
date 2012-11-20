/*
 * Difficulty Ranges:
 * drawOrPickUp(): 0-29, 30-69, 70-100
 */
package phase10.ai;

import java.util.ArrayList;
import java.util.Random;

import phase10.Hand;
import phase10.Phase10;
import phase10.PhaseGroup;
import phase10.Player;
import phase10.card.Card;
import phase10.card.WildCard;
import phase10.util.Configuration;

/**
 * @author Paul Harris
 * @since 10-4-2012
 *
 * This class is an AI computer opponent that can play phase 10.
 * 
 */
public class AIPlayer extends Player {
	private static final long serialVersionUID = 20121L;
	private int difficulty;
	private ArrayList<Integer> opponentCard;
	private ArrayList<Integer> discardedCards;
	
	public AIPlayer(Phase10 game, int difficulty, String name){
		super(name, game);
		this.game = game;
		this.difficulty = difficulty;
	}
	
	public static void main(String[] args){
	/*int tot = 0;
	for(int x = 0; x < 1000000; x++){
		if(bestChoice(10)){
			tot++;
		}
	}
	System.out.println((tot/1000000.0*100));*/
	}
	
	public void playTurn(){
		if(!(drawOrPickUp()^bestChoice(10))){
			game.getRound().drawFromDeck(this);
		}else{
			game.getRound().drawFromDiscard(this);
		}
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
		// TODO sense your immenant doom, idk if its part of 
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
	
	/*
	 * use it in a run if possible, but remember if a person has picked up a card that would be advantagious
	 * take difficulty into account, a less difficult player takes the first opportunity
	 */
	private boolean playOffPhases(){
		Player current;
		ArrayList<PhaseGroup> 	setGroups = new ArrayList<PhaseGroup>(),
								runGroups = new ArrayList<PhaseGroup>(),
								colorGroups = new ArrayList<PhaseGroup>();
		PhaseGroup temp;
		
		for(int player = 0; player < game.getNumberOfPlayers(); player++){
			current = game.getPlayer(player);
			if(current.hasLaidDownPhase()){
				for(int group = 0; group < current.getNumberOfPhaseGroups(); group++){
					temp = current.getPhaseGroup(group);
					if(temp.getType() == Configuration.RUN_PHASE){
						runGroups.add(temp);
					}else if(temp.getType() == Configuration.SET_PHASE){
						setGroups.add(temp);
					}else if(temp.getType() == Configuration.COLOR_PHASE){
						colorGroups.add(temp);
					}
				}
			}
		}
		
		
	}
	
	private void discardCards(){
		//use the log
	}
	
	private int currentPointValue(){
		Groups g = new Groups(getHand(), this);
		return g.getPointValue();
	}
	
	private int[] possiblePointValues(){
		int[] totalPointValues;
		if(!colorPhase()){
			totalPointValues = new int[Configuration.WILD_VALUE];
			for(int x = 0; x < Configuration.WILD_VALUE - 1; x++){		
				totalPointValues[x] = addedPointValue(new Card(x));
			}
			totalPointValues[Configuration.WILD_VALUE] = addedPointValue(new WildCard(Configuration.WILD_VALUE));
		}else{
			totalPointValues = new int[Configuration.COLORS.length + 1];
			for(int x = 0; x < Configuration.COLORS.length; x++){
				totalPointValues[x] = addedPointValue(new Card(Configuration.COLORS[x], 1));
			}
			totalPointValues[Configuration.COLORS.length] = addedPointValue(new WildCard(Configuration.WILD_VALUE));
		}
		return totalPointValues;
	}
	
	private int addedPointValue(Card c) {
		Groups tempGroup = new Groups(getHand(), c, this);
		return tempGroup.getPointValue();
	}

	public int getDifficulty(){
		return difficulty;
	}
	
	public void setDifficulty(int d){
		difficulty = d;
	}
	
	private boolean bestChoice(double shape){
		Random g = new Random(System.currentTimeMillis());
		final double c = 50.0;
		double 	a = (c-(2*shape))/5000.0,
				b = ((4*shape)-(3*c))/100.0,
				threshold = (a*difficulty*difficulty) + (b*difficulty) + c;
		if(g.nextInt(100)>threshold)
			return true;
		else
			return false;
	}
	
	private class Groups{
		private ArrayList<PhaseGroup> complete,
					partial,
					excess,
					connected,
					conflicting;
		private ArrayList<Card> single;
		private Card[] cardValues;
		private AIPlayer player;
		
		private Groups(AIPlayer p){
			player = p;
			complete = new ArrayList<PhaseGroup>();
			if(!p.colorPhase()){
				partial = new ArrayList<PhaseGroup>();
				excess = new ArrayList<PhaseGroup>();
				connected = new ArrayList<PhaseGroup>();
				conflicting = new ArrayList<PhaseGroup>();
				single = new ArrayList<Card>();
			}
		}
		
		public Groups(Hand h, AIPlayer p){
			this(p);
			cardValues = new Card[h.getNumberOfCards()];
			for(int x = 0; x < h.getNumberOfCards(); x++){
				cardValues[x] = h.getCard(x);
			}
			group();
		}
		
		public Groups(Hand h, Card c, AIPlayer p){
			this(p);
			cardValues = new Card[h.getNumberOfCards()+1];
			for(int x = 0; x < h.getNumberOfCards(); x++){
				cardValues[x] = h.getCard(x);
			}
			cardValues[h.getNumberOfCards()] = c;
			group();
		}
		
		public int getPointValue(){
			boolean[] cardCounted = new boolean[cardValues.length];
			int val = 0;
			
			for(int x = 0; x < cardCounted.length; x++){
				cardCounted[x] = false;
			}
			for(ArrayList<Card> c : complete){
				for(Card num : c){
					cardCounted[num.getValue()] = true;//TODO
				}
			}
			for(int x = 0; x < cardCounted.length; x++){
				if(!cardCounted[x]){
					val += cardValues[x].getPointValue();
				}
			}
			return val;
		}
		
		private void group(){
			int[] setsNeeded = player.setsNeeded();
			if(setsNeeded != null){
				for(int x = 1; x < cardValues.length && cardValues[x].getValue() < Configuration.WILD_VALUE; x++){
					if(cardValues[x].getValue() == cardValues[x-1].getValue()){
						PhaseGroup setGroup = new PhaseGroup();
						setGroup.addCard(cardValues[x-1]);
						while(x < cardValues.length && cardValues[x] == cardValues[x-1]){
							setGroup.addCard(cardValues[x++]);
						}
						if(PhaseGroup.validate(setGroup, Configuration.SET_PHASE, setsNeeded[0]))
							complete.add(setGroup);
						else
							partial.add(setGroup);
					}
				}
			}
			if(player.numLengthRun() > 0){
				for(int x = 1; x < cardValues.length && cardValues[x].getValue() < Configuration.WILD_VALUE; x++){
					if(cardValues[x].getValue() == 1 + cardValues[x-1].getValue()){
						PhaseGroup runGroup = new PhaseGroup();
						runGroup.addCard(cardValues[x-1]);//TODO look at this
						while(x < cardValues.length 
								&& (cardValues[x].getValue() == 1 + cardValues[x-1].getValue() || cardValues[x].getValue() == cardValues[x-1].getValue()) 
								&& cardValues[x].getValue() < Configuration.WILD_VALUE){
							if(cardValues[x].getValue() == 1 + cardValues[x-1].getValue())
								runGroup.addCard(cardValues[x]);
							x++;
						}
						if(PhaseGroup.validate(runGroup, Configuration.RUN_PHASE, player.numLengthRun()))
							complete.add(runGroup);
						else
							partial.add(runGroup);
					}
				}
			}
			if(player.colorPhase()){
				int color = 0;
				for(int x = 0; x < cardValues.length && cardValues[x].getValue() < Configuration.WILD_VALUE; x++){
					PhaseGroup colorGroup = new PhaseGroup();
					while(cardValues[x].getValue() == color && x < cardValues.length && cardValues[x].getValue() < Configuration.WILD_VALUE){
						colorGroup.addCard(cardValues[x++]);
					}
					complete.add(colorGroup);
					color++;
				}
			}
			
			//TODO deal with wilds
		}
		
		public int[] cardsForConnectedGroups(){
			
		}
		
		public PhaseGroup[] getCompletePhaseGroups(){
			
		}
	}
}

