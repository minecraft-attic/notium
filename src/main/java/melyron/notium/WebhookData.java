package melyron.notium;

public class WebhookData {
    String title = "";
    String position = "";
    String description = "";

    WebhookData(String title, String cost, String description) {
        this.title = title;
        this.position = cost;
        this.description = description;
    }
}
