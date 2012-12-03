package phase10.ai;

import java.util.ArrayList;

import phase10.Phase10;
import phase10.Player;

public class AITester {
	int[][] p1WinPercent;
	public static void main(String[] args){
		new AITester();
	}
	
	public AITester() {
		 p1WinPercent = new int[101][101];
		Phase10 ten;
		 
		for(int p1Diff = 0; p1Diff <= 100; p1Diff++){
			for(int p2Diff = 0; p2Diff <= 100; p2Diff++){
				ten = new Phase10(null);
				ten.addPlayer(new AIPlayer(ten, p1Diff, "p1"));
				ten.addPlayer(new AIPlayer(ten, p2Diff, "p2"));
				ArrayList<Player> winners = ten.startGame();
				if(winners.get(0).getName().compareTo("p1") == 0)
					System.out.print("1 ");
				else
					System.out.print("0 ");
					//p1WinPercent[p1Diff][p2Diff] += 1;
			}
			System.out.println();
		}
	}
	
}
