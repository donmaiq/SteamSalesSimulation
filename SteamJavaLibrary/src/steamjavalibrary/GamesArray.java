package steamjavalibrary;

import java.util.ArrayList;
import java.util.Collections;

public class GamesArray {
    private ArrayList<SteamGame> app = new ArrayList();
    
    public ArrayList<SteamGame> getApps() {
        return app;
    }
    
    public void addApps(SteamGame apps) {
        this.app.add(apps);
    }
    
    public void sortGames(){
        Collections.sort(app);
    }
}
