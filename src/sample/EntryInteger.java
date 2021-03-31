package sample;

import javafx.scene.layout.Pane;

public class EntryInteger implements Comparable<EntryInteger> {
    private final Integer key;
    private final Pane value;

    public EntryInteger(Integer key, Pane value) {
        this.key = key;
        this.value = value;
    }
    public Integer getKey() {
        return key;
    }

    public Pane getValue() {
        return value;
    }

    @Override
    public int compareTo(EntryInteger other) {
        return key.compareTo(other.getKey());
    }

}