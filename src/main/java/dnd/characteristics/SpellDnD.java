package dnd.characteristics;

import dnd.values.SpellComponentsDnD;

import java.util.HashSet;

public class SpellDnD extends AbilityDnD {
    public String title;
    public int level;
    public String school;

    public String setupTime;
    public double distance;

    public HashSet<SpellComponentsDnD> components = new HashSet<>();
    public String duration;

    public HashSet<JobDnD> jobs = new HashSet<>();
    public HashSet<JobDnD> prestigeJob = new HashSet<>();

    public String summary;

    public SpellDnD() {
        this.title = "Своё заклинание";
        this.level = 1;
        this.school = "Своя школа";

        this.setupTime = "Определите сами";
        this.distance = 1;

        this.duration = "Определите сами";

        this.summary = "Описание способности";
    }
}
