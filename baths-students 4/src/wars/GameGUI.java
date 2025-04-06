package wars;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * Provide a GUI interface for the game
 * 
 * @author A.A.Marczyk
 * @version 20/02/12
 */
public class GameGUI {
    private BATHS gp = new SeaBattles("Fred");
    private JFrame myFrame = new JFrame("Game GUI");
    private Container contentPane = myFrame.getContentPane();
    private JTextArea listing = new JTextArea();
    private JLabel codeLabel = new JLabel();
    private JButton fightBtn = new JButton("Fight Encounter");
    private JButton viewBtn = new JButton("View State");
    private JButton clearBtn = new JButton("Clear");
    private JButton quitBtn = new JButton("Quit");
    private JPanel eastPanel = new JPanel();

    public GameGUI() {
        makeFrame();
        makeMenuBar(myFrame);
    }

    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame() {
        myFrame.setLayout(new BorderLayout());
        myFrame.add(listing, BorderLayout.CENTER);
        listing.setVisible(false);
        contentPane.add(eastPanel, BorderLayout.EAST);
        // set panel layout and add components
        eastPanel.setLayout(new GridLayout(4, 1));

        eastPanel.add(viewBtn);
        viewBtn.addActionListener(new ViewStateHandler());
        eastPanel.add(fightBtn);
        fightBtn.addActionListener(new FightHandler());
        eastPanel.add(clearBtn);
        clearBtn.addActionListener(new ClearHandler());
        eastPanel.add(quitBtn);
        quitBtn.addActionListener(new QuitHandler());

        clearBtn.setVisible(true);
        quitBtn.setVisible(true);
        // building is done - arrange the components and show        
        myFrame.pack();
        myFrame.setVisible(true);
    }

    /**
     * Create the main frame's menu bar.
     */
    private void makeMenuBar(JFrame frame) {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        // create the Ships menu
        JMenu shipMenu = new JMenu("Ships");
        menubar.add(shipMenu);

        JMenuItem listSquadronItem = new JMenuItem("List Squadron Ships");
        listSquadronItem.addActionListener(new ListSquadronHandler());
        shipMenu.add(listSquadronItem);

        JMenuItem viewShipItem = new JMenuItem("View a Ship");
        viewShipItem.addActionListener(new ViewShipHandler());
        shipMenu.add(viewShipItem);

        JMenuItem commissionShipItem = new JMenuItem("Commission a Ship");
        commissionShipItem.addActionListener(new CommissionShipHandler());
        shipMenu.add(commissionShipItem);

        JMenuItem decommission = new JMenuItem("Decommission Ship");
        decommission.addActionListener(new DecommissionHandler());
        shipMenu.add(decommission);
    }

    private class ListSquadronHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setVisible(true);
            String squadronDetails = gp.getSquadron();
            listing.setText(squadronDetails);
        }
    }

    private class ViewShipHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String shipName = JOptionPane.showInputDialog("Enter Ship Name:");
            String shipDetails = gp.getShipDetails(shipName);
            JOptionPane.showMessageDialog(myFrame, shipDetails);
        }
    }

    private class CommissionShipHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String shipName = JOptionPane.showInputDialog("Enter Ship Name to Commission:");
            String result = gp.commissionShip(shipName);
            JOptionPane.showMessageDialog(myFrame, result);
        }
    }

    private class DecommissionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String result = "";
            String inputValue = JOptionPane.showInputDialog("Ship code ?: ");

            if (gp.isInSquadron(inputValue)) 
            {
                gp.decommissionShip(inputValue);
                result = inputValue + " is decommissioned";
            } else 
            {
                result = inputValue + " not in fleet";
            }
            JOptionPane.showMessageDialog(myFrame, result);
        }
    }

    private class ViewStateHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String gameState = gp.toString();  // This will show the state of the game
            listing.setText(gameState);
            listing.setVisible(true);
        }
    }

    private class FightHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog("Enter Encounter number:");
            try {
                int encounterNumber = Integer.parseInt(input);
                String result = gp.fightEncounter(encounterNumber);
                JOptionPane.showMessageDialog(myFrame, result);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(myFrame, "Invalid encounter number.");
            }
        }
    }

    private class ClearHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setText("");
            listing.setVisible(false);
        }
    }

    private class QuitHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new GameGUI();
    }
}
