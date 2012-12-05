package phase10.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import phase10.Hand;
import phase10.PhaseGroup;
import phase10.ai.AIPlayer;
import phase10.card.Card;
import phase10.util.Configuration;

class Groups{
	private ArrayList<Card> single;
	private ArrayList<Card>	cardConflict;
	private Card[] cards;
	private ArrayList<PhaseGroup> 	complete;
	private ArrayList<PhaseGroup>	partial;
	private ArrayList<ArrayList<PhaseGroup>>	conflictingGroups;
	private AIPlayer player;
	
	/**
	 * @param p the AIPlayer holding the hand
	 */
	private Groups(AIPlayer p){
		player = p;
		complete = new ArrayList<PhaseGroup>();
		if(!player.colorPhase()){
			partial = new ArrayList<PhaseGroup>();
			conflictingGroups = new ArrayList<ArrayList<PhaseGroup>>();
			single = new ArrayList<Card>();
			cardConflict = new ArrayList<Card>();
		}
	}

	/**
	 * @param p the AIPlayer holding the hand
	 * @param h the hand of the player
	 */
	Groups(AIPlayer p, Hand h){
		this(p);
		cards = new Card[h.getNumberOfCards()];
		for(int index = 0; index < h.getNumberOfCards(); index++){
			cards[index] = h.getCard(index);
		}
		group();
	}
	
