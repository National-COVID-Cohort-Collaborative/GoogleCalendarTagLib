package org.cd2h.GoogleCalendarTagLib;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class EventProperty extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	Evnt event = null;
	String property = null;

	public int doStartTag() throws JspException {
		event = (Evnt) findAncestorWithClass(this, Evnt.class);

		if (event == null) {
			throw new JspTagException("EventProperty tag not nested in Event instance");
		}
		
		try {
			switch(property) {
			case "eventMonth":
				pageContext.getOut().print(event.eventMonth);
				break;
			case "eventDay":
				pageContext.getOut().print(event.eventDay);
				break;
			case "eventStartTime":
				pageContext.getOut().print(event.eventStartTime);
				break;
			case "eventEndTime":
				pageContext.getOut().print(event.eventEndTime);
				break;
			case "eventTimeZone":
				pageContext.getOut().print(event.eventTimeZone);
				break;
			default:
				pageContext.getOut().print("unknown event property");				
			}
		} catch (IOException e) {
			throw new JspException("Error acquiring event summary");
		}
		return SKIP_BODY;
	}

    public int doEndTag() throws JspException {
    	clearServiceState();
    	return super.doEndTag();
    }
    
	private void clearServiceState() {
		event = null;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
