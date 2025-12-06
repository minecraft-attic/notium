package melyron.notium;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class WebhookCache {
    private static boolean isValid = false;
    private static long lastChecked = 0L;
    private static final long CACHE_DURATION_MS = 5 * 60 * 1000;
    private static String lastCheckedUrl = "";

    public static boolean check(String webhookUrl) {
        long now = System.currentTimeMillis();
        if (now - lastChecked > CACHE_DURATION_MS || !Objects.equals(lastCheckedUrl, webhookUrl)) {
            isValid = isWebhookValid(webhookUrl);
            lastChecked = now;
            lastCheckedUrl = webhookUrl;
        }
        return isValid;
    }

    private static boolean isWebhookValid(String webhookUrl) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(webhookUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);
            int code = conn.getResponseCode();
            conn.getInputStream().close();
            return code == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
