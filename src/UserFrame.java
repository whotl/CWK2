
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * UserFrame offers a GUI for users to search data and display the data graphically
 *
 */
public class UserFrame {
	private User loggedUser;
	private Stage mainStage;
	private BorderPane root;
	
	private Button lineChartButton;
	private Button pieChartButton;
	private Button barChartButton;
	private Label filterLabel;
	private Button typeFilterButton;
	private Button priceFilterButton;
	private Button minNightsFilterButton;
	
	private ObservableList<Host> hosts;
	private int filter;
	private int chartType;
	
	public UserFrame(User user) {
		this.loggedUser = user;
		hosts = LoginFrame.sqLite.getAllHostsData();
		mainStage = new Stage();
		mainStage.setTitle("CWK2 User Pane");
		root = new BorderPane();
		root.setTop(setFilterScreen());
		root.setLeft(setDiagramScreen());
		filter = 1;
		chartType = 2;
		root.setCenter(setChart(chartType, filter));
		typeFilterButton.setDisable(true);
		pieChartButton.setDisable(true);
		setActions();
		Scene scene = new Scene(root, 800, 900);
		mainStage.setScene(scene);
		mainStage.show();
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			public void handle(WindowEvent arg0) {
				LoginFrame.sqLite.logout(loggedUser.getEmail());
			}
			
		});
	}
	
	public HBox setFilterScreen() {
		filterLabel = new Label("Filter");
		typeFilterButton = new Button("Type");
		priceFilterButton = new Button("Price");
		minNightsFilterButton = new Button("Minimum nights");
		HBox box = new HBox();
		box.setSpacing(30);
		box.setPadding(new Insets(20));
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().addAll(filterLabel, typeFilterButton, priceFilterButton, minNightsFilterButton);
		return box;
	}
	
	public VBox setDiagramScreen() {
		lineChartButton = new Button("Line chart");
		pieChartButton = new Button("Pie chart");
		barChartButton = new Button("Bar chart");
		VBox box = new VBox();
		box.setSpacing(30);
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().addAll(lineChartButton, pieChartButton, barChartButton);
		box.setStyle("-fx-padding: 30;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
		        + "-fx-border-radius: 5;" + "-fx-border-color: black;");
		return box;
	}
	
	@SuppressWarnings("unchecked")
	public Chart setChart(int chartType, int filter) {
		Chart chart = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (filter == 1) {
			for (Host host : hosts) {
				if (!map.containsKey(host.getType())) {
					map.put(host.getType(), 0);
				}
				map.replace(host.getType(), map.get(host.getType()) + 1);
			}
		} else if (filter == 2) {
			for (int i = 0; i < 5000; i += 500) {
				map.put(i + "-" + (i + 500), 0);
			}
			map.put(">=5000", 0);
			for (Host host : hosts) {
				if (host.getPrice() < 500) {
					map.replace("0-500", map.get("0-500") + 1);
				} else if (host.getPrice() < 1000) {
					map.replace("500-1000", map.get("500-1000") + 1);
				} else if (host.getPrice() < 1500) {
					map.replace("1000-1500", map.get("1000-1500") + 1);
				} else if (host.getPrice() < 2000) {
					map.replace("1500-2000", map.get("1500-2000") + 1);
				} else if (host.getPrice() < 2500) {
					map.replace("2000-2500", map.get("2000-2500") + 1);
				} else if (host.getPrice() < 3000) {
					map.replace("2500-3000", map.get("2500-3000") + 1);
				} else if (host.getPrice() < 3500) {
					map.replace("3000-3500", map.get("3000-3500") + 1);
				} else if (host.getPrice() < 4000) {
					map.replace("3500-4000", map.get("3500-4000") + 1);
				} else if (host.getPrice() < 4500) {
					map.replace("4000-4500", map.get("4000-4500") + 1);
				} else if (host.getPrice() < 5000) {
					map.replace("4500-5000", map.get("4500-5000") + 1);
				} else {
					map.replace(">=5000", map.get(">=5000") + 1);
				}
			}
		} else {
			for (Host host : hosts) {
				String key = host.getMinimumNights() + "";
				if (!map.containsKey(key)) {
					map.put(key, 0);
				}
				map.replace(key, map.get(key) + 1);
			}
		}
		if (chartType == 1) {
			CategoryAxis xAxis = new CategoryAxis();
		    NumberAxis yAxis = new NumberAxis();
		    if (filter == 1) {
		    	xAxis.setLabel("room type");
		    } else if (filter == 2) {
		    	xAxis.setLabel("room price");
		    } else {
		    	xAxis.setLabel("room min nights");
		    }
		    chart = new LineChart<String, Number>(xAxis, yAxis);
		    XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		    for (String key : map.keySet()) {
			    series.getData().add(new XYChart.Data<String, Number>(key, map.get(key)));
			}
		    ((LineChart<String, Number>) chart).getData().add(series);
		} else if (chartType == 2) {
			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	        for (String key : map.keySet()) {
				pieChartData.add(new PieChart.Data(key, map.get(key)));
			}
			chart = new PieChart(pieChartData);
		} else {
			CategoryAxis xAxis = new CategoryAxis();
		    NumberAxis yAxis = new NumberAxis();
		    if (filter == 1) {
		    	xAxis.setLabel("room type");
		    } else if (filter == 2) {
		    	xAxis.setLabel("room price");
		    } else {
		    	xAxis.setLabel("room min nights");
		    }
			chart = new BarChart<String, Number>(xAxis, yAxis);
		    XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		    for (String key : map.keySet()) {
			    series.getData().add(new XYChart.Data<String, Number>(key, map.get(key)));
			}
		    ((BarChart<String, Number>) chart).getData().add(series);
		}
		if (filter == 1) {
			chart.setTitle("Room Type");
		} else if (filter == 2) {
			chart.setTitle("Room Price");
		} else {
			chart.setTitle("Room Minimum Nights");
		}
        return chart;
	}
	
	public void setActions() {
		lineChartButton.setOnAction(new LineChartButtonListener());
		pieChartButton.setOnAction(new PieChartButtonListener());
		barChartButton.setOnAction(new BarChartButtonListener());
		typeFilterButton.setOnAction(new TypeFilterButtonListener());
		priceFilterButton.setOnAction(new PriceFilterButtonListener());
		minNightsFilterButton.setOnAction(new MinNightsFilterButtonListener());
	}
	
	/**
	 * Button listeners
	 */

	
	private class PieChartButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			chartType = 2;
			root.setCenter(setChart(chartType, filter));
			pieChartButton.setDisable(true);
			lineChartButton.setDisable(false);
			barChartButton.setDisable(false);
		}
		
	}
	
	private class LineChartButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			chartType = 1;
			root.setCenter(setChart(chartType, filter));
			pieChartButton.setDisable(false);
			lineChartButton.setDisable(true);
			barChartButton.setDisable(false);
		}
		
	}
	
	private class BarChartButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			chartType = 3;
			root.setCenter(setChart(chartType, filter));
			pieChartButton.setDisable(false);
			lineChartButton.setDisable(false);
			barChartButton.setDisable(true);
		}
		
	}
	
	private class TypeFilterButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			filter = 1;
			typeFilterButton.setDisable(true);
			priceFilterButton.setDisable(false);
			minNightsFilterButton.setDisable(false);
			root.setCenter(setChart(chartType, filter));
		}
		
	}
	
	private class PriceFilterButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			filter = 2;
			typeFilterButton.setDisable(false);
			priceFilterButton.setDisable(true);
			minNightsFilterButton.setDisable(false);
			root.setCenter(setChart(chartType, filter));
		}
		
	}
	
	private class MinNightsFilterButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			filter = 3;
			typeFilterButton.setDisable(false);
			priceFilterButton.setDisable(false);
			minNightsFilterButton.setDisable(true);
			root.setCenter(setChart(chartType, filter));
		}
		
	}
}
