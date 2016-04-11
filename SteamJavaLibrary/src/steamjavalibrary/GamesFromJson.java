/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.util.ArrayList;

/**
 *
 * @author Jonnie
 */
public class GamesFromJson {
    private ArrayList<SteamGame> app = new ArrayList();
    
    public ArrayList<SteamGame> getApps() {
        return app;
    }
    public void addApps(SteamGame apps) {
        this.app.add(apps);
    }
    
}
