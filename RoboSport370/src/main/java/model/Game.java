package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

import model.enums.BoardSize;
import model.enums.TeamColour;

public class Game {
    private Board board;
    private List<Team> teams;
    private GameTime gameTime;

    public Game(BoardSize boardSize, int teamCount){
        this.board = new Board(boardSize);
        this.teams = initTeams(teamCount);
        // TODO Replace with RobotThingTime in milliseconds
        this.gameTime = new GameTime(10000);
    }

    private List<Team> initTeams(int teamCount){
        if(teamCount == 2){
            teams.add(new Team (TeamColour.RED, true));
            teams.add(new Team (TeamColour.GREEN, true));
            teams.add(new Team (TeamColour.YELLOW, false));
            teams.add(new Team (TeamColour.BLUE, false));
            teams.add(new Team (TeamColour.ORANGE, false));
            teams.add(new Team (TeamColour.PURPLE, false));
        }
        else if(teamCount == 3){
            teams.add(new Team (TeamColour.RED, true));
            teams.add(new Team (TeamColour.GREEN, false));
            teams.add(new Team (TeamColour.YELLOW, true));
            teams.add(new Team (TeamColour.BLUE, true));
            teams.add(new Team (TeamColour.ORANGE, false));
            teams.add(new Team (TeamColour.PURPLE, false));
        }
        else if(teamCount == 6){
            teams.add(new Team (TeamColour.RED, true));
            teams.add(new Team (TeamColour.GREEN, true));
            teams.add(new Team (TeamColour.YELLOW, true));
            teams.add(new Team (TeamColour.BLUE, true));
            teams.add(new Team (TeamColour.ORANGE, true));
            teams.add(new Team (TeamColour.PURPLE, true));
        }
        else {
            System.err.printLn("Team count value is invalid.");
        }
    }

    public Team getTeam(TeamColour colour){
        return teams.stream()
                .filter(Team::isEnabled)
                .filter(t -> t.getColour().equals(colour))
                .findFirst()
                .orElse(null);
    }

    public int getRemainingTeams(){
        return (int) teams.stream()
                .filter(Team::isEnabled)
                .filter(t -> t.remainingRobots() > 0)
                .count();
    }

    public Board getBoard() {
        return board;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public GameTime getGameTime() {
        return gameTime;
    }
}
