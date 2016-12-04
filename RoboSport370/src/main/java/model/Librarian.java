package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.enums.RobotType;

public class Librarian {
    private List<RobotAI> robots;

    public Librarian() {
        robots = new ArrayList<>();
        loadRobots();
    }

    /**
     * Load the robots from the website
     */
    private void loadRobots() {
        // Grab the robots from gpu0.usask.ca
        JSONArray array = getRobotList();
        if (array == null) {
            System.err.println("Couldn't get Robots from gpu0.usask.ca");
            return;
        }

        // Loop through the list and add the robots to robots
        for (Object robotJson : array) {
            JSONObject curRobotJson = (JSONObject) ((JSONObject) robotJson).get("script");

            RobotAI curRobot = RobotAI.getBuilder(true)
                    .get(getRobotType(curRobotJson))
                    .build(curRobotJson);

            // Add the stats to the robot.
            curRobot.setStats(getRobotStats(curRobotJson));

            // Add other attributes
            curRobot.setName((String) curRobotJson.get("name"));
            curRobot.setTeam((String) curRobotJson.get("team"));

            robots.add(curRobot);
        }
    }

    private RobotType getRobotType(JSONObject robot) {
        switch ((String) robot.get("class")) {
            case "Scout":
                return RobotType.SCOUT;
            case "Sniper":
                return RobotType.SNIPER;
            case "Tank":
                return RobotType.TANK;
            default:
                return null;
        }
    }

    /**
     * Get the stats from the specified {@code JSONObject} {@code Robot}
     *
     * @param robot the robot to get the stats of
     * @return {@link Stats} for the specified {@code Robot}
     */
    private Stats getRobotStats(JSONObject robot) {
        Stats stats = new Stats();

        stats.setWins((long) robot.get("wins"));
        stats.setLosses((long) robot.get("losses"));

        stats.setLived((long) robot.get("lived"));
        stats.setDied((long) robot.get("died"));

        stats.setExecutions((long) robot.get("executions"));
        stats.setKilled((long) robot.get("killed"));

        stats.setMoved((long) robot.get("moved"));
        stats.setMatches((long) robot.get("matches"));
        stats.setAbsorbed((long) robot.get("absorbed"));

        return stats;
    }

    /**
     * Get the list of all of the robots from the website
     *
     * @return the {@link JSONArray} of {@code Robot}s or {@code null} if an error occurred.
     */
    private JSONArray getRobotList() {
        JSONParser parser = new JSONParser();

        String json = "";
        try {
            String host = "10.81.6.35";
            Socket socket = new Socket(host, 20001);
            OutputStream os = socket.getOutputStream();
            os.write("{\"list-request\":{\"data\":\"full\"}}\n".getBytes());
            os.flush();

            InputStream is = socket.getInputStream();
            int ch;
            while ((ch = is.read()) != -1)
                json += (char) ch;
            socket.close();

            return (JSONArray) parser.parse(json);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<RobotAI> getRobots() {
        return robots;
    }
}
