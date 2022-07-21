package ru.knastnt.springjmsibmmq.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("main-stage.fxml")
public class MyController {
    @FXML
    private Label weatherLabel;

    public void loadWeatherForecast(ActionEvent actionEvent) {
        this.weatherLabel.setText("weatherService.getWeatherForecast()");
    }
}
