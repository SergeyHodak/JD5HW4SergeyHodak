package tables.developers;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prefs.Prefs;
import storage.DatabaseInitService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DevelopersDaoServiceTests {
    private Connection connection;
    private DevelopersDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        String connectionUrl = new Prefs().getString("testDbUrl");
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        daoService = new DevelopersDaoService(connection);
        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testCreate() throws NumberOfCharactersExceedsTheLimit, AgeOutOfRange, SQLException {
        List<Developers> originalDevelopers = new ArrayList<>();

        Developers fullValueDevelopers = new Developers();
        fullValueDevelopers.setFirstName("TestFirstName");
        fullValueDevelopers.setSecondName("TestSecondName");
        fullValueDevelopers.setAge(28);
        fullValueDevelopers.setGender(Developers.Gender.male);
        originalDevelopers.add(fullValueDevelopers);

        Developers nullFirstNameDevelopers = new Developers();
        nullFirstNameDevelopers.setFirstName(null);
        nullFirstNameDevelopers.setSecondName("TestSecondName1");
        nullFirstNameDevelopers.setAge(20);
        nullFirstNameDevelopers.setGender(Developers.Gender.female);
        originalDevelopers.add(nullFirstNameDevelopers);

        Developers nullSecondNameDevelopers = new Developers();
        nullSecondNameDevelopers.setFirstName("TestFirstName1");
        nullSecondNameDevelopers.setSecondName(null);
        nullSecondNameDevelopers.setAge(21);
        nullSecondNameDevelopers.setGender(Developers.Gender.male);
        originalDevelopers.add(nullSecondNameDevelopers);

        Developers zeroAgeDevelopers = new Developers();
        zeroAgeDevelopers.setFirstName("TestFirstName2");
        zeroAgeDevelopers.setSecondName("TestSecondName2");
        zeroAgeDevelopers.setAge(0);
        zeroAgeDevelopers.setGender(Developers.Gender.female);
        originalDevelopers.add(zeroAgeDevelopers);

        for (Developers original : originalDevelopers) {
            long id = daoService.create(original);
            Developers saved = daoService.getById(id);

            Assertions.assertEquals(id, saved.getId());
            Assertions.assertEquals(original.getFirstName(), saved.getFirstName());
            Assertions.assertEquals(original.getSecondName(), saved.getSecondName());
            Assertions.assertEquals(original.getAge(), saved.getAge());
            Assertions.assertEquals(original.getGender(), saved.getGender());
        }
    }

    @Test
    public void getAllTest() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange {
        Developers expected = new Developers();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(19);
        expected.setGender(Developers.Gender.female);

        long id = daoService.create(expected);
        expected.setId(id);

        List<Developers> expectedCustomers = Collections.singletonList(expected);
        List<Developers> actualCustomers = daoService.getAll();

        Assertions.assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    public void testUpdate() throws NumberOfCharactersExceedsTheLimit, AgeOutOfRange, SQLException {
        Developers original = new Developers();
        original.setFirstName("TestFirstName");
        original.setSecondName("TestSecondName");
        original.setAge(19);
        original.setGender(Developers.Gender.male);

        long id = daoService.create(original);
        original.setId(id);

        List<Developers> originalDevelopers = new ArrayList<>();
        Developers fullUpdate = new Developers();
        fullUpdate.setFirstName("TestUpdateFirstName");
        fullUpdate.setSecondName("TestUpdateSecondName");
        fullUpdate.setAge(49);
        fullUpdate.setGender(Developers.Gender.female);
        daoService.update(fullUpdate);
        originalDevelopers.add(daoService.getById(id));

        Developers nullFirstNameUpdate = new Developers();
        nullFirstNameUpdate.setFirstName(null);
        nullFirstNameUpdate.setSecondName("TestUpdateSecondName1");
        nullFirstNameUpdate.setAge(34);
        nullFirstNameUpdate.setGender(Developers.Gender.male);
        daoService.update(nullFirstNameUpdate);
        originalDevelopers.add(daoService.getById(id));

        Developers nullSecondNameUpdate = new Developers();
        nullSecondNameUpdate.setFirstName("TestUpdateFirstName1");
        nullSecondNameUpdate.setSecondName(null);
        nullSecondNameUpdate.setAge(33);
        nullSecondNameUpdate.setGender(Developers.Gender.female);
        daoService.update(nullSecondNameUpdate);
        originalDevelopers.add(daoService.getById(id));

        Developers zeroAgeUpdate = new Developers();
        zeroAgeUpdate.setFirstName("TestUpdateFirstName2");
        zeroAgeUpdate.setSecondName("TestUpdateSecondName2");
        zeroAgeUpdate.setAge(0);
        zeroAgeUpdate.setGender(Developers.Gender.male);
        daoService.update(zeroAgeUpdate);
        originalDevelopers.add(daoService.getById(id));

        for (Developers updated : originalDevelopers) {
            Developers saved = daoService.getById(id);

            Assertions.assertEquals(id, updated.getId());
            Assertions.assertEquals(saved.getFirstName(), updated.getFirstName());
            Assertions.assertEquals(saved.getSecondName(), updated.getSecondName());
            Assertions.assertEquals(saved.getAge(), updated.getAge());
            Assertions.assertEquals(saved.getGender(), updated.getGender());
        }
    }

    @Test
    public void testDelete() throws SQLException, NumberOfCharactersExceedsTheLimit, AgeOutOfRange {
        Developers expected = new Developers();
        expected.setFirstName("TestFirstName");
        expected.setSecondName("TestSecondName");
        expected.setAge(23);
        expected.setGender(Developers.Gender.male);

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
}