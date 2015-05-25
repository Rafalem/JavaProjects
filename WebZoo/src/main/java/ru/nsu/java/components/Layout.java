package ru.nsu.java.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

@Import(stylesheet = "context:layout/layout.css")
public class Layout {
    @Property
    private String pageName;

    @Inject
    private ComponentResources resources;

    @Inject
    private Messages messages;

    public String getClassForPageName() {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "current_page_item"
                : null;
    }

    public String[] getPageNames()
    {
        return new String[]{"Index", "Edit", "Search", "Photo", "About"};
    }

    public String getPageNameLocalized()
    {
        return messages.get("Menu."+pageName);
    }
}
