package app;

import java.util.ArrayList;

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

public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html += "<head>";
        html += "<title>Our Mission</title>";
        html += "<link rel='stylesheet' type='text/css' href='stylesheet2.css' />";
        html += "</head>";

        // Add the body
        html += "<body>";

        // Navigation bar
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

        // Add Div for page Content
        html += "<div class='content'>";

        // Main content
        html += """
        <section>
            <div class="textBox">
            <h2> ABOUT US </h2>
            <br>
            <div>
            <p>
                We are a company that is trying to show the world how much people have wasted over the years.
                We need you to become the best person to show this information to so use this information to make
                the correct choices for your daily needs. We want to reduce any waste possible by providing you the
                most up to date data and the best data to change your lives for the better, providing this information
                requires you to change the way you live your lives and make gives us a reason to improve our
                data overtime and provide the most updated data to suit you the user's needs.
            </p>
            </div>
            <div>
            <h3> How To Use Website</h3>
            <p>
                The purpose of this website is to provide statistics and tools to assist the user to learn and understand
                more information about food groups and food commodities.
            </p>
            </div>
            <div>
            <h3>Who Is Intended</h3>
            <p>
                The purpose of this website is to provide statistics and tools to assist the user to learn and understand
                more information about food groups and food commodities.
            </p>
            </div>
        </section>
        """;

        // Add persona details
        html += "<aside class='images'>";
        html += "<table>";
        html += "<tr>";
        html += "<h2> Meet The Personas</h2>";

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<Persona> personas = jdbc.getAllPersonas();

        for (Persona persona : personas) {
            html += "<div class='persona'>";
            html += "<img src='" + persona.getPersonaImg() + "' alt='" + persona.getName() + "' class='persona-img'>";
            html += "<div class='persona-details'>";
            html += "<p><strong>Name:</strong> " + persona.getName() + "</p>";
            html += "<p><strong>Description:</strong> " + persona.getDescription() + "</p>";
            html += "<p><strong>Goals:</strong> " + persona.getNeedsAndGoals() + "</p>";
            html += "<p><strong>Needs:</strong> " + persona.getNeedsAndGoals() + "</p>";
            html += "<p><strong>Skills & Experiences:</strong> " + persona.getSkillsAndExperiences() + "</p>";
            html += "</div>";
            html += "</div>";
        }

        html += "</tr>";
        html += "</table>";
        html += "</aside>";

        // Close Content div
        html += "</div>";

        // Footer
        html += """
            <div class='footer'>
                <p>Calvin Bayno O'Flaherty (s4093869@student.rmit.edu.au)
                    Vivian Tran (s4090043@student.rmit.edu.au)
                </p>
            </div>
        """;

        // Finish the HTML webpage
        html += "</body>";
        html += "</html>";

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }
}