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
        this.gameTime = new GameTime();
    }

    private List<Team> initTeams(int teamCount){
        // TODO Game.InitTeams()
        throw new NotImplementedException();
    }

    public Team getTeam(TeamColour colour){
        return teams.stream()
                .filter(t -> t.getColour().equals(colour))
                .findFirst()
                .orElse(null);
    }

    public int getRemainingTeams(){
        return (int) teams.stream()
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
