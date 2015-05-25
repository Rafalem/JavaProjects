package ru.nsu.java.db.jdbc;

import org.apache.log4j.Logger;
import ru.nsu.java.db.api.Animal;
import ru.nsu.java.db.api.AnimalsDAO;
import ru.nsu.java.db.api.DAOException;

import javax.naming.NamingException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JDBC_DAO implements AnimalsDAO {

    private static final Logger log = Logger.getLogger(JDBC_DAO.class);

    private Connection currentConnection = null;

    public JDBC_DAO() throws NamingException, DAOException {
        begin();
    }

    private Connection getConnection() throws DAOException {
        if (currentConnection == null)
            throw new DAOException("Must be in transaction");

        return currentConnection;
    }

    public List<String> listNameAnimals() throws DAOException {
        Connection conn = getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM animals");

            List<String> result = new ArrayList<String>();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public void addAnimal(String Name, Integer kind, Date comingDate) throws DAOException {
        Connection conn = getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO animals(name,kindId,comingDate) VALUES (?, ?, ?)");

            stmt.setString(1, Name);
            stmt.setInt(2, kind);
            stmt.setDate(3, comingDate);
            stmt.executeUpdate();

            commit();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public void deleteAnimal(Integer animalId) throws DAOException {
        Connection conn = getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "Delete From animals where id_anim = ?");

            stmt.setInt(1, animalId);
            stmt.executeUpdate();

            commit();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public void updateAnimal(Integer animalId,String name, Integer kind, Date comingDate) throws DAOException {
        Connection conn = getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "Update animals Set name=?, kindId=?, comingDate=? where id_anim = ?");

            stmt.setString(1, name);
            stmt.setInt(2, kind);
            stmt.setDate(3, comingDate);
            stmt.setInt(4, animalId);

            stmt.executeUpdate();

            commit();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public List<Animal> getAll(Integer group) throws DAOException {
        Connection conn = getConnection();
        try {

            PreparedStatement stmt = null;

            if ((group != 1) && (group != 2) && (group != 3))
                stmt = conn.prepareStatement(
                        "SELECT animals.id_anim, " +
                                "animals.name, " +
                                "kinds.nameKind, " +
                                "animals.comingDate " +
                                "From animals, kinds " +
                                "Where (animals.kindId = kinds.id_kind)");
            else {
                stmt = conn.prepareStatement(
                        "SELECT animals.id_anim, " +
                                "animals.name, " +
                                "kinds.nameKind, " +
                                "animals.comingDate " +
                                "From animals, kinds " +
                                "Where (animals.kindId = kinds.id_kind) " +
                                "and (kinds.groupId = ?)");

                switch (group) {
                    case 1: {
                        stmt.setInt(1, 1);
                        break;
                    }
                    case 2: {
                        stmt.setInt(1, 2);
                        break;
                    }
                    case 3: {
                        stmt.setInt(1, 3);
                        break;
                    }
                }
            }
            ResultSet rs = stmt.executeQuery();

            List<Animal> result = new ArrayList<Animal>();
            while (rs.next()) {
                result.add(
                        new AnimalBean(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getDate(4))
                );
            }
            return result;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public Integer getIdKindFromString(String Kind) throws DAOException {
        Connection conn = getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id_kind From kinds " +
                            "Where (kinds.nameKind = ?)");

            stmt.setString(1, Kind);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }

    }

    public static class AnimalBean extends Animal {

        public AnimalBean(Integer id, String iName, String kind, Date comingDate) {
            this.Name = iName;
            this.Id = id;
            this.Kind = kind;
            this.comingDate = comingDate;
        }

        public final Integer Id;
        public final String Name;
        public final String Kind;
        public final Date comingDate;

        @Override
        public Integer getId() {
            return Id;
        }

        @Override
        public String getName() {
            return Name;
        }

        @Override
        public String getKind() {
            return Kind;
        }

        @Override
        public String getComingDate() {
            String curStringDate = new SimpleDateFormat("dd.MM.yyyy").format(comingDate);
            return curStringDate;
        }
    }

    public List<Animal> searchAnimal(String searchString) throws DAOException {
        Connection conn = getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT animals.id_anim, " +
                            "animals.name, " +
                            "kinds.nameKind, " +
                            "animals.comingDate " +
                            "From animals, kinds " +
                            "Where (animals.kindId = kinds.id_kind) " +
                            "and ((animals.name Like ?) or (kinds.nameKind Like ?))");

            String searchQual = "%" + searchString + "%";
            stmt.setString(1, searchQual);
            stmt.setString(2, searchQual);

            ResultSet rs = stmt.executeQuery();

            List<Animal> result = new ArrayList<Animal>();
            while (rs.next()) {
                result.add(
                        new AnimalBean(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getDate(4))
                );
            }
            return result;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public void begin() throws DAOException {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            currentConnection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/webzoo", "root", "1234");
            currentConnection.setAutoCommit(false);

            log.info("Соединение с БД прошло успешно");

        } catch (SQLException ex) {
            log.info("Соединение с БД завершилось неудачей");
            throw new DAOException(ex);

        }
    }

    public void commit() throws DAOException {
        try {
            if (currentConnection == null) {
                throw new DAOException("Transaction is not started");
            }
            currentConnection.commit();
            currentConnection.close();
            currentConnection = null;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public void rollback() throws DAOException {
        try {
            if (currentConnection == null) {
                throw new DAOException("Transaction is not started");
            }
            currentConnection.rollback();
            currentConnection.close();
            currentConnection = null;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

}
