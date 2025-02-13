package lab.point;

public class Checker {
    public static boolean check(double x, double y, double r) {
        if (r > 0) {
            if (x >= 0 && y <= 0 && y >= -r / 2 && x <= r) return true;
            else if (x <= 0 && y <= 0 && y > -x / 2 - r / 2) return true;
            else if (x <= 0 && y >= 0 && (x * x + y * y < r * r)) return true;
            return false;
        }
        r = -r;
        if (y >= 0 && x <= 0 && y <= r / 2 && x >= -r) return true;
        else if (x >= 0 && y >= 0 && y <= -x / 2 + r / 2) return true;
        else if (x >= 0 && y <= 0 && (x * x + y * y < r * r)) return true;
        return false;
    }
}
