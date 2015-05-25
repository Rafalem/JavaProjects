package ru.nsu.java.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = "Confirm.js")
public class Confirm {

    @Parameter(name = "message", value = "Are you sure?", defaultPrefix = BindingConstants.LITERAL)
    private String message;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @InjectContainer
    private ClientElement clientElement;

    @AfterRender
    public void afterRender() {
        JSONObject spec = new JSONObject();
        spec.put("elementId", clientElement.getClientId());
        spec.put("message", message);
        javaScriptSupport.addInitializerCall("confirm", spec);
    }

}
