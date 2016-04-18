/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.util.Enumeration;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author Jonnie
 */
public class Data {
    public static  GamesArray allgames = new GamesArray();
    public static  UsersArray allusers = new UsersArray();
    public static String[] genreslist = {"rpg","mmo","fps","casual","adventure","arcade","rts"};
    
    
    public Data(){
        Gson gson = new GsonBuilder().create();
        //TODO READ USERS FROM allusers.txt.gz
        
        try(ZipFile zipFile = new ZipFile(new File("src/steamjavalibrary/allgames.zip")) ){
            System.out.println("\nLoading Users...");
            Enumeration<? extends ZipEntry> entries = zipFile.entries();         
            ZipEntry entry = entries.nextElement();
            InputStream stream = zipFile.getInputStream(entry);
            Reader read = new InputStreamReader(stream, "UTF-8");
            /*
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jarray = jobject.getAsJsonArray("app");
            */
            allgames = gson.fromJson(read, GamesArray.class);
            read.close();
            System.out.println(allgames.getApps().size()+" games loaded.");
        } catch(Exception e){
            System.out.println("error "+e);
        }
        
       
        try(ZipFile zipFile = new ZipFile(new File("src/steamjavalibrary/allusers.zip")) ){
            System.out.println("\nLoading Users...");
            Enumeration<? extends ZipEntry> entries = zipFile.entries();         
            ZipEntry entry = entries.nextElement();
            InputStream stream = zipFile.getInputStream(entry);
            Reader read = new InputStreamReader(stream, "UTF-8");
            //JsonElement jelement = new JsonParser().parse(usersjson);
           
            /*
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jarray = jobject.getAsJsonArray("users");
            System.out.print(jarray.size());
            System.out.print(jarray.get(1000).getAsString());
            */
            
            allusers = gson.fromJson(read, UsersArray.class);
            read.close();
            stream.close();
            System.out.println(allusers.getUsers().size()+" users loaded.\n");
        } catch(Exception e){
            System.out.println("error "+e);
        }
    }
}
