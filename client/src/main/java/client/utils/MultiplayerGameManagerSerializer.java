package client.utils;

import client.MultiplayerGameManager;
import client.Question;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import commons.Multiplayer;

import java.io.IOException;
import java.util.Iterator;

public class MultiplayerGameManagerSerializer extends StdSerializer<MultiplayerGameManager> {

    private ObjectMapper om = new ObjectMapper();

    public MultiplayerGameManagerSerializer() {
        this(null);
    }

    public MultiplayerGameManagerSerializer(Class<?> vc) {
        super((Class<MultiplayerGameManager>) vc);
    }

    /**
     * This method serializes the java MultiplayerGameManager object by listing its attributes gameID,
     * list of questions, and list of players (array used here for ease)
     *
     * @param mgc      object received to be serialized
     * @param gen      the actual json generator that sends the messages over and converts the object
     * @param provider used to obtain serializers capable of serializing instances of specific types
     * @throws IOException occurs when the data is corrupted or an error occured in the stream
     */
    public void serialize(MultiplayerGameManager mgc, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("gameID", mgc.getGameID());

        gen.writeFieldName("questions");
        gen.writeStartArray();

        if (mgc.getQuestions().size() > 0) {
            Iterator<Question> iter = mgc.getQuestions().iterator();
            gen.writeRaw(om.writeValueAsString(iter.next()));

            while (iter.hasNext()) {
                gen.writeRaw(",");
                gen.writeRaw(om.writeValueAsString(iter.next()));
            }
        }
        gen.writeEndArray();

        gen.writeFieldName("players");
        gen.writeStartArray();

        if (mgc.getPlayers().size() > 0) {
            Iterator<Multiplayer> iter = mgc.getPlayers().iterator();
            gen.writeRaw(om.writeValueAsString(iter.next()));

            while (iter.hasNext()) {
                gen.writeRaw(",");
                gen.writeRaw(om.writeValueAsString(iter.next()));
            }
        }
        gen.writeEndArray();

        gen.writeEndObject();
    }
}