	/**
	 * @param p the AIPlayer holding the hand
	 * @param h the hand the player is hold
	 * @param c a card to be added to the hand
	 */
	//TODO sort by Color?
	Groups(AIPlayer p, Hand h, Card c){
		this(p);
		cards = new Card[h.getNumberOfCards()+1];
		for(int index = 0; index < h.getNumberOfCards(); index++){
			cards[index] = h.getCard(index);
		}
		
		// insert the new card in sorted order
		int index = 0;
		Card 	lastCard = c,
				nextCard = c;
		while(cards[index] != null && cards[index].getValue() > c.getValue()){
			index++;
		}
		while(cards[index] != null){
			nextCard = cards[index];
			cards[index++] = lastCard;
			lastCard = nextCard;
		}
		cards[index] = lastCard;
		
		group();
	}
	
	
	/** 
	 * @param card the that you want to see if it's in a complete group
	 * @return true, if the card is in a complete group. false, if the card is not found
	 */
	public boolean cardInCompleteGroup(Card card) {
		for(PhaseGroup c: complete){
			for(int index = 0; index < c.getNumberOfCards(); index++){
				if(c.getCard(index) == card)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * @return returns the point value of the hand
	 */
	public int getPointValue(){
		boolean countIt;
		int val = 0;
		
		for(Card c: cards){
			countIt = true;
			
			counted:
			for(PhaseGroup g: complete){
				for(int groupIndex = 0; groupIndex < g.getNumberOfCards(); groupIndex++){
					if(c == g.getCard(groupIndex)){ // if the card is found in a complete PhaseGroup
						countIt = false; // it doesn't count to the total point value of the hand
						break counted; // stop looking
					}
				}
			}
			
			if(countIt){
				val += c.getPointValue();
			}
		}
		return val;
	}
	
	/**
	 * This method puts the cards into complete, partial, and single groups.
	 */
	private void group(){
		completeAndPartialGroup();
		dealWithWilds();
		singleAndConflictingGroup();
		resolveConflicts();
	}
	
	/* figure out some way, break this problem down,  
	nesseccary? no runs less than 4 means must be a partial group present
	
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
	}*/
	 
	/**
	 * This method finds conflicts where cards are in two different phase groups, 
	 * and adds them to conflicting (which is deal with in resolveConflicts).
	 * It also adds any cards not part of a group to single. 
	 */
	private void singleAndConflictingGroup(){
		ArrayList<ArrayList<PhaseGroup>> cardIsUsed = new ArrayList<ArrayList<PhaseGroup>>(); 
		Card tempCard;
		for(int num = 0; num < cards.length; num++){ 
			cardIsUsed.add(new ArrayList<PhaseGroup>());
		}
		
		// If the card is used in a PhaseGroup, 
		// add the PhaseGroup it is in to a slot in cardIsUsed at the same index of the card in cards.
		for(PhaseGroup g: complete){ 
			for(int groupIndex = 0; groupIndex < g.getNumberOfCards(); groupIndex++){
				tempCard = g.getCard(groupIndex);
				int cardIndex = 0;
				for(;cardIndex < cards.length; cardIndex++){
					if(tempCard == cards[cardIndex]){
						cardIsUsed.get(cardIndex).add(g);
					}
				}
			}
		}
		for(PhaseGroup g: partial){
			for(int groupIndex = 0; groupIndex < g.getNumberOfCards(); groupIndex++){
				tempCard = g.getCard(groupIndex);
				int cardIndex = 0;
				for(;cardIndex < cards.length; cardIndex++){
					if(tempCard == cards[cardIndex]){
						cardIsUsed.get(cardIndex).add(g);
					}
				}
			}
		}
		
		// if the card is in more than one PhaseGroup,
		// add the card to cardConflict and the PhaseGroups that hold the card to conflictingGroups
		for(int x = 0; x < cardIsUsed.size(); x++){
			if(cardIsUsed.get(x).size() > 1){
				cardConflict.add(cards[x]);
				ArrayList<PhaseGroup> tempList = new ArrayList<PhaseGroup>();
				for(PhaseGroup g: cardIsUsed.get(x))
					tempList.add(g);
				conflictingGroups.add(tempList);
			}
			else if(cardIsUsed.get(x).size() == 0){ // if it's not used in any groups, add to single
				single.add(cards[x]);
			}
		}
	}
	
	/**
	 * This method creates every possible complete and partial group, according to what turn it is on.
	 * A partial phaseGroup does not meet the length requirements of the phase, while a complete one does
	 */
	private void completeAndPartialGroup(){ 
		if(player.setsNeeded()[0] > 0){ // The hand will be sorted by value
			for(int index = 1; index < cards.length && cards[index].getValue() < Configuration.WILD_VALUE; index++){
				if(cards[index].getValue() == cards[index-1].getValue()){ 
					PhaseGroup setGroup = new PhaseGroup(player.getGame());
					setGroup.addCard(cards[index-1]);
					while(index < cards.length && cards[index].getValue() == cards[index-1].getValue()){ 
						setGroup.addCard(cards[index++]); //keep adding cards until the card is a different number than the previous
					} 
					setGroup.setType(Configuration.SET_PHASE);
					if(PhaseGroup.validate(setGroup, Configuration.SET_PHASE, player.setsNeeded()[0]))
						complete.add(setGroup);
					else
						partial.add(setGroup);
				}
			}
		}
		if(player.lengthOfRunNeeded() > 0){ // The hand will be sorted by value
			for(int index = 1; index < cards.length && cards[index].getValue() < Configuration.WILD_VALUE; index++){
				if(cards[index].getValue() == 1 + cards[index-1].getValue()){
					PhaseGroup runGroup = new PhaseGroup(player.getGame());
					runGroup.addCard(cards[index-1]);
					while(index < cards.length 
							&& (cards[index].getValue() == 1 + cards[index-1].getValue() || cards[index].getValue() == cards[index-1].getValue()) 
							&& cards[index].getValue() < Configuration.WILD_VALUE){
						if(cards[index].getValue() == 1 + cards[index-1].getValue()) //if it's the same as the card before, keep looking, but ignore it
							runGroup.addCard(cards[index]);  
						index++;
					}
					runGroup.setType(Configuration.RUN_PHASE);
					if(PhaseGroup.validate(runGroup, Configuration.RUN_PHASE, player.lengthOfRunNeeded()))
						complete.add(runGroup);
					else
						partial.add(runGroup);
				}
			}
		}
		if(player.colorPhase()){ // The hand will be sorted by color
			int color = 0; // make a group for each color
			for(int index = 0; index < cards.length && cards[index].getValue() < Configuration.WILD_VALUE; index++){
				PhaseGroup colorGroup = new PhaseGroup(player.getGame());
				while(cards[index].getValue() == color && index < cards.length && cards[index].getValue() < Configuration.WILD_VALUE){
					colorGroup.addCard(cards[index++]);
				}
				colorGroup.setType(Configuration.COLOR_PHASE);
				if(PhaseGroup.validate(colorGroup, Configuration.COLOR_PHASE, 7))
					complete.add(colorGroup);
				else
					partial.add(colorGroup);
				color++;
			}
		}
	}
	
	//TODO set wild value, does it work?? If the card is lost to another group, is it still a complete group?
	/**
	 * makes sure every card is only in one PhaseGroup.
	 */
	private void resolveConflicts(){
		// only deal with the first card, after done delete for conflicts and move to the new first card
		while(cardConflict.size() != 0){
			int maxPointValue = -1;
			PhaseGroup maxPhaseGroup = null;
			int tempPointValue;
			
			for(Iterator<PhaseGroup> iterator = conflictingGroups.get(0).iterator(); iterator.hasNext();){ // for each conflict:
				PhaseGroup p = iterator.next();
				p.removeCard(cardConflict.get(0));
				if(partial.contains(p)){  
					continue;
				}
				
				tempPointValue = 0; //point value if the card is removed from the hand
				 
				if(!((p.getType() == Configuration.SET_PHASE && PhaseGroup.validate(p, p.getType(), player.setsNeeded()[0])) || 
						(p.getType() == Configuration.RUN_PHASE && PhaseGroup.validate(p, p.getType(), player.lengthOfRunNeeded())) ||
						(p.getType() == Configuration.COLOR_PHASE && PhaseGroup.validate(p, p.getType(), 7)))){ // if it not still a complete group after removing the card
					for(int x = 0; x < p.getNumberOfCards(); x++){
						tempPointValue += p.getCard(x).getPointValue(); // get the point value of all the other cards
					}
				}
				if(tempPointValue > maxPointValue){ // keep track of the phase group that would have the highest point value without the card 
					maxPointValue = tempPointValue;
					maxPhaseGroup = p;
				}
			}
			if(maxPhaseGroup != null) // the group that would be the most costly after removing the card, gets to keep the card 
				maxPhaseGroup.addCard(cardConflict.get(0));
			cardConflict.remove(0); // move on to the next one
			conflictingGroups.remove(0);
		}
	}
	
	// TODO single if there are more than one wild
	private void dealWithWilds(){
		wildLoop:
		for(Card w: single){
			if(w.getValue() != Configuration.WILD_VALUE){
				continue wildLoop;
			}
			for(PhaseGroup p: partial){
				p.addCard(w);
				if((p.getType() == Configuration.SET_PHASE && PhaseGroup.validate(p, p.getType(), player.setsNeeded()[0])) || 
						(p.getType() == Configuration.RUN_PHASE && PhaseGroup.validate(p, p.getType(), player.lengthOfRunNeeded())) ||
						(p.getType() == Configuration.COLOR_PHASE && PhaseGroup.validate(p, p.getType(), 7))){
					complete.add(p);
					partial.remove(p);
					continue wildLoop;
				}
			}
			for(PhaseGroup c: complete){
				c.addCard(w);
			}
			
		}
	}
	
	//TODO fill in, this method used for finding which cards are needed, use difficulty
	private int[] cardsForConnectedGroups(){
		return new int[1]; // change name
	}
	
	//TODO fill in
	public PhaseGroup[] getCompletePhaseGroups(){
		if(complete.size() > 0){
			PhaseGroup[] temp = new PhaseGroup[complete.size()];
			int x = 0;
			for(PhaseGroup c: complete)
				temp[x++] = c;
			return temp;
		}else{
			return new PhaseGroup[1];
		}
	}
	
	//TODO figure out discard value.
	// on run phases with higher diff don't discard certain
	public Card[] recommendDiscard(){
		double[] discardValue = new double[cards.length];
		
		bigLoop:
		for(int x = 0; x < cards.length; x++){
			if(cards[x].getValue() == Configuration.WILD_VALUE){
				discardValue[x] = Double.MAX_VALUE * -1;
				continue bigLoop;
			}else if(cards[x].getValue() == Configuration.SKIP_VALUE){
				discardValue[x] = Double.MAX_VALUE;
				continue bigLoop;
			}else{
				discardValue[x] = cards[x].getPointValue() + cards[x].getValue()/100.0;
			}
			
			for(Card s: single){
				if(cards[x] == s){
					continue bigLoop;
				}
			}
			
			if(player.getDifficulty() > 70 && player.lengthOfRunNeeded() > 4){
				if(cards[x].getValue() == 6 || cards[x].getValue() == 7)
					continue;
				if((cards[x].getValue() == 5 || cards[x].getValue() == 8) && player.lengthOfRunNeeded() >= 8)
					continue;
				if((cards[x].getValue() == 4 || cards[x].getValue() == 9) && player.lengthOfRunNeeded() == 9)
					continue;
			}
			
			foundComplete:
			for(PhaseGroup c: complete){ // what about excess groups/conflicting?
				for(int completeIndex = 0; completeIndex < c.getNumberOfCards(); completeIndex++){
					if(c.getCard(completeIndex) == cards[x]){
						for(int restOfComplete = 0; restOfComplete < c.getNumberOfCards(); restOfComplete++){
							if(c.getCard(restOfComplete) != cards[x])
								discardValue[x] -= c.getCard(restOfComplete).getPointValue();
						}
						break foundComplete;
					}
				}
			}
			
			for(PhaseGroup p: partial){ // rethink this, cards in complete groups should be ranked ahead of complete groups
				for(int partialIndex = 0; partialIndex < p.getNumberOfCards(); partialIndex++){
					if(p.getCard(partialIndex) == cards[x]){
						for(int restOfPartial = 0; restOfPartial < p.getNumberOfCards(); restOfPartial++){
							if(p.getCard(restOfPartial) != cards[x]){
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
		Card[] sortedResults = Arrays.copyOf(cards, cards.length);
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
