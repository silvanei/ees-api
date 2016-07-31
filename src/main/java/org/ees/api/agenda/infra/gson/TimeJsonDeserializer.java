package org.ees.api.agenda.infra.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Time;

/**
 * Created by silvanei on 30/07/16.
 */
public class TimeJsonDeserializer implements JsonDeserializer<Time>, JsonSerializer<Time> {

    @Override
    public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String time = json.getAsString();
        String[] parts = time.split(":");
        return new Time(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    @Override
    public JsonElement serialize(Time time, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(time.toString());
    }
}
