package dnd.mainobjects;

import dnd.characteristics.AbilityDnD;
import dnd.characteristics.FeatDnD;
import dnd.characteristics.SpellDnD;
import dnd.dmtools.*;
import dnd.equipment.*;
import dnd.values.VariantRules;
import dnd.values.aspectvalues.AspectCategoriesDnD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DungeonMasterDnD implements Serializable {
    public long dungeonMasterChatId;
    public String dungeonMasterUsername;
    public String campaignPassword;
    public String campaignName;

    public HashSet<VariantRules> setOfRules = new HashSet<>();

    public HashMap<String, PlayerDnD> campaignParty = new HashMap<>();
    public int multiclassLimit = 0;

    public ArrayList<String> itemCollectionNames = new ArrayList<>();
    public ArrayList<String> weaponCollectionNames = new ArrayList<>();
    public ArrayList<String> armorCollectionNames = new ArrayList<>();
    public ArrayList<String> instrumentsCollectionNames = new ArrayList<>();
    public ArrayList<String> kitCollectionNames = new ArrayList<>();
    public ArrayList<String> featCollectionNames = new ArrayList<>();
    public ArrayList<String> abilityCollectionNames = new ArrayList<>();
    public ArrayList<String> spellCollectionNames = new ArrayList<>();

    public ArrayList<ItemDnD> itemCollection = new ArrayList<>();
    public ArrayList<WeaponDnD> weaponCollection = new ArrayList<>();
    public ArrayList<ArmorDnD> armorCollection = new ArrayList<>();
    public ArrayList<InstrumentDnD> instrumentsCollection = new ArrayList<>();
    public ArrayList<KitDnD> kitCollection = new ArrayList<>();
    public ArrayList<FeatDnD> featCollection = new ArrayList<>();
    public ArrayList<AbilityDnD> abilityCollection = new ArrayList<>();
    public ArrayList<SpellDnD> spellCollection = new ArrayList<>();

    public boolean lockVault = false;

    public AspectCategoriesDnD currentAspectType = AspectCategoriesDnD.NONE;
    public int editIndex = 0;
    public String editParameter;

    public ItemDnD editItem;
    public WeaponDnD editWeapon;
    public ArmorDnD editArmor;
    public InstrumentDnD editInstruments;
    public KitDnD editKit;
    public FeatDnD editFeat;
    public AbilityDnD editAbility;
    public SpellDnD editSpell;

    public int editQuestIndex = 0;
    public ArrayList<QuestDnDForDm> questRoster = new ArrayList<>();

    public ArrayList<QuestDnDForPlayers> questBoard = new ArrayList<>();

    public ArrayList<NonPlayerDnD> npcRoster = new ArrayList<>();
    public ArrayList<CompanionDnD> companionRoster = new ArrayList<>();

    public ArrayList<NoteDnD> settingNotes = new ArrayList<>();

    public DungeonMasterDnD() {}

    public String campaignStatus() {
        StringBuilder campaign = new StringBuilder();

        campaign.append("Имя компании: ").append(campaignName).append("\n");
        campaign.append("Количество игроков:").append(campaignParty.size());
        int index = 1;
        for (String tag: campaignParty.keySet()) {
            campaign.append(index).append(". ").append(tag).append(" - ")
                    .append(campaignParty.get(tag).name).append("\n");
        }
        return campaign.toString();
    }

    public String questBoardAsString() {
        StringBuilder quests = new StringBuilder();

        quests.append("Задания: ");
        if (questBoard.isEmpty()) {
            quests.append("На данный момент нет.");
            return quests.toString();
        }

        quests.append("\n");
        for (int i = 1; i < questBoard.size() + 1; i++) {
            quests.append(i).append(". ").append(questBoard.get(i).title).append("\n");
        }
        return quests.toString();
    }

    public String questRosterAsString() {
        StringBuilder quests = new StringBuilder();

        quests.append("Задания ДМ-а: ");
        if (questRoster.isEmpty()) {
            quests.append("На данный момент нет.");
            return quests.toString();
        }

        quests.append("\n");
        for (int i = 1; i < questRoster.size() + 1; i++) {
            quests.append(i).append(". ").append(questRoster.get(i).title).append("\n");
        }
        return quests.toString();
    }

    public String getBoardQuest(int index) {
        if (index <= 0) {
            index = 1;
        }
        return questBoard.get(index - 1).toString();
    }

    public String getRosterQuest(int index) {
        if (index <= 0) {
            index = 1;
        }
        return questRoster.get(index - 1).toString();
    }
}
