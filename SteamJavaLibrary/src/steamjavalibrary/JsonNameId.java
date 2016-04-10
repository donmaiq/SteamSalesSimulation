/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
/**
 *
 * @author Jonnie
 */
public class JsonNameId {
    private int appid;
    private String name;
    
    public JsonNameId(){
    }
    
    public JsonNameId(int appid, String name) {
        this.appid = appid;
        this.name = name;
    }
}
