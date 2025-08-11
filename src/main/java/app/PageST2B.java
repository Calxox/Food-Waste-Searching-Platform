package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PageST2B implements Handler {

    public static final String URL = "/page2B.html";

    @Override
    public void handle(Context context) throws Exception {
        String html = "<html>";
        html += "<head>" + 
                "<title>Explore by Food</title>" +
                "<link rel='stylesheet' type='text/css' href='stylesheet4.css' />" +
                "</head>";
        html += "<body>";

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
                <h1>Subtask 2.B</h1>
            </div>
        """;

        html += "<div class='content'>";

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<FoodGroup> groupNames = jdbc.getAllFoodGroups();
        ArrayList<Date> dates = jdbc.getAllDates();

        html += """
            <form action='/page2B.html' method='post'>
            <div class="dropdowngroup">
                <label for='groupList_drop'>Select the food group:</label>
                <select id="groupList_drop" name="groupList_drop">
        """;

        for (FoodGroup name : groupNames) {
            html += "<option value='" + name.getGroupCode() + "'>" + name.getGroupName() + "</option>";
        }
        html += """
                </select>
            </div>
            <div class="dropdownStartYear">
                <label for='startYear_drop'>Select the start year:</label>
                <select id="startYear_drop" name="startYear_drop">
        """;
        
        for (Date year : dates) {
            html += "<option value='" + year.getYear() + "'>" + year.getYear() + "</option>";
        }
        html += """
                </select>
            </div>
            <div class="dropdownEndYear">
                <label for='endYear_drop'>Select the end year:</label>
                <select id="endYear_drop" name="endYear_drop">
        """;
        
        for (Date year : dates) {
            html += "<option value='" + year.getYear() + "'>" + year.getYear() + "</option>";
        }
        html += """
                </select>
            </div>
            <button type='submit' class='btn'>Submit</button>
            </form>
        """;

        String groupCode = context.formParam("groupList_drop");
        String startYear = context.formParam("startYear_drop");
        String endYear = context.formParam("endYear_drop");

        

        if (groupCode != null && startYear != null && endYear != null) {
            String minYear = jdbc.getMinYearFoodGroup(groupCode, startYear);
            String maxYear = jdbc.getMaxYearFoodGroup(groupCode, endYear);


            if (minYear != null && maxYear != null) {
                ArrayList<Map<String, Object>> results = jdbc.getFoodGroupLossEvent(groupCode, minYear, maxYear);

                if (!results.isEmpty()) {
                    html += """
                        <div class='columns'>
                            <form id='columnsForm'>
                                <label for='showActivity'>Activity</label>
                                <input type='checkbox' id='showActivity' name='showActivity' checked>
                                <label for='showSupplyStage'>Supply Stage</label>
                                <input type='checkbox' id='showSupplyStage' name='showSupplyStage' checked>
                                <label for='showCauseOfLoss'>Cause of Loss</label>
                                <input type='checkbox' id='showCauseOfLoss' name='showCauseOfLoss' checked>
                                <button type='button' onclick='updateColumns()'>Update</button>
                            </form>
                        </div>
                        <div class='results'>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Start Year Loss %</th>
                                        <th>End Year Loss %</th>
                                        <th>Loss Change %</th>
                                        <th>Activity</th>
                                        <th>Supply Stage</th>
                                        <th>Cause of Loss</th>
                                    </tr>
                                </thead>
                                <tbody id='resultsBody'>
                    """;

                    for (Map<String, Object> row : results) {
                        html += "<tr>";
                        html += "<td>" + row.get("startYearPercent") + "</td>";
                        html += "<td>" + row.get("endYearPercent") + "</td>";
                        html += "<td>" + row.get("lossChange") + "</td>";

                        List<String> activities = jdbc.getActivityFoodGroup(groupCode, Integer.parseInt(startYear), Integer.parseInt(endYear));
                        html += "<td>" + String.join(", ", activities) + "</td>";

                        List<String> foodSupplies = jdbc.getFoodSupplyFoodGroup(groupCode, Integer.parseInt(startYear), Integer.parseInt(endYear));
                        html += "<td>" + String.join(", ", foodSupplies) + "</td>";

                        List<String> causesOfLoss = jdbc.getCauseOfLossFoodGroup(groupCode, Integer.parseInt(startYear), Integer.parseInt(endYear));
                        html += "<td>" + String.join(", ", causesOfLoss) + "</td>";

                        html += "</tr>";
                    }

                    html += """
                                </tbody>
                            </table>
                        </div>
                    """;
                } else {
                    html += "<p>No results to show for the selected food group and years.</p>";
                }
            } else {
                html += "<p>No data available for the selected food group and years.</p>";
            }
        }

        html += """
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

        html += "</div>";
        html += "</body></html>";

        context.html(html);
    }
}
