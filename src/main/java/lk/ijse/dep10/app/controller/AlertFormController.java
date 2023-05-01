package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;

public class AlertFormController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDoNotSave;

    @FXML
    private Button btnSave;
    private File file;
    private TextArea textEditor;
    @FXML
    private Label lblAlert;

    public void initialize(){
        String[] array={"Text Editor"};
        if (file!=null) {
            array = file.getPath().split("/");
        }
        lblAlert.setText("Save changes of document"+array[array.length-1] +"before closing?");
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnDoNotSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

}
