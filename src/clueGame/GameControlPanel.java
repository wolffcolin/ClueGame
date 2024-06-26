package clueGame;

/*
Class: GameControlPanel
Description: GUI control panel for the human player
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GameControlPanel extends JPanel {
    private JTextField turnName;
    private JTextField roll;
    private JTextField guess;
    private JTextField guessResult;

    private JFrame frame;

    public GameControlPanel() {
        setLayout(new GridLayout(2, 0));
        JPanel panel = createTurnAndRollPanel();
        add(panel);
        panel = createGuessPanel();
        add(panel);
    }

    // creates the text fields for the name of whose turn it is, their roll, and two
    // buttons for making an accusation and passing on the turn to the next player
    private JPanel createTurnAndRollPanel() {

        Board board = Board.getInstance();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));

        JLabel nameLabel = new JLabel("Whose Turn?");
        turnName = new JTextField(20);
        turnName.setEditable(false);
        JPanel turnPanel = new JPanel();
        turnPanel.add(nameLabel);
        turnPanel.add(turnName);

        JLabel rollLabel = new JLabel("Roll");
        roll = new JTextField(2);
        roll.setEditable(false);
        JPanel rollPanel = new JPanel();
        rollPanel.add(rollLabel);
        rollPanel.add(roll);

        JButton makeAccusation = new JButton("Make Accusation");
        JButton nextPlayer = new JButton("Next Player");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(makeAccusation);
        buttonPanel.add(nextPlayer);

        makeAccusation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.getCurrentPlayer().isAHuman()) {
                    ArrayList<String> people = board.allCardStringsOfType(CardType.PERSON);
                    ArrayList<String> rooms = board.allCardStringsOfType(CardType.ROOM);
                    ArrayList<String> weapons = board.allCardStringsOfType(CardType.WEAPON);

                    AccusationDialog accuse = new AccusationDialog(frame, people, rooms, weapons);

                    accuse.pack();
                    accuse.setLocationRelativeTo(frame);
                    accuse.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Not your turn", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        nextPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.nextClicked();
            }
        });

        panel.add(turnPanel);
        panel.add(rollPanel);
        panel.add(buttonPanel);
        return panel;
    }

    // creates two text fields where the guess and the response for the guess are
    // displayed
    private JPanel createGuessPanel() {

        // initialize and set layout to guess panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        // initialize and set layout to guess panel
        JPanel guessPanel = new JPanel();
        guessPanel.setLayout(new GridLayout(1, 0));
        guess = new JTextField(20);
        guess.setEditable(false);
        guessPanel.add(guess);
        guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));

        // initialize and set layout to guess result panel
        JPanel guessResultPanel = new JPanel();
        guessResultPanel.setLayout(new GridLayout(1, 0));
        guessResult = new JTextField(20);
        guessResult.setEditable(false);
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

    public void getFrame(JFrame frame) {
        this.frame = frame;
    }
}
