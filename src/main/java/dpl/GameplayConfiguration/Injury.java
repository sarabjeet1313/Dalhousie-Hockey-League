package dpl.GameplayConfiguration;

public class Injury {
    double randomInjuryChance;
    int injuryDaysLow;
    int injuryDaysHigh;

    public Injury(double randomInjuryChance, int injuryDaysLow, int injuryDaysHigh) {
        this.randomInjuryChance = randomInjuryChance;
        this.injuryDaysLow = injuryDaysLow;
        this.injuryDaysHigh = injuryDaysHigh;
    }

    public double getRandomInjuryChance() {
        return randomInjuryChance;
    }

    public void setRandomInjuryChance(double randomInjuryChance) {
        this.randomInjuryChance = randomInjuryChance;
    }

    public int getInjuryDaysLow() {
        return injuryDaysLow;
    }

    public void setInjuryDaysLow(int injuryDaysLow) {
        this.injuryDaysLow = injuryDaysLow;
    }

    public int getInjuryDaysHigh() {
        return injuryDaysHigh;
    }

    public void setInjuryDaysHigh(int injuryDaysHigh) {
        this.injuryDaysHigh = injuryDaysHigh;
    }

}
