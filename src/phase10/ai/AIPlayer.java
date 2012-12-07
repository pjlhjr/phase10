package phase10.ai;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import phase10.Phase10;
import phase10.PhaseGroup;
import phase10.Player;
import phase10.card.Card;
import phase10.card.WildCard;
import phase10.exceptions.Phase10Exception;
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
	private final int difficulty; // Easy: 0-29, Medium: 30-69, Hard:70-100
	private static final double BEST_CHOICE_AT_FIFTY = 5.5; // The AI will make a bad move BEST_CHOICE_AT_FIFTY % of the time at difficulty of 50
	private ArrayList<Card> pickedUpCards; // The cards the nextPlayer has picked up from the discard pile this round
	private Card lastCardDiscarded; // The last card the AIPlayer discarded
	private int latestRound; // The round the player was on the last time playTurn() was called
	private Player nextPlayer; // the player that comes after the AIPlayer
	private String oldName; // a surprise :)
	transient private Groups group; // the most recent grouping

	/**
	 * @param game The Phase10 game the AIPlayer is playing
	 * @param difficulty The difficulty of AIPlayer, from 0-100. (Easy: 0-29, Medium: 30-69, Hard:70-100) 
	 * @param name The name of the AIPlayer
	 */
	public AIPlayer(Phase10 game, int difficulty, String name){
		super(game, name);
		this.difficulty = difficulty;
		pickedUpCards = new ArrayList<Card>();
		latestRound = game.getRoundNumber();
		oldName = name;
	}

	public static void main(String[] args){
		new AITester();
	}

	/**
	 * This method is called by the game manager for the AIPlayer to complete a turn.
	 * This method draws or picks up a card from the top of the discard pile, 
	 * lays down the AIPlayers card, plays off other peoples' laid down phases, and discards.
	 */
	public void playTurn(){
		// all exceptions in this method are caught, because if this method throws an exception
		// the AIPlayer will not draw/discard a card and the program will freeze
		try{
			updateLastCardDiscarded();
		}catch(Exception e){
			System.out.println("AIPlayer, lastCard: " + e.toString());
		}
		try{
			if(!(drawOrPickUp()^bestChoice(BEST_CHOICE_AT_FIFTY))){ // choose whether to draw from the deck or pick up from the stack
				if(!game.getRound().drawFromDiscard()) // If picking up from the discard is not possible,
					game.getRound().drawFromDeck(); // draw from the deck instead
			}else{
				game.getRound().drawFromDeck();
			}
		}catch(Exception e){
			System.out.println("AIPlayer, draw: " + e.toString());
			game.getRound().drawFromDeck(); // if something goes wrong, draw
		}

		try{
			group = new Groups(this, getHand());
			if(!hasLaidDownPhase() && group.getCompletePhaseGroups() != null && // if the AI player, hasn't laid down PhaseGroups
					group.getCompletePhaseGroups().length == Configuration.getNumberRequired(getPhase()) && // will lay down the right number of phasesGroups
					getGame().getRound().getTurnNumber() >= 8 - (difficulty/10)) // is past a certain turn number based off difficulty
				addPhaseGroups(group.getCompletePhaseGroups()); 
		}catch(Exception e){
			System.out.println("AIPlayer, laydown: " + e.toString()); // laid down all my cards Hand92,AI90afterAI74
		}

		try{
			if(hasLaidDownPhase())
				playOffPhases();
		}catch(Exception e){
			System.out.println("AIPlayer, playoff: " + e.toString());
		}

		try{
			lastCardDiscarded = discardCard();
			game.getRound().discard(lastCardDiscarded); // discard
		}catch(Phase10Exception e){	
		}catch(Exception e){
			lastCardDiscarded = getHand().getCard(0);
			game.getRound().discard(lastCardDiscarded);
			System.out.println("AIPlayer, discard: " + e.toString());
		}
	}

	/**
	 * @return an array with the sets that are needed on this phase.
	 * If there is only one set needed it will return an array length 1 with size of the set needed in [0]
	 * If there are two sets needed the array will be length two. The first element will be the bigger set needed.
	 * If there are no sets needed it will return null 
	 */
	int[] setsNeeded(){
		int numNeed = 0; // figure out the size of the array needed
		for(int x = 0; x < Configuration.getNumberRequired(getPhase()); x++){
			if(Configuration.getTypeRequired(getPhase(), x) == Configuration.SET_PHASE)
				numNeed++;
		}
		if(numNeed > 0){
			int[] need = new int[numNeed]; // fill the array with the size of the sets that are needed
			numNeed = 0;
			for(int x = 0; x < Configuration.getNumberRequired(getPhase()); x++){
				if(Configuration.getTypeRequired(getPhase(), x) == Configuration.SET_PHASE)
					need[numNeed++] = Configuration.getLengthRequired(getPhase(), x);
			}

			return need;
		}
		return null;
	}

	/**
	 * If the nextPlayer picked up the last discard, add it to pickedUpCards
	 */
	private void updateLastCardDiscarded(){
		int x = 0;
		while(this != game.getPlayer(x++)){} // find the nextPlayer
		x = x % game.getNumberOfPlayers();
		nextPlayer = game.getPlayer(x);
		
		if(latestRound != game.getRoundNumber()){ // if it's a new Round, reset
			pickedUpCards = new ArrayList<Card>();
			lastCardDiscarded = null;
			latestRound = game.getRoundNumber();
			
			for(int y = 0; y < game.getNumberOfPlayers(); y++){ // ?????
				if(game.getPlayer(y) != this && 
						(getScore() >= game.getPlayer(y).getScore() || getPhase() > game.getPlayer(y).getPhase())){//????
					setName(oldName);
					return;
				}
			}
			setName(oldName + "is#WINNING"); // ?????
		}else if(lastCardDiscarded != null && Configuration.getTypeRequired(nextPlayer.getPhase(), 0) == Configuration.SET_PHASE){
			if(nextPlayer.drewFromDiscard())
				pickedUpCards.add(lastCardDiscarded); // add the card to picked up card if it was picked up 
		}
	}

	/**
	 * @return the length of the run needed on this phase. Or 0 if there is no run needed.
	 */
	int lengthOfRunNeeded(){
		for(int x = 0; x < Configuration.getNumberRequired(getPhase()); x++){
			if(Configuration.getTypeRequired(getPhase(), x) == Configuration.RUN_PHASE)
				return Configuration.getLengthRequired(getPhase(), x);
		}
		return 0;
	}

	/**
	 * @return true if it is the color phase, or false if it is not 
	 */
	boolean colorPhase(){
		if(Configuration.getTypeRequired(getPhase(), 0) == Configuration.COLOR_PHASE)
			return true;
		return false;
	}

	/**
	 * @return true if it is recommended to pick up a card, 
	 * or false if it is recommended to draw a card 
	 */
	private boolean drawOrPickUp(){
		Card cardOnTopOfPile = game.getRound().getTopOfDiscardStack();
		
		if(hasLaidDownPhase() && difficulty > 30 && bestChoice(BEST_CHOICE_AT_FIFTY)){ // pick up cards that can be played off laid down PhaseGroups
			for(Card c: cardsThatCanBeLaidDown()){
				if(c.getColor() == cardOnTopOfPile.getColor()){
					return true;
				}else if(c.getValue() == cardOnTopOfPile.getValue()){
					return true;
				}
			}
		}
		
		if(difficulty > 30 && addedPointValue(cardOnTopOfPile) < currentPointValue()){ // if the card improves the score of the player's hand
			if(difficulty < 70) // don't continue checking if an easy player
				return true;

			group = new Groups(this, getHand(), cardOnTopOfPile);
			if(group.cardInCompleteGroup(cardOnTopOfPile)) // if the card would be put in a complete group
				return true;
		}
		return false; // if any of the tests fail, draw from the deck
	}

	/** 
	 * @return whether to laydown a phase or not
	 */
	@SuppressWarnings("unused")
	private boolean layDownPhase(){ 
		group = new Groups(this, getHand());
		if(colorPhase()){
			return PhaseGroup.validate(group.getCompletePhaseGroups()[0], Configuration.COLOR_PHASE, 7);
		}else{
			int sets = setsNeeded().length,
					run = lengthOfRunNeeded()/(lengthOfRunNeeded()+1);
			for(PhaseGroup g: group.getCompletePhaseGroups()){
				if(sets > 0 && PhaseGroup.validate(g, Configuration.SET_PHASE, setsNeeded()[setsNeeded().length-1]))
					sets--;
				if(run > 0 && PhaseGroup.validate(g, Configuration.RUN_PHASE, lengthOfRunNeeded()))
					run--;
			}
			if(sets == 0 && run == 0)
				return true;
		}
		return false;
	};

	/**
	 * @return true if it played off other phases
	 */
	private boolean playOffPhases(){
		Player current;
		ArrayList<Card> potentialCards = cardsThatCanBeLaidDown();
		ArrayList<Card> cardsToBeLaidDown = new ArrayList<Card>();

		for(int x = 0; x < getHand().getNumberOfCards(); x++){ // get the cards that need to be added to the phases
			for(Card c: potentialCards){
				if(c.getColor() == Color.BLACK){ 
					if(c.getValue() == getHand().getCard(x).getValue())
						cardsToBeLaidDown.add(getHand().getCard(x));
				}else if(c.getColor() == getHand().getCard(x).getColor()){
					cardsToBeLaidDown.add(getHand().getCard(x));
				}
			}
		}

		for(int player = 0; player < game.getNumberOfPlayers(); player++){ // add them to the phases
			current = game.getPlayer(player);
			if(current.hasLaidDownPhase()){
				for(int group = 0; group < current.getNumberOfPhaseGroups(); group++){
					for(Card c: cardsToBeLaidDown){
						if(bestChoice(BEST_CHOICE_AT_FIFTY) && current.getPhaseGroup(group).addCard(c)){
							playOffPhases();
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * @return the card that is recommended to discard
	 */
	private Card discardCard(){
		group = new Groups(this, getHand());
		int index = -1;
		Card[] discard = group.recommendDiscard();
		boolean search;

		do{
			search = true;
			index++;

			searching: // continue searching looks at the next card, until a good one is found
				while(search){
					if(index >= discard.length){ // if it has looked through every card
						return discard[0];
					}
					if(discard[index].getValue() == Configuration.SKIP_VALUE) // automatically return a skip
						return discard[index];
					if(difficulty > 70){ // don't lay down cards the opponent has picked up
						for(Card p: pickedUpCards){
							if(discard[index].getValue() == p.getValue()){
								index++;
								continue searching;
							}
						}
					}
					if(difficulty > 30 && nextPlayer.hasLaidDownPhase()){ // don't give an opponent something they can lay down
						for(Card l: cardsThatCanBeLaidDown()){
							if(discard[index].getValue() == l.getValue()){
								index++;
								continue searching;
							}
						}
					}
					if(difficulty > 30 && group.getCompletePhaseGroups() != null){ // don't lay down a card part of a completed phaseGroup
						for(PhaseGroup g: group.getCompletePhaseGroups()){
							for(int x = 0; x < g.getNumberOfCards(); x++){
								if(g.getCard(x) == discard[index]){
									index++;
									continue searching;
								}
							}
						}
					}
					search = false; // if the card has passed all these test, it can exit the search
				}
		}while(!bestChoice(BEST_CHOICE_AT_FIFTY));

		return discard[index];
	}

	/**
	 * @return gets the current point value of the hand
	 */
	private int currentPointValue(){
		group = new Groups(this, getHand());
		return group.getPointValue();
	}

	/**
	 * @return an array with the point value of the cards if they were added to the hand
	 * the value of an given card is given for the value of the card minus one
	 * wilds are stored in Configuration.WILD_VALUE - 1 (=12)
	 * For the color phase, the colors are stored in there index in Configuration.COLORS
	 */
	@SuppressWarnings("unused")
	private int[] possiblePointValues(){
		int[] totalPointValues;
		if(!colorPhase()){
			totalPointValues = new int[Configuration.WILD_VALUE];
			for(int index = 0; index < Configuration.WILD_VALUE - 1; index++){		
				totalPointValues[index] = addedPointValue(new Card(index));
			}
			totalPointValues[Configuration.WILD_VALUE - 1] = addedPointValue(new WildCard());
		}else{
			totalPointValues = new int[Configuration.COLORS.length + 1];
			for(int index = 0; index < Configuration.COLORS.length; index++){
				totalPointValues[index] = addedPointValue(new Card(Configuration.COLORS[index], 1));
			}
			totalPointValues[Configuration.COLORS.length] = addedPointValue(new WildCard());
		}
		return totalPointValues;
	}

	/**
	 * @param c a card to be added to a theoretical hand
	 * @return the point value of the current hand, plus the additional Card c
	 */
	private int addedPointValue(Card c) {
		group = new Groups(this, getHand(), c);
		return group.getPointValue();
	}

	/**
	 * @return the difficulty level of the player
	 */
	public int getDifficulty(){
		return difficulty;
	}

	/**
	 * @param shape the percentage of time best choice would return false at a difficulty of 50.
	 * Based off this value this method creates a second order curve, assuming a 50% error false rate
	 * at a difficulty of 0 and a 0% false rate at difficulty 100
	 * @return true if the AIPlayer should go with the generated optimal choice, 
	 * or false if the AIPlayer should go with a less than optimal choice
	 */
	private boolean bestChoice(double shape){
		Random g = new Random(System.currentTimeMillis());
		final double c = 50.0;
		double 	a = (c-(2*shape))/5000.0,
				b = ((4*shape)-(3*c))/100.0,
				threshold = (a*difficulty*difficulty) + (b*difficulty) + c; // make a point on a 2nd order curve
		if(g.nextDouble() * 100 >threshold)
			return true;
		else
			return false;
	}

	/**
	 * @return the best complete grouping of the cards in the AIPlayer's hand
	 */
	private PhaseGroup[] getPhaseGroups(){
		group = new Groups(this, getHand());
		return group.getCompletePhaseGroups();
	}

	/**
	 * @return the Phase10 object that contains this AIPlayer
	 */
	Phase10 getGame(){
		return game;
	}

	/**
	 * @return an ArrayList of all the type of the cards that can be laid down.
	 * If it's the number that's important the card will be black.
	 * If it's the color that's important the card will have a value of Configuration.WILD_VALUE
	 */
	ArrayList<Card> cardsThatCanBeLaidDown(){
		ArrayList<Card> layDownAble = new ArrayList<Card>(); 
		layDownAble.add(new WildCard()); // a wild can always be laid down
		for(int x = 0; x < getGame().getNumberOfPlayers(); x++){
			Player current = getGame().getPlayer(x);
			if(current.hasLaidDownPhase()){
				for(int y = 0; y < current.getNumberOfPhaseGroups(); y++){ // go through each person's phase groups who has laid down their phase
					PhaseGroup temp = current.getPhaseGroup(y);

					if(temp.getType() == Configuration.RUN_PHASE){ // for a run, add one less than the first card's value and one more than the last
						if(temp.getCard(0).getValue() == Configuration.WILD_VALUE) // look at the hidden value if its wild
							layDownAble.add(new Card(((WildCard)temp.getCard(0)).getHiddenValue() - 1));
						else
							layDownAble.add(new Card(temp.getCard(0).getValue() - 1));
						if(temp.getCard(temp.getNumberOfCards() -1).getValue() == Configuration.WILD_VALUE)
							layDownAble.add(new Card(((WildCard)temp.getCard(temp.getNumberOfCards() -1)).getHiddenValue() + 1));
						else
							layDownAble.add(new Card(temp.getCard(temp.getNumberOfCards()-1).getValue() + 1));
					}else if(temp.getType() == Configuration.SET_PHASE){ // for set, add a card equal to the card value
						if(temp.getCard(0).getValue() != Configuration.WILD_VALUE)
							layDownAble.add(new Card(temp.getCard(0).getValue()));
						else
							layDownAble.add(new Card(((WildCard)temp.getCard(0)).getHiddenValue()));
					}else{
						layDownAble.add(new Card(temp.getCard(0).getColor(), Configuration.WILD_VALUE));
					}
				}
			}
		}

		return layDownAble;
	}
}