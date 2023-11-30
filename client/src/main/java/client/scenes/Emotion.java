package client.scenes;

public enum Emotion {

    HAPPY("\uD83D\uDE03"),
    SAD( "\ud83d\ude22"),
    ANGRY("\uD83D\uDE24"),
    HEART("\ud83d\udc96"),
    CHECK("\u2705"),
    WRONG("\u274c");

    private final String text;

    /**
     * constructor method
     * @param text the string form of the emoji
     */
    Emotion(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
