package pojo;

public class GeneticBoardState extends BoardState {

    private int range;

    public GeneticBoardState(int size) {
        super(size);
    }
    
    public void setRange(int percentage) {
        this.range = percentage;
    }

    public int getRange() {
        return range;
    }

}
