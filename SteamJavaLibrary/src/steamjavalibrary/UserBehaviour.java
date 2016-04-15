/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 *
 * @author Jonnie
 */
public class UserBehaviour {
    public int hypescale; //0-100, chance to buy new releases
    public int variationscale; //0-100, variation in games
    private Map<String, Integer> genrespectrum = new HashMap();

    public UserBehaviour() {
        Random r = new Random();
        hypescale = r.nextInt(100);
        variationscale = r.nextInt(100);
        String[] genres = SteamJavaLibrary.data.genreslist;
        for(int i=0;i<genres.length;i++){
            genrespectrum.put(genres[i], r.nextInt(100));
        }
    }
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
    
}
