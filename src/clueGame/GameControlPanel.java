package clueGame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

public class GameControlPanel extends JPanel {
    private JTextField turnName;
    private JTextField roll;
    private JTextField guess;
    private JTextField guessResult;

    public GameControlPanel() {
        setLayout(new GridLayout(2, 0));
        JPanel panel = createTurnAndRollPanel();
        add(panel);
        panel = createGuessPanel();
        add(panel);
    }

    private JPanel createTurnAndRollPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));

        JLabel nameLabel = new JLabel("Whose Turn?");
        turnName = new JTextField(20);
        JPanel turnPanel = new JPanel();
        turnPanel.add(nameLabel);
        turnPanel.add(turnName);

        JLabel rollLabel = new JLabel("Roll");
        roll = new JTextField(2);
        JPanel rollPanel = new JPanel();
        rollPanel.add(rollLabel);
        rollPanel.add(roll);

        JButton makeAccusation = new JButton("Make Accusation");
        JButton nextPlayer = new JButton("Next Player");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(makeAccusation);
        buttonPanel.add(nextPlayer);

        panel.add(turnPanel);
        panel.add(rollPanel);
        panel.add(buttonPanel);
        return panel;
    }

    private JPanel createGuessPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        JPanel guessPanel = new JPanel();
        guessPanel.setLayout(new GridLayout(1, 0));
        guess = new JTextField(20);
        guessPanel.add(guess);
        guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));

        JPanel guessResultPanel = new JPanel();
        guessResultPanel.setLayout(new GridLayout(1, 0));
        guessResult = new JTextField(20);
        guessResultPanel.add(guessResult);
        guessResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));

        panel.add(guessPanel);
        panel.add(guessResultPanel);
        return panel;
    }

    public static void main(String[] args) {
        GameControlPanel panel = new GameControlPanel(); // create the panel
        JFrame frame = new JFrame(); // create the frame
        frame.setContentPane(panel); // put the panel in the frame
        frame.setSize(750, 180); // size the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
        frame.setTitle("Control Panel");
        frame.setVisible(true); // make it visible

        // testing data
        panel.setTurnName(new ComputerPlayer("The Killer", Color.RED));
        panel.setRoll("5");
        panel.setGuess("I dont have a guess");
        panel.setGuessResult("Why did you make a guess with nothing??");
    }

    public void setTurnName(Player player) {
        String name = player.getName();
        turnName.setText(name);
    }

    public void setRoll(String rollNum) {
        roll.setText(rollNum);
    }

    public void setGuess(String guessText) {
        guess.setText(guessText);
    }

    public void setGuessResult(String guessResultText) {
        guessResult.setText(guessResultText);
    }
}
