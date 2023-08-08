package org.cd2h.GoogleCalendarTagLib;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.cd2h.GoogleCalendarTagLib.util.Test;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

public class Calendr extends BodyTagSupport {
	/**
	 * Application name.
	 */
	private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
	/**
	 * Global instance of the JSON factory.
	 */
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final long serialVersionUID = 1L;
	String name = null;

	Calendar service = null;
	
	public int doStartTag() throws JspException {
		try {
			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, Test.getCredential())
				.setApplicationName(APPLICATION_NAME).build();

			service.calendars();
		} catch (IOException e) {
			throw new JspException("Error acquiring calendar events");
		} catch (GeneralSecurityException e) {
			throw new JspException("Error acquiring calendar events");
		}

	return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
    	return super.doEndTag();
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
