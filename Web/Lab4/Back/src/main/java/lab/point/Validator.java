package lab.point;

public class Validator {
    public boolean validateX(String x) {
        double value;

        try {
            value = Double.parseDouble(x);
        } catch (NumberFormatException e) {
            return false;
        }

        if (value >= -2 && value <= 2) return true;
        return false;
    }

    public boolean validateY(String y) {
        double value;

        try {
            value = Double.parseDouble(y);
        } catch (NumberFormatException e) {
            return false;
        }

        if (value < 3 && value > -3) {
            return true;
        }

        return false;
    }

    public boolean validateR(String r) {
        double value;

        try {
            value = Double.parseDouble(r);
        } catch (NumberFormatException e) {
            return false;
        }

        if (value >= -2 && value <= 2 && value != 0) return true;
        return false;
    }
}
