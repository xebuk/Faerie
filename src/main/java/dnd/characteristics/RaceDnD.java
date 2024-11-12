package dnd.characteristics;

import dnd.values.LanguagesDnD;
import dnd.values.RacesSizeDnD;
import dnd.values.ScriptsDnD;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RaceDnD implements Serializable {
    public String name;
    public String subspeciesName;
    public RacesSizeDnD size;

    public int strengthBonus = 0;
    public int dexterityBonus = 0;
    public int constitutionBonus = 0;
    public int intelligenceBonus = 0;
    public int wisdomBonus = 0;
    public int charismaBonus = 0;

    public int statBonus1;
    public String statBonusTowards1;
    public int statBonus2;
    public String statBonusTowards2;

    public int walkingSpeed;

    public int bonusLanguages = 0;
    public Set<LanguagesDnD> languages;
    public Set<ScriptsDnD> scripts;

    public HashMap<String, String> features;

    public List<String> personality;
    public List<String> ideal;
    public List<String> bond;
    public List<String> flaw;
}
