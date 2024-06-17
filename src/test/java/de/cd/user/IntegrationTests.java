package de.cd.user;

import de.cd.user.model.UserRepository;
import de.cd.user.model.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;

@SpringBootTest
public class IntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAllUsersShouldWork() {
        Iterable<User> userIterable = userRepository.findAll();
        Iterator<User> userIterator = userIterable.iterator();
        //boolean right = userIterator.hasNext();
        //System.out.println(right);
        User user = userIterator.next();
        //System.out.println(user.getUsername() + " " + user.getPasswordHash() + " " + user.getEmail());
        assert (user.getUsername().equals("Ingo"));
        assert (user.getEmail().equals("ingo@fh-muenster.de"));
        assert (user.getPasswordHash()).equals("$2a$10$CwryjPaRyWedMdsDGDZ86e3s2EjXAxwVkAssoiOX7zjgCvIyVF8KS");
    }

    @Test
    public void testFindAllUsersShouldNotWork() {
        Iterable<User> userIterable = userRepository.findAll();
        Iterator<User> userIterator = userIterable.iterator();
        //boolean right = userIterator.hasNext();
        //System.out.println(right);
        User user = userIterator.next();
        //System.out.println(user.getUsername() + " " + user.getPasswordHash() + " " + user.getEmail());
        assert (!user.getUsername().equals("No"));
        assert (!user.getEmail().equals("ingo@wrong.de"));
        assert (!user.getPasswordHash().equals("wrong"));
    }
}
