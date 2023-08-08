package org.cd2h.GoogleCalendarTagLib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class EventIterator extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	Calendr calendar = null;
	int count = 10;

	Events events = null;
	
	List<Event> items = null;
	int pos = 0;
	
	public int doStartTag() throws JspException {
		calendar = (Calendr) findAncestorWithClass(this, Calendr.class);

		if (calendar == null) {
			throw new JspTagException("EventIterator tag not nesting in Calendar instance");
		}
		
		try {
			// List the next 10 events from the target calendar.
			DateTime now = new DateTime(System.currentTimeMillis());
			events = calendar.service.events().list(calendar.name)
					.setMaxResults(count)
					.setTimeMin(now)
					.setOrderBy("startTime")
					.setSingleEvents(true)
					.execute();
			items = events.getItems();
		} catch (IOException e) {
			throw new JspException("Error acquiring calendar events");
		}
		
		if (pos < items.size()) {
			return EVAL_BODY_INCLUDE;
		}
		
		return SKIP_BODY;
	}

	public int doAfterBody() throws JspTagException {
		if (++pos < items.size())
			return EVAL_BODY_AGAIN;
		
		clearServiceState();
		return SKIP_BODY;
	}

	private void clearServiceState() {
		calendar = null;
		items = null;
		pos = 0;
		count = 10;
	}
	
	public Event getEvent() {
		return items.get(pos);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
