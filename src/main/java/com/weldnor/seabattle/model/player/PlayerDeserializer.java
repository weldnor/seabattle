package com.weldnor.seabattle.model.player;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class PlayerDeserializer extends StdDeserializer<Player> {

    public PlayerDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Player deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        JsonNode typeNode = node.get("type");
        String type = typeNode.asText();

        if (type.equals("human"))
            return new HumanPlayer();
        //else
        return new SimpleBotPlayer();

        //TODO: throw error
    }
}
