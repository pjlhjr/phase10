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
	private final int difficulty;
	//private ArrayList<Integer> opponentCard;
	//private ArrayList<Integer> discardedCards;
	private Groups group;
	
	public AIPlayer(Phase10 game, int difficulty, String name){
		super(game, name);
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
	
	/**
	 * This method is called by the game manager for the AIPlayer to complete a turn.
	 * This method either draws or picks up a card from the top of the discard pile, 
	 * lays down the AIPlayers card, plays off other peoples' laid down phases, and discards.
	 */
	//TODO test difficulty levels
	public void playTurn(){
		/*if(!(drawOrPickUp()^bestChoice(10))){
			game.getRound().drawFromDeck(this);
		}else{
			game.getRound().drawFromDiscard(this);
		}
		if(!hasLaidDownPhase() && layDownPhase())
			addPhaseGroups(group.getCompletePhaseGroups());
		if(hasLaidDownPhase())
			playOffPhases();
		game.getRound().discard(this, discardCard());*/
		game.getRound().drawFromDeck();
		game.getRound().discard(getHand().getCard(0));
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
			need = new int[1];
			need[0] = 0;
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
	
	//TODO make hard player look at median, get rid of higher cards if on later turns
	private boolean drawOrPickUp(){//true to pick up, false to draw
		// TODO sense your immenant doom, idk if its part of, 
		// on long run phases some cards are needed no matter what, on difficulty > 70 draw those
		Card cardOnTopOfPile = game.getRound().getTopOfDiscardStack();
		if(addedPointValue(cardOnTopOfPile) < currentPointValue()){
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

	//TODO incorp diff
	private boolean layDownPhase(){
		group = new Groups(getHand(), this);
		if(colorPhase()){
			return PhaseGroup.validate(group.getCompletePhaseGroups()[0], Configuration.COLOR_PHASE, 7);
		}else{
			int sets = setsNeeded().length,
					run = numLengthRun()/(numLengthRun()+1);
			for(PhaseGroup g: group.getCompletePhaseGroups()){
				if(sets > 0 && PhaseGroup.validate(g, Configuration.SET_PHASE, setsNeeded()[setsNeeded().length-1]))
					sets--;
				if(run > 0 && PhaseGroup.validate(g, Configuration.RUN_PHASE, numLengthRun()))
					run--;
			}
			if(sets == 0 && run == 0)
				return true;
		}
		return false;
	}
	
	/*
	 * TODO finish basic algorithm, figure out how to incorporate difficulty
	 * use it in a run if possible, but remember if a person has picked up a card that would be advantagious
	 * take difficulty into account, a less difficult player takes the first opportunity,
	 * get rid of return statement
	 */
	//TODO make diff. at higher diff look further than first card
	private boolean playOffPhases(){
		Player current;
/*		ArrayList<PhaseGroup> 	setGroups = new ArrayList<PhaseGroup>(),
								runGroups = new ArrayList<PhaseGroup>(),
								colorGroups = new ArrayList<PhaseGroup>();
		PhaseGroup temp;*/
		
		for(int player = 0; player < game.getNumberOfPlayers(); player++){
			current = game.getPlayer(player);
			if(current.hasLaidDownPhase()){
				for(int group = 0; group < current.getNumberOfPhaseGroups(); group++){
					for(int hand = 0; hand < getHand().getNumberOfCards(); hand++){
						current.getPhaseGroup(group).addCard(getHand().getCard(hand));
					}// Wild cards, have to have a certain hiddenValue
					/*temp = current.getPhaseGroup(group);
					if(temp.getType() == Configuration.RUN_PHASE){
						runGroups.add(temp);
					}else if(temp.getType() == Configuration.SET_PHASE){
						setGroups.add(temp);
					}else if(temp.getType() == Configuration.COLOR_PHASE){
						colorGroups.add(temp);
					}*/
				}
			}
		}
		
		
	}
	
	//TODO use the log. incorporate difficulty. get rid of higher point value cards later in the round
	private Card discardCard(){
		group = new Groups(getHand(), this);
		int x = 0;
		Card[] c = group.recommendDiscard();
		while(!bestChoice(10)){
			//something else
			x++;
		}
		x %= c.length;
		return c[x];
	}
	
	private int currentPointValue(){
		group = new Groups(getHand(), this);
		return group.getPointValue();
	}
	
	private int[] possiblePointValues(){
		int[] totalPointValues;
		if(!colorPhase()){
			totalPointValues = new int[Configuration.WILD_VALUE];
			for(int x = 0; x < Configuration.WILD_VALUE - 1; x++){		
				totalPointValues[x] = addedPointValue(new Card(x));
			}
			totalPointValues[Configuration.WILD_VALUE - 1] = addedPointValue(new WildCard(Configuration.WILD_VALUE));
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
		group = new Groups(getHand(), c, this);
		return group.getPointValue();
	}

	public int getDifficulty(){
		return difficulty;
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
		private ArrayList<Card> single,
								conflictingCards;
		private Card[] cardValues;
		private AIPlayer player;
		private ArrayList<PhaseGroup> 	complete,
										partial;
		private ArrayList<ArrayList<PhaseGroup>>	connected,
													conflicting;
		
		private Groups(AIPlayer p){
			player = p;
			complete = new ArrayList<PhaseGroup>();
			if(!p.colorPhase()){
				partial = new ArrayList<PhaseGroup>();
				conflicting = new ArrayList<ArrayList<PhaseGroup>>();
				single = new ArrayList<Card>();
				conflictingCards = new ArrayList<Card>();
				if(numLengthRun() > 0);
					connected = new ArrayList<ArrayList<PhaseGroup>>();
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
		
		//TODO deal with something, I don't know what
		public int getPointValue(){
			boolean[] cardCounted = new boolean[cardValues.length];
			int val = 0;
			
			for(int x = 0; x < cardCounted.length; x++){
				cardCounted[x] = false;
			}
			for(ArrayList<Card> c : complete){
				for(Card num : c){
					cardCounted[num.getValue()] = true;//TODO just do it, I don't remember what it is
				}
			}
			for(int x = 0; x < cardCounted.length; x++){
				if(!cardCounted[x]){
					val += cardValues[x].getPointValue();
				}
			}
			return val;
		}
		
		//TODO deal with wilds
		private void group(){
			completeAndPartialGroup();
			if(!colorPhase()){
				singleAndConflictingGroup();
				if(numLengthRun() > 0)
					connectedGroup(); // only do at certain difficulties
			}
			//TODO deal with wilds
		}
		
		//TODO figure out some way, break this problem down, single cards
		private void connectedGroup(){
			for(PhaseGroup g: complete){
				if(g.getType() == Configuration.RUN_PHASE){
					for(PhaseGroup other: complete){
						if(other.getType() == Configuration.RUN_PHASE){
							if(g.getCard(0).getValue()-2==other.getCard(other.getNumberOfCards()-1).getValue()){
								ArrayList<PhaseGroup> tempList = new ArrayList<PhaseGroup>();
								for(ArrayList<PhaseGroup> c: connected)
									if(c.contains(other) && c.contains(g))
										continue;
								tempList.add(other);
								tempList.add(g);
								connected.add(tempList);
							}
						}
					}
					for(PhaseGroup other: partial){
						if(other.getType() == Configuration.RUN_PHASE){
							if(g.getCard(0).getValue()-2==other.getCard(other.getNumberOfCards()-1).getValue()){
								ArrayList<PhaseGroup> tempList = new ArrayList<PhaseGroup>();
								for(ArrayList<PhaseGroup> c: connected)
									if(c.contains(other) && c.contains(g))
										continue;
								tempList.add(other);
								tempList.add(g);
								connected.add(tempList);
							}
						}
					}
				}
			}
			for(PhaseGroup p: partial){
				if()
			}
			for(Card c: single){
				if(c instanceof WildCard){
					
				}else{
					
				}
			}
		}
		 
		private void singleAndConflictingGroup(){
			ArrayList<PhaseGroup>[] conflict = (ArrayList<PhaseGroup>[])new Object[cardValues.length];
			Card tempCard;
			for(int x = 0; x < conflict.length; x++){
				conflict[x] = new ArrayList<PhaseGroup>();
			}
			for(PhaseGroup g: complete){
				for(int x = 0; x < g.getNumberOfCards(); x++){
					tempCard = g.getCard(x);
					int y = 0;
					for(;y < cardValues.length; y++){
						if(tempCard == cardValues[y]){
							conflict[y].add(g);
							break;
						}
					}
				}
			}
			for(PhaseGroup g: partial){
				for(int x = 0; x < g.getNumberOfCards(); x++){
					tempCard = g.getCard(x);
					int y = 0;
					for(;y < cardValues.length; y++){
						if(tempCard == cardValues[y]){
							conflict[y].add(g);
							break;
						}
					}
				}
			}
			for(int x = 0; x < conflict.length; x++){
				if(conflict[x].size() > 1){
					conflictingCards.add(cardValues[x]);
					ArrayList<PhaseGroup> tempList = new ArrayList<PhaseGroup>();
					for(PhaseGroup g: conflict[x])
						tempList.add(g);
					conflicting.add(tempList);
				}
				else if(conflict[x].size() == 0){
					single.add(cardValues[x]);
				}
			}
		}
		
		private void completeAndPartialGroup(){
			if(player.setsNeeded()[0] != 0){
				for(int x = 1; x < cardValues.length && cardValues[x].getValue() < Configuration.WILD_VALUE; x++){
					if(cardValues[x].getValue() == cardValues[x-1].getValue()){
						PhaseGroup setGroup = new PhaseGroup();
						setGroup.addCard(cardValues[x-1]);
						while(x < cardValues.length && cardValues[x] == cardValues[x-1]){
							setGroup.addCard(cardValues[x++]);
						}
						setGroup.setType(Configuration.SET_PHASE);
						if(PhaseGroup.validate(setGroup, Configuration.SET_PHASE, player.setsNeeded()[0]))
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
						runGroup.setType(Configuration.RUN_PHASE);
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
					colorGroup.setType(Configuration.COLOR_PHASE);
					complete.add(colorGroup);
					color++;
				}
			}
		}
		
		//TODO fill in, this method used for finding which cards are needed, use difficulty
		private int[] cardsForConnectedGroups(){
			
		}
		
		//TODO fill in
		public PhaseGroup[] getCompletePhaseGroups(){
			
		}
		
		//TODO figure out discard value.
		// on run phases with higher diff don't discard certain
		public Card[] recommendDiscard(){
			double[] discardValue = new double[cardValues.length];
			for(int x = 0; x < cardValues.length; x++){
				discardValue[x] = cardValues[x].getPointValue()/10.0;
				if(difficulty > 70 && numLengthRun() > 4){
					if(cardValues[x].getValue() == 6 || cardValues[x].getValue() == 7)
						continue;
					if(cardValues[x].getValue() == 5 || cardValues[x].getValue() == 8 && numLengthRun() >= 8)
						continue;
					if(cardValues[x].getValue() == 4 || cardValues[x].getValue() == 9 && numLengthRun() == 9)
						continue;
				}
				
			}
			for(int x = 0; x < discardValue.length; x++){
				int lowestIndex = x;
				for(int y = x + 1; y < discardValue.length; y++){
					double temp;
					if(discardValue[lowestIndex] > discardValue[y]){
						temp = discardValue[lowestIndex];
						discardValue[lowestIndex] = discardValue[y];
						discardValue[y] = temp;
					}
				}
			}
		}
	}
}

