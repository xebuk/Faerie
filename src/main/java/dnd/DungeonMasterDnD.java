package dnd;

import dnd.dmtools.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DungeonMasterDnD implements Serializable {
    public long chatId;
    public String username;
    public String campaignName;

    public HashMap<String, PlayerDnD> playerDnDHashMap = new HashMap<>();
    public int multiclassLimit = 0;

    public ArrayList<CommonItemDnD> itemCollection = new ArrayList<>();
    public ArrayList<MagicItemDnD> magicItemCollection = new ArrayList<>();

    public ArrayList<QuestDnDForDm> questRoster = new ArrayList<>();

    public ArrayList<NonPlayerDnD> npcRoster = new ArrayList<>();
    public ArrayList<CompanionDnD> companionRoster = new ArrayList<>();

    public HashMap<String, String> settingNotes = new HashMap<>();

    public DungeonMasterDnD() {}
}
