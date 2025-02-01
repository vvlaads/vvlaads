package Interfaces;

import Items.Item;

public interface Playable {
    void play(Item item, double location);

    void cast(Item item, double location);
}
