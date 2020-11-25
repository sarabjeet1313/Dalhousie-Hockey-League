package dpl.SerializeDeserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dpl.DplConstants.FileConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.Standing;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;

public class StandingSerializationDeserialization implements IStandingsPersistance {
    private int season;

    public StandingSerializationDeserialization() {

    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getSeason() {
        return season;
    }

    public boolean insertToStandings(Standing standing)  throws IOException {
        boolean isSerialized = Boolean.FALSE;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer fileWriter = null;
        URL url = null;
        try {
            url = getClass().getClassLoader().getResource(FileConstants.STANDING_SERIALIZATION_FILE.toString());
            fileWriter = new FileWriter(url.getFile());
            gson.toJson(standing, fileWriter);
            fileWriter.close();
            isSerialized = Boolean.TRUE;
        } catch (IOException e) {
            isSerialized = Boolean.FALSE;
            throw e;
        } finally {
            fileWriter.close();
        }
        return isSerialized;
    }
}


