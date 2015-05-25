package ru.nsu.java.pages;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Zone;
import ru.nsu.java.db.api.Animal;
import ru.nsu.java.db.api.AnimalsDAO;
import ru.nsu.java.db.api.DAOException;
import ru.nsu.java.db.jdbc.JDBC_DAO;

import javax.naming.NamingException;
import java.util.List;

public class Search {
    @InjectComponent
    private Zone animalZone;
    @InjectComponent
    private Zone searchZone;
    @InjectComponent
    private Zone searchResults;

    @Property
    private Animal animal;

    @Component
    private Grid animalGrid;
    @Component
    private Form searchForm;
    @Component
    private Grid searchGrid;

    @Persist
    @Property
    @Validate("required")
    private String searchString;

    @SetupRender
    public void setupGrid() {
        animalGrid.getSortModel().updateSort("name");
        searchGrid.getSortModel().updateSort("name");
    }

    @AfterRender
    public void afterRender() {
        animalGrid.getSortModel().updateSort("name");
        searchGrid.getSortModel().updateSort("name");
    }

    public List<Animal> getAnimals() throws DAOException, NamingException {
        AnimalsDAO animalsDAO = new JDBC_DAO();
        return animalsDAO.getAll(0);
    }

    public List<Animal> getFindAnimals() throws DAOException, NamingException {
        AnimalsDAO animalsDAO = new JDBC_DAO();
        return animalsDAO.searchAnimal(searchString);
    }

    void onSuccessFromSearchForm() throws DAOException, NamingException {
        //getFindAnimals();
    }
}
