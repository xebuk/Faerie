package dnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DungeonMasterDnD implements Serializable {
    public String username;

    public HashMap<String, PlayerDnD> playerDnDHashMap;

    public ArrayList<CommonItemDnD> itemCollection;
    public ArrayList<MagicItemDnD> magicItemCollection;

    public ArrayList<QuestDnD> questRoster;

    public ArrayList<NonPlayerDnD> npcRoster;
    public ArrayList<CompanionDnD> companionRoster;

    public HashMap<String, String> settingNotes;
}
