/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.GsonBuilder;
import java.util.zip.GZIPInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

//testing utils
import java.util.Scanner;

/**
 *
 * @author joona
 */
public class SteamJavaLibrary {

    public static String[] genreslist = {"rpg","mmo","fps","casual","adventure","arcade","rts"};
    public static  GamesFromJson allgames = new GamesFromJson();
    public static  UsersArray allusers = new UsersArray();
    
    public static void main(String[] args){
        //create list from json
        
        Gson gson = new GsonBuilder().create();
        //TODO READ USERS FROM allusers.txt.gz
        
        try(Reader reader = new InputStreamReader
        (SteamJavaLibrary.class.getResourceAsStream
        ("steamgames.json"), "UTF-8")){
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
        
                try(FileReader read = new FileReader("C:/Downloads/allusers.json")){
        //try(GZIPInputStream read = new GZIPInputStream(new FileInputStream("allusers.txt.gz"))){
            JsonElement jelement = new JsonParser().parse(read);
           
            /*
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jarray = jobject.getAsJsonArray("users");
            System.out.print(jarray.size());
            System.out.print(jarray.get(1000).getAsString());
            */
            
            allusers = gson.fromJson(jelement, UsersArray.class);
            System.out.println(allusers.getUsers().get(12584).getPersonaname());
        } catch(Exception e){
            System.out.println("error "+e);
        }
        
                
        //TEST LOOP
        Random r = new Random();
        Scanner lukija = new Scanner(System.in);
        while(true){
            String x = lukija.nextLine();
            SteamUser user1 = allusers.getUsers().get(r.nextInt(allusers.getUsers().size()));
            System.out.println(user1.getPersonaname()+" omistaa "+user1.getOwnedgames().get(0).getName()+" pelin");
            System.out.println("Sillä on arvostelu: "+allgames.getApps().get(allgames.getApps().indexOf(user1.getOwnedgames().get(0))).getReview());
            System.out.println("Hänen lempigenre on "+user1.getBehaviour().getFavGenre()+"\n");
        }
        
        
        
    }
    
}
