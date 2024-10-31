import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final Cell[][] cells;
    private final List<Ship> ships;

    public Board() {
        cells = new Cell[10][10];
        ships = new ArrayList<>();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                cells[row][col] = new Cell();
            }
        }
        placeShips();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void placeShips() {
        int[] shipSizes = {5, 4, 3, 3, 2}; // Sizes of the ships
        Random random = new Random();

        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                // Randomly choose horizontal or vertical
                boolean horizontal = random.nextBoolean();
                int row, col;

                if (horizontal) {
                    row = random.nextInt(10);
                    col = random.nextInt(10 - size); // Ensure space
                } else {
                    row = random.nextInt(10 - size); // Ensure space
                    col = random.nextInt(10);
                }

                // Check if the positions are blank
                boolean canPlace = true;
                for (int i = 0; i < size; i++) {
                    if (horizontal) {
                        if (cells[row][col + i].getState() != Cell.CellState.BLANK) {
                            canPlace = false;
                            break;
                        }
                    } else {
                        if (cells[row + i][col].getState() != Cell.CellState.BLANK) {
                            canPlace = false;
                            break;
                        }
                    }
                }

                // Place the ship if possible
                if (canPlace) {
                    Ship ship = new Ship(size);
                    for (int i = 0; i < size; i++) {
                        if (horizontal) {
                            cells[row][col + i].setState(Cell.CellState.BLANK); // Keep state for blank
                            ship.addPosition(cells[row][col + i]);
                        } else {
                            cells[row + i][col].setState(Cell.CellState.BLANK); // Keep state for blank
                            ship.addPosition(cells[row + i][col]);
                        }
                    }
                    ships.add(ship);
                    placed = true;
                }
            }
        }
    }
}
