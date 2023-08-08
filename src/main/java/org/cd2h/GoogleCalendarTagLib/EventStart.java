package org.cd2h.GoogleCalendarTagLib;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class EventStart extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	Evnt event = null;
	String property = null;

	public int doStartTag() throws JspException {
		event = (Evnt) findAncestorWithClass(this, Evnt.class);

		if (event == null) {
			throw new JspTagException("EventLink tag not nested in Event instance");
		}
		
		try {
			pageContext.getOut().print(event.event.getStart().getDateTime());
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
