package userinterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import main.Token;
import main.Tokenization;
import main.Type;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LexicalUI implements Initializable {

    private ObservableList<Token> listView;
    private File file;
    private ArrayList<Token> tokens;

    @FXML
    private TableView<Token> tableOfTokens;

    @FXML
    private TableColumn<Token, String> tokenColumn;

    @FXML
    private TableColumn<Token, Type> typeColumn;

    @FXML
    private TableColumn<Token, Integer> idColumn;


    @FXML
    void chooseFileAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null);
        Tokenization tokenization = new Tokenization();
        tokens = tokenization.getTokens(file);
    }

    @FXML
    void clearAction(ActionEvent event) {
        tokens = null;
        tableOfTokens.setItems(null);
    }

    @FXML
    void getTokensAction(ActionEvent event) {
        if (tokens == null) {
            return;
        }
        listView = FXCollections.observableArrayList(tokens);
        tableOfTokens.setItems(listView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tokenColumn.setCellValueFactory(new PropertyValueFactory<Token, String>("token"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Token, Type>("typeOfToken"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Token, Integer>("id"));
    }
}
