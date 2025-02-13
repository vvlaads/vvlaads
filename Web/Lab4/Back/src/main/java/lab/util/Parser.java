package lab.util;

import lab.database.Point;

import java.util.List;

public class Parser {
    public String pointToJSON(Point point) {
        String result = "{";
        result += "\"x\": \"" + point.getX() + "\",";
        result += "\"y\": \"" + point.getY() + "\",";
        result += "\"r\": \"" + point.getR() + "\",";
        result += "\"result\": \"" + point.isResult() + "\",";
        result += "\"createdTime\": \"" + point.getCreatedTime() + "\"}";
        return result;
    }

    public String pointsToJSON(List<Point> pointList) {
        String result = "[";
        for (int i = 0; i < pointList.size(); i++) {
            result += pointToJSON(pointList.get(i));
            if (i + 1 < pointList.size()) {
                result += ",";
            }
        }
        result += "]";
        return result;
    }
}
