package com.endava.petclinic.services;

import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.util.EnvReader;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class DBService {

    public Owner getOwnerById(Long id) {

        try (Connection conn = DriverManager.getConnection(EnvReader.getDBUrl(), EnvReader.getDBUsername(), EnvReader.getDBPassword())) {
            var mapColumnsToProperties = new HashMap<String, String>();
            //mapping your database to entity here
            mapColumnsToProperties.put("first_name", "firstName");
            mapColumnsToProperties.put("last_name", "lastName");
            BeanProcessor beanProcessor = new BeanProcessor(mapColumnsToProperties);
            RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
            ResultSetHandler<Owner> h = new BeanHandler<Owner>(Owner.class, rowProcessor);

            QueryRunner runner = new QueryRunner();
            Owner owner = runner.query(conn, "SELECT * from owners where id = ?", h, id);
            return owner;
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to DB", e);
        }
    }

    public Pet getPetById(Long id) {

        try (Connection conn = DriverManager.getConnection(EnvReader.getDBUrl(), EnvReader.getDBUsername(), EnvReader.getDBPassword())) {
            var mapColumnsToProperties = new HashMap<String, String>();
            //mapping your database to entity here
            mapColumnsToProperties.put("birth_date", "birthDate");
            BeanProcessor beanProcessor = new BeanProcessor(mapColumnsToProperties);
            RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
            ResultSetHandler<Pet> h = new BeanHandler<Pet>(Pet.class, rowProcessor);

            QueryRunner runner = new QueryRunner();
            Pet pet = runner.query(conn, "SELECT * from pets where id = ?", h, id);
            String birthDate = pet.getBirthDate();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(birthDate, dateTimeFormatter);
            String formattedBirthdate = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            pet.setBirthDate(formattedBirthdate);
            return pet;
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to DB", e);
        }
    }
}
