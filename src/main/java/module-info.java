module Live.Chat.Clien.main {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.livechat.client to javafx.fxml;
    exports org.livechat.client;
}