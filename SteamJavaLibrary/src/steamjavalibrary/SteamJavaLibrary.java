package steamjavalibrary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author joona
 */
public class SteamJavaLibrary extends Application{
    public static Data data;
    
    /**
     * Runs the UI for the application.
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        data = new Data();
        Parent root = FXMLLoader.load(getClass().getResource("ui.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    /**
     * The main for the project.
     * Launches the UI.
     * @param args 
     */
    public static void main(String[] args){
        launch(args);
    }
}
