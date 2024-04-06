package clueGame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;

public class GameControlPanel extends JPanel {
    private JTextField turnName;
    private JTextField roll;

    public GameControlPanel() {
        setLayout(new GridLayout(2, 0));
        JPanel panel = createTurnAndRollPanel();
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
    }
}
