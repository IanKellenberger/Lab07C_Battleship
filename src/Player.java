public class Player {
    private int hits;
    private int misses;

    public Player() {
        this.hits = 0;
        this.misses = 0;
    }

    public void addHit() {
        hits++;
    }

    public void addMiss() {
        misses++;
    }

    public void reset() {
        hits = 0;
        misses = 0;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }
}
