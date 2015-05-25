package ru.nsu.java.pages;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Zone;
import ru.nsu.java.db.api.Animal;
import ru.nsu.java.db.api.AnimalsDAO;
import ru.nsu.java.db.api.DAOException;
import ru.nsu.java.db.jdbc.JDBC_DAO;

import javax.naming.NamingException;
import java.util.List;


public class Index {
    @InjectComponent
    private Zone table1;
    @InjectComponent
    private Zone table2;
    @InjectComponent
    private Zone table3;

    @Property
    private Animal animal;

    @Component
    private Grid theGrid1;
    @Component
    private Grid theGrid2;
    @Component
    private Grid theGrid3;

    @SetupRender
    public void setupGrids() {
        theGrid1.getSortModel().updateSort("name");
        theGrid2.getSortModel().updateSort("name");
        theGrid3.getSortModel().updateSort("name");
    }

    public List<Animal> getAnimals() throws DAOException, NamingException {
        AnimalsDAO animalsDAO = new JDBC_DAO();
        return animalsDAO.getAll(1);
    }

    public List<Animal> getBirds() throws DAOException, NamingException {
        AnimalsDAO animalsDAO = new JDBC_DAO();
        return animalsDAO.getAll(2);
    }

    public List<Animal> getPredators() throws DAOException, NamingException {
        AnimalsDAO animalsDAO = new JDBC_DAO();
        return animalsDAO.getAll(3);
    }

    Object onActionFromAnimals() {
        return table1.getBody();
    }

    Object onActionFromBirds() {
        return table2.getBody();
    }

    Object onActionFromPredators() {
        return table3.getBody();
    }
}
