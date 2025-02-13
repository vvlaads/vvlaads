package lab.auth;

import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lab.database.User;
import lab.database.UserManager;
import lab.util.HashSum;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Path("/auth")
public class AuthService {
    @EJB
    private UserManager userManager;
    public static final ConcurrentHashMap<String, String> sessionStore = new ConcurrentHashMap<>();

    private boolean isUserLoggedIn(String userId) {
        return sessionStore.containsValue(userId);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context HttpServletRequest request, UserInfo userInfo) {
        String login = userInfo.getLogin();
        String password = userInfo.getPassword();
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Поля обязательны для заполнения\"}").build();
        }

        password = HashSum.hash(password);
        User user = new User(login, password);
        if (userManager.check(user)) {
            if (sessionStore.containsValue(login)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\": \"Пользователь уже авторизован на другом устройстве\"}").build();
            }
            String sessionId = UUID.randomUUID().toString();
            sessionStore.put(sessionId, login);

            return Response.ok("{\"message\": \"Успешный вход\", \"sessionId\": \"" + sessionId + "\", \"userId\": \"" + login + "\"}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\": \"Неверный логин или пароль\"}").build();
    }


    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@HeaderParam("X-Session-Id") String sessionId, @Context HttpServletRequest request) {
        if (sessionId != null && sessionStore.containsKey(sessionId)) {
            sessionStore.remove(sessionId);
            request.getSession().invalidate();
            return Response.ok("{\"message\": \"Выход выполнен\"}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"message\": \"Сессия отсутствует\"}").build();
    }

    @GET
    @Path("/session")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkSession(@HeaderParam("X-Session-Id") String sessionId) {
        if (sessionId != null && sessionStore.containsKey(sessionId)) {
            return Response.ok("{\"userId\": \"" + sessionStore.get(sessionId) + "\"}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
