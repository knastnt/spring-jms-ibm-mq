package ru.knastnt.springjmsibmmq.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.knastnt.springjmsibmmq.mq.Sender;

import javax.annotation.PostConstruct;

@Component
@FxmlView("main-stage.fxml")
public class MyController {
    @Autowired
    private Sender sender;

    @FXML
    private Label label;
    @FXML
    private javafx.scene.control.TextArea textArea;

    @FXML
    public void initialize() {
        textArea.setText("{1:F01RZBMRUM0AXXX1474127635}{2:O2021302220112RZBAATW0AXXX68325734722201121502N}{3:{108:SWTTGT221C00336R}{121:9610ddc0-4ac6-4405-85af-4e331e4db20d}}{4:\n" +
                ":20:TR2021060303047\n" +
                ":21:RZBAWW2281UBSBZZ\n" +
                ":32A:220113USD820395,93\n" +
                ":52A:RZBAATW0\n" +
                ":53B:/30611810500000100000\n" +
                ":57A://RU044525225.30101810400000000225SABRRUM0012\n" +
                ":58A:/30111810900000001257\n" +
                "UBSBCHZ0XXX\n" +
                ":72:/helloSUBACC123/OTCgoodbuy\n" +
                "-}{5:{MAC:00000000}{CHK:F8830662ADD3}{TNG:}}");
    }

    public void loadWeatherForecast(ActionEvent actionEvent) {
        sender.send(textArea.getText());

        this.label.setText("Sended");
    }
}
