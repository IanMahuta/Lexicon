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
	
	private FileReader file;
	private List<String> dict;
	
	
	public Dictionary(String filename){
		try {
			file = new FileReader(filename);
			generateDict();
		} catch (FileNotFoundException e) {
			System.err.println("File not found, shutting down");
			e.printStackTrace();
		}
	}
	public Dictionary(){
		this("dictionary.txt");
	}
	
	private void generateDict(){
		dict = new ArrayList<String>();
		Scanner scan = new Scanner(file);
		while(scan.hasNextLine()){
			dict.add(scan.nextLine());
		}
		scan.close();
	}
	
	public List<String> getDict(){
		return dict;
	}
	public ArrayList<String> getWords(int numWords) {
		ArrayList<String> words = new ArrayList<String>();
		int rand = 0;
		for(int i = 0; i<numWords; i++){
			rand = (int) (Math.random()*dict.size());
			words.add(dict.get(rand));
		}
		return words;
	}
}
