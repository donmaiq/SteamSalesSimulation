package steamjavalibrary;

import java.util.ArrayList;

public class GamesArray {
    private ArrayList<SteamGame> app = new ArrayList();
    
    public ArrayList<SteamGame> getApps() {
        return app;
    }
    
    public void addApps(SteamGame apps) {
        this.app.add(apps);
    }
    
}
