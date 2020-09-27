package dal.asd.dpl.teammanagement;

import java.util.List;

public class FreeAgents {
	private List<Players> freePlayerList;

	public FreeAgents(List<Players> freePlayerList) {
		this.freePlayerList = freePlayerList;
	}

	public List<Players> getFreePlayerList() {
		return freePlayerList;
	}

	public void setFreePlayerList(List<Players> freePlayerList) {
		this.freePlayerList = freePlayerList;
	}
}
