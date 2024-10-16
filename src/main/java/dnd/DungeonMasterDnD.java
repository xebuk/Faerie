package dnd;

import java.io.Serializable;
import java.util.HashMap;

public class DungeonMasterDnD implements Serializable {
    public String username;

    public HashMap<String, PlayerDnD> playerDnDHashMap;
}
