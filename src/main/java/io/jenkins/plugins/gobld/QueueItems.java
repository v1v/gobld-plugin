package io.jenkins.plugins.gobld;

import java.util.List;

public class QueueItems {
    private List<QueueItem> items;

    public QueueItems(List<QueueItem> items) {
        this.items = items;
    }

    public List<QueueItem> getItems() {
        return items;
    }

    public void setItems(List<QueueItem> items) {
        this.items = items;
    }
}
