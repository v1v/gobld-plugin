package io.jenkins.plugins.gobld;

import hudson.Extension;
import hudson.model.Queue;
import hudson.model.UnprotectedRootAction;
import hudson.security.csrf.CrumbExclusion;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.interceptor.RequirePOST;
import org.kohsuke.stapler.json.JsonHttpResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Extension
public class GobldWebHook implements UnprotectedRootAction {
	public static final String URL_NAME = "gobld-webhook";

	@Override
	public String getIconFileName() {
			return null;
	}

	@Override
	public String getDisplayName() {
			return null;
	}

	@Override
	public String getUrlName() {
			return URL_NAME;
	}

	public HttpResponse doQueue(StaplerRequest req) {
		return new JsonHttpResponse(JSONObject.fromObject(getQueue()));
	}

	@RequirePOST
	public HttpResponse doQueuePost(StaplerRequest req) {
		return new JsonHttpResponse(JSONObject.fromObject(getQueue()));
	}

	@Extension
	public static class CrumbExclusionImpl extends CrumbExclusion {
		public boolean process(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
			String pathInfo = req.getPathInfo();
			if(pathInfo != null && pathInfo.startsWith("/"+URL_NAME)) {
				chain.doFilter(req, resp);
				return true;
			} else {
				return false;
			}
		}
	}

	private QueueItems getQueue() {
		Queue queue = Objects.requireNonNull(Jenkins.getInstanceOrNull()).getQueue();
		List<QueueItem> items = new java.util.ArrayList<>(Collections.emptyList());
		for (Queue.Item item : queue.getItems()) {
			QueueItem queueItem = generateQueueItem(item);
			if (queueItem !=null) {
				items.add(queueItem);
			}
		}
		return new QueueItems(items);
	}

	private QueueItem generateQueueItem(Queue.Item item) {
		if (item == null) {
			return null;
		}

		QueueItem queueItem = new QueueItem.Builder()
				.setBlocked(item.isBlocked())
				.setId(item.getId())
				.setUrl(item.getUrl())
				.setWhy(item.getWhy())
				.build();
		Queue.Task task = item.task;
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
