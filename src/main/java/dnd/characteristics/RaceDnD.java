package dnd.characteristics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RaceDnD {
    public String name;
    public String subspeciesName;

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

    public List<String> languages;

    public HashMap<String, String> features;

    public List<String> personality;
    public List<String> ideal;
    public List<String> bond;
    public List<String> flaw;
}
