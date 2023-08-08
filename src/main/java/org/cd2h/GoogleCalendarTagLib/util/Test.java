package org.cd2h.GoogleCalendarTagLib.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/* class to demonstrate use of Calendar events list API */
public class Test {
	/**
	 * Application name.
	 */
	private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
	/**
	 * Global instance of the JSON factory.
	 */
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

	/**
	 * Creates an authorized Credential object.
	 *
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws GeneralSecurityException
	 * @throws IOException              If the credentials.json file cannot be
	 *                                  found.
	 */
	@SuppressWarnings("deprecation")
	public static GoogleCredential getCredential() throws GeneralSecurityException, IOException {

		GoogleCredential credential = null;

		NetHttpTransport HttpTransport = GoogleNetHttpTransport.newTrustedTransport();

		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

		try {

			InputStream jsonFileStream = Test.class.getResourceAsStream("credentials.json");

			GoogleCredential readJsonFile = GoogleCredential.fromStream(jsonFileStream, HttpTransport, JSON_FACTORY)
					.createScoped(CalendarScopes.all());

			credential = new GoogleCredential.Builder().setTransport(readJsonFile.getTransport())
					.setJsonFactory(readJsonFile.getJsonFactory())
					.setServiceAccountId(readJsonFile.getServiceAccountId())
					.setServiceAccountUser("cd2h-website-events@cd2h-website.iam.gserviceaccount.com")
					.setServiceAccountScopes(readJsonFile.getServiceAccountScopes())
					.setServiceAccountPrivateKey(readJsonFile.getServiceAccountPrivateKey()).build();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return credential;
	}

	public static void main(String... args) throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredential())
				.setApplicationName(APPLICATION_NAME).build();

		service.calendars();
		// List the next 10 events from the target calendar.
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("6l35k1j2vrhj4q167vchh1qvv8@group.calendar.google.com")
				.setMaxResults(10)
				.setTimeMin(now)
				.setOrderBy("startTime")
				.setSingleEvents(true)
				.execute();
		List<Event> items = events.getItems();
		if (items.isEmpty()) {
			System.out.println("No upcoming events found.");
		} else {
			System.out.println("Upcoming events");
			for (Event event : items) {
				DateTime start = event.getStart().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				}
				System.out.printf("%s (%s)\n", event.getSummary(), start);
				System.out.println(event.getHtmlLink());

				SimpleDateFormat formatter = new SimpleDateFormat("MMM dd K:mm a z");
				formatter.setTimeZone(TimeZone.getTimeZone("CST"));
				Date startDate = new Date(event.getStart().getDateTime().getValue());
				System.out.println(formatter.format(startDate));
}
		}
	}
}