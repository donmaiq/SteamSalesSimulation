package steamjavalibrary;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * A class that specifies a users behaviour.
 * @author Jonnie
 */
public class UserBehaviour {
    private final int hypescale; //0-100, chance to buy new releases
    private final int variationscale; //0-100, variation in games
    private final Map<String, Integer> genrespectrum;
    /**
     * Gives users their own behaviour (user that likes a certain genre is more likely to buy games from that genre)
     */
    public UserBehaviour() {
        Random r = new Random();
        hypescale = r.nextInt(101);
        variationscale = r.nextInt(101);
        genrespectrum = new HashMap();
        String[] genres = {"rpg","mmo","fps","casual","adventure","arcade","rts"};
        for(int i=0;i<genres.length;i++){
            genrespectrum.put(genres[i], r.nextInt(101));
        }
    }
    /**
     * returns the string of the favorite genre of the user.
     * @return 
     */
    public String getFavGenre(){
        int max = 0;
        String fav = null;
        for(Map.Entry<String,Integer> pari : genrespectrum.entrySet()){
            if(pari.getValue()>max){max=pari.getValue(); fav = pari.getKey();}
        }
        return fav;
    }
    /**
     * Getter for user's hypescale.
     * @return 
     */
    public int getHypescale() {
        return hypescale;
    }
    /**
     * Getter for user's variationscale.
     * @return 
     */
    public int getVariationscale() {
        return variationscale;
    }
    /**
     * Getter for Map genrespectrum.
     * @return 
     */
    public Map<String, Integer> getGenrespectrum() {
        return genrespectrum;
    }
    
    
}
