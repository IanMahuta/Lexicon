package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dictionary {
	
	private FileReader nounReader;
	private FileReader verbReader;
	private FileReader adjReader;
	private FileReader advReader;
	private List<String> nouns;
	private List<String> verbs;
	private List<String> adjs;
	private List<String> advs;
	
	
	public Dictionary(String nounName, String verbName, String adjName, String advName){
		try {
			nounReader = new FileReader(nounName);
			verbReader = new FileReader(verbName);
			adjReader = new FileReader(adjName);
			advReader = new FileReader(advName);
			generateDict();
		} catch (FileNotFoundException e) {
			System.err.println("File not found, shutting down");
			e.printStackTrace();
		}
	}
	public Dictionary(){
		this("nouns.txt","verbs.txt","adjectives.txt","adverbs.txt");
	}
	
	private void generateDict(){
		nouns = new ArrayList<String>();
		verbs = new ArrayList<String>();
		adjs = new ArrayList<String>();
		advs = new ArrayList<String>();
		Scanner scan = new Scanner(nounReader);
		
		// get nouns
		while(scan.hasNextLine()){
			nouns.add(scan.nextLine());
		}
		scan.close();
		// get verbs
		scan = new Scanner(verbReader);
		while(scan.hasNextLine()){
			verbs.add(scan.nextLine());
		}
		scan.close();
		// get adjectives
		scan = new Scanner(adjReader);
		while(scan.hasNextLine()){
			adjs.add(scan.nextLine());
		}
		scan.close();
		// get adverbs
		scan = new Scanner(advReader);
		while(scan.hasNextLine()){
			advs.add(scan.nextLine());
		}
		scan.close();
	}
	
	public ArrayList<String> getWords(int numWords) {
		int numNoun = 2;
		int numVerb = 2;
		int numAdj = 0;
		int numAdv = 1;
		 // no adjectives pulled for default of 5 words, also used if unsupported value is selected
		if(numWords == 6){numNoun = 2; numVerb = 2; numAdj = 1; numAdv = 1;}
		else if(numWords == 7){numNoun = 2; numVerb = 3; numAdj = 1; numAdv = 1;}
		else if(numWords == 8){numNoun = 3; numVerb = 3; numAdj = 1; numAdv = 1;}
		else if(numWords == 9){numNoun = 3; numVerb = 3; numAdj = 1; numAdv = 2;}
		else if(numWords == 10){numNoun = 3; numVerb = 3; numAdj = 2; numAdv = 2;}
		ArrayList<String> words = new ArrayList<String>();
		int rand = 0;
		for(int i = 0; i<numNoun; i++){
			rand = (int) (Math.random()*nouns.size());
			words.add(nouns.get(rand));
		}
		for(int i = 0; i<numVerb; i++){
			rand = (int) (Math.random()*verbs.size());
			words.add(verbs.get(rand));
		}
		for(int i = 0; i<numAdj; i++){
			rand = (int) (Math.random()*adjs.size());
			words.add(adjs.get(rand));
		}
		for(int i = 0; i<numAdv; i++){
			rand = (int) (Math.random()*advs.size());
			words.add(advs.get(rand));
		}
		return words;
	}
}
