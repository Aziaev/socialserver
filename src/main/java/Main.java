import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * Created by Artur.Ziaev on 13.11.2016.
 */
public class Main {
    public static void main(String[] args) {
        Server server = new Server(9090);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("C:\\Users\\artur.ziaev\\Google Диск\\personal\\projects\\JAVA\\javainnopolislessons\\SocialServer\\public_html");
        resourceHandler.setDirectoriesListed(true);
    }
}
