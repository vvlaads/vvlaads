package lab.point;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lab.auth.AuthService;
import lab.database.Point;
import lab.database.PointManager;
import lab.database.User;
import lab.database.UserManager;
import lab.util.Parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/point")
public class PointService {

    @EJB
    private UserManager userManager;

    @EJB
    private PointManager pointManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response point(@HeaderParam("X-Session-Id") String sessionId, PointInfo pointInfo) {
        Date createdTime = new Date();

        if (sessionId == null || !AuthService.sessionStore.containsKey(sessionId)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Необходима авторизация\"}").build();
        }

        String username = AuthService.sessionStore.get(sessionId);
        User user = userManager.findUserByLogin(username);

        if (pointInfo.getX() == null || pointInfo.getY() == null || pointInfo.getR() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Поля x, y и r обязательны\"}").build();
        }

        Validator validator = new Validator();
        boolean correctX = validator.validateX(pointInfo.getX());
        boolean correctY = validator.validateY(pointInfo.getY().replace(",", "."));
        boolean correctR = validator.validateR(pointInfo.getR());

        if (!correctX || !correctY || !correctR) {
            return Response.status(422).entity("{\"error\": \"Данные некорректны\"}").build();
        }

        double valueX = Double.parseDouble(pointInfo.getX());
        double valueY = Double.parseDouble(pointInfo.getY().replace(",", "."));
        double valueR = Double.parseDouble(pointInfo.getR());

        boolean result = Checker.check(valueX, valueY, valueR);
        String time = new SimpleDateFormat("HH:mm:ss").format(createdTime);
        Point point = new Point(valueX, valueY, valueR, result, time, user);
        boolean success = pointManager.savePoint(point);
        Parser parser = new Parser();

        if (success) {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Точка добавлена\"," +
                            "\"point\": " + parser.pointToJSON(point) + "}").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Ошибка сохранения\"}").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response points(@HeaderParam("X-Session-Id") String sessionId) {
        if (sessionId == null || !AuthService.sessionStore.containsKey(sessionId)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Необходима авторизация\"}").build();
        }

        String username = AuthService.sessionStore.get(sessionId);
        User user = userManager.findUserByLogin(username);
        List<Point> points = pointManager.getPointsByUser(user);
        Parser parser = new Parser();

        return Response.status(Response.Status.OK)
                .entity("{\"message\": \"Точки получены\"," +
                        "\"data\": {\"points\":" + parser.pointsToJSON(points) + "}}").build();
    }
}
