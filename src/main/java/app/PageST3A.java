package app;

import java.util.ArrayList;
import java.util.Map;

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

public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.1</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='stylesheet5.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

         // navigation bar
         html = html + """
            <header>
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

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>Subtask 3.A</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";



        ArrayList<Integer> dates = getAllDates();
        ArrayList<String> countryNames = getAllCountries();
        ArrayList<String> regionNames = getAllRegions();


        // the form
        // country/region dropdown
        html = html + """
            <form action = '/page3A.html' method='post'">
            <div class="dropdownCountry">
                <label for = 'countryList_drop'> Select the Country</label>
                <select id="countryList_drop" name="countryList_drop">
                <option value = none> none </option>
                
        """;

        // add options for country
                    for (String name : countryNames) {
                        html = html + "<option value='" + name + "'>" + name + "</option>";
                    }
        html = html + """
                    
                </select>
            </div>
            <div class="dropdownRegion">

                <label for = 'regionList_drop'> Select the Region</label>
                <select id="regionList_drop" name="regionList_drop">
                <option value = none> none </option>
        """;

        // add options for region
                    for (String name : regionNames) {
                        html = html + "<option value='" + name + "'>" + name + "</option>";
                    }
        html = html + """
                </select>
            </div>
        """;

        // year dropdown
        html = html + """
            <div class="dropdownYear">
                <label for = 'year_drop'> Select the Year:</label>
                <select id="year_drop" name="year_drop">
        """;
                    for (int year : dates) {
                        html = html + "<option value='" + year + "'>" + year + "</option>";
                    }
        html = html + """
                </select>
            </div>   
        """;

        // similarity dropdown
        html = html + """
            <div class="dropdownSimilarity">
                <label for = 'similarity_drop'> Similarity by:</label>
                <select id="similarity_drop" name="similarity_drop">
                <option value = one>The foods products they have in common</option>
                <option value = two>The overall percentage of food loss/waste </option>
                <option value = three>Both common foods products and their loss/waste percentage (for country only)</option>
                </select>
            </div>   
        """;

        // html = html + """
        //     <div class="dropdownSimilarity">
        //         <label for = 'similarity_drop'> Similarity by:</label>
        //         <select id="similarity_drop" name="similarity_drop">
        //         <option value = one>one</option>
        //         <option value = two>two</option>
        //         <option value = three>three</option>
        //         </select>
        //     </div>   
        // """;


        // number dropdown
        // html = html + """
        //     <div class="dropdownNumber">
        //         <label for = 'number_drop'> Similarity by:</label>
        //         <select id="number_drop" name="number_drop">
        // """;
        //             for (int i = 1; i <= 100; i++) {
        //                 html = html + "<option value='" + i + "'>" + i + "</option>";
        //             }
        // html = html + """
        //         </select>

        //         <input class='form-control' id='movietype_textbox' name='movietype_textbox'>"
        //     </div>   
        //     <button type='submit' id = "button" class='btn'>submit</button></form>
        // """;
        
         // number dropdown
         html = html + """
            <div class="dropdownNumber">
                <label for = 'number_textbox'> Number of Similariy</label>

                <input id='number_textbox' name='number_textbox'>
            </div>   
            <button type='submit' id = "button" class='btn'>submit</button></form>
        """;

        html = html + """
                <div class='results'>
                    <table>
                        <thead>
                            <tr>
                                <th>Place</th>
                                <th>Food type</th>
                                <th>Overall Loss</th>
                                <th>Similarity</th>
                            </tr>
                        </thead>
                        <tbody id='resultsBody'>
                        """;

        String countryList_drop = context.formParam("countryList_drop");
        String regionList_drop = context.formParam("regionList_drop");
        String year_drop = context.formParam("year_drop");
        String similarity_drop = context.formParam("similarity_drop");
        String number_textbox = context.formParam("number_textbox");

        JDBCConnection jdbc = new JDBCConnection();
        

        if (countryList_drop == null || regionList_drop == null || similarity_drop == null && number_textbox == null &&  year_drop == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            html = html + "<p style = 'padding: 5px'><i>Enter data to find similar locations of food loss and waste along with food types!</i></p>";
        } else {
            ArrayList<Map<String, String>> results = jdbc.getSimilarityData(countryList_drop, regionList_drop, year_drop, similarity_drop, number_textbox);
            for (Map<String, String > row : results) {
                html = html + "<tr>";
                html = html + "<td>" + row.get("name") + "</td>";
                html = html + "<td>" + row.get("foodGroup") + "</td>";
                html = html + "<td>" + row.get("overall") + "</td>";
                html = html + "<td>" + row.get("similarity") + "</td>";
                html = html + "</tr>";
            }
            
        }
    

        html = html + """
                        </tbody>
                    </table>
                </div>
        """;

        // Close Content div
        html = html + "</div>";

        // Footer
        // html = html + """
        //     <div class='footer'>
        //         <p>COSC2803 - Studio Project Starter Code (Apr24)</p>
        //     </div>
        // """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    

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
            String query = "SELECT * FROM country ORDER BY countryName;";
            
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

    public ArrayList<String> getAllRegions() {
        ArrayList<String> regions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE)) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT * FROM Region ORDER BY regionName;";
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

    /**
     * Get the dates in the database.
     */
    public ArrayList<Integer> getAllDates() {
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
            String query = "SELECT * FROM date";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int year  = results.getInt("year");

                // Add the country object to the array
                dates.add(year);
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
        return dates;
    }

}
