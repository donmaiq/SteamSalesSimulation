package steamjavalibrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GamesArray {
    private ArrayList<SteamGame> app = new ArrayList();
    
    private final ArrayList<SteamGame> genrerpg = new ArrayList();
    private final ArrayList<SteamGame> genremmo = new ArrayList();
    private final ArrayList<SteamGame> genrefps = new ArrayList();
    private final ArrayList<SteamGame> genrecasual = new ArrayList();
    private final ArrayList<SteamGame> genreadventure = new ArrayList();
    private final ArrayList<SteamGame> genrearcade = new ArrayList();
    private final ArrayList<SteamGame> genrerts = new ArrayList();
    private final Map<String, ArrayList<SteamGame>> genreControl = new HashMap();
    
    public void setupLists(){
        for(int i=0;i<getApps().size();i++){
            if(getApps().get(i).getGenre().equals("rpg")){genrerpg.add(getApps().get(i));}
            else if(getApps().get(i).getGenre().equals("mmo")){genremmo.add(getApps().get(i));}
            else if(getApps().get(i).getGenre().equals("fps")){genrefps.add(getApps().get(i));}
            else if(getApps().get(i).getGenre().equals("casual")){genrecasual.add(getApps().get(i));}
            else if(getApps().get(i).getGenre().equals("adventure")){genreadventure.add(getApps().get(i));}
            else if(getApps().get(i).getGenre().equals("arcade")){genrearcade.add(getApps().get(i));}
            else if(getApps().get(i).getGenre().equals("rts")){genrerts.add(getApps().get(i));}
        }
        genreControl.put("rpg", genrerpg);
        genreControl.put("mmo", genrerpg);
        genreControl.put("fps", genrefps);
        genreControl.put("casual", genrecasual);
        genreControl.put("adventure", genreadventure);
        genreControl.put("arcade", genrearcade);
        genreControl.put("rts", genrerts);
    }
    
    public ArrayList<SteamGame> getApps() {
        return app;
    }
    public ArrayList<SteamGame> getGenreArray(String string){
        return genreControl.get(string);
    }
    public void addApps(SteamGame apps) {
        this.app.add(apps);
    }
    
    public void sortGames(){
        Collections.sort(app);
    }
}
