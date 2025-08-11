package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='stylesheet1.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // navigation bar
        html = html + """
            <header class = "header">
            <div class="logo">
                <p><a href='/'>GRP177</a></p>
            </div>
            <nav>
                <ul>
                <li><a href='page3B.html'>Find Similar Food</a></li>
                <li><a href='page3A.html'>Find Similar Locations</a></li>
                <li><a href='page2B.html'>Explore By Food</a></li>
                <li><a href='page2A.html'>Explore By Country</a></li>
                <li><a href='mission.html'>About Us</a></li>

                <li><p> | </p> </li>
                <li style="float:right"><a href='/'>Homepage</a></li>
                </ul>
            </nav>
            </header>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // textbox
        html = html + """
        <section>
            <div class="textBox">
            <h2> FOOD LOSS AND FOOD WASTE </h2>
            <br>
            <p>
                Everywhere around the world there is constant food loss and food waste.
                Come and explore our website where you can find more information about
                food loss and waste change by country and food groups, identify locations
                with similar food loss/waste percentages and exploring Food Commodities and Groups.
                <br><br>
        """;

        html = html + "Did you know in a single year the MAXIMUM percentage food and waste loss of " + getRice() + " is " + getMaxLossOfRice() + "?";

        ArrayList<Integer> activities = getMinYear();

        html = html + " The year range is from " + activities.get(0) + "-" + activities.get(1) + " for available data so come and explore to find out more!";
        

        html = html + """
                </p>
            </div>
        """;

       

        // min year and max year
        // Get the ArrayList of Strings of all countries
        // ArrayList<Integer> years = getMinMaxYear();

        // // Add HTML for the country list
        // html = html + "<ul>";

        // // Finally we can print out all of the countries
        // for (int year : years) {
        //     html = html + "<li>" + year + "</li>";
        // }

        // // Finish the List HTML
        // html = html + "</ul>";

        // image
        html = html + """
            </section>
            <aside class="images">
                <img src= "deepaiIMG.png" alt="img">
            </aside>
        """;


        // Footer
        html = html + """
            <div class='footer'>
                <p>COSC2803 - Studio Project Starter Code (Apr24)</p>
            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    // public String outputMinYear() {
    //     // convert string to int
    //     String html = "";
    //     // html = html + "<h2>" + country + " Year:" + year + "</h2>";
    //     // Look up movies from JDBC
    //     ArrayList<Integer> activities = getMinYear();
        
    //     // Add HTML
    //     html = html + "<ul>";
    //     for (int activity : activities) {
    //         html = html + "<li>" + activity + "<li>";
    //     }
    //     html = html + "</ul>";
    //     return html;
    // }
    /**
     * Get the names of the countries in the database.
     */
    public ArrayList<String> getAllCountries() {
        // Create the ArrayList of String objects to return
        ArrayList<String> countries = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM country";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String countryName  = results.getString("countryName");

                // Add the country object to the array
                countries.add(countryName);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
            //e.printStackTrace();
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
                //e.printStackTrace();
            }
        }

        // Finally we return all of the countries
        return countries;
    }

    public ArrayList<Integer> getMinYear() {
        // Create the ArrayList of String objects to return
        ArrayList<Integer> dates = new ArrayList<Integer>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select min(year), max(year) FROM date";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int year  = results.getInt("min(year)");

                // Add the country object to the array
                dates.add(year);

                int year2  = results.getInt("max(year)");
                dates.add(year2);

            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
            //e.printStackTrace();
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
                //e.printStackTrace();
            }
        }

    //     // Finally we return all of the countries
        return dates;
    }

    public String getMaxLossOfRice() {
        // Create the ArrayList of String objects to return
        String maxLoss = ""; 
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT MAX(loss_percentage) FROM countryLossEvent WHERE cpc_code = 113";
                           
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            
            maxLoss  += results.getString("MAX(loss_percentage)");

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
            //e.printStackTrace();
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
                //e.printStackTrace();
            }
        }

    //     // Finally we return all of the countries
        return maxLoss;
    }

    public String getRice() {
        // Create the ArrayList of String objects to return


        // Setup the variable for the JDBC connection
        Connection connection = null;
        String maxLoss = ""; 
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select commodityName from commodity where cpc_code = 113";
                           
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            
            maxLoss += results.getString("commodityName");


            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
            //e.printStackTrace();
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
                //e.printStackTrace();
            }
        }

    //     // Finally we return all of the countries
        return maxLoss;
    }
}
