package app;

import java.util.ArrayList;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;

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

public class PageST3B implements Handler {

    public static final String URL = "/page3B.html";
    // my eyes hurt from this time - 3:00am
    // sleepiness got me dead - 4:30am
    @Override
    public void handle(Context context) throws Exception {
        String html = "<html>";
        html += "<head>" + 
                "<title>Subtask 3.B</title>" +
                "<link rel='stylesheet' type='text/css' href='stylesheet6.css' />" +
                "</head>";

        html += "<body>";
        html += "<div class='container'>";  // Added container div

        html += """
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

        html += """
            <div class='header'>
                <h1>Subtask 3.B</h1>
            </div>
        """;

        html += "<div class='content'>";

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<Commodity> commodities = jdbc.getAllFoodCommodities();

        html += "<div class='form-wrapper'>";  // Added form-wrapper div
        html += "<form action='/page3B.html' method='post'>";
        html += "<label for='commodity'>Select Food Commodity:</label>";
        html += "<select id='commodity' name='commodity'>";
        for (Commodity commodity : commodities) {
            html += "<option value='" + commodity.getName() + "'>" + commodity.getName() + "</option>";
        }
        html += "</select>";

        html += "<label for='similarityBy'>Select Similarity By:</label>";
        html += "<select id='similarityBy' name='similarityBy'>";
        html += "<option value='Ratio'>Ratio of Food Loss to Food Waste</option>";
        html += "<option value='Highest loss'>Highest Percentage of Food Loss/Waste</option>";
        html += "<option value='Lowest loss'>Lowest Percentage of Food Loss/Waste</option>";
        html += "</select>";

        html += "<label for='numGroups'>Number of Similar Groups to Find:</label>";
        html += "<input type='number' id='numGroups' name='numGroups' min='1' max='10' value='5'>";
        html += "<button type='submit'>Submit</button>";
        html += "</form>";
        html += "</div>";  // Close form-wrapper div

        String selectedCommodity = context.formParam("commodity");
        String similarityBy = context.formParam("similarityBy");
        String numGroupsStr = context.formParam("numGroups");

        if (selectedCommodity != null && similarityBy != null && numGroupsStr != null) {
            int numGroups = Integer.parseInt(numGroupsStr);

            String groupCode = jdbc.getGroupCodeByCommodity(selectedCommodity);
            ArrayList<Map<String, Object>> similarGroups = jdbc.findSimilarFoodGroups(groupCode, similarityBy, numGroups);

            if (!similarGroups.isEmpty()) {
                html += "<div class='results-wrapper'>";  // Added results-wrapper div
                html += "<h2>Results</h2>";
                html += "<table class='results'>";
                html += "<thead>";
                html += "<tr>";
                html += "<th>Group Code</th>";
                html += "<th>Group Name</th>";
                html += "<th>Value</th>";
                html += "<th>Similarity</th>";
                html += "</tr>";
                html += "</thead>";
                html += "<tbody>";

                for (Map<String, Object> row : similarGroups) {
                    html += "<tr>";
                    html += "<td>" + row.get("groupCode") + "</td>";
                    html += "<td>" + row.get("groupName") + "</td>";
                    html += "<td>" + row.get("value") + "</td>";
                    html += "<td>" + row.get("similarity") + "</td>";
                    html += "</tr>";
                }

                html += "</tbody>";
                html += "</table>";
                html += "</div>";  // Close results-wrapper div
            } else {
                html += "<p>No similar groups found.</p>";
            }
        }

        html += "</div>";

        html += "</div>";  // Close container div

        html += "</body>" + "</html>";

        context.html(html);
    }

}
