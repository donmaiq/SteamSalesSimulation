/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math;
/**
 *
 * @author Jonnie
 */
public class UserBehaviour {
    public int hypescale; //0-100, chance to buy new releases
    public int variationscale; //0-100, variation in games
    private Map<String, Integer> genrespectrum = new HashMap();

    public UserBehaviour() {
        hypescale = (int) Math.random()*100;
        variationscale = (int) Math.random()*100;
        String[] genres = {"rpg","mmo","fps","casual","adventure"};
        for(int i=0;i<genres.length;i++){
            int value= (int) Math.random()*100;
            genrespectrum.put(genres[i], value);
        }
    }
   
    
}
