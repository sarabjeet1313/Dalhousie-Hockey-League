package dal.asd.dpl.TeamManagement;

public class Coach {

	private String coachName;
	private double skating;
	private double shooting;
	private double checking;
	private double saving;
	
	public Coach(String coachName, double skating, double shooting, double checking, double saving) {
		super();
		this.coachName = coachName;
		this.skating = skating;
		this.shooting = shooting;
		this.checking = checking;
		this.saving = saving;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public double getSkating() {
		return skating;
	}

	public void setSkating(double skating) {
		this.skating = skating;
	}

	public double getShooting() {
		return shooting;
	}

	public void setShooting(double shooting) {
		this.shooting = shooting;
	}

	public double getChecking() {
		return checking;
	}

	public void setChecking(double checking) {
		this.checking = checking;
	}

	public double getSaving() {
		return saving;
	}

	public void setSaving(double saving) {
		this.saving = saving;
	}
	
}
