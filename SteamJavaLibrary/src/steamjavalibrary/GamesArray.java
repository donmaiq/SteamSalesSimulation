package steamjavalibrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Container for the different SteamGame ArrayLists.
 * @author Jonnie
 */
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
    
    /**
     * Empty constructor for json to populate.
     */
    public GamesArray(){
    }
    
    /**
     * Custom controller that populates genre ArrayLists after the main list has been sorted.
     */
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
        genreControl.put("mmo", genremmo);
        genreControl.put("fps", genrefps);
        genreControl.put("casual", genrecasual);
        genreControl.put("adventure", genreadventure);
        genreControl.put("arcade", genrearcade);
        genreControl.put("rts", genrerts);
    }
    
    /**
     * Getter for Array of all games.
     * @return ArrayList of all SteamGames
     */
    public ArrayList<SteamGame> getApps() {
        return app;
    }
    /**
     * Getter for ArrayLists of specified genre.
     * @param string genre to fetch.
     * @return  ArrayList of SteamGames by parameter genre.
     */
    public ArrayList<SteamGame> getGenreArray(String string){
        return genreControl.get(string);
    }
    /**
     * Adds games to Array SteamGame.
     * @param game game to be added.
     */
    public void addApps(SteamGame game) {
        this.app.add(game);
    }
    /**
     * Sorts games in app Array based on their review.
     */
    public void sortGames(){
        Collections.sort(app);
    }
}
