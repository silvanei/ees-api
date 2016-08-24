package org.ees.api.agenda.infra.gson;

import com.google.gson.*;

import javax.ws.rs.core.Link;
import java.lang.reflect.Type;

/**
 * Created by silvanei on 05/08/2016.
 */
public class LinkGson implements JsonDeserializer, JsonSerializer<Link> {
    @Override
    public JsonElement serialize(Link link, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("rel", link.getRel());
        jsonObject.addProperty("href", link.getUri().toString());
        return jsonObject;
    }

    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }
}
