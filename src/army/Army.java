/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package army;

import Database.DBModel;
import Function.Function;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafx.stage.StageStyle.TRANSPARENT;

/**
 *
 * @author Rexnet
 */
public class Army extends Application {

    Function function = new Function();

//    public Army() {
//
//        DBModel model = new DBModel();
//        model.createDataBase();
//    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(Function.HOME));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Monitoring App");
        //stage.getIcons().add(new Image(getClass().getResource(icon).toExternalForm()));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
