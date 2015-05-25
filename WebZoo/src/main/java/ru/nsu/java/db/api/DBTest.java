package ru.nsu.java.db.api;

import net.ttddyy.dsproxy.listener.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import javax.naming.InitialContext;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DBTest {
    @Parameters
    public static Collection implementations() {
        Object[] data = new Object[]{ru.nsu.java.db.jdbc.JDBC_DAO.class};
        return Arrays.asList(data);
    }

    @BeforeClass
    public static void onlyOnce() throws Exception {
        //JNDI init
        InitialContext ic = new InitialContext();

        // Construct BasicDataSource
        BasicDataSource bds = new BasicDataSource();

        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/webzoo");
        bds.setUsername("root");
        bds.setPassword("1234");

        // Construct ProxyDataSource
        ProxyDataSource proxyDS = new ProxyDataSource();
        proxyDS.setDataSource(bds);
        proxyDS.setListener(new SLF4JQueryLoggingListener());

        ic.bind("webzoo", proxyDS);
    }


    private AnimalsDAO dao;
    private static final Logger log = Logger.getLogger(DBTest.class);

    public DBTest(Class impl) throws Exception {
        dao = (AnimalsDAO) impl.newInstance();
    }

    @Test
    public void testAddAnimal() throws Exception {
        log.info("Running testAddAnimal for " + dao.getClass());
        dao.begin();
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);
        dao.addAnimal("Слон", 1, curDate);
        dao.rollback();
    }

    @Test
    public void testSearchAnimal() throws Exception {
        log.info("Running testSearchAnimals for " + dao.getClass());
        dao.begin();
        List<? extends Animal> res = dao.searchAnimal("Слон");
        log.info("Before access to entity");
        assertEquals(2, res.size());
        assertEquals("Слон", res.get(0).getName());
        dao.rollback();
    }

}
