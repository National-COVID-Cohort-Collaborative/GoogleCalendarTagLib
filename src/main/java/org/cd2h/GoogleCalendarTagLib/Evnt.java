package org.cd2h.GoogleCalendarTagLib;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.google.api.services.calendar.model.Event;

public class Evnt extends BodyTagSupport {
	private static final long serialVersionUID = 1L;

	static SimpleDateFormat startFormatter = new SimpleDateFormat("MMM dd K:mm a z");
	static SimpleDateFormat endFormatter = new SimpleDateFormat("K:mm a");
	static {
		startFormatter.setTimeZone(TimeZone.getTimeZone("CST"));
		endFormatter.setTimeZone(TimeZone.getTimeZone("CST"));
	}

	EventIterator iterator = null;
	
	Event event = null;
	
	String eventMonth = null;
	String eventDay = null;
	String eventStartTime = null;
	String eventEndTime = null;
	String eventTimeZone = null;

	// (Central", "Daylight", "Time)
	public int doStartTag() throws JspException {
		iterator = (EventIterator) findAncestorWithClass(this, EventIterator.class);

		if (iterator == null) {
			throw new JspTagException("Event tag not nested in Calendar iterator instance");
		}
		
		event = iterator.getEvent();
		
		String[] startString = startFormatter.format(new Date(event.getStart().getDateTime().getValue())).split(" ");
		String endString = endFormatter.format(new Date(event.getEnd().getDateTime().getValue()));
		eventMonth = startString[0];
		eventDay = startString[1];
		eventStartTime = startString[2] + " " + startString[3];
		eventEndTime = endString;
		eventTimeZone = startString[4];
		System.out.println("start month: " + eventMonth);
		System.out.println("start day: " + eventDay);
		System.out.println("start time: " + eventStartTime);
		System.out.println("end time: " + eventEndTime);
		System.out.println("timezone: " + eventTimeZone);
		return EVAL_BODY_INCLUDE;
	}

    public int doEndTag() throws JspException {
    	clearServiceState();
    	return super.doEndTag();
    }
    
	private void clearServiceState() {
		event = null;
	}

}
