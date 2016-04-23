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

    public UserBehaviour() {
        Random r = new Random();
        hypescale = r.nextInt(100);
        variationscale = r.nextInt(101);
        genrespectrum = new HashMap();
        String[] genres = {"rpg","mmo","fps","casual","adventure","arcade","rts"};
        for(int i=0;i<genres.length;i++){
            genrespectrum.put(genres[i], r.nextInt(100));
        }
    }
    /**
     * returns the string of the favourite genre of the user.
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
    
    public int getHypescale() {
        return hypescale;
    }
    
    public int getVariationscale() {
        return variationscale;
    }
    
    public Map<String, Integer> getGenrespectrum() {
        return genrespectrum;
    }
    
    
}
