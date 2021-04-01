package sample;

import javafx.scene.layout.Pane;

/**
 * 
 */
public class EntryString implements Comparable<EntryString> {
    private final String key;
    private final Pane value;

    public EntryString(String key, Pane value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return key;
    }

    public Pane getValue() {
        return value;
    }

    @Override
    public int compareTo(EntryString other) {
        return key.compareTo(other.getKey());
    }

}