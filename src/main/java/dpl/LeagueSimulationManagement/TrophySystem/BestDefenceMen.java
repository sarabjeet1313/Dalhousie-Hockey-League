package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class BestDefenceMen extends Subject{
    private static BestDefenceMen instance;
    private Player bestDefenceMen;

    private BestDefenceMen(){ }

    public static BestDefenceMen getInstance(){
        if(instance == null){
            instance = new BestDefenceMen();
        }
        return  instance;
    }

    public void notifyWhenPlayerGoal(Player player){
        setValue("player", player);
        notifyAllObservers();
    }

    public Player getBestDefenceMen() {
        return bestDefenceMen;
    }

    public void setBestDefenceMen(Player bestDefenceMen) {
        this.bestDefenceMen = bestDefenceMen;
    }
}
