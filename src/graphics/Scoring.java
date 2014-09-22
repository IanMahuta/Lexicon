package graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class Scoring extends JFrame implements ActionListener {
	int playerAns = 2;
	int currentPlayer = 1;
	JButton button;
	JTextArea idArea;
	JTextArea answerArea;
	int numPlayers;
	ArrayList<String> currentAnswers;
	ButtonGroup buttonGroup;
	JRadioButton[] buttons;
	JPanel buttonsPane;
	int[] currentScores;
	JTextArea instructionsArea;
	Board board;

	public Scoring(int numPlayers, List<String> currentAnswers, int[] currentScores, Board board, int currentRound){
		this.board = board;
		this.currentScores = currentScores;

		this.numPlayers = numPlayers;
		this.currentAnswers = (ArrayList<String>) currentAnswers;

		this.setTitle("Lexicon v 0.1 Round "+currentRound+" Scoring");
		this.setLayout(new BorderLayout());

		instructionsArea = new JTextArea("Player "+currentPlayer+", please score the other player's answers.");
		instructionsArea.setEditable(false);
		Container content = this.getContentPane();
		content.add(instructionsArea, BorderLayout.NORTH);

		JPanel buttonPane = new JPanel();
		if(numPlayers>=3){
			button = new JButton("Next");
		}else{
			button = new JButton("Submit");
		}
		button.addActionListener(this);
		buttonPane.add(button);
		content.add(buttonPane, BorderLayout.SOUTH);

		JFrame innerFrame = new JFrame();
		innerFrame.setLayout(new GridLayout(3,1));
		idArea = new JTextArea("Player "+playerAns+"'s answer:");
		idArea.setEditable(false);

		answerArea = new JTextArea(currentAnswers.get(playerAns-1));
		answerArea.setEditable(false);

		buttons = new JRadioButton[10];
		buttonGroup = new ButtonGroup();
		buttonsPane = new JPanel(new GridLayout(1,10));
		for(int i = 0; i<10; i++){
			buttons[i] = new JRadioButton(""+(i+1), false);
			buttonGroup.add(buttons[i]);
			buttonsPane.add(buttons[i]);
		}


		Container innerContent = innerFrame.getContentPane();
		innerContent.add(idArea);
		innerContent.add(answerArea);
		innerContent.add(buttonsPane);

		content.add(innerContent);

		this.setVisible(true);
		this.setSize(700,300);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int selectedScore = 0;
		for(int i = 0; i<buttons.length; i++){
			if(buttons[i].isSelected()){
				selectedScore = Integer.parseInt(buttons[i].getText());
			}
		}
		if(selectedScore>0){
			currentScores[playerAns-1]+=selectedScore;
			buttonGroup.clearSelection();
			if(button.getText().equals("Next")){
				// switch answer
				if(currentPlayer!=numPlayers){
					playerAns++;
					if(playerAns==currentPlayer){
						playerAns++;
					}
					if(playerAns==numPlayers){
						button.setText("Submit");
					}
				}else{
					if(playerAns!=currentPlayer-2){
						playerAns++;
					}else{
						playerAns++;
						button.setText("Submit");
					}
				}
				idArea.setText("Player "+playerAns+"'s answer:");
				answerArea.setText(currentAnswers.get(playerAns-1));
			}else{
				// switch player
				if(numPlayers>=3){
					button.setText("Next");
				}
				if(currentPlayer!=numPlayers){
					playerAns = 1;
					currentPlayer++;
					instructionsArea.setText("Player "+currentPlayer+", please score the other player's answers.");
					idArea.setText("Player "+playerAns+"'s answer:");
					answerArea.setText(currentAnswers.get(playerAns-1));
				}else{
					// end scoring
					board.updateScores(currentScores);
					this.setVisible(false);
				}
			}
		}
	}

}
