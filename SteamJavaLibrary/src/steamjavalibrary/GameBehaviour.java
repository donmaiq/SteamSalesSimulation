/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jonnie
 */
public class GameBehaviour {
    private int salerating; //0-100, chance for a sale
    private int[] salescale = new int[3];
    private int pricetimescale; //0-100, variation in price over time
    private int pricedropamount; //0-50 %, how much price drops per iteration

    public GameBehaviour() {
        salerating = (int) Math.random()*100;
        salescale[0] = (int) Math.ceil(Math.random()*5)*5;
        salescale[1] = (int) Math.ceil(Math.random()*5+5)*5;
        salescale[2] = (int) Math.ceil(Math.random()*5+15)*5;
        pricetimescale = (int) Math.random()*100;
        pricedropamount = (int) Math.random()*50;
    }
}
