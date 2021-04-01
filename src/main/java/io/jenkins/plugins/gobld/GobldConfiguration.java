package io.jenkins.plugins.gobld;

import hudson.Extension;
import hudson.util.FormValidation;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.util.logging.Logger;

@Extension
@Symbol("gobld")
public class GobldConfiguration extends GlobalConfiguration {
    private final static Logger LOGGER = Logger.getLogger(GobldConfiguration.class.getName());

    private String assignUrl;
    private String terminateUrl;

    @DataBoundConstructor
    public GobldConfiguration() {
        load();
    }

    public static GobldConfiguration get() {
        return GlobalConfiguration.all().get(GobldConfiguration.class);
    }

    public String getAssignUrl() {
        return assignUrl;
    }

    public String getTerminateUrl() {
        return terminateUrl;
    }

    @DataBoundSetter
    public void setAssignUrl(String assignUrl) {
        this.assignUrl = assignUrl;
        save();
    }

    @DataBoundSetter
    public void setTerminateUrl(String terminateUrl) {
        this.terminateUrl = terminateUrl;
        save();
    }

    @Override
    public boolean configure(StaplerRequest request, JSONObject json) throws FormException {
        request.bindJSON(this, json);
        return true;
    }

    public FormValidation doCheckAssignUrl(@QueryParameter("assignUrl") final String assignUrl) {
        if (isEmptyOrNull(assignUrl)) {
            return FormValidation.error("Provide valid gobld URL.");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckTerminateUrl(@QueryParameter("terminateUrl") final String terminateUrl) {
        if (isEmptyOrNull(terminateUrl)) {
            return FormValidation.error("Provide valid gobld URL.");
        }
        return FormValidation.ok();
    }

    private boolean isEmptyOrNull(String value) {
        return (value == null || value.isEmpty());
    }
}
