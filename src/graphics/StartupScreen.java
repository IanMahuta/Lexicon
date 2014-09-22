package graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class StartupScreen extends JFrame implements ActionListener {
	JComboBox playersBox;
	JComboBox roundsBox;
	JComboBox wordsBox;
	JButton startButton;
	//JButton closeButton;
	public StartupScreen(){
		this.setLayout(new BorderLayout());
		JFrame selectFrame = new JFrame();
		Container content = this.getContentPane();
		selectFrame.setLayout(new GridLayout(3,2));
		Container innerContent = selectFrame.getContentPane();

		this.setTitle("Lexicon v 0.1");

		JTextArea title = new JTextArea("Welcome to Lexicon");
		title.setEditable(false);
		content.add(title, BorderLayout.NORTH);

		JPanel buttonsPane = new JPanel();
		startButton = new JButton("Start");
		buttonsPane.add(startButton);
		content.add(buttonsPane, BorderLayout.SOUTH);

		JTextArea playersText = new JTextArea("Numer of Players");
		playersText.setEditable(false);
		JTextArea roundsText = new JTextArea("Number of Rounds");
		roundsText.setEditable(false);
		JTextArea wordsText = new JTextArea("Words per Round");
		wordsText.setEditable(false);

		Integer[] nums = {2,3,4};
		playersBox = new JComboBox<Integer>(nums);
		playersBox.setSelectedIndex(2);
		Integer[] rounds = {1,3,5,10};
		roundsBox = new JComboBox<Integer>(rounds);
		roundsBox.setSelectedIndex(2);
		Integer[] words = {5,6,7,8,9,10};
		wordsBox = new JComboBox<Integer>(words);
		wordsBox.setSelectedIndex(2);

		innerContent.add(playersBox);
		innerContent.add(playersText);
		innerContent.add(roundsBox);
		innerContent.add(roundsText);
		innerContent.add(wordsBox);
		innerContent.add(wordsText);

		content.add(innerContent);

		this.setSize(275,400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		startButton.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(startButton)){
			this.setVisible(false);
			new Board((int)playersBox.getSelectedItem(),(int)roundsBox.getSelectedItem(),(int)wordsBox.getSelectedItem());
		}
	}
}
