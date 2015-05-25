package ru.nsu.java.db.api;

import java.sql.Date;
import java.util.List;

public interface AnimalsDAO {
    void addAnimal(String Name, Integer kind, Date comingDate) throws DAOException;
    void deleteAnimal(Integer animalId) throws DAOException;
    void updateAnimal(Integer animalId,String Name, Integer kind, Date comingDate) throws DAOException;
    Integer getIdKindFromString(String Kind) throws DAOException;

    List<Animal> getAll(Integer group) throws DAOException;
//    List<String> listNameAnimals() throws DAOException;

    List<Animal> searchAnimal(String searchString)
            throws DAOException;

    // Transaction control
    void begin() throws DAOException;

    void commit() throws DAOException;

    void rollback() throws DAOException;

}
