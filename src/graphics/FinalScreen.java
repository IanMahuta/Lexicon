package graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FinalScreen extends JFrame {
	public FinalScreen(int numPlayers, int numRounds, int[] finalScores){
		this.setTitle("Lexicon v 0.1 Final Scores");
		JTextArea finalHeader = new JTextArea("The final scores after "+numRounds+" rounds are:");
		finalHeader.setEditable(false);
		
		JPanel resultsPane = new JPanel(new GridLayout(numPlayers, 2));
		JTextArea[] names = new JTextArea[numPlayers];
		JTextArea[] scores = new JTextArea[numPlayers];
		for(int i = 0; i< numPlayers; i++){
			names[i] = new JTextArea("Player "+(i+1));
			names[i].setEditable(false);
			
			scores[i] = new JTextArea(""+finalScores[i]+" points");
			scores[i].setEditable(false);
			
			resultsPane.add(names[i]);
			resultsPane.add(scores[i]);
		}
		
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(finalHeader, BorderLayout.NORTH);
		content.add(resultsPane);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(300,400);
	}
}
