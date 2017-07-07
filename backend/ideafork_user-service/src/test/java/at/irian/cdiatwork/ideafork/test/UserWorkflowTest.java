package at.irian.cdiatwork.ideafork.test;

import at.irian.cdiatwork.ideafork.jwt.api.LoginEntryPoint;
import at.irian.cdiatwork.ideafork.user.domain.User;
import at.irian.cdiatwork.ideafork.user.domain.UserAction;
import at.irian.cdiatwork.ideafork.user.repository.UserActionRepository;
import at.irian.cdiatwork.ideafork.user.repository.UserRepository;
import at.irian.cdiatwork.ideafork.user.rest.SimpleLoginResource;
import at.irian.cdiatwork.ideafork.user.rest.SimpleRegistrationResource;
import at.irian.cdiatwork.ideafork.user.rest.UserApplication;
import at.irian.cdiatwork.ideafork.user.rest.UserResource;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.meecrowave.junit.MonoMeecrowave;
import org.junit.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;

public class UserWorkflowTest {
    @ClassRule
    public static final MonoMeecrowave.Rule RULE = new MonoMeecrowave.Rule();

    private static WebTarget userRegistrationTarget;
    private static WebTarget loginTarget;
    private static WebTarget userTarget;
    private static Client client;

    @BeforeClass
    public static void createTarget() {
        client = ClientBuilder.newClient();

        int testHttpPort = RULE.getConfiguration().getHttpPort();
        createUserRegistrationTarget(testHttpPort);
        createLoginTarget(testHttpPort);
        createUserTarget(testHttpPort);
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

    private static void createLoginTarget(int testHttpPort) {
        String applicationPath = UserApplication.class.getAnnotation(ApplicationPath.class).value();
        String loginPath = SimpleLoginResource.class.getAnnotation(Path.class).value();

        for (Method method : SimpleLoginResource.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(LoginEntryPoint.class)) {
                Path path = method.getAnnotation(Path.class);
                if (path != null) {
                    loginPath += path.value();
                    break;
                }
            }
        }

        String baseUserUrl = "http://localhost:" + testHttpPort + applicationPath + loginPath;
        URI uri = UriBuilder.fromUri(baseUserUrl).build();
        loginTarget = client.target(uri);
    }

    private static void createUserTarget(int testHttpPort) {
        String applicationPath = UserApplication.class.getAnnotation(ApplicationPath.class).value();
        String userPath = UserResource.class.getAnnotation(Path.class).value();
        String baseUserUrl = "http://localhost:" + testHttpPort + applicationPath + userPath;
        URI uri = UriBuilder.fromUri(baseUserUrl).build();
        userTarget = client.target(uri);
    }

    @Before
    public void init() {
        UserActionRepository userActionRepository = BeanProvider.getContextualReference(UserActionRepository.class);
        List<UserAction> allUserActions = userActionRepository.findAll();
        for (UserAction userAction : allUserActions) {
            userActionRepository.attachAndRemove(userAction);
        }

        UserRepository userRepository = BeanProvider.getContextualReference(UserRepository.class);
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            userRepository.attachAndRemove(user);
        }
    }

    @Test
    public void createUser() {
        registerUser();
    }

    @Test
    public void failedLogin() {
        registerUser();

        loginInvalidUser();
    }

    @Test
    public void failedUpdate() {
        registerUser();
        loginInvalidUser();

        Response response = userTarget.request().header(HttpHeaders.AUTHORIZATION, "invalid").buildPost(Entity.json(createTestUser())).invoke();
        Assert.assertNotNull(response);
        Assert.assertEquals(UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void updateUserDetails() {
        registerUser();
        String token = loginUser();

        updateUser(token);
    }

    @Test
    public void reloadUserDetails() {
        registerUser();

        String token = loginUser();
        User createdUser = updateUser(token);

        Response response = userTarget.request().header(HttpHeaders.AUTHORIZATION, token).get();
        Assert.assertEquals(OK.getStatusCode(), response.getStatus());
        User loadedUser = response.readEntity(User.class);
        compareUsers(createdUser, loadedUser);
    }

    @Test
    public void findUserByNick() {
        registerUser();

        String token = loginUser();
        User createdUser = updateUser(token);

        Response response = userTarget.path(createdUser.getNickName()).request().header(HttpHeaders.AUTHORIZATION, token).get();
        Assert.assertEquals(OK.getStatusCode(), response.getStatus());
        User loadedUser = response.readEntity(User.class);
        compareUsers(createdUser, loadedUser);
    }

    private Response registerUser() {
        User user = new User();
        user.setEmail("gp@test.org");
        user.setPassword("xyz");

        Response response = userRegistrationTarget.request().buildPost(Entity.json(user)).invoke();

        Assert.assertNotNull(response);
        Assert.assertEquals(CREATED.getStatusCode(), response.getStatus());
        User createdUser = response.readEntity(User.class);

        Assert.assertEquals("gp@test.org", createdUser.getEmail());
        return response;
    }

    private String loginUser() {
        User user = new User("gp@test.org", "xyz");

        Response response = loginTarget.request().buildPost(Entity.json(user)).invoke();
        String token = response.getHeaderString(HttpHeaders.AUTHORIZATION);
        Assert.assertNotNull(token);
        return token;
    }

    private void loginInvalidUser() {
        User user = new User("gp@test.org", "wrong");

        Response response = loginTarget.request().buildPost(Entity.json(user)).invoke();
        Assert.assertNotNull(response);
        Assert.assertEquals(UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    private User updateUser(String token) {
        User user = createTestUser();
        Response response = userTarget.request().header(HttpHeaders.AUTHORIZATION, token).buildPost(Entity.json(user)).invoke();
        Assert.assertNotNull(response);
        Assert.assertEquals(OK.getStatusCode(), response.getStatus());
        return response.readEntity(User.class);
    }

    private String updateUserWithTokenUpdate(String token) {
        User user = createTestUser();

        Response response = userTarget.request().header(HttpHeaders.AUTHORIZATION, token).buildPost(Entity.json(user)).invoke();
        Assert.assertNotNull(response);
        Assert.assertEquals(OK.getStatusCode(), response.getStatus());
        String newToken = response.getHeaderString(HttpHeaders.AUTHORIZATION);
        Assert.assertNotNull(newToken);
        Assert.assertNotEquals(token, newToken); //a new token was sent back
        return newToken;
    }

    private User createTestUser() {
        User user = new User();
        user.setNickName("os890");
        user.setFirstName("Gerhard");
        user.setLastName("Petracek");
        return user;
    }

    private void compareUsers(User expectedUser, User userToCheck) {
        Assert.assertEquals(expectedUser.getEmail(), userToCheck.getEmail());
        Assert.assertEquals(expectedUser.getPassword(), userToCheck.getPassword());
        Assert.assertEquals(expectedUser.getNickName(), userToCheck.getNickName());
        Assert.assertEquals(expectedUser.getFirstName(), userToCheck.getFirstName());
        Assert.assertEquals(expectedUser.getLastName(), userToCheck.getLastName());
    }
}
