package Wifi;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.logging.Logger;
import static javafx.application.Application.launch;

public class WirelessMonitorBox extends VBox
{
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
        
    }
    
    private final Logger logger = Logger.getLogger(getClass().getName());
    private Label nameLabel;
    private ProgressBar signalStrengthBar;
    private WirelessMonitor wirelessMonitor;
    
    private WirelessMonitorBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        setSpacing(5);
        setAlignment(Pos.TOP_CENTER);
        bindBoxToParent(parentWidth, parentHeight);
        determineOperatingSystem();
        initializeFields();
        BorderPane labelsBorderPane = new BorderPane();
        labelsBorderPane.setLeft(new Label("Name"));
        labelsBorderPane.setRight(nameLabel);
        getChildren().addAll(new Label("Wireless network"), labelsBorderPane,
                new Label("Signal Strength"), signalStrengthBar);
        updateDataPeriodically();
    }
    
    @SuppressWarnings ("TypeMayBeWeakened")
    private void bindBoxToParent (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        prefWidthProperty().bind(parentWidth);
    }
    
    private void updateDataPeriodically ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::updateHandler));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void updateHandler (ActionEvent e)
    {
        wirelessMonitor.updateData();
        nameLabel.setText(wirelessMonitor.wirelessName());
        signalStrengthBar.setProgress(wirelessMonitor.signalLevel());
    }
    
    private void initializeFields ()
    {
        nameLabel = new Label();
        signalStrengthBar = new ProgressBar();
        signalStrengthBar.prefWidthProperty().bind(prefWidthProperty());
    }
    
    public static WirelessMonitorBox newInstance (DoubleProperty parentWidth,
            DoubleProperty parentHeight)
    {
        return new WirelessMonitorBox(parentWidth, parentHeight);
    }
    
    private void determineOperatingSystem ()
    {
        if (System.getProperty("os.name").contains("Windows"))
        {
            wirelessMonitor = WindowsWirelessMonitor.newInstance();
        }
        else if (System.getProperty("os.name").contains("Linux"))
        {
            wirelessMonitor = LinuxWirelessMonitor.newInstance();
        }
    }
    
    @Override
    public String toString ()
    {
        return "WirelessMonitorBox{" + "nameLabel=" + nameLabel + ", signalStrengthBar=" +
                signalStrengthBar + ", wirelessMonitor=" + wirelessMonitor + '}';
    }
}
