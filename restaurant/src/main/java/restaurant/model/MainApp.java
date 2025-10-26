package restaurant;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import restaurant.model.MenuItem;
import restaurant.model.Order;
import restaurant.model.OrderItem;

public class MainApp extends Application {

    private final Order currentOrder = new Order();
    private final ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();
    private final ObservableList<MenuItem> menuList = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Aplikasi Nota Pemesanan - AMBA CAFE");

        menuList.addAll(
                new MenuItem("Nasi Goreng", 16000),
                new MenuItem("Ayam Bakar", 20000),
                new MenuItem("Mie Ayam", 12000),
                new MenuItem("Es Teh Manis", 5000),
                new MenuItem("Es Jeruk", 7000)
        );

        Label lblHeader = new Label("AMBA CAFE");
        lblHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lblCustomer = new Label("Nama Pelanggan");
        TextField tfCustomer = new TextField();

        Label lblMenu = new Label("Pilih Menu");
        ComboBox<MenuItem> cbMenu = new ComboBox<>(menuList);
        cbMenu.getSelectionModel().selectFirst();

        Label lblQty = new Label("Jumlah");
        Spinner<Integer> spQty = new Spinner<>(1, 1000, 1);

        Button btnAdd = new Button("Tambah Item");

        TableView<OrderItem> table = new TableView<>();
        TableColumn<OrderItem, String> colName = new TableColumn<>("Item");
        colName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        TableColumn<OrderItem, Integer> colQty = new TableColumn<>("Qty");
        colQty.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getQuantity()).asObject());
        TableColumn<OrderItem, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getPrice()).asObject());
        TableColumn<OrderItem, Double> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getSubtotal()).asObject());
        TableColumn<OrderItem, Void> colAction = new TableColumn<>("Aksi");

        Callback<TableColumn<OrderItem, Void>, TableCell<OrderItem, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<OrderItem, Void> call(final TableColumn<OrderItem, Void> param) {
                final TableCell<OrderItem, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Hapus");
                    {
                        btn.setOnAction(e -> {
                            OrderItem data = getTableView().getItems().get(getIndex());
                            currentOrder.removeItem(data);
                            refreshList();
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colAction.setCellFactory(cellFactory);

        table.getColumns().addAll(colName, colQty, colPrice, colSubtotal, colAction);
        table.setItems(orderItems);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label lblTax = new Label("Pajak (%)");
        TextField tfTax = new TextField("10");

        Label lblDiscount = new Label("Diskon (%)");
        TextField tfDiscount = new TextField("0");

        Button btnGenerate = new Button("Buat Nota");
        TextArea taReceipt = new TextArea();
        taReceipt.setPrefRowCount(20);
        taReceipt.setEditable(false);
        taReceipt.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px;");

        btnAdd.setOnAction(e -> {
            MenuItem selected = cbMenu.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Pilih menu terlebih dahulu");
                return;
            }
            int qty = spQty.getValue();
            OrderItem oi = new OrderItem(selected, qty);
            currentOrder.addItem(oi);
            refreshList();
            spQty.getValueFactory().setValue(1);
        });

        btnGenerate.setOnAction(e -> {
            currentOrder.setCustomerName(tfCustomer.getText().trim());
            try {
                currentOrder.setTaxRate(Double.parseDouble(tfTax.getText().trim()));
            } catch (Exception ex) {
                showAlert("Pajak tidak valid");
                return;
            }
            try {
                currentOrder.setDiscountRate(Double.parseDouble(tfDiscount.getText().trim()));
            } catch (Exception ex) {
                showAlert("Diskon tidak valid");
                return;
            }
            String receipt = currentOrder.generateReceipt();
            taReceipt.setText(receipt);
        });

        HBox topRow = new HBox(10, lblHeader);
        HBox inputRow = new HBox(10, lblCustomer, tfCustomer);
        HBox menuRow = new HBox(10, lblMenu, cbMenu, lblQty, spQty, btnAdd);
        HBox taxRow = new HBox(10, lblTax, tfTax, lblDiscount, tfDiscount, btnGenerate);
        topRow.setPadding(new Insets(10));
        inputRow.setPadding(new Insets(10));
        menuRow.setPadding(new Insets(10));
        taxRow.setPadding(new Insets(10));

        VBox left = new VBox(10, topRow, inputRow, menuRow, table, taxRow);
        left.setPadding(new Insets(10));
        VBox right = new VBox(10, new Label("NOTA / STRUK"), taReceipt);
        right.setPadding(new Insets(10));
        right.setPrefWidth(420);
        HBox root = new HBox(10, left, right);
        Scene scene = new Scene(root, 1120, 640);
        stage.setScene(scene);
        stage.show();
    }

    private void refreshList() {
        orderItems.clear();
        orderItems.addAll(currentOrder.getItems());
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
