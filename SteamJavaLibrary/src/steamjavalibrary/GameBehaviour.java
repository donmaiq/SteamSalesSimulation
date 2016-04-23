/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;
import java.lang.Math;
import java.util.Random;

/**
 * A class containing information of a games behaviour.
 * @author Jonnie
 */
public class GameBehaviour {
    private int salerating; //0-100, chance for a sale
    private int[] salescale = new int[3];
    private int pricetimescale; //0-100, variation in price over time
    private int pricedropamount; //0-50 %, how much price drops per iteration

    public GameBehaviour() {
        Random r = new Random();
        salerating = r.nextInt(100);
        salescale[0] = (int) Math.ceil(r.nextDouble()*5)*5; //5-25
        salescale[1] = (int) Math.ceil(r.nextDouble()*5+5)*5; //30-50
        salescale[2] = (int) Math.ceil(r.nextDouble()*8+12)*5; //65-80
        pricetimescale = r.nextInt(100); 
        pricedropamount = (int) r.nextGaussian()*15+25; //normal dist. 10-40, 25peak.
    }
    public int getPricedropamount() {
        return pricedropamount;
    }
    public int getPricetimescale(){
        return pricetimescale;
    }
    public int[] getSalescale(){
        return salescale;
    }
    public int getSalerating(){
        return salerating;
    }
    
}
