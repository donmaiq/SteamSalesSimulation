/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamjavalibrary;

import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

//testing utils
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author joona
 */
public class SteamJavaLibrary extends Application{
    public static Data data = new Data();
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        Group root = new Group();
        Scene scene = new Scene(root, 800, 500);
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        BorderPane borderPane = new BorderPane();
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setCenter(tabPane);
        
        primaryStage.setScene(scene);
        //scene.getStylesheets().add(SteamJavaLibrary.class.getResource("Steam.css").toExternalForm());
        primaryStage.setTitle("Steam Sales Simulator");
        root.getChildren().add(borderPane);
        
        //FIRST TAB
            Tab tab1 = new Tab();
            tab1.setText("Simulation");
                GridPane grid1 = new GridPane();
                grid1.setAlignment(Pos.CENTER);
                grid1.setHgap(10);
                grid1.setVgap(10);
                grid1.setPadding(new Insets(5, 5, 5, 5));
                
                Button simbut = new Button();
                simbut.setText("Start");
                HBox hbsimbut = new HBox(10);  
                hbsimbut.setAlignment(Pos.CENTER);
                simbut.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if(simbut.getText().equals("Start")){
                            simbut.setText("Stop");
                            startsimulation(1);
                        }else{
                            simbut.setText("Start");                            
                            startsimulation(0);
                        }
                    }
                });
                hbsimbut.getChildren().add(simbut);
                grid1.add(hbsimbut, 4,9,2,1);
                grid1.add(textField, 0,1,8,5);
                textField.setDisable(true);
            tab1.setContent(grid1);
            tabPane.getTabs().add(tab1);
        //END OF FIRST TAB
        
        //Second TAB
            Tab tab2 = new Tab();
            tab2.setText("Statistics");
                GridPane grid2 = new GridPane();
                grid2.setAlignment(Pos.CENTER);
                grid2.setHgap(10);
                grid2.setVgap(10);
                grid2.setPadding(new Insets(5, 5, 5, 5));
            tab2.setContent(grid2);
            tabPane.getTabs().add(tab2);
        //END OF SECOND TAB
        
        //THIRD TAB
            Tab tab3 = new Tab();
            tab3.setText("Users");
                GridPane grid3 = new GridPane();
                grid3.setAlignment(Pos.CENTER);
                grid3.setHgap(10);
                grid3.setVgap(10);
                grid3.setPadding(new Insets(5, 5, 5, 5));
                
                Image image = new Image("https://users.metropolia.fi/~jonniek/uploads/steamlogo-white.png", 150, 50, false, false);
                final ImageView imv = new ImageView();
                imv.setImage(image);
                final HBox pictureRegion = new HBox(10);
                pictureRegion.getChildren().add(imv);
                pictureRegion.setAlignment(Pos.CENTER);
                grid3.add(pictureRegion, 0,1,2,1);

                final ImageView ava = new ImageView();
                final HBox avaRegion = new HBox(10);
                avaRegion.getChildren().add(ava);
                avaRegion.setId("profileimg");
                grid3.add(avaRegion, 0, 2);

                Text username = new Text("");
                username.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                username.setId("text");
                grid3.add(username, 1, 2);

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
                grid3.add(hbBtn, 0,3,2,1);
            tab3.setContent(grid3);
            tabPane.getTabs().add(tab3);
        //END OF THIRD TAB
        
        grid3.setGridLinesVisible(true);
        //grid1.setGridLinesVisible(true);
        primaryStage.show();
    }
    static TextArea textField = new TextArea();
    
    public static void appendText(String str) {
        Platform.runLater(() -> textField.appendText(str));
    }
    
    static Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), 
            event -> appendText("test\n")));
    
    public static void startsimulation(int args){
        if(args==1){   
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }else{
            timeline.stop();
        }
    }
    public static String[] Printrandom(){
        Random r = new Random();
        SteamUser user1 = data.allusers.getUsers().get(r.nextInt(data.allusers.getUsers().size()));
        String[] returni = new String[2];
        returni[0] = user1.getPersonaname();
        returni[1] = user1.getAvatar();
        return returni;
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
