package dnd.mainobjects;

import dnd.dmtools.*;
import dnd.equipment.ItemDnD;
import dnd.equipment.MagicItemDnD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DungeonMasterDnD implements Serializable {
    public long chatId;
    public String username;
    public String password;
    public String campaignName;

    public HashMap<String, PlayerDnD> playerDnDHashMap = new HashMap<>();
    public int multiclassLimit = 0;

    public ArrayList<ItemDnD> itemCollection = new ArrayList<>();
    public ArrayList<MagicItemDnD> magicItemCollection = new ArrayList<>();

    public ArrayList<QuestDnDForDm> questRoster = new ArrayList<>();

    public ArrayList<NonPlayerDnD> npcRoster = new ArrayList<>();
    public ArrayList<CompanionDnD> companionRoster = new ArrayList<>();

    public HashMap<String, String> settingNotes = new HashMap<>();

    public DungeonMasterDnD() {}

    public String campaignStatus() {
        StringBuilder campaign = new StringBuilder();

        campaign.append("Имя компании: ").append(campaignName).append("\n");
        campaign.append("Количество игроков:").append(playerDnDHashMap.size());
        return campaign.toString();
    }
}
