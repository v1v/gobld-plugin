package io.jenkins.plugins.gobld;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Util class
 *
 * Originally created in https://github.com/jenkinsci/statistics-gatherer-plugin/blob/master/src/main/java/org/jenkins/plugins/statistics/gatherer/util/RestClientUtil.java
 */
public class RestUtil {

	private static final Logger LOGGER = Logger.getLogger(RestUtil.class.getName());
	public static final String APPLICATION_JSON = "application/json";
	public static final String ACCEPT = "accept";
	public static final String CONTENT_TYPE = "Content-Type";

	public static void postToService(final String url, String jsonToPost) {
		try {
			Unirest.post(url)
				.header(ACCEPT, APPLICATION_JSON)
				.header(CONTENT_TYPE, APPLICATION_JSON)
				.body(jsonToPost)
				.asJsonAsync(new Callback<JsonNode>() {
					public void failed(UnirestException e) {
						LOGGER.log(Level.WARNING, "The request for url " + url + " has failed.", e);
					}
					public void completed(HttpResponse<JsonNode> response) {
						int responseCode = response.getStatus();
						LOGGER.log(Level.INFO, "The request for url " + url + " completed with status " + responseCode);
					}
					public void cancelled() {
						LOGGER.log(Level.INFO, "The request for url " + url + " has been cancelled");
					}

				});
		} catch (Throwable e) {
			LOGGER.log(Level.WARNING, "Unable to post event to url " + url, e);
		}
	}
}
