package dnd.characteristics;

import dnd.dmtools.CommonItemDnD;

import java.util.ArrayList;
import java.util.List;

public class BackgroundDnD {
    public String name = "";
    public String summary = "";

    public String specialAbility = "";
    public String specialAbilitySummary = "";

    public List<String> learnedSkills;
    public List<String> languages;

    public String instrumentMastery1 = "";
    public String instrumentMastery2 = "";

    public ArrayList<CommonItemDnD> startingEquipment;

    public ArrayList<String> specialInfo;

    public List<String> personality;
    public List<String> ideal;
    public List<String> bond;
    public List<String> flaw;
}
