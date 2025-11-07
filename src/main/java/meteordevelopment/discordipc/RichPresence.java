package meteordevelopment.discordipc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RichPresence {
    private String details;
    private String state;

    private Assets assets;
    private Timestamps timestamps;
    private Button[] buttons;

    public void setDetails(String details) {
        this.details = details;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setLargeImage(String key, String text) {
        if (assets == null) assets = new Assets();
        assets.large_image = key;
        assets.large_text = text;
    }

    public void setSmallImage(String key, String text) {
        if (assets == null) assets = new Assets();
        assets.small_image = key;
        assets.small_text = text;
    }

    public void setStart(long time) {
        if (timestamps == null) timestamps = new Timestamps();
        timestamps.start = time;
    }

    public void setEnd(long time) {
        if (timestamps == null) timestamps = new Timestamps();
        timestamps.end = time;
    }

    public void setButtons(Button... buttons) {
        if (buttons != null && buttons.length > 2) {
            Button[] limited = new Button[2];
            System.arraycopy(buttons, 0, limited, 0, 2);
            this.buttons = limited;
        } else {
            this.buttons = buttons;
        }
    }

    public void clearButtons() {
        this.buttons = null;
    }

    public JsonObject toJson() {
        // Main
        JsonObject o = new JsonObject();

        if (details != null) o.addProperty("details", details);
        if (state != null) o.addProperty("state", state);

        // Assets
        if (assets != null) {
            JsonObject a = new JsonObject();

            if (assets.large_image != null) a.addProperty("large_image", assets.large_image);
            if (assets.large_text != null) a.addProperty("large_text", assets.large_text);
            if (assets.small_image != null) a.addProperty("small_image", assets.small_image);
            if (assets.small_text != null) a.addProperty("small_text", assets.small_text);

            o.add("assets", a);
        }

        // Timestamps
        if (timestamps != null) {
            JsonObject t = new JsonObject();

            if (timestamps.start != null) t.addProperty("start", timestamps.start);
            if (timestamps.end != null) t.addProperty("end", timestamps.end);

            o.add("timestamps", t);
        }

        // Buttons
        if (buttons != null && buttons.length > 0) {
            JsonArray arr = new JsonArray();
            for (Button b : buttons) {
                if (b == null) continue;
                if (b.label == null || b.label.isEmpty()) continue;
                if (b.url == null || b.url.isEmpty()) continue;

                JsonObject jb = new JsonObject();
                jb.addProperty("label", b.label);
                jb.addProperty("url", b.url);
                arr.add(jb);
            }
            if (!arr.isEmpty()) {
                o.add("buttons", arr);
            }
        }

        return o;
    }

    public static class Assets {
        public String large_image, large_text;
        public String small_image, small_text;
    }

    public static class Timestamps {
        public Long start;
        public Long end;
    }

    public static class Button {
        public String label;
        public String url;

        public Button(String label, String url) {
            this.label = label;
            this.url = url;
        }
    }
}
