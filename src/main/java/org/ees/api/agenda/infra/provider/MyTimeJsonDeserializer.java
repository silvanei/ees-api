package org.ees.api.agenda.infra.provider;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Time;

/**
 * Created by silvanei on 30/07/16.
 */
public class MyTimeJsonDeserializer implements JsonDeserializer<Time> {

    @Override
    public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String time = json.getAsString();
        String[] parts = time.split(":");
        return new Time(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }
}
