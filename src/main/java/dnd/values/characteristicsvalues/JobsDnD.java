package dnd.values.characteristicsvalues;

import dnd.characteristics.JobDnD;
import dnd.equipment.ItemDnD;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public enum JobsDnD implements Serializable {
    ARTIFICER,
    BARBARIAN,
    BARD,
    CLERIC,
    DRUID,
    FIGHTER,
    MONK,
    PALADIN,
    RANGER,
    ROGUE,
    SORCERER,
    WARLOCK,
    WIZARD,

    HOMEBREW;

    private static final Map<String, JobsDnD> jobs = Map.ofEntries(Map.entry("Изобретатель", ARTIFICER),
            Map.entry("Варвар", BARBARIAN), Map.entry("Бард", BARD),
            Map.entry("Жрец", CLERIC), Map.entry("Друид", DRUID),
            Map.entry("Воин", FIGHTER), Map.entry("Монах", MONK),
            Map.entry("Паладин", PALADIN), Map.entry("Следопыт", RANGER),
            Map.entry("Плут", ROGUE), Map.entry("Чародей", SORCERER),
            Map.entry("Колдун", WARLOCK), Map.entry("Волшебник", WIZARD),
            Map.entry("Свой класс", HOMEBREW));

    private static final Map<JobsDnD, String> jobsString = Map.ofEntries(Map.entry(ARTIFICER, "Изобретатель"),
            Map.entry(BARBARIAN, "Варвар"), Map.entry(BARD, "Бард"),
            Map.entry(CLERIC, "Жрец"), Map.entry(DRUID, "Друид"),
            Map.entry(FIGHTER, "Воин"), Map.entry(MONK, "Монах"),
            Map.entry(PALADIN, "Паладин"), Map.entry(RANGER, "Следопыт"),
            Map.entry(ROGUE, "Плут"), Map.entry(SORCERER, "Чародей"),
            Map.entry(WARLOCK, "Колдун"), Map.entry(WIZARD, "Волшебник"),
            Map.entry(HOMEBREW, "Свой класс"));

    private static final Map<JobsDnD, JobDnD> jobsAsClasses = Map.ofEntries(Map.entry(ARTIFICER, new JobDnD.ArtificerDnD()),
            Map.entry(BARBARIAN, new JobDnD.BarbarianDnD()), Map.entry(BARD, new JobDnD.BardDnD()),
            Map.entry(CLERIC, new JobDnD.ClericDnD()), Map.entry(DRUID, new JobDnD.DruidDnD()),
            Map.entry(FIGHTER, new JobDnD.FighterDnD()), Map.entry(MONK, new JobDnD.MonkDnD()),
            Map.entry(PALADIN, new JobDnD.PaladinDnD()), Map.entry(RANGER, new JobDnD.RangerDnD()),
            Map.entry(ROGUE, new JobDnD.RogueDnD()), Map.entry(SORCERER, new JobDnD.SorcererDnD()),
            Map.entry(WARLOCK, new JobDnD.WarlockDnD()), Map.entry(WIZARD, new JobDnD.WizardDnD()),
            Map.entry(HOMEBREW, new JobDnD.HomebrewJobDnD()));

    public static JobDnD getJobAsClass(JobsDnD job) {
        return jobsAsClasses.get(job);
    }

    public static Map<String, JobsDnD> getJobs() {
        return jobs;
    }

    public static JobsDnD getJob(String job) {
        return jobs.get(job);
    }

    @Override
    public String toString() {
        return jobsString.get(this);
    }
}
