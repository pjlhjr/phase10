/*
 * Difficulty Ranges:
 * drawOrPickUp(): 0-29, 30-69, 70-100
 */
package phase10.ai;

import java.util.ArrayList;
import java.util.Arrays;
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
		System.out.println("AIPlayer difficulty: " + difficulty);
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
	/* TODO test difficulty levels
	* adjust difficulty levels based on how far ahead/behind
	* should I worry about what other players do next turn?
	* if an opposing player only has one card left, etc
	* other special cases
	* look for patterns in other player's style, if lower cards are being laid down, closer to the end
	* better if -34- than 3-5, for partial run (partial over connected)
	* make sure that things aren't being done that are for Rummy in general and not application specific
	* "safe" cards to discard
	* don't discard cards that will be immediately picked up and laid down
	*/
	public void playTurn(){
		try{
			if(!(drawOrPickUp()^bestChoice(10))){
				game.getRound().drawFromDeck();
			}else{
				if(!game.getRound().drawFromDiscard())
					game.getRound().drawFromDeck();
			}
		}catch(Exception e){
			System.out.println("AIPlayer, draw: " + e.toString());
			game.getRound().drawFromDeck();
		}
		try{
			if(!hasLaidDownPhase() && addPhaseGroups(group.getCompletePhaseGroups()))
				System.out.print("laid down a phase"); // make sure it works if too many cards or groups in group
		}catch(Exception e){
			System.out.println("AIPlayer, laydown: " + e.toString());
		}
		try{
			if(hasLaidDownPhase())
				playOffPhases();
		}catch(Exception e){
			System.out.println("AIPlayer, playoff: " + e.toString());
		}
		try{
			game.getRound().discard(discardCard());
		}catch(Exception e){
			game.getRound().discard(getHand().getCard(0));
			System.out.println("AIPlayer, playoff: " + e.toString());
		}
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
	
	//TODO make hard player look at median, get rid of higher cards if on later turns, easier player more likely to keep cards
	private boolean drawOrPickUp(){//true to pick up, false to draw
		// TODO sense your immenant doom, idk if its part of, 
		// on long run phases some cards are needed no matter what, on difficulty > 70 draw those
		Card cardOnTopOfPile = game.getRound().getTopOfDiscardStack();
		if(addedPointValue(cardOnTopOfPile) < currentPointValue()){
			// if i was put in a partial or complete group
			// think about making it current point value + 5, don't pick up skip, pick up wild
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

	//TODO incorp diff, only allow to lay down phase after a certain point, varing by difficulty
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
	// on easier difficultlies pace how often the AI lays down phases
	private boolean playOffPhases(){
		Player current;
		
		for(int player = 0; player < game.getNumberOfPlayers(); player++){
			current = game.getPlayer(player);
			if(current.hasLaidDownPhase()){
				for(int group = 0; group < current.getNumberOfPhaseGroups(); group++){
					for(int hand = 0; hand < getHand().getNumberOfCards(); hand++){
						if(bestChoice(10) && current.getPhaseGroup(group).addCard(getHand().getCard(hand))){
							playOffPhases();
							return true;
						}
					}// Wild cards, have to have a certain hiddenValue
				}
			}
		}
		
		return false;
	}
	
	//TODO use the log. incorporate difficulty. get rid of higher point value cards later in the round
	// help out player on occasion?
	// do a genetic algorithm selection based off score rank, etc
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
		private ArrayList<Card> single;
		private ArrayList<Card>	conflictingCards;
		private Card[] cardValues;
		private ArrayList<PhaseGroup> 	complete;
		private ArrayList<PhaseGroup>	partial;
		private ArrayList<ArrayList<PhaseGroup>>	connected;
		private ArrayList<ArrayList<PhaseGroup>>	conflicting;
		
		private Groups(AIPlayer p){
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
		
		//TODO keep the hand sorted
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
			boolean countIt;
			int val = 0;
			
			for(Card c: cardValues){
				countIt = true;
				
				counted:
				for(PhaseGroup g: complete){
					for(int x = 0; x < g.getNumberOfCards(); x++){
						if(c == g.getCard(x)){
							countIt = false;
							break counted;
						}
					}
				}
				
				if(countIt){
					val += c.getPointValue();
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
			ArrayList<ArrayList<PhaseGroup>> conflict = new ArrayList<ArrayList<PhaseGroup>>(); 
			Card tempCard;
			for(int x = 0; x < cardValues.length; x++){ // WHY??
				conflict.add(new ArrayList<PhaseGroup>());
			}
			for(PhaseGroup g: complete){ // only possible with both sets and phases
				for(int x = 0; x < g.getNumberOfCards(); x++){
					tempCard = g.getCard(x);
					int y = 0;
					for(;y < cardValues.length; y++){
						if(tempCard == cardValues[y]){
							conflict.get(y).add(g);
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
							conflict.get(y).add(g);
							break;
						}
					}
				}
			}
			for(int x = 0; x < conflict.size(); x++){
				if(conflict.get(x).size() > 1){
					conflictingCards.add(cardValues[x]);
					ArrayList<PhaseGroup> tempList = new ArrayList<PhaseGroup>();
					for(PhaseGroup g: conflict.get(x))
						tempList.add(g);
					conflicting.add(tempList);
				}
				else if(conflict.get(x).size() == 0){
					single.add(cardValues[x]);
				}
			}
		}
		
		private void completeAndPartialGroup(){
			if(setsNeeded()[0] != 0){
				for(int x = 1; x < cardValues.length && cardValues[x].getValue() < Configuration.WILD_VALUE; x++){
					if(cardValues[x].getValue() == cardValues[x-1].getValue()){
						PhaseGroup setGroup = new PhaseGroup(game);
						setGroup.addCard(cardValues[x-1]);
						while(x < cardValues.length && cardValues[x].getValue() == cardValues[x-1].getValue()){
							setGroup.addCard(cardValues[x++]);
						} 
						setGroup.setType(Configuration.SET_PHASE);
						if(PhaseGroup.validate(setGroup, Configuration.SET_PHASE, setsNeeded()[0]))
							complete.add(setGroup);
						else
							partial.add(setGroup);
					}
				}
			}
			if(numLengthRun() > 0){
				for(int x = 1; x < cardValues.length && cardValues[x].getValue() < Configuration.WILD_VALUE; x++){
					if(cardValues[x].getValue() == 1 + cardValues[x-1].getValue()){
						PhaseGroup runGroup = new PhaseGroup(game);
						runGroup.addCard(cardValues[x-1]);//TODO look at this
						while(x < cardValues.length 
								&& (cardValues[x].getValue() == 1 + cardValues[x-1].getValue() || cardValues[x].getValue() == cardValues[x-1].getValue()) 
								&& cardValues[x].getValue() < Configuration.WILD_VALUE){
							if(cardValues[x].getValue() == 1 + cardValues[x-1].getValue())
								runGroup.addCard(cardValues[x]);
							x++;
						}
						runGroup.setType(Configuration.RUN_PHASE);
						if(PhaseGroup.validate(runGroup, Configuration.RUN_PHASE, numLengthRun()))
							complete.add(runGroup);
						else
							partial.add(runGroup);
					}
				}
			}
			if(colorPhase()){
				int color = 0;
				for(int x = 0; x < cardValues.length && cardValues[x].getValue() < Configuration.WILD_VALUE; x++){
					PhaseGroup colorGroup = new PhaseGroup(game);
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
			PhaseGroup[] temp = new PhaseGroup[complete.size()];
			int x = 0;
			for(PhaseGroup c: complete)
				temp[x++] = c;
			return temp;
		}
		
		//TODO figure out discard value.
		// on run phases with higher diff don't discard certain
		public Card[] recommendDiscard(){
			double[] discardValue = new double[cardValues.length];
			
			bigLoop:
			for(int x = 0; x < cardValues.length; x++){
				if(cardValues[x].getValue() == Configuration.WILD_VALUE){
					discardValue[x] = Double.MAX_VALUE * -1;
					continue bigLoop;
				}else if(cardValues[x].getValue() == Configuration.SKIP_VALUE){
					discardValue[x] = Double.MAX_VALUE;
					continue bigLoop;
				}else{
					discardValue[x] = cardValues[x].getPointValue() + cardValues[x].getValue()/100.0;
				}
				
				for(Card s: single){
					if(cardValues[x] == s){
						continue bigLoop;
					}
				}
				
				if(difficulty > 70 && numLengthRun() > 4){
					if(cardValues[x].getValue() == 6 || cardValues[x].getValue() == 7)
						continue;
					if((cardValues[x].getValue() == 5 || cardValues[x].getValue() == 8) && numLengthRun() >= 8)
						continue;
					if((cardValues[x].getValue() == 4 || cardValues[x].getValue() == 9) && numLengthRun() == 9)
						continue;
				}
				
				foundComplete:
				for(PhaseGroup c: complete){ // what about excess groups/conflicting?
					for(int completeIndex = 0; completeIndex < c.getNumberOfCards(); completeIndex++){
						if(c.getCard(completeIndex) == cardValues[x]){
							for(int restOfComplete = 0; restOfComplete < c.getNumberOfCards(); restOfComplete++){
								if(c.getCard(restOfComplete) != cardValues[x])
									discardValue[x] -= c.getCard(restOfComplete).getPointValue();
							}
							break foundComplete;
						}
					}
				}
				
				for(PhaseGroup p: partial){ // rethink this, cards in complete groups should be ranked ahead of complete groups
					for(int partialIndex = 0; partialIndex < p.getNumberOfCards(); partialIndex++){
						if(p.getCard(partialIndex) == cardValues[x]){
							for(int restOfPartial = 0; restOfPartial < p.getNumberOfCards(); restOfPartial++){
								if(p.getCard(restOfPartial) != cardValues[x]){
									discardValue[x] -= Configuration.WILD_VALUE - p.getCard(restOfPartial).getPointValue();
								}
							}
							break;
						}
					}
				}
				//don't count things in connected groups
				//subtract for the point values of other cards in phase group (would be added if the card was taken out)
				//partial:
				// if not in a group add the point value
				// always discard skips, never wild
			}
			
			//insertion sort, sorting sortedResults according to the discardValue
			Card[] sortedResults = Arrays.copyOf(cardValues, cardValues.length);
			for(int x = 0; x < discardValue.length; x++){
				int highestIndex = x;
				double tempIndex;
				Card tempCard;
				for(int y = x + 1; y < discardValue.length; y++){
					if(discardValue[highestIndex] < discardValue[y])
						highestIndex = y;
				}
				tempIndex = discardValue[highestIndex];
				tempCard = sortedResults[highestIndex];
				discardValue[highestIndex] = discardValue[x];
				sortedResults[highestIndex] = sortedResults[x];
				discardValue[x] = tempIndex;
				sortedResults[x] = tempCard;
			}
			
			return sortedResults;
		} // keep cards that opponents are using in groups
	}
}