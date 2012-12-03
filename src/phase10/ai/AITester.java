package phase10.ai;

import phase10.Phase10;

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
				ten.startGame();
				// get the winner
			}
		}
	}

}
