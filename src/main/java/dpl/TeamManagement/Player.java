package dpl.TeamManagement;

import dpl.DplConstants.GeneralConstants;
import dpl.NewsSystem.InjuryPublisher;
import dpl.NewsSystem.NewsSubscriber;

public class Player implements IPlayerInfo {

    private String playerName;
    private String position;
    private boolean captain;
    private int age;
    private int skating;
    private int shooting;
    private int checking;
    private int saving;
    private boolean isInjured;
    private boolean retireStatus;
    private int daysInjured;

    public Player() {
        super();
    }

    public Player(String playerName, String position, boolean captain, int age, int skating, int shooting, int checking,
                  int saving, boolean isInjured, boolean retireStatus, int daysInjured) {
        super();
        this.playerName = playerName;
        this.position = position;
        this.captain = captain;
        this.age = age;
        this.skating = skating;
        this.shooting = shooting;
        this.checking = checking;
        this.saving = saving;
        this.isInjured = isInjured;
        this.retireStatus = retireStatus;
        this.daysInjured = daysInjured;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isCaptain() {
        return captain;
    }

    public void setCaptain(boolean captain) {
        this.captain = captain;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSkating() {
        return skating;
    }

    public void setSkating(int skating) {
        this.skating = skating;
    }

    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public int getChecking() {
        return checking;
    }

    public void setChecking(int checking) {
        this.checking = checking;
    }

    public int getSaving() {
        return saving;
    }

    public void setSaving(int saving) {
        this.saving = saving;
    }

    public boolean isInjured() {
        return isInjured;
    }

    public void setInjured(boolean isInjured) {
        this.isInjured = isInjured;
    }

    public boolean isRetireStatus() {
        return retireStatus;
    }

    public void setRetireStatus(boolean retireStatus) {
        this.retireStatus = retireStatus;
    }

    public int getDaysInjured() {
        return daysInjured;
    }

    public void setDaysInjured(int daysInjured) {
        this.daysInjured = daysInjured;
    }

    @Override
    public double getPlayerStrength(Player player) {

        double strength = 0.0;
        String position = player.getPosition();
        int skating = player.getSkating();
        int shooting = player.getShooting();
        int checking = player.getChecking();
        int saving = player.getSaving();
        boolean isInjured = player.isInjured();

        if (position.equalsIgnoreCase(GeneralConstants.FORWARD.toString())) {
            strength = skating + shooting + (checking / 2.0);
        } else if (position.equalsIgnoreCase(GeneralConstants.DEFENSE.toString())) {
            strength = skating + checking + (shooting / 2.0);
        } else if (position.equalsIgnoreCase(GeneralConstants.GOALIE.toString())) {
            strength = skating + saving;
        }

        if (isInjured) {
            return strength / 2;
        } else {
            return strength;
        }
    }

}
