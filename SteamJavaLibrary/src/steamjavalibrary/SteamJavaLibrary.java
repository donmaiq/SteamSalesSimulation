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
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
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
    public static  GamesArray allgames = new GamesArray();
    public static  UsersArray allusers = new UsersArray();
    
    public static void main(String[] args){
        //create list from json
        
        Gson gson = new GsonBuilder().create();
        //TODO READ USERS FROM allusers.txt.gz
        
        try(Reader reader = new InputStreamReader
        (SteamJavaLibrary.class.getResourceAsStream
        ("steamgames.json"), "UTF-8")){
            System.out.println("Loading Games...");
            JsonElement jelement = new JsonParser().parse(reader);
            reader.close();
            /*
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jarray = jobject.getAsJsonArray("app");
            */
            
            allgames = gson.fromJson(jelement, GamesArray.class);
            System.out.println(allgames.getApps().size()+" games loaded.");
        } catch(Exception e){
            System.out.println("error "+e);
        }
        
        try(FileReader read = new FileReader("C:/Downloads/allusers.json")){
            System.out.println("\nLoading Users...");
        /*try(GZIPInputStream read = new GZIPInputStream(new FileInputStream("C:/Downloads/allusers.txt.gz")) ){
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int bytes_read;
            byte[] buffer = new byte[32768];
            long c=0;
            while((bytes_read = read.read(buffer)) > 0){
                c++;
                out.write(buffer, 0, bytes_read);
            }
            System.out.print(c);
            String usersjson = out.toString("UTF-8"); */
            //JsonElement jelement = new JsonParser().parse(usersjson);
           
            /*
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jarray = jobject.getAsJsonArray("users");
            System.out.print(jarray.size());
            System.out.print(jarray.get(1000).getAsString());
            */
            
            allusers = gson.fromJson(read, UsersArray.class);
            read.close();
            System.out.println(allusers.getUsers().size()+" users loaded.\n");
        } catch(Exception e){
            System.out.println("error "+e);
        }
        
                
        //TEST LOOP
        Random r = new Random();
        Scanner lukija = new Scanner(System.in);
        while(true){
            SteamUser user1 = allusers.getUsers().get(r.nextInt(allusers.getUsers().size()));
            System.out.println(user1.getPersonaname()+" owns "+user1.getOwnedgames().size()+" games:");
            for(int i=0;i<user1.getOwnedgames().size();i++){
                System.out.println(user1.getOwnedgames().get(i).getName());               
                System.out.print("\tReview:"+user1.getOwnedgames().get(i).getReview()+"\n\tGenres:");
                for(int a=0;a<user1.getOwnedgames().get(i).getGenres().size();a++){
                    System.out.print(user1.getOwnedgames().get(i).getGenres().get(a));
                    if(a+1!=user1.getOwnedgames().get(i).getGenres().size()) System.out.print(",");
                }
                System.out.print("\n");
            }
            System.out.println("His favorite genre is "+user1.getBehaviour().getFavGenre()+"\n");
            String x = lukija.nextLine();
            if(x.equals("stop")) break;
        }
        
        
        
    }
    
}
