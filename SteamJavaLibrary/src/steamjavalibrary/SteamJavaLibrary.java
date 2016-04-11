/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author joona
 */
public class SteamJavaLibrary {

    public static String[] genreslist = {"rpg","mmo","fps","casual","adventure","arcade","rts"};
    public static  GamesFromJson allgames = new GamesFromJson();
    
    public static void main(String[] args) {
        //create list from json
        
        try(Reader reader = new InputStreamReader
        (SteamJavaLibrary.class.getResourceAsStream
        ("steamgames.json"), "UTF-8")){
            
            Gson gson = new GsonBuilder().create();
            JsonElement jelement = new JsonParser().parse(reader);
            /*
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jarray = jobject.getAsJsonArray("app");
            */
            
            allgames = gson.fromJson(jelement, GamesFromJson.class);
            System.out.println(allgames.getApps().size());
        } catch(Exception e){
            System.out.println("error "+e);
        }
        
        
        //running some tests   
        SteamUser user1 = new SteamUser(1,"Jonnie");
        SteamUser user2 = new SteamUser(2,"Joona");
        
        System.out.println(user1.getName()+" omistaa "+user1.getOwnedgames().get(0).getName()+" pelin");
        System.out.println("Sillä on arvostelu: "+allgames.getApps().get(allgames.getApps().indexOf(user1.getOwnedgames().get(0))).getReview());
        System.out.println("Hänen lempigenre on "+user1.getBehaviour().getFavGenre()+"\n");
        
        System.out.println(user2.getName()+" omistaa "+user2.getOwnedgames().get(0).getName()+" pelin");
        System.out.println("Sillä on arvostelu: "+allgames.getApps().get(allgames.getApps().indexOf(user2.getOwnedgames().get(0))).getReview());
        System.out.println("Hänen lempigenre on "+user2.getBehaviour().getFavGenre());
        
        
        
    }
    
}
