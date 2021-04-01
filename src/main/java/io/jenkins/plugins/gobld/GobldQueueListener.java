package io.jenkins.plugins.gobld;

import hudson.Extension;
import hudson.model.Queue;
import hudson.model.queue.QueueListener;
import net.sf.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listener to capture the start of new builds and provision gobld nodes if possible
 */
@Extension
public class GobldQueueListener extends QueueListener {

    private static final Logger LOGGER = Logger.getLogger(GobldQueueListener.class.getName());

    @Override
    public void onEnterBuildable(final Queue.BuildableItem item) {
        LOGGER.log(Level.INFO, "Request gobld worker if possible");
        RestUtil.postToService(GobldConfiguration.get().getAssignUrl(), JSONObject.fromObject(getQueueItem(item)).toString());
    }

    @Override
    public void onLeft(final Queue.LeftItem item) {
        if (item.isCancelled()) {
            LOGGER.log(Level.INFO, "Terminate gobld worker if possible");
            RestUtil.postToService(GobldConfiguration.get().getTerminateUrl(), JSONObject.fromObject(getQueueItem(item)).toString());
        }
    }

    private QueueItem getQueueItem(Queue.Item item) {
        final Queue.Task task = item.task;
        QueueItem queueItem = new QueueItem.Builder()
                .setBlocked(item.isBlocked())
                .setId(item.getId())
                .setUrl(item.getUrl())
                .setWhy(item.getWhy())
                .build();
        if (task!=null) {
            if (item.task.getAssignedLabel()!=null) {
                queueItem.setLabelExpression(item.task.getAssignedLabel().toString());
            }
            if (item.task.getUrl()!=null) {
                queueItem.setTaskURL(item.task.getUrl());
            }
        }
        return queueItem;
    }

}
