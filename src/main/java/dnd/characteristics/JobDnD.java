package dnd.characteristics;

import dnd.dmtools.CommonItemDnD;
import dnd.dmtools.MagicItemDnD;

import java.util.ArrayList;
import java.util.List;

public class JobDnD {
    public String title;
    public String advancedTitle;
    public boolean usesMagic;

    public int level = 1;
    public int startingHealth;
    public String healthDice;

    public String mainStat1;
    public String mainStat2;

    public int startingSkillAmount;
    public List<String> skillRoster;

    public ArrayList<AbilityDnD> abilityRoster;
}
