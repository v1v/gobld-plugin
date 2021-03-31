package io.jenkins.plugins.gobld;

import com.gargoylesoftware.htmlunit.WebResponse;
import hudson.model.FreeStyleProject;
import hudson.model.labels.LabelAtom;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class GobldWebHookTest {

	@Rule
	public JenkinsRule j = new JenkinsRule();

	@Test
	public void customizeWorkspaceWithFile() throws Exception {
		FreeStyleProject project = j.createFreeStyleProject("my-job");

		project.setAssignedLabel(new LabelAtom("testLabel"));
		project.scheduleBuild2(0);

		WebResponse webResponse = j.createWebClient().goTo("gobld-webhook/queue", "application/json").getWebResponse();
		String response = webResponse.getContentAsString();
		// TODO: transform string to Json then ensure the items.size field is 1
		assertThat(response, containsString("my-job"));
		assertThat(response, containsString("testLabel"));
	}
}
