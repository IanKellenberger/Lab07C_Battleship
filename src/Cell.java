public class Cell {
    public enum CellState {
        BLANK, HIT, MISS
    }

    private CellState state;

    public Cell() {
        this.state = CellState.BLANK;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }
}
