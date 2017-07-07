package at.irian.cdiatwork.ideafork.test;

import at.irian.cdiatwork.ideafork.user.domain.User;
import at.irian.cdiatwork.ideafork.user.repository.UserRepository;
import at.irian.cdiatwork.ideafork.user.rest.SimpleRegistrationResource;
import at.irian.cdiatwork.ideafork.user.rest.UserApplication;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.meecrowave.junit.MonoMeecrowave;
import org.junit.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;

public class UserWorkflowTest {
    @ClassRule
    public static final MonoMeecrowave.Rule RULE = new MonoMeecrowave.Rule();

    private static WebTarget userRegistrationTarget;
    private static Client client;

    @BeforeClass
    public static void createTarget() {
        client = ClientBuilder.newClient();

        int testHttpPort = RULE.getConfiguration().getHttpPort();
        createUserRegistrationTarget(testHttpPort);
    }

    @AfterClass
    public static void onShutdown() {
        client.close();
    }

    private static void createUserRegistrationTarget(int testHttpPort) {
        String applicationPath = UserApplication.class.getAnnotation(ApplicationPath.class).value();
        String userRegistrationPath = SimpleRegistrationResource.class.getAnnotation(Path.class).value();
        String baseUserUrl = "http://localhost:" + testHttpPort + applicationPath + userRegistrationPath;
        URI uri = UriBuilder.fromUri(baseUserUrl).build();
        userRegistrationTarget = client.target(uri);
    }

    @Before
    public void init() {
        UserRepository userRepository = BeanProvider.getContextualReference(UserRepository.class);
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            userRepository.attachAndRemove(user);
        }
    }

    @Test
    public void registerUser() {
        User user = new User();
        user.setEmail("gp@test.org");
        user.setPassword("xyz");

        Response response = userRegistrationTarget.request().buildPost(Entity.json(user)).invoke();

        Assert.assertNotNull(response);
        Assert.assertEquals(CREATED.getStatusCode(), response.getStatus());
        User createdUser = response.readEntity(User.class);

        Assert.assertEquals("gp@test.org", createdUser.getEmail());
    }
}
