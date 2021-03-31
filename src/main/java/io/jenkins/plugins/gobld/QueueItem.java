package io.jenkins.plugins.gobld;

public class QueueItem {

    private boolean blocked;
    private long id;
    private String labelExpression;
    private String taskURL;
    private String url;
    private String why;

    public QueueItem(boolean blocked, long id, String url, String why, String labelExpression, String taskURL) {
        this.blocked = blocked;
        this.id = id;
        this.url = url;
        this.why = why;
        this.labelExpression = labelExpression;
        this.taskURL = taskURL;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public long getId() {
        return id;
    }

    public String getLabelExpression() {
        return labelExpression;
    }

    public String getTaskURL() {
        return taskURL;
    }

    public String getUrl() {
        return url;
    }

    public String getWhy() {
        return why;
    }

    public void setLabelExpression(String labelExpression) {
        this.labelExpression = labelExpression;
    }

    public void setTaskURL(String taskURL) {
        this.taskURL = taskURL;
    }

    public static class Builder {
        private boolean blocked;
        private long id;
        private String labelExpression;
        private String taskURL;
        private String url;
        private String why;

        public Builder(){}

        public Builder setBlocked(boolean blocked) {
            this.blocked = blocked;
            return this;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setWhy(String why) {
            this.why = why;
            return this;
        }

        public Builder setLabelExpression(String labelExpression) {
            this.labelExpression = labelExpression;
            return this;
        }

        public Builder setTaskURL(String taskURL) {
            this.taskURL = taskURL;
            return this;
        }

        public QueueItem build() {
            return new QueueItem(blocked, id, url, why, labelExpression, taskURL);
        }
    }
}
