package com.rost.srg.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rost.srg.models.Person;

@Component
public class PersonDAO {
    private static int ID;
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String username = "postgres";
    private static final String password = "Rasta";
    private static Connection connection;
    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT max(id) AS ID FROM PERSON");
            resultSet.next();
            ID = resultSet.getInt("ID");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            /*Statement statement = connection.createStatement();
            String sql = "SELECT * FROM PERSON";
            ResultSet resultSet = statement.executeQuery(sql);*/
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PERSON");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return people;
    }

    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE ID = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return person;
    }

    public int save(Person person) {
        int newId = ++ID;
        try {
            /*
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO PERSON VALUES("
                    + newId + ", "
                    + "'" + person.getName() + "', "
                    + person.getAge() + ", "
                    + "'" + person.getEmail() + "');";
            statement.executeUpdate(sql);
            */
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PERSON VALUES(?, ?, ?, ?)");
            preparedStatement.setInt(1, newId);
            preparedStatement.setString(2, person.getName());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.setString(4, person.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
       return newId;
    }

    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("update person set name = ?, age = ?, email = ? where id = ?");
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PERSON WHERE ID = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
