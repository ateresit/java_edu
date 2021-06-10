package com.itia.cs.client;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ServerPanelController implements Initializable {

    @FXML
    TableView<FileListTable> filesListArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //  строим таблицу со списком файлов
    TableColumn<FileListTable, String> fileTypeColumn = new TableColumn<>("Тип");
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileType().getName()));
        fileTypeColumn.setPrefWidth(24);

    TableColumn<FileListTable, String> fileNameColumn = new TableColumn<>("Наименование");
        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileName()));
        fileNameColumn.setPrefWidth(300);

    TableColumn<FileListTable, Long> fileSizeColumn = new TableColumn<>("Размер");
        fileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getFileSize()));
        fileSizeColumn.setPrefWidth(150);
        fileSizeColumn.setCellFactory(c -> {
        return new TableCell<FileListTable, Long>(){
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
//                        String text = item.toString();
                    String text = String.format("%,d byte", item); // разделяет строку по 3 знака и ставит пробел
                    if (item == -1L){
                        text = "[DIR]";
                    }
                    setText(text);
                }
            }
        };
    });

    // форматируем дату для читаемого вывода перед выводом в таблицу
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    TableColumn<FileListTable, String> fileDateColumn = new TableColumn<>("Дата изменения");
        fileDateColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileLastModified().format(formatDate)));
        fileDateColumn.setPrefWidth(130);

//        filesListArea.getColumns().add(fileTypeColumn);
        filesListArea.getColumns().addAll(fileTypeColumn, fileNameColumn, fileSizeColumn, fileDateColumn);
        filesListArea.getSortOrder().add(fileTypeColumn);

    updateFileList(Paths.get(".","clientFX/test_folder"));
}

    public void updateFileList(Path path){
        try {
            filesListArea.getItems().clear();
            filesListArea.getItems().addAll(Files.list(path).map(FileListTable::new).collect(Collectors.toList()));
            filesListArea.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось обновить список файлов.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public String getSelectedFileName(){
        if (!filesListArea.isFocused()){
            return null;
        }
        return filesListArea.getSelectionModel().getSelectedItem().getFileName();
    }

}
