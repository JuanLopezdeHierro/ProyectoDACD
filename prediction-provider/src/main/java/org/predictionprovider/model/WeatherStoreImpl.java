package org.predictionprovider.model;

import org.predictionprovider.variables.Location;
import org.predictionprovider.variables.Weather;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class WeatherStoreImpl implements WeatherStore {
    private Connection connection;

    public WeatherStoreImpl(String dbUrl) {
        try {
            connection = DriverManager.getConnection(dbUrl);
            initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeDatabase() {
        // No es necesario inicializar la base de datos aquí, ya que las tablas se crearán dinámicamente cuando se almacenen los datos
    }

    @Override
    public void saveWeatherData(Location location, List<Weather> weatherData) {
        String tableName = location.getName().toLowerCase().replace(" ", "_"); // Nombre de la tabla basado en el nombre de la isla
        createTable(tableName); // Crear tabla si no existe

        String sql = "INSERT INTO " + tableName + " (temp, precipitation_probability, humidity, clouds, wind_speed, timestamp) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Weather weather : weatherData) {
                pstmt.setDouble(1, weather.getTemp());
                pstmt.setDouble(2, weather.getPrecipitationProbability());
                pstmt.setDouble(3, weather.getHumidity());
                pstmt.setDouble(4, weather.getClouds());
                pstmt.setDouble(5, weather.getWindSpeed());
                pstmt.setString(6, weather.getTimestamp());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Weather> getWeatherForecast(Location location) {
        return null;
    }

    // Método para crear una tabla para una isla específica si no existe
    private void createTable(String tableName) {
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "temp REAL," +
                    "precipitation_probability REAL," +
                    "humidity REAL," +
                    "clouds REAL," +
                    "wind_speed REAL," +
                    "timestamp DATETIME" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

