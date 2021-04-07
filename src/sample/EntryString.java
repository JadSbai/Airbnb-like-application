package sample;

import javafx.scene.layout.Pane;

/**
 * Simple class that implements a key, value system for list of properties from the ListingListController to be sorted
 * but
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 07/04/2021
 */
public class EntryString implements Comparable<EntryString> {

    private final String key;
    private final Pane value;

    /**
     * Constructor
     * @param key key
     * @param value value
     */
    public EntryString(String key, Pane value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key of the entry
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value of the entry
     * @return value
     */
    public Pane getValue() {
        return value;
    }

    /**
     * Comparing method used to order the entries according to their keys
     * @param other another entry which is being compared
     * @return an integer used to establish the order. It will be positive or negative depending on which one goes first
     */
    @Override
    public int compareTo(EntryString other) {
        return key.compareTo(other.getKey());
    }
}