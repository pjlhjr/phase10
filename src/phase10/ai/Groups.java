package phase10.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import phase10.Hand;
import phase10.PhaseGroup;
import phase10.ai.AIPlayer;
import phase10.card.Card;
import phase10.card.CardColorComparator;
import phase10.card.CardValueComparator;
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
		partial = new ArrayList<PhaseGroup>();
		conflictingGroups = new ArrayList<ArrayList<PhaseGroup>>();
		single = new ArrayList<Card>();
		cardConflict = new ArrayList<Card>();
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
	Groups(AIPlayer p, Hand h, Card c){
		this(p);
		cards = new Card[h.getNumberOfCards()+1];
		for(int index = 0; index < h.getNumberOfCards(); index++){
			cards[index] = h.getCard(index);
		}
		cards[cards.length - 1] = c;
		
		if(player.colorPhase())
			Arrays.sort(cards, new CardColorComparator());
		else
			Arrays.sort(cards, new CardValueComparator());
		
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
		validateCompleteGroups();
		resolveConflicts();
		connectRuns();
		validateCompleteGroups();
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
	 
	private void connectRuns() { //TODO connect 3 runs/single cards
		if(player.lengthOfRunNeeded() > 0){
			for(PhaseGroup p1: partial){
				if(p1.getType() == Configuration.RUN_PHASE){
					for(PhaseGroup p2: partial){
						if(p2.getType() == Configuration.RUN_PHASE && p1 != p2){
							PhaseGroup temp = new PhaseGroup(player.getGame());
							for(int x = 0; x < p1.getNumberOfCards(); x++)
								temp.addCard(p1.getCard(x));
							for(int x = 0; x < p2.getNumberOfCards(); x++)
								temp.addCard(p2.getCard(x));
							if(PhaseGroup.validate(temp, Configuration.RUN_PHASE, player.lengthOfRunNeeded())){
								partial.remove(p1);
								partial.remove(p2);
								temp.setType(Configuration.RUN_PHASE);
								complete.add(temp);
								connectRuns();
								return;
							}	
						}
					}
				}
			}
		}
	}

	//TODO promote partials to complete on last sets, if you delete one with a wild
	private void validateCompleteGroups(){ 
		if(!player.colorPhase()){
			ArrayList<PhaseGroup> completeRuns = new ArrayList<PhaseGroup>();
			ArrayList<PhaseGroup> completeSets = new ArrayList<PhaseGroup>();
	
			for(PhaseGroup c: complete){
				if(c.getType() == Configuration.RUN_PHASE)
					completeRuns.add(c);
				else
					completeSets.add(c);
			}
			
			if(completeRuns.size() > 1){ 
				double 	highestValue = Double.MAX_VALUE * -1,
						tempValue = 0;
				PhaseGroup highestGroup = null;
				for(int x = 0; x < completeRuns.size(); x++){
					tempValue = 0;
					for(int y = 0; y < completeRuns.get(x).getNumberOfCards(); y++){
						tempValue += cardValue(completeRuns.get(x).getCard(y));
					} 
					
					// subtract a value off for any conflicts, because it will have somewhere else to go
					for(ArrayList<PhaseGroup> c: conflictingGroups){
						for(int y = 0; y < c.size(); y++){
							if(completeRuns.get(x) == c.get(y)){
								for(PhaseGroup g: c){
									if(g != completeRuns.get(x)){
										for(int a = 0; a < g.getNumberOfCards(); a++){
											tempValue -= cardValue(g.getCard(a));
										}
									}
								}
							}
						}
					}
					
					if(tempValue > highestValue){
						highestValue = tempValue;
						if(highestGroup != null)
							complete.remove(highestGroup);
						highestGroup = completeRuns.get(x);
					}else{
						complete.remove(completeRuns.get(x));
					}
				}
			}
			
			if(completeSets.size() > 0 && completeSets.size() != player.setsNeeded().length){//if too few, look to at partial on phases 9 & 10
				while(completeSets.size() > player.setsNeeded().length){
					int lowestSetNum = Configuration.WILD_VALUE;
					PhaseGroup lowestGroup = completeSets.get(0);
					for(PhaseGroup g: completeSets){
						if(lowestSetNum > g.getCard(0).getValue()){
							lowestSetNum = g.getCard(0).getValue();
							lowestGroup = g;
						}
					}
					complete.remove(lowestGroup);
					completeSets.remove(lowestGroup);
				}
				
				if(player.setsNeeded().length > 1 && completeSets.size() < player.setsNeeded().length){
					for(PhaseGroup p: partial){
						if(PhaseGroup.validate(p, Configuration.SET_PHASE, player.setsNeeded()[1])){
							complete.add(p);
							partial.remove(p);
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param c the card that you want the point value of
	 * @return the point value of the card + a "tie breaker" value, 1/100th of the face value
	 */
	private double cardValue(Card c){
		return c.getPointValue() + (c.getValue()/100.0); 
	}
	
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
						cardIsUsed.get(cardIndex).add(g);//break;
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
		if(player.setsNeeded() != null && player.setsNeeded()[0] > 0){ // The hand will be sorted by value
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
			for(int index = 0; index < cards.length && cards[index].getValue() < Configuration.WILD_VALUE; index++){
				PhaseGroup colorGroup = new PhaseGroup(player.getGame());
				colorGroup.addCard(cards[index++]);
				while(cards[index].getColor() == colorGroup.getCard(0).getColor() &&
						index < cards.length && cards[index].getValue() < Configuration.WILD_VALUE){
					colorGroup.addCard(cards[index++]);
				}
				colorGroup.setType(Configuration.COLOR_PHASE);
				if(PhaseGroup.validate(colorGroup, Configuration.COLOR_PHASE, 7))
					complete.add(colorGroup);
				else
					partial.add(colorGroup);
			}
		}
	}
	
	//TODO set wild value, does it work?? Can the single card of the same value be used instead?
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
				
				tempPointValue = 0; //point value if the card is removed from the hand
				if(!validGroup(p)){ // if it not still a complete group after removing the card
					for(int x = 0; x < p.getNumberOfCards(); x++){
						tempPointValue += p.getCard(x).getPointValue(); // get the point value of all the other cards
					}
				}//TODO continue if false?
				if(tempPointValue > maxPointValue){ // keep track of the phase group that would have the highest point value without the card 
					maxPointValue = tempPointValue;
					maxPhaseGroup = p;
				}
			}//TODO add to each if all were maxPhaseGroups
			if(maxPhaseGroup != null) // the group that would be the most costly after removing the card, gets to keep the card 
				maxPhaseGroup.addCard(cardConflict.get(0));
			
			for(PhaseGroup g: conflictingGroups.get(0)){ // make sure no partial groups are left in complete groups
				if(!validGroup(g) && !partial.contains(g)){
					complete.remove(g);
					partial.add(g);
				}
			}
			
			cardConflict.remove(0); // move on to the next one
			conflictingGroups.remove(0);
		}
	}
	
	// TODO make this right make sure there is not another phase that is already the length needed
	/**
	 * @param p The phase group that will be checked
	 * @return True if it is a complete phaseGroup in this player's phase. False if not.
	 */
	private boolean validGroup(PhaseGroup p){
		return ((p.getType() == Configuration.SET_PHASE && PhaseGroup.validate(p, p.getType(), player.setsNeeded()[0])) || 
				(p.getType() == Configuration.RUN_PHASE && PhaseGroup.validate(p, p.getType(), player.lengthOfRunNeeded())) ||
				(p.getType() == Configuration.COLOR_PHASE && PhaseGroup.validate(p, p.getType(), 7)));
	}
	
	// TODO single if there are more than one wild. connect runs with wilds
	/**
	 * This method adds wilds to every possible phase group.
	 * The conflicts created are later resolved.
	 */
	private void dealWithWilds(){
		wildLoop:
		for(Card w: cards){
			if(w.getValue() != Configuration.WILD_VALUE){
				continue wildLoop;
			}
			for(PhaseGroup c: complete){
				c.addCard(w);
			}
			for(int x = 0; x < partial.size(); x++){
				PhaseGroup p = partial.get(x);
				p.addCard(w);
				if(validGroup(p)){
					complete.add(p);
					partial.remove(p);
					x--;
				}
			}
		}
	}
	
	/**
	 * @return the complete phase groups, empty array size 1 if no complete groups
	 */
	public PhaseGroup[] getCompletePhaseGroups(){
		if(complete.size() > 0){ // transform from ArrayList to an array (toArray does work with generics)
			PhaseGroup[] temp = new PhaseGroup[complete.size()];
			int x = 0;
			for(PhaseGroup c: complete)
				temp[x++] = c;
			return temp;
		}else{
			return null;
		}
	}
	
	public Card[] recommendDiscard(){ // look at conflicts
		double[] discardValue = new double[cards.length];
		
		bigLoop: // this loop assigns a "discard value" to each card, the higher the value more likely it will be discarded
		for(int x = 0; x < cards.length; x++){
			if(cards[x].getValue() == Configuration.WILD_VALUE){ // don't discard wilds
				discardValue[x] = Double.MAX_VALUE * -1;
				continue bigLoop;
			}else if(cards[x].getValue() == Configuration.SKIP_VALUE){ // discard skips
				discardValue[x] = Double.MAX_VALUE;
				continue bigLoop;
			}else{ 
				discardValue[x] = cardValue(cards[x]);
			}
			
			// a hard player knows that on the 
			// TODO look for duplicates
			if(player.getDifficulty() > 70 && player.lengthOfRunNeeded() > 4){
				if((cards[x].getValue() == 6 || cards[x].getValue() == 7)||
				((cards[x].getValue() == 5 || cards[x].getValue() == 8) && player.lengthOfRunNeeded() >= 8) ||
				((cards[x].getValue() == 4 || cards[x].getValue() == 9) && player.lengthOfRunNeeded() == 9)){
					discardValue[x] -= cards[x].getPointValue();
				}	
			}			
			
			for(Card s: single){  // If the card is not in a group that's all you do for that card
				if(cards[x] == s){
					continue bigLoop;
				}
			}
			
			for(PhaseGroup c: complete){ // TODO what about excess groups
				for(int completeIndex = 0; completeIndex < c.getNumberOfCards(); completeIndex++){
					if(c.getCard(completeIndex) == cards[x]){
						for(int restOfComplete = 0; restOfComplete < c.getNumberOfCards(); restOfComplete++){
							discardValue[x] -= c.getCard(restOfComplete).getPointValue();
						}
						discardValue[x] *= 5;
						continue bigLoop;
					}
				}
			}
			
			for(PhaseGroup p: partial){ // rethink this, cards in complete groups should be ranked ahead of complete groups
				for(int partialIndex = 0; partialIndex < p.getNumberOfCards(); partialIndex++){
					if(p.getCard(partialIndex) == cards[x]){
						for(int restOfPartial = 0; restOfPartial < p.getNumberOfCards(); restOfPartial++){
							discardValue[x] -= Configuration.WILD_VALUE - p.getCard(restOfPartial).getPointValue();
						}
						continue bigLoop;
					}
				}
			}
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
	} // TODO keep cards that opponents are using in groups
}
