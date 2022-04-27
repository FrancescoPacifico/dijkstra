module it.francesco.dijkstra {

    requires javafx.controls;

    opens it.francesco.dijkstra.app to javafx.fxml;
    exports it.francesco.dijkstra.app;

}