package DAL;

import java.sql.*;

public class MySQLAccess {
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/librarysystem";

    private static final String USER = "root";
    private static final String PASS = "Tutuan96";


    /**
     * to connect to db
     * @throws Exception if cannot connect
     */
    public void connectDB() throws Exception {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("Error connect db");
        }
    }

    /**
     * get data from db
     * @param query query applied to db
     * @return ResultSet, null if doesn't find
     */
    public ResultSet getData(String query) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Error query");
        }
        return this.resultSet;
    }

    /**
     * update data to db
     * @param query query applied to db
     * @return number of row effected
     */
    public int updateData(String query) {
        int result = 0;
        try {
            statement = connection.createStatement();
            result = statement.executeUpdate(query);
        } catch (Exception e){
            System.out.println("Error query");
            return 0;
        }
        return result;
    }

    public PreparedStatement getPreparedStatement(String query){
        try{
            preparedStatement = connection.prepareStatement(query);
        } catch (Exception e) {
            System.out.println("Error query");
        }
        return this.preparedStatement;
    }

    /**
     * close connection to db
     */
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
