package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/foodloss.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the Countries in the database.
     * @return
     *    Returns an ArrayList of Country objects
     */

    public ArrayList<Persona> getAllPersonas()
    {
        ArrayList<Persona> personas = new ArrayList<Persona>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Persona";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String ID = results.getString("personaID");
                String name = results.getString("name");
                String age = results.getString("age");
                String description = results.getString("description");
                String needsAndGoals = results.getString("needsAndGoals");
                String skillsAndExperiences = results.getString("skillsAndExperience");
                String personaImg = results.getString("personaIMG");

                // Create a Country Object
                Persona persona = new Persona(ID, name, age, description, needsAndGoals, skillsAndExperiences, personaImg);

                // Add the Country object to the array
                personas.add(persona);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return personas;
    }
    public ArrayList<Country> getAllCountries() {
        // Create the ArrayList of Country objects to return
        ArrayList<Country> countries = new ArrayList<Country>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Country";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String m49Code     = results.getString("m49code");
                String name  = results.getString("countryName");

                // Create a Country Object
                Country country = new Country(m49Code, name);

                // Add the Country object to the array
                countries.add(country);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return countries;
    }

    public ArrayList<String> getAllRegions() {
        ArrayList<String> regions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT regionName FROM Region ORDER BY regionName;";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String regionName = results.getString("regionName");
                regions.add(regionName);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return regions;
    }

    // TODO: Add your required methods here

    /**
     * Get all of the years in the database.
     * @return
     *    Returns an ArrayList of years objects
     */
    public ArrayList<Date> getAllDates() {
        ArrayList<Date> dates = new ArrayList<Date>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT year FROM date ORDER BY year";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                Integer year     = results.getInt("year");

                // Create a Date Object
                Date date = new Date(year);

                // Add the date object to the array
                dates.add(date);
            }
            

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the dates
        return dates;
    }

    public ArrayList<Date> getDatesByCountry(String country) {
        ArrayList<Date> dates = new ArrayList<Date>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT year FROM countryLossEvent WHERE m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"')";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                Integer year     = results.getInt("year");

                // Create a Date Object
                Date date = new Date(year);

                // Add the date object to the array
                dates.add(date);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the dates
        return dates;
    }

    public ArrayList<Date> getDatesByRegion(String region) {
        ArrayList<Date> dates = new ArrayList<Date>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT year FROM regionLossEvent WHERE m49code = (SELECT m49code FROM region WHERE regionName = '" + region +"')";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                Integer year     = results.getInt("year");

                // Create a Date Object
                Date date = new Date(year);

                // Add the date object to the array
                dates.add(date);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the dates
        return dates;
    }
    
    public String getMinYearRegion(String region,String chosenMinYear) {
        String year = "";

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select min(year) FROM regionLossEvent WHERE m49code = (SELECT m49code FROM region WHERE regionName = '" + region +"') AND year >=" + chosenMinYear + "";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            
                year = results.getString("min(year)");

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the dates
        return year;
    }
    public String getMaxYearRegion(String region, String chosenMaxYear) {
        String year = "";

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select max(year) FROM regionLossEvent WHERE m49code = (SELECT m49code FROM region WHERE regionName = '" + region +"') AND year <=" + chosenMaxYear +" ";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            
                year = results.getString("max(year)");

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the dates
        return year;
    }
    public ArrayList<Date> getMinYear() {
        ArrayList<Date> dates = new ArrayList<Date>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select min(year) FROM date";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                Integer year     = results.getInt("min(year)");

                // Create a Date Object
                Date date = new Date(year);

                // Add the date object to the array
                dates.add(date);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the dates
        return dates;
    }

    public ArrayList<Map<String, Double>> getCountryLossEvent(String country, String startYear, String endYear) {
        ArrayList<Map<String, Double>> data = new ArrayList<>();
 
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
 
            // Construct the SQL query based on the parameters
            // start year percentage
            String query1 =  "SELECT ROUND(AVG(loss_percentage),1) FROM countryLossEvent WHERE year = " + startYear +
                             " AND m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"')";
            ResultSet results1 = statement.executeQuery(query1);
        
            Map<String, Double> row = new HashMap<>();

            
            row.put("startYearPercent", results1.getDouble("ROUND(AVG(loss_percentage),1)"));

            String query2 =  "SELECT ROUND(AVG(loss_percentage),1) FROM countryLossEvent WHERE year = " + endYear +
            " AND m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"')";
           ResultSet results2 = statement.executeQuery(query2);
 
            
            row.put("endYearPercent", results2.getDouble("ROUND(AVG(loss_percentage),1)"));
            

            String query3 =  "WITH startValue AS (SELECT avg(loss_percentage) as loss1 FROM countryLossEvent WHERE year =" + startYear +
            " AND m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"')), endValue AS (SELECT avg(loss_percentage) as loss2 " +
            "FROM countryLossEvent WHERE year =" + endYear + " AND m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"')) " +
            "SELECT ROUND(loss2 - loss1, 1) AS changeValue FROM startValue, endValue;";
           ResultSet results3 = statement.executeQuery(query3);

           
           row.put("lossChange", results3.getDouble("changeValue"));
        
           data.add(row);

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return data;
        
    }

    

    public ArrayList<Map<String, Double>> getRegionLossEvent(String region, String startYear, String endYear) {
        ArrayList<Map<String, Double>> results = new ArrayList<>();
    
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
    
            String query = "WITH StartYearData AS (" +
                           "    SELECT AVG(loss_percentage) AS startYearPercent " +
                           "    FROM RegionLossEvent " +
                           "    WHERE year = " + startYear + " " +
                           "    AND m49code = (SELECT m49code FROM Region WHERE regionName = '" + region + "')" +
                           "), " +
                           "EndYearData AS (" +
                           "    SELECT AVG(loss_percentage) AS endYearPercent " +
                           "    FROM RegionLossEvent " +
                           "    WHERE year = " + endYear + " " +
                           "    AND m49code = (SELECT m49code FROM Region WHERE regionName = '" + region + "')" +
                           ") " +
                           "SELECT StartYearData.startYearPercent, EndYearData.endYearPercent, " +
                           "       (EndYearData.endYearPercent - StartYearData.startYearPercent) AS lossChange " +
                           "FROM StartYearData, EndYearData";
    
            ResultSet resultSet = statement.executeQuery(query);
    
            while (resultSet.next()) {
                Map<String, Double> row = new HashMap<>();
                row.put("startYearPercent", resultSet.getDouble("startYearPercent"));
                row.put("endYearPercent", resultSet.getDouble("endYearPercent"));
                row.put("lossChange", resultSet.getDouble("lossChange"));
    
                results.add(row);
            }
    
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return results;
    }
    
    


    // get percentage loss by country and year
    public ArrayList<LossPercent> getLossPercentages(String country, String yearString) {
        ArrayList<LossPercent> LossPercentages = new ArrayList<LossPercent>();
        // convert string to int
        int year = Integer.parseInt(yearString);

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT AVG(loss_percentage) FROM countryLossEvent GROUP BY year HAVING year =" + year + " AND m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"')";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                
                // Integer year    = results.getInt("year");
                Double lossPercentages = results.getDouble("AVG(loss_percentage)");

                // Create a Date Object
                LossPercent lossPercent = new LossPercent(lossPercentages);

                // Add the date object to the array
                LossPercentages.add(lossPercent);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the dates
        return LossPercentages;
    }

    public ArrayList<LossPercent> getLossPercentagesRegion(String region, String yearString) {
        ArrayList<LossPercent> LossPercentages = new ArrayList<LossPercent>();
        // convert string to int
        int year = Integer.parseInt(yearString);

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT AVG(loss_percentage) FROM regionLossEvent GROUP BY year HAVING year =" + year + " AND m49code = (SELECT m49code FROM region WHERE regionName = '" + region +"')";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                
                // Integer year    = results.getInt("year");
                Double lossPercentages = results.getDouble("AVG(loss_percentage)");

                // Create a Date Object
                LossPercent lossPercent = new LossPercent(lossPercentages);

                // Add the date object to the array
                LossPercentages.add(lossPercent);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the dates
        return LossPercentages;
    }

    // get percentage loss by country and year
    public ArrayList<Activity> getActivity(String country, String startYear, String endYear) {
        ArrayList<Activity> Activities = new ArrayList<Activity>();
        // convert string to int
        int year1 = Integer.parseInt(startYear);
        int year2 = Integer.parseInt(endYear);

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT activity FROM countryLossEvent WHERE year >= " + year1 + " AND year <= "+ year2 + " AND m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"') GROUP BY activity";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                
                // Integer year    = results.getInt("year");
                String activities = results.getString("activity");

                // Create a Date Object
                Activity activity = new Activity(activities);

                // Add the date object to the array
                Activities.add(activity);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the dates
        return Activities;
    }

    public ArrayList<String> getActivityRegion(String region, int startYear, int endYear) {
        ArrayList<String> activities = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT DISTINCT activity FROM RegionLossEvent WHERE m49code = (SELECT m49code FROM Region WHERE regionName = '" + region + "') " +
                           "AND year BETWEEN " + startYear + " AND " + endYear;

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                activities.add(results.getString("activity"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return activities;
    }

    // get percentage loss by country and year
    public ArrayList<FoodSupply> getFoodSupply(String country, String startYear, String endYear) {
        ArrayList<FoodSupply> FoodSupplies = new ArrayList<FoodSupply>();
        // convert string to int
        int year1 = Integer.parseInt(startYear);
        int year2 = Integer.parseInt(endYear);

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT food_supply FROM countryLossEvent HAVING year >= " + year1 + " AND year <= "+ year2 + " AND m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"') GROUP BY food_supply ";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                
                // Integer year    = results.getInt("year");
                String foodSupplies = results.getString("food_supply");

                // Create a Date Object
                FoodSupply foodSupply = new FoodSupply(foodSupplies);

                // Add the date object to the array
                FoodSupplies.add(foodSupply);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the dates
        return FoodSupplies;
    }

    public ArrayList<String> getFoodSupplyRegion(String region, int startYear, int endYear) {
        ArrayList<String> foodSupplies = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT DISTINCT food_supply FROM RegionLossEvent WHERE m49code = (SELECT m49code FROM Region WHERE regionName = '" + region + "') " +
                           "AND year BETWEEN " + startYear + " AND " + endYear;

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                foodSupplies.add(results.getString("food_supply"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return foodSupplies;
    }
    public ArrayList<String> getCauseOfLossRegion(String region, int startYear, int endYear) {
        ArrayList<String> causeOfLosses = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT DISTINCT cause_of_loss FROM RegionLossEvent WHERE m49code = (SELECT m49code FROM Region WHERE regionName = '" + region + "') " +
                           "AND year BETWEEN " + startYear + " AND " + endYear;

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                causeOfLosses.add(results.getString("cause_of_loss"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return causeOfLosses;
    }


    // why the did this change and why didnt I see this change its actually annoying (re added it so it doesnt fail)
    public ArrayList<Map<String, String>> getSimilarityData(String country, String region, String year, String similarityBy, String number) {
        ArrayList<Map<String, String>> data = new ArrayList<>();
 
        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
 
            // Construct the SQL query based on the parameters
            String query = "";
 
            if(!country.equals("none") && region.equals("none")){
                if(similarityBy.equals("one")){
                        query += "WITH chosenValue AS("
                        + "SELECT p1.countryName, d1.commodityName, ROUND(AVG(c1.loss_percentage),1) AS currentValue FROM countryLossEvent c1 JOIN commodity d1 ON c1.cpc_code = d1.cpc_code"
                        + " JOIN country p1 ON c1.m49code = p1.m49code WHERE c1.year = " + year 
                        + " AND c1.m49code = (SELECT m49code FROM country WHERE countryName = '" + country + "')"
                        + " GROUP BY d1.commodityName ORDER BY d1.commodityName LIMIT " + number + " ),"
                        + " foundValue AS (SELECT p2.countryName, d2.commodityName AS commodity, ROUND(AVG(c2.loss_percentage),1) AS comparedValue FROM countryLossEvent c2"
                        + " JOIN commodity d2 ON c2.cpc_code = d2.cpc_code"
                        + " JOIN country p2 ON c2.m49code = p2.m49code"
                        + " WHERE c2.year = " + year + " AND c2.m49code <> (SELECT m49code FROM country WHERE countryName = '" + country + "')"
                        + " GROUP BY d2.commodityName"
                        + " ORDER BY d2.commodityName"
                        + " LIMIT " + number + ")"
                        + " SELECT b.countryName AS name, commodity, currentValue AS overall, ABS(currentValue - comparedValue) AS similarity"
                        + " FROM chosenValue a"
                        + " LEFT JOIN foundValue b"
                        + " ORDER BY similarity"
                        + " LIMIT " + number;
                } else if (similarityBy.equals("two")){
                    query += "WITH chosenValue AS(SELECT p1.countryName, ROUND(AVG(c1.loss_percentage),1) AS currentValue "
                            + " FROM countryLossEvent c1"
                            + " JOIN country p1 ON c1.m49code = p1.m49code WHERE c1.year = " + year
                            + " AND c1.m49code = (SELECT m49code FROM country WHERE countryName = '" + country + "')"
                            + " GROUP BY c1.m49code"
                            + " ORDER BY c1.loss_percentage),"
                            + " foundValue AS ("
                            + " SELECT p2.countryName, ROUND(AVG(c2.loss_percentage),1) AS comparedValue"
                            + " FROM countryLossEvent c2"
                            + " JOIN country p2 ON c2.m49code = p2.m49code WHERE c2.year = " + year
                            + " AND c2.m49code <> (SELECT m49code FROM country WHERE countryName = '" + country + "')"
                            + " GROUP BY c2.m49code"
                            + " ORDER BY c2.loss_percentage)"
                            + " SELECT b.countryName AS name, (currentValue-currentValue) AS commodity, b.comparedValue AS overall, ABS(currentValue - comparedValue) AS similarity"
                            + " FROM chosenValue a"
                            + " CROSS JOIN foundValue b"
                            + " ORDER BY similarity"
                            + " LIMIT " + number;
                } else if (similarityBy.equals("three")){
                    query += "WITH chosenValue AS("
                            + " SELECT p1.countryName, d1.commodityName, ROUND(AVG(c1.loss_percentage),1) AS currentValue"
                            + " FROM countryLossEvent c1 JOIN commodity d1 ON c1.cpc_code = d1.cpc_code"
                            + " JOIN country p1 ON c1.m49code = p1.m49code WHERE c1.year = " + year
                            + " AND c1.m49code = (SELECT m49code FROM country WHERE countryName = '" + country + "')"
                            + " GROUP BY c1.m49code, d1.commodityName"
                            + " ORDER BY currentValue LIMIT " + number + "),"
                            + " foundValue AS ("
                            + " SELECT countryName, commodityName, ROUND(AVG(c2.loss_percentage),1) AS comparedValue"
                            + " FROM countryLossEvent c2 JOIN commodity d2 ON c2.cpc_code = d2.cpc_code"
                            + " JOIN country p2 ON c2.m49code = p2.m49code WHERE c2.year = " + year
                            + " AND c2.m49code <> (SELECT m49code FROM country WHERE countryName = '" + country + "')"
                            + " GROUP BY c2.m49code, d2.commodityName ORDER BY comparedValue"
                            + " LIMIT " + number + ")"
                            + " SELECT b.countryName AS name, b.commodityName AS commodity, b.comparedValue AS overall, ABS(currentValue - comparedValue) AS similarity"
                            + " FROM chosenValue a LEFT JOIN foundValue b ORDER BY similarity"
                            + " LIMIT " + number; 
                }

            } else if (!region.equals("none")){
                if(similarityBy.equals("one")){
                query+= "WITH chosenValue AS(SELECT p1.regionName, d1.commodityName, ROUND(AVG(c1.loss_percentage),1) AS currentValue"
                    + " FROM regionLossEvent c1 JOIN commodity d1 ON c1.cpc_code = d1.cpc_code"
                    + " JOIN region p1 ON c1.m49code = p1.m49code WHERE c1.year = " + year
                    + " AND p1.regionID = (SELECT regionID from region WHERE regionName = '" + region + "')"
                    + " GROUP BY d1.commodityName ORDER BY d1.commodityName"
                    + " LIMIT " + number
                    + " ),foundValue AS (SELECT p2.regionName, d2.commodityName, ROUND(AVG(c2.loss_percentage),1) AS comparedValue"
                    + " FROM regionLossEvent c2 JOIN commodity d2 ON c2.cpc_code = d2.cpc_code"
                    + " JOIN region p2 ON c2.m49code = p2.m49code WHERE c2.year = " + year
                    + " AND p2.regionID <> (SELECT regionID from region WHERE regionName = '" + region + "')"
                    + " GROUP BY d2.commodityName ORDER BY d2.commodityName"
                    + " LIMIT " + number
                    + " ) SELECT b.regionName AS name, b.commodityName AS commodity, currentValue AS overall, ABS(currentValue - comparedValue) AS similarity"
                    + " FROM chosenValue a LEFT JOIN foundValue b ORDER BY similarity"
                    + " LIMIT " + number;
                } else if (similarityBy.equals("two")){
                query+= "WITH chosenValue AS( SELECT p1.regionName, ROUND(AVG(c1.loss_percentage),1) AS currentValue FROM regionLossEvent c1"
                    + " JOIN region p1 ON c1.m49code = p1.m49code WHERE c1.year = " + year
                    + " AND p1.regionID = (SELECT regionID from region WHERE regionName = '" + region + "')"
                    + " GROUP BY c1.m49code ORDER BY c1.loss_percentage"
                    + " LIMIT " + number
                    + " ), foundValue AS (SELECT p2.regionName, ROUND(AVG(c2.loss_percentage),1) AS comparedValue FROM regionLossEvent c2"
                    + " JOIN region p2 ON c2.m49code = p2.m49code WHERE c2.year = " + year
                    + " AND p2.regionID <> (SELECT regionID from region WHERE regionName = '" + region + "')"
                    + " GROUP BY c2.m49code ORDER BY c2.loss_percentage"
                    + " LIMIT " + number
                    + " ) SELECT b.regionName AS name, (currentValue-currentValue) as commodity, b.comparedValue AS overall, ABS(currentValue - comparedValue) AS similarity"
                    + " FROM chosenValue a LEFT JOIN foundValue b ORDER BY similarity"
                    + " LIMIT " + number;
                }
            }

 
        ResultSet results = statement.executeQuery(query);
        
        while (results.next()) {
            Map<String, String> row = new HashMap<>();
            row.put("name", results.getString("name"));
            row.put("commodity", results.getString("commodity"));
            row.put("overall", results.getString("overall"));
            row.put("similarity", results.getString("similarity"));
            data.add(row);
        }
        statement.close();
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }
     
    return data;
    }
    public ArrayList<FoodGroup> getAllFoodGroups() {
        ArrayList<FoodGroup> groups = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT * FROM FoodGroup ORDER BY groupName";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String groupCode = results.getString("groupCode");
                String name = results.getString("groupName");
                FoodGroup group = new FoodGroup(groupCode, name);
                groups.add(group);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return groups;
    }

    

    public String getGroupCode(String groupName) {
        String groupCode = "";

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT groupCode FROM FoodGroup WHERE groupName = '" + groupName + "'";
            ResultSet results = statement.executeQuery(query);

            if (results.next()) {
                groupCode = results.getString("groupCode");
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return groupCode;
    }

    public String getMinYearFoodGroup(String groupCode, String chosenMinYear) {
        String year = null;

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT MIN(CLE.year) AS min_year " +
                           "FROM CountryLossEvent CLE " +
                           "JOIN Commodity C ON CLE.cpc_code = C.cpc_code " +
                           "WHERE C.groupCode = '" + groupCode + "' AND CLE.year >= " + chosenMinYear;

            ResultSet results = statement.executeQuery(query);

            if (results.next()) {
                year = results.getString("min_year");
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Min Year Query Result: " + year);
        return year;
    }

    public String getMaxYearFoodGroup(String groupCode, String chosenMaxYear) {
        String year = null;

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT MAX(CLE.year) AS max_year " +
                           "FROM CountryLossEvent CLE " +
                           "JOIN Commodity C ON CLE.cpc_code = C.cpc_code " +
                           "WHERE C.groupCode = '" + groupCode + "' AND CLE.year <= " + chosenMaxYear;

            ResultSet results = statement.executeQuery(query);

            if (results.next()) {
                year = results.getString("max_year");
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Max Year Query Result: " + year);
        return year;
    }

    public ArrayList<Map<String, Object>> getFoodGroupLossEvent(String groupCode, String minYear, String maxYear) {
        ArrayList<Map<String, Object>> results = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "WITH StartYearData AS (" +
                           "    SELECT AVG(loss_percentage) AS startYearPercent " +
                           "    FROM CountryLossEvent " +
                           "    WHERE year = " + minYear + " " +
                           "    AND cpc_code IN (SELECT cpc_code FROM Commodity WHERE groupCode = '" + groupCode + "')" +
                           "), " +
                           "EndYearData AS (" +
                           "    SELECT AVG(loss_percentage) AS endYearPercent " +
                           "    FROM CountryLossEvent " +
                           "    WHERE year = " + maxYear + " " +
                           "    AND cpc_code IN (SELECT cpc_code FROM Commodity WHERE groupCode = '" + groupCode + "')" +
                           ") " +
                           "SELECT StartYearData.startYearPercent, EndYearData.endYearPercent, " +
                           "       (EndYearData.endYearPercent - StartYearData.startYearPercent) AS lossChange " +
                           "FROM StartYearData, EndYearData";

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("startYearPercent", resultSet.getDouble("startYearPercent"));
                row.put("endYearPercent", resultSet.getDouble("endYearPercent"));
                row.put("lossChange", resultSet.getDouble("lossChange"));

                results.add(row);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return results;
    }

    public ArrayList<String> getActivityFoodGroup(String groupCode, int startYear, int endYear) {
        ArrayList<String> activities = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT DISTINCT activity " +
                           "FROM CountryLossEvent " +
                           "WHERE cpc_code IN (SELECT cpc_code FROM Commodity WHERE groupCode = '" + groupCode + "') " +
                           "AND year BETWEEN " + startYear + " AND " + endYear;

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                activities.add(results.getString("activity"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return activities;
    }

    public ArrayList<String> getFoodSupplyFoodGroup(String groupCode, int startYear, int endYear) {
        ArrayList<String> foodSupplies = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT DISTINCT food_supply " +
                           "FROM CountryLossEvent " +
                           "WHERE cpc_code IN (SELECT cpc_code FROM Commodity WHERE groupCode = '" + groupCode + "') " +
                           "AND year BETWEEN " + startYear + " AND " + endYear;

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                foodSupplies.add(results.getString("food_supply"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return foodSupplies;
    }

    public ArrayList<String> getCauseOfLossFoodGroup(String groupCode, int startYear, int endYear) {
        ArrayList<String> causeOfLosses = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT DISTINCT cause_of_loss " +
                           "FROM CountryLossEvent " +
                           "WHERE cpc_code IN (SELECT cpc_code FROM Commodity WHERE groupCode = '" + groupCode + "') " +
                           "AND year BETWEEN " + startYear + " AND " + endYear;

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                causeOfLosses.add(results.getString("cause_of_loss"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return causeOfLosses;
    }
    public ArrayList<Commodity> getAllFoodCommodities() {
        ArrayList<Commodity> commodities = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT * FROM commodity ORDER BY commodityName;";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String groupCode = results.getString("groupCode");
                String cpc_code = results.getString("cpc_code");
                String name = results.getString("CommodityName");

                Commodity commodity = new Commodity(groupCode, cpc_code, name);
                commodities.add(commodity);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return commodities;
    }

    public String getGroupCodeByCommodity(String commodity) {
        String groupCode = "";

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT groupCode FROM Commodity WHERE CommodityName = '" + commodity + "'";
            ResultSet results = statement.executeQuery(query);

            if (results.next()) {
                groupCode = results.getString("groupCode");
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return groupCode;
    }

    public ArrayList<Map<String, Object>> findSimilarFoodGroups(String groupCode, String similarityBy, int numGroups) {
        ArrayList<Map<String, Object>> similarGroups = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String valueQueryPart = "";
            if (similarityBy.equals("Ratio")) {
                valueQueryPart = "(SUM(CASE WHEN loss_percentage IS NOT NULL THEN loss_percentage ELSE 0 END) / SUM(CASE WHEN loss_percentage IS NOT NULL THEN loss_percentage ELSE 0 END))";
            } else if (similarityBy.equals("Highest loss")) {
                valueQueryPart = "MAX(loss_percentage)";
            } else if (similarityBy.equals("Lowest loss")) {
                valueQueryPart = "MIN(loss_percentage)";
            }

            String query = "WITH GroupValue AS (" +
                    "    SELECT groupCode, " + valueQueryPart + " AS value " +
                    "    FROM CountryLossEvent CLE " +
                    "    JOIN Commodity C ON CLE.cpc_code = C.cpc_code " +
                    "    WHERE C.groupCode = '" + groupCode + "' " +
                    "    GROUP BY C.groupCode " +
                    "), " +
                    "SimilarGroups AS (" +
                    "    SELECT C.groupCode, FG.groupName, " + valueQueryPart + " AS value, " +
                    "           ABS(GroupValue.value - " + valueQueryPart + ") AS similarity " +
                    "    FROM CountryLossEvent CLE " +
                    "    JOIN Commodity C ON CLE.cpc_code = C.cpc_code " +
                    "    JOIN FoodGroup FG ON C.groupCode = FG.groupCode " +
                    "    CROSS JOIN GroupValue " +
                    "    WHERE C.groupCode != '" + groupCode + "' " +
                    "    GROUP BY C.groupCode " +
                    ") " +
                    "SELECT groupCode, groupName, value, similarity " +
                    "FROM SimilarGroups " +
                    "ORDER BY similarity " +
                    "LIMIT " + numGroups;

            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("groupCode", results.getString("groupCode"));
                row.put("groupName", results.getString("groupName"));
                row.put("value", results.getDouble("value"));
                row.put("similarity", results.getDouble("similarity"));
                similarGroups.add(row);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return similarGroups;
    }
}
