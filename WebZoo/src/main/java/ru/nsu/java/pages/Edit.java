package ru.nsu.java.pages;

import org.apache.log4j.Logger;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Edit {
    @InjectComponent
    private Zone animalZone;
    @InjectComponent
    private Zone editZone;

    @Component
    private Form addForm;
    @Component
    private Form editForm;
    @Component
    private Grid animalGrid;

    @Property
    private Animal animal;

    @Property
    @Validate("required")
    private String addName;
    @Property
    @Validate("required")
    private String addKind;
    @Property
    @Validate("required")
    private String addDate;
    @Persist
    @Property
    @Validate("required")
    private String editName;
    @Persist
    @Property
    @Validate("required")
    private String editKind;
    @Persist
    @Property
    @Validate("required")
    private String editDate;

    @SetupRender
    public void setupGrid() {
        animalGrid.getSortModel().updateSort("name");
    }

    @AfterRender
    public void afterRender() {
        animalGrid.getSortModel().updateSort("name");
    }

    private static Logger log = Logger.getLogger(Edit.class);

    @Persist
    private Integer curIdAnimal;


    public List<Animal> getAnimals() throws DAOException, NamingException {
        AnimalsDAO animalsDAO = new JDBC_DAO();
    return animalsDAO.getAll(0);
}

    Object onSuccessFromAddForm() throws DAOException, NamingException, ParseException {
        AnimalsDAO animalDAO = new JDBC_DAO();
        java.util.Date pDate = new SimpleDateFormat("dd.MM.yyyy").parse(addDate);
        Date comingDate = new java.sql.Date(pDate.getTime());

        Integer kind = animalDAO.getIdKindFromString(addKind);

        if (kind != 0)
            animalDAO.addAnimal(addName, kind, comingDate);
        return animalZone;
    }

    Object onSuccessFromEditForm() throws DAOException, NamingException, ParseException {
        AnimalsDAO animalDAO = new JDBC_DAO();
        Integer kind = animalDAO.getIdKindFromString(editKind);
        java.util.Date pDate = new SimpleDateFormat("dd.MM.yyyy").parse(editDate);
        Date comingDate = new java.sql.Date(pDate.getTime());
        animalDAO.updateAnimal(curIdAnimal, editName, kind, comingDate);
        return animalZone;
    }

    Object onActionFromDelete(Integer animalId) throws DAOException, NamingException {
        AnimalsDAO animalDAO = new JDBC_DAO();
        animalDAO.deleteAnimal(animalId);
        return animalZone;
    }

    Object onActionFromEdit(Integer animalId, String animalName,
                            String animalKind, String animalComingDate)
            throws DAOException, NamingException {
        curIdAnimal = animalId;
        editName=animalName;
        editKind=animalKind;
        editDate=animalComingDate;
        return animalZone;
    }
}
