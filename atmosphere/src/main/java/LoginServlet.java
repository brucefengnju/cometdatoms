import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String existingUser = (String) session.getAttribute("user");
        String user = req.getParameter("user");
        if (user == null && existingUser == null) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else if (existingUser != null && !existingUser.equals(user)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else if (user != null && existingUser == null) {
            session.setAttribute("user", user);
            BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, ChatHandler.class.getName(), true).broadcast(user + " connected !");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
