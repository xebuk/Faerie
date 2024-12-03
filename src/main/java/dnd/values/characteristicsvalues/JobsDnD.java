package dnd.values.characteristicsvalues;

import dnd.characteristics.JobDnD;

import java.io.Serializable;
import java.util.Map;

public enum JobsDnD implements Serializable {
    Artificer,
    Barbarian,
    Bard,
    Cleric,
    Druid,
    Fighter,
    Monk,
    Paladin,
    Ranger,
    Rogue,
    Sorcerer,
    Warlock,
    Wizard,

    Homebrew;

    private static final Map<String, JobsDnD> jobs = Map.ofEntries(Map.entry("Изобретатель", Artificer),
            Map.entry("Варвар", Barbarian), Map.entry("Бард", Bard),
            Map.entry("Жрец", Cleric), Map.entry("Друид", Druid),
            Map.entry("Воин", Fighter), Map.entry("Монах", Monk),
            Map.entry("Паладин", Paladin), Map.entry("Следопыт", Ranger),
            Map.entry("Плут", Rogue), Map.entry("Чародей", Sorcerer),
            Map.entry("Колдун", Warlock), Map.entry("Волшебник", Wizard),
            Map.entry("Свой класс", Homebrew));

    private static final Map<JobsDnD, String> jobsString = Map.ofEntries(Map.entry(Artificer, "Изобретатель"),
            Map.entry(Barbarian, "Варвар"), Map.entry(Bard, "Бард"),
            Map.entry(Cleric, "Жрец"), Map.entry(Druid, "Друид"),
            Map.entry(Fighter, "Воин"), Map.entry(Monk, "Монах"),
            Map.entry(Paladin, "Паладин"), Map.entry(Ranger, "Следопыт"),
            Map.entry(Rogue, "Плут"), Map.entry(Sorcerer, "Чародей"),
            Map.entry(Warlock, "Колдун"), Map.entry(Wizard, "Волшебник"),
            Map.entry(Homebrew, "Свой класс"));

    private static final Map<JobsDnD, JobDnD> jobsAsClasses = Map.ofEntries(Map.entry(Artificer, new JobDnD.ArtificerDnD()),
            Map.entry(Barbarian, new JobDnD.BarbarianDnD()), Map.entry(Bard, new JobDnD.BardDnD()),
            Map.entry(Cleric, new JobDnD.ClericDnD()), Map.entry(Druid, new JobDnD.DruidDnD()),
            Map.entry(Fighter, new JobDnD.FighterDnD()), Map.entry(Monk, new JobDnD.MonkDnD()),
            Map.entry(Paladin, new JobDnD.PaladinDnD()), Map.entry(Ranger, new JobDnD.RangerDnD()),
            Map.entry(Rogue, new JobDnD.RogueDnD()), Map.entry(Sorcerer, new JobDnD.SorcererDnD()),
            Map.entry(Warlock, new JobDnD.WarlockDnD()), Map.entry(Wizard, new JobDnD.WizardDnD()),
            Map.entry(Homebrew, new JobDnD.HomebrewJobDnD()));

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
