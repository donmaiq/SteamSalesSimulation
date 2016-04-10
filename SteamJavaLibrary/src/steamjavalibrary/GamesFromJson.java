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
    private ArrayList<JsonNameId> apps = new ArrayList();
    
    public ArrayList<JsonNameId> getApps() {
        return apps;
    }
    public void setApps(JsonNameId apps) {
        this.apps.add(apps);
    }
    
}
