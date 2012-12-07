/*
 * Difficulty Ranges:
 * drawOrPickUp(): 0-29, 30-69, 70-100
 * LosingAfterTwo.p10, Impressed.p10
 */
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
	private final int difficulty;
	private static final double BEST_CHOICE_AT_FIFTY = 5.5;
	transient private Groups group;
	
	public AIPlayer(Phase10 game, int difficulty, String name){
		super(game, name);
		this.difficulty = difficulty;
	}
	
	public static void main(String[] args){
		new AITester();
	}
	
	/**
	 * This method is called by the game manager for the AIPlayer to complete a turn.
	 * This method draws or picks up a card from the top of the discard pile, 
	 * lays down the AIPlayers card, plays off other peoples' laid down phases, and discards.
	 */
	/* TODO test difficulty levels
	* adjust difficulty levels based on how far ahead/behind
	* look for patterns in other player's style, if lower cards are being laid down, closer to the end
	* "safe" cards to discard
	* old lay down phase if past a certain point, based off difficulty
	* add difficulty stratification
	*/
	public void playTurn(){
		// all exceptions in this method are caught, because if this method throws an exception
		// the AIPlayer will not draw/discard a card and the program will freeze
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
			if(!hasLaidDownPhase() && getPhaseGroups() != null) // attempt to lay down phase groups
				addPhaseGroups(group.getCompletePhaseGroups()); // make sure to lay down on time & don't lay down before certain point varying by diff
				// make sure it works if too many cards or groups in group
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
			game.getRound().discard(discardCard()); // discarded
		}catch(Phase10Exception e){	
		}catch(Exception e){
			game.getRound().discard(getHand().getCard(0));
			System.out.println("AIPlayer, playoff: " + e.toString());
		}
	}
	
	/**
	 * @return an array with the sets that are needed on this phase.
	 * If there is only one set needed it will return an array length 1 with size of the set needed in [0]
	 * If there are two sets needed the array will be length two. The first element will be the bigger set needed.
	 * If there are no sets needed it will return null 
	 */
	//TODO use evan's array in config.
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
	// TODO  pick up card on run phase
	private boolean drawOrPickUp(){
		Card cardOnTopOfPile = game.getRound().getTopOfDiscardStack();
		if(hasLaidDownPhase() && difficulty > 30 && bestChoice(BEST_CHOICE_AT_FIFTY)){ // TODO partial groups, contains is WRONG
			for(Card c: cardsThatCanBeLaidDown()){
				if(c.getColor() == cardOnTopOfPile.getColor()){
					return true;
				}else if(c.getValue() == cardOnTopOfPile.getValue()){
					return true;
				}
			}
		}
		if(addedPointValue(cardOnTopOfPile) < currentPointValue()){ // if the card improves the score of the player's hand
			if(difficulty < 30) // don't continue checking if an easy player
				return true;
			
			int[] values = possiblePointValues();
			int avgValue = 0;
			for(int value : values)
				avgValue += value;
			avgValue /= values.length;
			
			if(values[cardOnTopOfPile.getValue()] < avgValue){ // if the card improves the score of the player's better than avg
				if (difficulty < 70) // don't continue checking if a medium player
					return true;
				
				group = new Groups(this, getHand(), cardOnTopOfPile);
				if(group.cardInCompleteGroup(cardOnTopOfPile)) // if the card would be put in a complete group
					return true;
			}
		}
		return false; // if any of the tests fail, draw from the deck
	}

	/*
	 * 
	 * incorp diff, only allow to lay down phase after a certain point, varing by difficulty
	 * @deprecated never used, unnessessary
	 * @return whether to laydown a phase or not
	private boolean layDownPhase(){ 
		group = new Groups(getHand());
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
	}*/
	
	
	/* * TODO finish basic algorithm, figure out how to incorporate difficulty
	 * use it in a run if possible, but remember if a person has picked up a card that would be advantagious
	 * take difficulty into account, a less difficult player takes the first opportunity,
	 * get rid of return statement
	 
	TODO make diff. at higher diff look further than first card
	 on easier difficultlies pace how often the AI lays down phases
	 Wild cards, have to have a certain hiddenValue
	 make it smarter
*/
	/**
	 * @return true if it played off other phases
	 */
	private boolean playOffPhases(){
		Player current;
		ArrayList<Card> potentialCards = cardsThatCanBeLaidDown();
		ArrayList<Card> cardsToBeLaidDown = new ArrayList<Card>();
		
		for(int x = 0; x < getHand().getNumberOfCards(); x++){
			for(Card c: potentialCards){
				if(c.getColor() == Color.BLACK){ 
					if(c.getValue() == getHand().getCard(x).getValue())
						cardsToBeLaidDown.add(getHand().getCard(x));
				}else if(c.getColor() == getHand().getCard(x).getColor()){
					cardsToBeLaidDown.add(getHand().getCard(x));
				}
			}
		}
		
		for(int player = 0; player < game.getNumberOfPlayers(); player++){
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
	
	/*TODO use the log. incorporate difficulty. get rid of higher point value cards later in the round
	help out player on occasion?
	do a genetic algorithm selection based off score rank, etc
	not part of a group that is unnecessary 
	get rid of higher cards if on later turns, easier player more likely to keep cards
	 */	
	/**
	 * @return the card that is recommended to discard
	 */
	private Card discardCard(){
		group = new Groups(this, getHand());
		int x = 0;
		Card[] c = group.recommendDiscard();
		ArrayList<Card> layDownAble = cardsThatCanBeLaidDown();
		
		while(!bestChoice(BEST_CHOICE_AT_FIFTY) && layDownAble.contains(c[x])){
			if(++x >= c.length){
				return c[0];
			}
		}
		
		return c[x];
	}
	
	
	
	/**
	 * @return gets the current point value of the hand
	 */
	private int currentPointValue(){
		group = new Groups(this, getHand());
		return group.getPointValue();
	}
	
	
	// TODO look to see if the card would be able to be laid down
	/**
	 * @return an array with the point value of the cards if they were added to the hand
	 * the value of an given card is given for the value of the card minus one
	 * wilds are stored in Configuration.WILD_VALUE - 1 (=12)
	 * For the color phase, the colors are stored in there index in Configuration.COLORS
	 */
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
				threshold = (a*difficulty*difficulty) + (b*difficulty) + c;
		if(g.nextDouble() * 100 >threshold)
			return true;
		else
			return false;
	}
	
	
	/**
	 * @return the best complete grouping of the cards in the AIPlayer's hand
	 */
	// TODO look at the length, only give the right types
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
	
	ArrayList<Card> cardsThatCanBeLaidDown(){
		ArrayList<Card> layDownAble = new ArrayList<Card>(); 
		layDownAble.add(new WildCard());
		for(int x = 0; x < getGame().getNumberOfPlayers(); x++){
			Player current = getGame().getPlayer(x);
			if(current.hasLaidDownPhase()){
				for(int y = 0; y < current.getNumberOfPhaseGroups(); y++){
					PhaseGroup temp = current.getPhaseGroup(y);
					if(temp.getType() == Configuration.RUN_PHASE){
						layDownAble.add(new Card(temp.getCard(0).getValue() - 1));
						layDownAble.add(new Card(temp.getCard(temp.getNumberOfCards()-1).getValue() + 1));
					}else if(temp.getType() == Configuration.SET_PHASE){
						layDownAble.add(new Card(temp.getCard(0).getValue()));
					}else{
						layDownAble.add(new Card(temp.getCard(0).getColor(), Configuration.WILD_VALUE));
					}
				}
			}
		}
		
		return layDownAble;
	}
}