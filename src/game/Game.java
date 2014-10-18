package game;

import graphics.StartupScreen;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private int numPlayers;
	private int numRounds;
	private int numWords;
	private int currentRound = 1;
	private ArrayList<String> words;
	private Dictionary dict;
	
	public static void main(String[] args){
		new StartupScreen();
	}
	
	public Game(){
		this(4, 5, 7, "nouns.txt","verbs.txt","adjectives.txt","adverbs.txt");
	}
	public Game(int players, int rounds, int words, String nounName, String verbName, String adjName, String advName){
		numPlayers = players;
		numRounds = rounds;
		numWords = words;
		
		// generate Dictionary
		dict = new Dictionary(nounName, verbName, adjName, advName);
	}
	
	public List<String> nextRound(){
		if(currentRound <= numRounds){
			words = dict.getWords(numWords);
			// display words and play
			/*for(int i = 0; i< numWords; i++){
				System.out.println(words.get(i));
			}*/
			currentRound++;
		}else{
			words = null;
		}
		return words;
	}
}
