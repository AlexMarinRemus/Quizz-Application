package client.utils;

import client.MultiplayerGameManager;
import client.Question;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import commons.Multiplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultiplayerGameManagerDeserializer extends StdDeserializer<MultiplayerGameManager> {

    private ObjectMapper om = new ObjectMapper();

    public MultiplayerGameManagerDeserializer() {
        this(null);
    }

    public MultiplayerGameManagerDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * This method deserializes the JSON to its java MultiplayerGameManager object by listing its attributes gameID,
     * list of questions, and list of players (array used here for ease)
     *
     * @param jp   the actual json parser that provides forward, read-only access to the JSON in a streaming way
     * @param ctxt context for the deserialization of a single root-level value, single-threaded
     * @return the object we want to receive on the other end
     * @throws IOException             occurs when the data is corrupted or an error occured in the stream
     * @throws JsonProcessingException class for all problems encountered when processing any JSON
     */
    public MultiplayerGameManager deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        int testnum = node.get("gameID").asInt();

        List<Question> questionsList = new ArrayList<>();
        var questionsNode = node.get("questions");
        int questionsSize = questionsNode.size();
        for (int i = 0; i < questionsSize; i++) {
            var tmp = questionsNode.get(i);
            var obj = tmp.asText();
            questionsList.add(om.readValue(questionsNode.get(i).toString(), Question.class));
        }

        List<Multiplayer> playersList = new ArrayList<>();
        var playersRaw = node.get("players");
        var playersSize = playersRaw.size();
        for (int i = 0; i < playersSize; i++) {
            playersList.add(om.readValue(playersRaw.get(i).toString(), Multiplayer.class));
        }

        MultiplayerGameManager mgm = new MultiplayerGameManager(testnum);

        mgm.addPlayers(playersList);
        mgm.addQuestions(questionsList);

        return mgm;
    }
}
