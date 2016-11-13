import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SampleServlet;

/**
 * Created by Artur.Ziaev on 13.11.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(9090);
//
//        ResourceHandler resourceHandler = new ResourceHandler();
//        resourceHandler.setResourceBase("C:\\Users\\artur.ziaev\\Google Диск\\personal\\projects\\JAVA\\javainnopolislessons\\SocialServer\\public_html");
//        resourceHandler.setDirectoriesListed(true);
//
//        server.setHandler(resourceHandler);

//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.addServlet(new ServletHolder(new SampleServlet()), "/api");
//        server.setHandler(context);

        /* JAX-RS */
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/api/*");
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "resources");
        server.setHandler(context);

        server.start();
        server.join();
    }
}
