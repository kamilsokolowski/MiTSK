package gui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class  Gui extends Application {

    public static Image operationalDeviceImg;
    public static Image notOperationalDeviceImg;
    public static Image waitingClientImg;
    public static Image okClientImg;
    public static Image availableServiceMan;
    public static Image notAvailableServiceMan;

    public static ArrayList<ImageView> workerList = new ArrayList<ImageView>();
    public static ArrayList<ImageView> clientsList = new ArrayList<ImageView>();
    public static ArrayList<ImageView> eqList = new ArrayList<ImageView>();

    public static Text averageText = new Text();
    public static Text deviationText = new Text();

    @Override
    public void start(Stage stage) throws Exception {
        getImages();
        setStage(stage);
    }
    private void setStage(Stage stage)
    {
        Scene scene = new Scene(this.getRoot(), 600, 600);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("MSK_PROJEKT");
        stage.show();

    }

    private BorderPane getRoot()
    {
        BorderPane root = new BorderPane();
        Text space = new Text();
        space.setText("   ");
        Text avgTextSimple = new Text();
        avgTextSimple.setText("average: ");
        Text deviationTextSimple = new Text();
        deviationTextSimple.setText("deviation: ");

        FlowPane flowPaneForStat = new FlowPane();

        flowPaneForStat.getChildren().add(avgTextSimple);
        flowPaneForStat.getChildren().add(averageText);
        flowPaneForStat.getChildren().add(space);
        flowPaneForStat.getChildren().add(deviationTextSimple);
        flowPaneForStat.getChildren().add(deviationText);

        root.setTop(getWorkers());
        root.setBottom(getClient());
        root.setRight(flowPaneForStat);

        return root;
    }
    private static FlowPane getWorkers(){

        FlowPane flow1 = new FlowPane();

        for(int i=0; i<settigs.SimulationSettings.numberOfServiceMan;i++){
            workerList.add(new ImageView(availableServiceMan));
        }
        for(ImageView tmp : workerList){
            tmp.setFitWidth(100);
            tmp.setFitHeight(100);
        }
        flow1.getChildren().addAll(workerList);

        return flow1;
    }

    private FlowPane getClient(){

        FlowPane flow1 = new FlowPane();

        for(int i=0; i<settigs.SimulationSettings.numberOfClients;i++){
            clientsList.add(new ImageView(okClientImg));
        }
        for(int i=0; i<settigs.SimulationSettings.numberOfClients;i++){
            eqList.add(new ImageView(operationalDeviceImg));
        }
        for(ImageView tmp : clientsList){
            tmp.setFitWidth(100);
            tmp.setFitHeight(100);

        }
        for(ImageView tmp : eqList){
            tmp.setFitWidth(50);
            tmp.setFitHeight(50);

        }
        for(int i=0; i<settigs.SimulationSettings.numberOfClients;i++){

            flow1.getChildren().add(clientsList.get(i));
            flow1.getChildren().add(eqList.get(i));
        }

        return flow1;
    }
    public static void changeImgClient(int id, boolean serviceManCalled ){
        Platform.runLater(() -> {

            if(serviceManCalled){
                clientsList.get(id).setImage(waitingClientImg);
            }
            else{
                clientsList.get(id).setImage(okClientImg);
            }

        });
    }
    public static void changeImgWorker(int id, boolean availabe){
        Platform.runLater(() -> {

            if(!availabe){
                workerList.get(id).setImage(notAvailableServiceMan);
            }
            else{
                workerList.get(id).setImage(availableServiceMan);
            }

        });
    }
    public static void changeImgEq(int id, boolean isOperational){
        Platform.runLater(() -> {

            if(!isOperational){
                eqList.get(id).setImage(notOperationalDeviceImg);
            }
            else{
                eqList.get(id).setImage(operationalDeviceImg);
            }

        });
    }
    public static void changeAverage(int val){
        averageText.setText(String.valueOf(val));

    }
    public static void changeDeviation(int val){
        deviationText.setText(String.valueOf(val));
    }
    private void getImages() throws FileNotFoundException {
        InputStream stream = new FileInputStream("C:\\Users\\Rafal\\Desktop\\msk proj obrazy\\dzialajacy_sprzet.PNG");
        operationalDeviceImg = new Image(stream);

        stream = new FileInputStream("C:\\Users\\Rafal\\Desktop\\msk proj obrazy\\ok_klient.PNG");
        okClientImg = new Image(stream);

        stream = new FileInputStream("C:\\Users\\Rafal\\Desktop\\msk proj obrazy\\zajety_pracownik.PNG");
        notAvailableServiceMan = new Image(stream);

        stream = new FileInputStream("C:\\Users\\Rafal\\Desktop\\msk proj obrazy\\oczekujacy_klient.PNG");
        waitingClientImg = new Image(stream);

        stream = new FileInputStream("C:\\Users\\Rafal\\Desktop\\msk proj obrazy\\wolny_pracownik.PNG");
        availableServiceMan = new Image(stream);

        stream = new FileInputStream("C:\\Users\\Rafal\\Desktop\\msk proj obrazy\\zepsuty_sprzet.PNG");
        notOperationalDeviceImg = new Image(stream);
    }

    public static void main(String[] args) {
        Thread guiLaunch = new Thread(() -> {
            launch(args);
        });
        guiLaunch.start();
    }
}
