package dpl.LeagueSimulationManagement.TrophySystem.data;

public class TrophyState {
    private String trophyName;
    private String awardedTeam;
    private String awardedPerson;
    private String awardCategory;
    private int awardedYear;

    public String getTrophyName() {
        return trophyName;
    }

    public void setTrophyName(String trophyName) {
        this.trophyName = trophyName;
    }

    public String getAwardedTeam() {
        return awardedTeam;
    }

    public void setAwardedTeam(String awardedTeam) {
        this.awardedTeam = awardedTeam;
    }

    public String getAwardedPerson() {
        return awardedPerson;
    }

    public void setAwardedPerson(String awardedPerson) {
        this.awardedPerson = awardedPerson;
    }

    public String getAwardCategory() {
        return awardCategory;
    }

    public void setAwardCategory(String awardCategory) {
        this.awardCategory = awardCategory;
    }

    public int getAwardedYear() {
        return awardedYear;
    }

    public void setAwardedYear(int awardedYear) {
        this.awardedYear = awardedYear;
    }
}
