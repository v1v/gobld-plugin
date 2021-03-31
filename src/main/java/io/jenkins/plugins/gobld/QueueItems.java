package io.jenkins.plugins.gobld;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class QueueItems {
    private List<QueueItem> items;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date currentTime;

    public QueueItems(List<QueueItem> items) {
        this.items = items;
        this.currentTime = new Date();
    }

    public List<QueueItem> getItems() {
        return items;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setItems(List<QueueItem> items) {
        this.items = items;
    }
}
