package org.ees.api.agenda.infra.gson;

import com.google.gson.*;
import org.ees.api.agenda.entity.DiaDaSemana;

import java.lang.reflect.Type;

/**
 * Created by silvanei on 30/07/16.
 */
public class DiaDaSemanaGson implements JsonSerializer<DiaDaSemana> {

    @Override
    public JsonElement serialize(DiaDaSemana diaDaSemana, Type type, JsonSerializationContext jsonSerializationContext) {

        return new JsonPrimitive(diaDaSemana.dia());
    }
}
