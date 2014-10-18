package graphics;

import game.Game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Board extends JFrame implements ActionListener{

	JButton undoButton;
	JButton submitButton;
	JButton[] wordButtons;
	JTextArea playerArea;
	JTextArea answerField;
	Game game;
	List<String> words;
	Stack<Integer> wordStack;
	Integer currentPlayer = 1;
	int numPlayers;
	ArrayList<String> currentAnswers;
	int[] currentScores;
	Scoring scoring;
	int numRounds;
	int currentRound = 0;
	ArrayList<String> linkingWords;
	JButton[] linkingButtons;

	public Board(int numPlayers, int numRounds, int numWords){
		this.setTitle("Lexicon v 0.2 Round"+currentRound);
		this.numPlayers = numPlayers;
		this.numRounds = numRounds;
		currentAnswers = new ArrayList<String>();
		currentScores = new int[numPlayers];
		for(int i = 0; i<numPlayers; i++){
			currentScores[i] = 0;
		}

		this.setLayout(new BorderLayout());
		JTextArea rulesArea = new JTextArea();
		rulesArea.setText("Below are listed "+numWords+" words, place the words in order by clicking on them to make the "+
				"funniest sentence or phrase that you can.");
		rulesArea.setEditable(false);

		JPanel buttonsPane = new JPanel();
		undoButton = new JButton("Undo");
		submitButton = new JButton("Submit");
		undoButton.addActionListener(this);
		submitButton.addActionListener(this);
		buttonsPane.add(undoButton);
		buttonsPane.add(submitButton);

		JFrame innerFrame = new JFrame();
		innerFrame.setLayout(new GridLayout(3,1));
		Container innerContent = innerFrame.getContentPane();

		JPanel wordsPane = new JPanel();
		wordButtons = new JButton[numWords];
		for(int i = 0; i<numWords; i++){
			wordButtons[i] = new JButton(""+i);
			wordsPane.add(wordButtons[i]);
			wordButtons[i].addActionListener(this);
		}

		JPanel answerPane = new JPanel();
		playerArea = new JTextArea("Player "+currentPlayer);
		playerArea.setEditable(false);
		answerField = new JTextArea("                                 ");
		answerField.setEditable(false);
		answerField.setSize(500,25);
		answerPane.add(playerArea);
		answerPane.add(answerField);

		JPanel linkingPane = new JPanel();
		linkingWords = new ArrayList<String>();
		linkingWords.add("the");
		linkingWords.add("a");
		linkingWords.add("an");
		linkingWords.add("it");
		linkingWords.add("and");
		linkingWords.add("or");
		linkingWords.add("but");
		linkingWords.add("for");
		linkingWords.add("so");
		linkingButtons = new JButton[linkingWords.size()];
		for(int i = 0; i < linkingWords.size(); i++){
			linkingButtons[i] = new JButton(linkingWords.get(i));
			linkingButtons[i].addActionListener(this);
			linkingPane.add(linkingButtons[i]);
		}
		innerContent.add(wordsPane);
		innerContent.add(answerPane);
		innerContent.add(linkingPane);

		Container content = this.getContentPane();

		content.add(rulesArea, BorderLayout.NORTH);
		content.add(buttonsPane, BorderLayout.SOUTH);
		content.add(innerContent);

		this.setVisible(true);
		this.setSize(700,300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		wordStack = new Stack<Integer>();

		game = new Game(numPlayers, numRounds, numWords, "nouns.txt","verbs.txt","adjectives.txt","adverbs.txt");
		processNextRound();
	}

	public void processNextRound(){
		currentRound++;
		this.setTitle("Lexicon v 0.1 Round "+currentRound);
		words = game.nextRound();
		if(words!=null){
			for(int i = 0; i<words.size(); i++){
				wordButtons[i].setText(words.get(i));
			}
			currentPlayer = 1;
			playerArea.setText("Player "+currentPlayer);
			currentAnswers.clear();
		}else{
			this.setVisible(false);
			new FinalScreen(numPlayers, numRounds, currentScores);
		}
	}

	public void processNextPlayer(){
		currentPlayer++;
		playerArea.setText("Player "+currentPlayer);
	}

	public void updateScores(int[] currentScores){
		this.currentScores = currentScores;
		this.setVisible(true);
		processNextRound();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(submitButton)){
			// go to scoring screen or next player
			wordStack.clear();
			for(int i = 0; i<wordButtons.length; i++){
				wordButtons[i].setEnabled(true);
			}
			currentAnswers.add(answerField.getText());
			answerField.setText("                                                        ");


			if(currentPlayer<numPlayers){
				processNextPlayer();
			}else{
				this.setVisible(false);

				scoring = new Scoring(numPlayers, currentAnswers, currentScores, this, currentRound);
			}
		}else if(e.getSource().equals(undoButton)){
			if(!wordStack.isEmpty()){
				int last = wordStack.pop();
				if(last>=0){
					wordButtons[last].setEnabled(true);
				}
				if(wordStack.isEmpty()){
					answerField.setText("                                                        ");
				}else{ // word from wordButtons
					String ans = answerField.getText();
					ans = ans.substring(0, ans.lastIndexOf(' '));
					answerField.setText(ans);
				}
			}
		}else{
			String sourceText = ((JButton) e.getSource()).getText();
			if(linkingWords.contains(sourceText)){
				if(wordStack.isEmpty()){
					answerField.setText("");
				}
				answerField.setText(answerField.getText()+" "+sourceText);
				wordStack.push(-1);
			}else{
				for(int i = 0; i<wordButtons.length; i++){
					if(sourceText.equals(wordButtons[i].getText())){
						if(wordStack.isEmpty()){
							answerField.setText("");
						}
						answerField.setText(answerField.getText()+" "+wordButtons[i].getText());
						wordStack.push(i);
						wordButtons[i].setEnabled(false);
					}
				}
			}
		}
	}
}
