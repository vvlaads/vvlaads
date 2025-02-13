package lab.point;

public class PointInfo {
    private String x;
    private String y;
    private String r;

    public PointInfo() {
    }

    public PointInfo(String x, String y, String r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }
}
