package dnd.characteristics;

import dnd.values.InstrumentsDnD;
import dnd.values.LanguagesDnD;
import dnd.values.ScriptsDnD;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class BackgroundDnD implements Serializable {
    public String name = "";
    public String summary = "";

    public String specialAbility = "";
    public String specialAbilitySummary = "";

    public int bonusSkills;
    public List<String> learnedSkills;

    public Set<InstrumentsDnD> instrumentMastery;

    public int bonusLanguages;
    public Set<LanguagesDnD> languages;
    public Set<ScriptsDnD> scripts;

    public List<String> specialInfo;

    public List<String> personality;
    public List<String> ideal;
    public List<String> bond;
    public List<String> flaw;
}
