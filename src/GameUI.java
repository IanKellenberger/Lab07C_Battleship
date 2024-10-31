import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI {
    private final JFrame frame;
    private final JButton[][] buttons;
    private final JLabel missCounterLabel;
    private final JLabel strikeCounterLabel;
    private final JLabel totalMissCounterLabel;
    private final JLabel totalHitCounterLabel;
    private final Board board;
    private final Player player;
    private int totalHits = 0;
    private int totalMisses = 0;
    private int missCounter = 0;
    private int strikeCounter = 0;

    public GameUI() {
        frame = new JFrame("Battleship Game");
        buttons = new JButton[10][10];
        board = new Board();
        player = new Player();
        missCounterLabel = new JLabel("Misses: 0");
        strikeCounterLabel = new JLabel("Strike: 0");
        totalMissCounterLabel = new JLabel("Total Misses: 0");
        totalHitCounterLabel = new JLabel("Total Hits: 0");

        setupUI();
    }

    private void setupUI() {
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(10, 10));

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                buttons[row][col] = new JButton("~"); // Light blue wave character
                buttons[row][col].setBackground(Color.CYAN);
                final int r = row;
                final int c = col;
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleCellClick(r, c);
                    }
                });
                panel.add(buttons[row][col]);
            }
        }

        frame.add(panel, BorderLayout.CENTER);
        JPanel statusPanel = new JPanel();
        statusPanel.add(missCounterLabel);
        statusPanel.add(strikeCounterLabel);
        statusPanel.add(totalMissCounterLabel);
        statusPanel.add(totalHitCounterLabel);
        frame.add(statusPanel, BorderLayout.SOUTH);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> resetGame());
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);
        frame.add(buttonPanel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    private void handleCellClick(int row, int col) {
        Cell cell = board.getCells()[row][col];

        if (cell.getState() == Cell.CellState.BLANK) {
            boolean hit = isHit(row, col);
            if (hit) {
                cell.setState(Cell.CellState.HIT);
                buttons[row][col].setText("X"); // Hit representation
                totalHits++;
                player.addHit();
                missCounter = 0;
                totalHitCounterLabel.setText("Total Hits: " + totalHits);
                checkGameStatus(); // Check if a ship is sunk
            } else {
                cell.setState(Cell.CellState.MISS);
                buttons[row][col].setText("M"); // Miss representation
                totalMisses++;
                player.addMiss();
                missCounter++;
                totalMissCounterLabel.setText("Total Misses: " + totalMisses);
                missCounterLabel.setText("Misses: " + missCounter);
                checkStrikeStatus(); // Check strike status
            }
            buttons[row][col].setEnabled(false); // Disable button after click
        }
    }

    private boolean isHit(int row, int col) {
        for (Ship ship : board.getShips()) {
            if (ship.getPositions().contains(board.getCells()[row][col])) {
                ship.registerHit();
                return true; // Hit detected
            }
        }
        return false; // No hit detected
    }

    private void checkGameStatus() {
        for (Ship ship : board.getShips()) {
            if (!ship.isSunk()) {
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "You won! Play again?");
        resetGame();
    }

    private void checkStrikeStatus() {
        if (missCounter >= 5) {
            strikeCounter++;
            strikeCounterLabel.setText("Strike: " + strikeCounter);
            missCounter = 0;

            if (strikeCounter >= 3) {
                JOptionPane.showMessageDialog(frame, "You lost! Play again?");
                resetGame();
            }
        }
    }

    private void resetGame() {
        player.reset();
        totalHits = 0;
        totalMisses = 0;
        missCounter = 0;
        strikeCounter = 0;
        totalHitCounterLabel.setText("Total Hits: 0");
        totalMissCounterLabel.setText("Total Misses: 0");
        missCounterLabel.setText("Misses: 0");
        strikeCounterLabel.setText("Strike: 0");

        // Reset board
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                buttons[row][col].setText("~"); // Light blue wave character
                buttons[row][col].setEnabled(true); // Re-enable buttons
                board.getCells()[row][col].setState(Cell.CellState.BLANK); // Reset state to blank
            }
        }

        board.placeShips(); // Place ships randomly again
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameUI::new);
    }
}
