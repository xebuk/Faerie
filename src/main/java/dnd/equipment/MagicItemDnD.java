package dnd.equipment;

import java.io.Serializable;

public class MagicItemDnD extends ItemDnD implements Serializable {
    public String name;
    public String summary;

    public int value;

    public int weight;

    public String effects;
}
