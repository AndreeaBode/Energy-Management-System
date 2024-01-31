package sd;

public class MeasureInfo {

    private final float sum;
    private final int count;

    public MeasureInfo(float sum, int count) {
        this.sum = sum;
        this.count = count;
    }

    public float getSum() {
        return sum;
    }

    public int getCount() {
        return count;
    }
}
