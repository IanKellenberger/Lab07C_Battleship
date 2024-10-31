import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final int length;
    private final List<Cell> positions;
    private int hits;

    public Ship(int length) {
        this.length = length;
        this.positions = new ArrayList<>();
        this.hits = 0;
    }

    public int getLength() {
        return length;
    }

    public List<Cell> getPositions() {
        return positions;
    }

    public void addPosition(Cell cell) {
        positions.add(cell);
    }

    public boolean isSunk() {
        return hits >= length;
    }

    public void registerHit() {
        hits++;
    }
}
