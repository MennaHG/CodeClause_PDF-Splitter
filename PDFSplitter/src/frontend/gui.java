/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package frontend;

import backend.pdfsplitter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.*;
import static javafx.scene.layout.VBox.setMargin;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javax.swing.JFileChooser;
public class gui extends Application {
    File pdffile;
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        VBox root = new VBox(30); root.setAlignment(Pos.TOP_CENTER);
        setMargin(root,new Insets(40,0,0,0));

        root.setStyle(" -fx-font-weight: bold Helvetica; -fx-font-family:Helvetica, Arial, sans-serif");
        root.setPadding(new Insets(35,20,20,20));
        Scene scene = new Scene(root,Color.BEIGE);
//        Image img = new Image(getClass().getResourceAsStream("icon.png"));
//        ImageView view = new ImageView();
//        view.setImage(img);
        FlowPane region = new FlowPane();
        region.setPadding(new Insets(10));
        region.setPrefSize(200, 300);
        Label label = new Label(" Add pdf here");
        label.setStyle("-fx-color:black; -fx-font: bold 30px  Helvetica, Arial, sans-serif");
        region.setAlignment(Pos.CENTER);
        ///"C:\Users\menna\OneDrive\Documents\NetBeansProjects\PDFSplitter\src\frontend\Upload-Transparent-Images.png"
        String path = "/frontend/upload.png";
        Image img = new Image(path,100,100,true,false);
        ImageView uploadimg = new ImageView(img);

        region.setStyle("-fx-background-color: grey; -fx-border-style: dashed; -fx-border-color: black; -fx-border-width: 5;");
        region.getChildren().addAll(uploadimg,label);
        region.setCursor(Cursor.HAND);
        String st[] = { "Split All", "Split By Range" };
        TextField txtfield = new TextField(); 
        txtfield.setVisible(false); txtfield.setPromptText("Ex: 1, 3-7");
         EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
         @Override 
         public void handle(MouseEvent e) { 
            JFileChooser file_upload = new JFileChooser();
            int choice = file_upload.showOpenDialog(null);
            if(choice == JFileChooser.APPROVE_OPTION){
                pdffile = new File(file_upload.getSelectedFile().getAbsolutePath());
                region.getChildren().clear();
                pdfsplitter splitter = new pdfsplitter();
                ImageView img;
                try {
                    img = new ImageView(splitter.render(pdffile));
                    img.setFitHeight(300);
                    img.setFitWidth(300);
                    region.getChildren().add(img);

                } catch (IOException ex) {
                    Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
         } 
      };  
      //Registering the event filter 
      region.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler); 
        // create a choiceBox
        ChoiceBox cbox = new ChoiceBox(FXCollections.observableArrayList(st));  
        cbox.setPrefSize(200,40);
        cbox.setMaxSize(200, 40);
        cbox.getSelectionModel().selectFirst();
         cbox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
 
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
               txtfield.setVisible(new_value.intValue() == 1);
                txtfield.setPrefSize(300,40); 
                txtfield.setMaxSize(300,40); 
            }
        });
         
         Button btn = new Button(" Download Split Files");
         btn.setCursor(Cursor.HAND);
         Image btnimg = new Image("/frontend/download.png",50,50,true,false);
        ImageView imgView = new ImageView(btnimg);
        btn.setGraphic(imgView);
         setMargin(btn,new Insets(20,0,0,0));

         btn.setPrefSize(350, 100); btn.setTextFill(Color.WHITE);
         btn.setStyle("-fx-background-color: Blue; -fx-font-size:25px; -fx-border-color: Black; -fx-border-width:3px; -fx-border-radius:4px;");
         
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
            pdfsplitter split = new pdfsplitter();
            try {
                if(txtfield.isVisible() && !"".equals(txtfield.getText()))
                    split.split(pdffile,txtfield.getText());
                else
                    split.split(pdffile);
            } catch (IOException ex) {
                Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//        
//        StackPane root = new StackPane();
        root.getChildren().addAll(region,cbox,txtfield,btn);

//        
//        Scene scene = new Scene(root, 300, 250);
//        "C:\Users\menna\OneDrive\Documents\NetBeansProjects\PDFSplitter\src\frontend\icon.png"
        Image icon = new Image("frontend/icon.png");
        primaryStage.setTitle(" PDF Splitter");
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
