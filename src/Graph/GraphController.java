/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import CPU.CoresManager;
import CPU.CpuInfo;
import CPU.HostProcess;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author pacevil
 */
public class GraphController implements Initializable {

    private static final int LOOP_DELAY = 1000;
    private static final int DELAY_PROCESSES = 2000;
    private static final int MAX_DATA_POINTS = 100;

    private final int delayCount = 0;

    @FXML
    private LineChart<Number, Number> cpuchart;
    private double prefWidth;
    private double prefHeight;
    public ObservableList<HostProcess> userProcessesList = FXCollections.observableArrayList();
    public ObservableList<HostProcess> allProcessesList = FXCollections.observableArrayList();
    private HashMap<String, HostProcess> monitoredProcesses = new HashMap<>();
    private CoresManager coresManager;
    private NumberAxis xCPUAxis;
    private NumberAxis xMemoryAxis;
    private XYChart.Series memorySeries;
    

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    private Node initTabResources() {
		
		HBox coresContainer = new HBox();
		
		coresContainer.setPrefWidth(prefWidth);
		
		coresManager = new CoresManager();
		
		int coresCount = Runtime.getRuntime().availableProcessors();//ManagementFactory.getThreadMXBean().getThreadCount();
		
		Label coreUsage;
		double coreWidth = prefWidth / coresCount;
		
		xCPUAxis = new NumberAxis(0, 100, 10);
		
		NumberAxis yAxis = new NumberAxis(0, 100, 25); 
		
		
		cpuchart = new LineChart<>(xCPUAxis, yAxis);
		cpuchart.setAnimated(false);
		cpuchart.setTitle("CPU usage");
		
		XYChart.Series series;
		for (int i = 0; i < coresCount; i++) {
			coreUsage = new Label();
			series = new XYChart.Series();
			series.setName("cpu" + i);
			
			cpuchart.getData().add(series);
			
			coreUsage.setPrefWidth(coreWidth);
			coreUsage.setAlignment(Pos.CENTER);
			
			coresContainer.getChildren().add(coreUsage);
			
			coresManager.addCpuInfo(new CpuInfo("cpu" + i, coreUsage, series));
		}
		
		
		HBox memoryContainer = new HBox();
		
		xMemoryAxis = new NumberAxis(0, 100, 10);
		
		//memoryChart = new LineChart<>(xMemoryAxis, new NumberAxis(0, 100, 25));
		//memoryChart.setTitle("Memory usage");
		//memoryChart.setAnimated(false);
		
		memorySeries = new XYChart.Series();
		memorySeries.setName("Memory usage");
		//memoryChart.getData().add(memorySeries);
		
		VBox vBox = new VBox(cpuchart, coresContainer);//, memoryChart, memoryContainer);
		
		return vBox;
	}

}
