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

public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.1</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='stylesheet3.css' />";
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
                <h1>Subtask 2.A</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Get the ArrayList
        ArrayList<Integer> dates = getAllDates();
        ArrayList<String> countryNames = getAllCountries();


        // the form
        // country dropdown
        html = html + """
            <form action = '/page2A.html' method='post'">
            <div class="dropdownCountry">

                <label for = 'countryList_drop'> Select the country:</label>
                <select id="countryList_drop" name="countryList_drop">
        """;

        // add options for country
                    for (String name : countryNames) {
                        html = html + "<option value='" + name + "'>" + name + "</option>";
                    }
        html = html + """
                </select>
            </div>
            <button type='submit' id = "button" class='btn'>Enter to get years</button>
        """;

        // start year drop down
        html = html + """
            <div class="dropdownStartYear">
                <label for = 'startYear_drop'> Select the start year:</label>
                <select id="startYear_drop" name="startYear_drop">
        """;
                    for (int year : dates) {
                        html = html + "<option value='" + year + "'>" + year + "</option>";
                    }
        html = html + """
                </select>
            </div>   
        """;

        // end year dropdown
        html = html + """
            <div class="dropdownEndYear">
                <label for = 'endYear_drop'> Select the end year:</label>
                <select id="endYear_drop" name="endYear_drop">
        """;
                    for (int year : dates) {
                        html = html + "<option value='" + year + "'>" + year + "</option>";
                    }
        html = html + """
                </select>
            </div>   
            <button type='submit' id = "button" class='btn'>submit</button></form>
        """;
        
        // radio buttons for filter
        html = html + """

                <div class='columns'>
                    
                    <form id='columnsForm'>
                        <label for='showActivity'>Activity</label>
                        <input type='checkbox' id='showActivity' name='showActivity' checked>
                        <label for='showSupplyStage'>Supply Stage </label>
                        <input type='checkbox' id='showSupplyStage' name='showSupplyStage' checked>
                        <label for='showCauseOfLoss'>Cause of Loss</label>
                        <input type='checkbox' id='showCauseOfLoss' name='showCauseOfLoss' checked>
                        <button type='button' onclick='updateColumns()'>Update</button>
                    </form>
                </div>

        """;

        html = html + """
                <div class='results'>
                    <table>
                        <thead>
                            <tr>
                                <th>Start Year Loss %</th>
                                <th>End Year Loss %</th>
                                <th>Loss change %</th>
                                <th>Activity</th>
                                <th>Supply Stage</th>
                                <th id = 'lossTable'>Cause of loss</th>
                            </tr>
                        </thead>
                        <tbody id='resultsBody'>
                        """;

        String countryList_drop = context.formParam("countryList_drop");
        String endYear_drop = context.formParam("endYear_drop");
        String startYear_drop = context.formParam("startYear_drop");

        // ArrayList<CountryEvent> countryLossEvents = new ArrayList<CountryEvent>();
        // if (startYear_drop == null || endYear_drop == null) {
        //     // If NULL, nothing to show, therefore we make some "no results" HTML
        //     html = html + "<p><i>No Results to show for dropbox</i></p>";
        // } else {
        //     countryLossEvents.add(countryEvent(countryList_drop, endYear_drop, startYear_drop));

        //     for(int i = 0; i < countryLossEvents.size(); i++){
                
        //         html = html + "<tr>";
        //         html = html + "<td>" + countryLossEvents.get(i).getStartYearLoss() + "</td>";
        //         html = html + "<td>" + countryLossEvents.get(i).getEndYearLoss() + "</td>";
        //         html = html + "<td>" + countryLossEvents.get(i).getChangeOfLoss() + "</td>";
        //         html = html + "<td>" + countryLossEvents.get(i).getActivity() + "</td>";
        //         html = html + "<td>" + countryLossEvents.get(i).getFoodSupply() + "</td>";
        //         html = html + "<td>" + countryLossEvents.get(i).getStartYearLoss() + "</td>";
        //         html = html + "</tr>";
        //     }

        // }
        JDBCConnection jdbc = new JDBCConnection();
        // ArrayList <CountryEvent> allResults = getAllCountryEvent(countryList_drop, endYear_drop, startYear_drop);
        
        ArrayList<Map<String, Double>> results = jdbc.getCountryLossEvent(countryList_drop, endYear_drop, startYear_drop);
        if (startYear_drop == null || endYear_drop == null || results == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            html = html + "<p><i>No Results to show for dropbox</i></p>";
        } else {
            for (Map<String, Double > row : results) {
                html = html + "<tr>";
                html = html + "<td>" + row.get("startYearPercent") + "</td>";
                html = html + "<td>" + row.get("endYearPercent") + "</td>";
                html = html + "<td>" + row.get("lossChange") + "</td>";
            }
            
            html = html + "<td>" + outputActivity(countryList_drop, startYear_drop, endYear_drop) + "</td>";
            html = html + "<td>" + outputFoodSupply(countryList_drop, startYear_drop, endYear_drop) + "</td>";
            html = html + "<td>" + outputCauseOfLoss(countryList_drop, startYear_drop, endYear_drop) + "</td>";
            html = html + "</tr>";
            
        }

        html = html + """
                        </tbody>
                    </table>
                </div>
        """;
       

        html = html + """
            <script>
                function updateColumns() {
                    const showActivity = document.getElementById('showActivity').checked;
                    const showSupplyStage = document.getElementById('showSupplyStage').checked;
                    const showCauseOfLoss = document.getElementById('showCauseOfLoss').checked;
 
                    document.querySelectorAll('td:nth-child(4), th:nth-child(4)').forEach(cell => {
                        cell.style.display = showActivity ? '' : 'none';
                    });
 
                    document.querySelectorAll('td:nth-child(5), th:nth-child(5)').forEach(cell => {
                        cell.style.display = showSupplyStage ? '' : 'none';
                    });

                    document.querySelectorAll('td:nth-child(6), th:nth-child(6)').forEach(cell => {
                        cell.style.display = showCauseOfLoss ? '' : 'none';
                    });
                }
            </script>
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

    public String outputActivity(String country, String startYear, String endYear) {
        // convert string to int
        int year1 = Integer.parseInt(startYear);
        int year2 = Integer.parseInt(endYear);

        String html = "";
        // html = html + "<h2>" + country + " Year:" + year + "</h2>";
        // Look up movies from JDBC
        ArrayList<String> activities = getActivity(country, year1, year2);
        
        // Add HTML
        for (String activity : activities) {
            html = html + "<p style = 'text-align: left'>" + activity + "<br></p>";
        }
        return html;
    }

    public String outputFoodSupply(String country, String startYear, String endYear) {
        // convert string to int
        int year1 = Integer.parseInt(startYear);
        int year2 = Integer.parseInt(endYear);

        String html = "";
        // html = html + "<h2>" + country + " Year:" + year + "</h2>";
        // Look up movies from JDBC
        ArrayList<String> foodSupplies = getFoodSupply(country, year1, year2);
        
        // Add HTML
        for (String foodSupply : foodSupplies) {
            html = html + "<p style = 'text-align: left'>" + foodSupply + "<br></p>";
        }
        return html;
    }

    public String outputCauseOfLoss(String country, String startYear, String endYear) {
        // convert string to int
        int year1 = Integer.parseInt(startYear);
        int year2 = Integer.parseInt(endYear);

        String html = "";
        // html = html + "<h2>" + country + " Year:" + year + "</h2>";
        // Look up movies from JDBC
        ArrayList<String> causeOfLosses = getCauseOfLoss(country, year1, year2);
        
        // Add HTML
        for (String causeOfLoss : causeOfLosses) {
            html = html + "<p style = 'text-align: left'>" + causeOfLoss + "<br></p>";
        }
        return html;
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

    public ArrayList<Integer> getDatesByCountry(String country) {
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
            String query = "SELECT year FROM countryLossEvent GROUP BY year HAVING m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"')";
            
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


    // get percentage loss by country and year
    public ArrayList<String> getActivity(String country, int startYear, int endYear) {
        ArrayList<String> Activities = new ArrayList<String>();
        // convert string to int
        // int year1 = Integer.parseInt(startYear);
        // int year2 = Integer.parseInt(endYear);

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT activity FROM countryLossEvent WHERE year >= " + startYear + " AND year <= "+ endYear + " AND m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"') GROUP BY activity";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                
                // Integer year    = results.getInt("year");
                String activities = results.getString("activity");

                // Create a Date Object
                // Activity activity = new Activity(activities);

                // Add the date object to the array
                Activities.add(activities);
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

    public ArrayList<String> getFoodSupply(String country, int startYear, int endYear) {
        ArrayList<String> FoodSupply = new ArrayList<String>();
        // convert string to int
        // int year1 = Integer.parseInt(startYear);
        // int year2 = Integer.parseInt(endYear);

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT food_supply FROM countryLossEvent WHERE year >= " + startYear + " AND year <= "+ endYear + " AND m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"') GROUP BY food_supply";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                // Integer year    = results.getInt("year");
                String foodSupplies = results.getString("food_supply");

                // Create a Date Object
                // Activity activity = new Activity(activities);

                // Add the date object to the array
                FoodSupply.add(foodSupplies);
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
        return FoodSupply;
    }

    public ArrayList<String> getCauseOfLoss(String country, int startYear, int endYear) {
        ArrayList<String> CauseOfLoss = new ArrayList<String>();
        // convert string to int
        // int year1 = Integer.parseInt(startYear);
        // int year2 = Integer.parseInt(endYear);

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT cause_of_loss FROM countryLossEvent WHERE year >= " + startYear + " AND year <= "+ endYear + " AND m49code = (SELECT m49code FROM country WHERE countryName = '" + country +"') GROUP BY cause_of_loss";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                // Integer year    = results.getInt("year");
                String losses = results.getString("cause_of_loss");

                // Create a Date Object
                // Activity activity = new Activity(activities);

                // Add the date object to the array
                CauseOfLoss.add(losses);
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
        return CauseOfLoss;
    }

    // public CountryEvent countryEvent(String country, String startYear, String endYear) {
        
    //     JDBCConnection jdbc = new JDBCConnection();
    //     ArrayList<Map<String, Double>> results = jdbc.getCountryLossEvent(country, startYear, endYear);
        
    //     double startLoss = 0;
    //     double endLoss = 0;
    //     double changeLoss = 0;
    //     String activity;
    //     String foodSupply;
    //     String causeLoss;

    //     for (Map<String, Double> row : results) {
    //         startLoss = row.get("startYearPercent");
    //         endLoss = row.get("endYearPercent");
    //         changeLoss = row.get("lossChange");
    //     }
        
    //     activity = outputActivity(country, startYear, endYear);
    //     foodSupply = outputFoodSupply(country, startYear, endYear);
    //     causeLoss = outputCauseOfLoss(country, startYear, endYear);
        
    //     CountryEvent event = new CountryEvent(startLoss, endLoss, changeLoss, activity, foodSupply, causeLoss);

    //     // Finally we return all of the dates
    //     return event;
    // }


}
