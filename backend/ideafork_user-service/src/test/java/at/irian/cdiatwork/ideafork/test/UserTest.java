package at.irian.cdiatwork.ideafork.test;

import at.irian.cdiatwork.ideafork.user.domain.User;
import at.irian.cdiatwork.ideafork.user.repository.UserRepository;
import at.irian.cdiatwork.ideafork.user.security.PasswordManager;
import org.apache.meecrowave.junit.MonoMeecrowave;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

public class UserTest {
    @ClassRule
    public static final MonoMeecrowave.Rule RULE = new MonoMeecrowave.Rule();

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordManager passwordManager;

    public UserTest() {
        RULE.inject(this);
    }

    @Before
    public void init() {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            userRepository.attachAndRemove(user);
        }
    }

    @Test
    public void createUser() {
        String password = passwordManager.createPasswordHash("xyz");
        User user = new User("gp@test.org", password);

        User savedUser = userRepository.save(user);
        assertUser(user, savedUser);

        User loadedUser = userRepository.loadByEmail("gp@test.org");

        assertUser(savedUser, loadedUser);
    }

    @Test
    public void updateUserDetails() {
        registerUser();

        updateUser();
    }

    @Test
    public void updateUserPassword() {
        registerUser();

        User user = updateUser();
        user.setPassword("abc");
        userRepository.save(user);

        User loadedUser = userRepository.loadByEmail("gp@test.org");
        Assert.assertNotNull(loadedUser);
        assertUser(user, loadedUser);
    }

    @Test
    public void reloadUserDetails() {
        registerUser();

        User createdUser = updateUser();

        User loadedUser = userRepository.loadByEmail("gp@test.org");
        Assert.assertNotNull(loadedUser);
        assertUser(createdUser, loadedUser);
    }

    @Test
    public void findUserByNick() {
        registerUser();
        User user = updateUser();

        User loadedUser = userRepository.loadByNickName("os890");
        Assert.assertNotNull(loadedUser);
        assertUser(user, loadedUser);
    }

    private void registerUser() {
        User user = new User("gp@test.org", "xyz");
        userRepository.save(user);

        User loadedUser = userRepository.loadByEmail("gp@test.org");
        Assert.assertNotNull(loadedUser);
        assertUser(user, loadedUser);
    }

    private User updateUser() {
        User user = userRepository.loadByEmail("gp@test.org");
        user.setNickName("os890");
        user.setFirstName("Gerhard");
        user.setLastName("Petracek");
        userRepository.save(user);

        User updatedUser = userRepository.loadByNickName("os890");
        Assert.assertNotNull(updatedUser);
        assertUser(user, updatedUser);
        return updatedUser;
    }

    //Assert.assertEquals(expectedUser, userToCheck); would fail due to a different value of the @Version property
    private void assertUser(User expectedUser, User userToCheck) {
        Assert.assertEquals(expectedUser.getEmail(), userToCheck.getEmail());
        Assert.assertEquals(expectedUser.getPassword(), userToCheck.getPassword());
        Assert.assertEquals(expectedUser.getNickName(), userToCheck.getNickName());
        Assert.assertEquals(expectedUser.getFirstName(), userToCheck.getFirstName());
        Assert.assertEquals(expectedUser.getLastName(), userToCheck.getLastName());
    }
}
