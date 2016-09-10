package org.ees.api.agenda.infra.gson;

import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by silvanei on 10/09/16.
 */
public class DateTimeGeson implements JsonDeserializer<DateTime>, JsonSerializer<DateTime> {

    @Override
    public DateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.getAsString().length() == 0) {
            return null;
        }

        try {
            return new DateTime(jsonElement.getAsLong());
        } catch (IllegalArgumentException e) {
            // May be it came in formatted as a java.util.Date, so try that
            Date date = jsonDeserializationContext.deserialize(jsonElement, Date.class);
            return new DateTime(date);
        }
    }

    @Override
    public JsonElement serialize(DateTime dateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(dateTime.getMillis());
    }
}
