package org.ees.api.agenda.resource.bean;

import org.joda.time.DateTime;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by silvanei on 10/09/16.
 */
public class DateParam {

    private final DateTime date;

    public DateParam(String dateStr) throws WebApplicationException {
        if (dateStr.isEmpty()) {
            this.date = null;
            return;
        }
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.date = new DateTime(dateFormat.parse(dateStr));
        } catch (ParseException e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity("Couldn't parse date string: " + e.getMessage())
                    .build());
        }
    }

    public DateTime getDate() {
        return date;
    }
}
