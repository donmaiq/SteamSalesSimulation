/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;

import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

//testing utils
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author joona
 */
public class SteamJavaLibrary extends Application{
    public static Data data = new Data();
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        Image image = new Image("https://users.metropolia.fi/~jonniek/uploads/steamlogo-white.png", 150, 50, false, false);
        final ImageView imv = new ImageView();
        imv.setImage(image);
        final HBox pictureRegion = new HBox(10);
        pictureRegion.getChildren().add(imv);
        pictureRegion.setAlignment(Pos.CENTER);
        grid.add(pictureRegion, 0,0,2,1);
        
        final ImageView ava = new ImageView();
        final HBox avaRegion = new HBox(10);
        avaRegion.getChildren().add(ava);
        avaRegion.setId("profileimg");
        grid.add(avaRegion, 0, 1);
        
        Text username = new Text("");
        username.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        username.setId("text");
        grid.add(username, 1, 1);
        
        
        Button btn = new Button();
        btn.setText("print random user in console");
        HBox hbBtn = new HBox(10);   
        hbBtn.setAlignment(Pos.CENTER);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String[] userl = Printrandom();
                username.setText(userl[0]);
                Image newavatar = new Image(userl[1], 0, 40, false, false);
                ava.setImage(newavatar);
            }
        });
        hbBtn.getChildren().add(btn);
        hbBtn.setId("button");
        grid.add(hbBtn, 0,2,2,1);
        
        grid.setGridLinesVisible(true);
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        //scene.getStylesheets().add(SteamJavaLibrary.class.getResource("Steam.css").toExternalForm());
        primaryStage.show();
    }
    public static String[] Printrandom(){
        Random r = new Random();
        SteamUser user1 = data.allusers.getUsers().get(r.nextInt(data.allusers.getUsers().size()));
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
        String[] returni = new String[2];
        returni[0] = user1.getPersonaname();
        returni[1] = user1.getAvatar();
        return returni;
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
