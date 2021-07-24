package application;

import java.util.concurrent.ThreadLocalRandom;

public class Game {

	private int answer;	
	private int tries;
	private boolean isWinner = false;

	public Game() {
		answer = ThreadLocalRandom.current().nextInt(1, 101);
		tries = 0;
		System.out.println("game created.  answer is " + answer);
	}
	
	public boolean tryGuess(int inGuess) {
		tries++;
		if (inGuess == answer)
		{
			isWinner = true;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getTries() {
		return tries;
	}
	
	public String getClue(int guess) {
		StringBuilder output = new StringBuilder();
		int difference = guess - answer;
		if (answer > guess) {
			output.append("The number is greater, and you are ");
		}
		if (answer < guess) {
			output.append("The number is less, and you are ");
		}
		if (Math.abs(difference) <= 10) {
			output.append("very close!");
		}		
		else if (Math.abs(difference) <= 25) {
			output.append("kinda close!");
		}
		else {
			output.append("not even close!");
		}
		return output.toString();
	}
	
	public boolean getWinner ()
	{
		return isWinner;
	}

}
