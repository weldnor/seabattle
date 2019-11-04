package com.weldnor.seabattle.model.player;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PlayerSerializer extends StdSerializer<Player> {
    public PlayerSerializer(Class<Player> t) {
        super(t);
    }

    @Override
    public void serialize(Player player, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (player instanceof BotPlayer) {
            jsonGenerator.writeStringField("type", "bot");
        }
        if (player instanceof HumanPlayer) {
            jsonGenerator.writeStringField("type", "human");
        }
        jsonGenerator.writeEndObject();
    }
}
