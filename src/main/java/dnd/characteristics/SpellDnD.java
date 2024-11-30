package dnd.characteristics;

import dnd.values.aspectvalues.SpellComponentsDnD;
import dnd.values.characteristicsvalues.JobsDnD;

import java.util.HashSet;

public class SpellDnD extends AbilityDnD {
    public String school;

    public String setupTime;
    public double distance;

    public HashSet<SpellComponentsDnD> components = new HashSet<>();
    public String duration;

    public void setSchool(String school) {
        this.school = school;
    }

    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void addComponent(SpellComponentsDnD component) {
        this.components.add(component);
    }

    public void delComponent(SpellComponentsDnD component) {
        this.components.remove(component);
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    @Override
    public String toString() {
        StringBuilder spell = new StringBuilder();
        spell.append(this.title).append("\n");
        spell.append("Уровень: ").append(this.level).append("\n");
        spell.append("Школа: ").append(school).append("\n");
        spell.append("Классы: ");
        if (jobs.isEmpty()) {
            spell.append("Нет., ");
        }
        else {
            for (JobsDnD job : jobs) {
                spell.append(job.toString()).append(", ");
            }
        }
        spell.setLength(spell.length() - 2);
        spell.append("\nПодклассы: ");
        if (prestigeJobs.isEmpty()) {
            spell.append("Нет.\n, ");
        }
        else {
            for (String prestigeJob : prestigeJobs) {
                spell.append(prestigeJob).append(", ");
            }
        }
        spell.setLength(spell.length() - 2);
        spell.append("\nВремя подготовки: ").append(setupTime).append("\n");
        spell.append("Дистанция: ").append(distance).append("\n");
        spell.append("Длительность: ").append(duration).append("\n");
        spell.append("Компоненты: ").append(components).append("\n");
        spell.append("Описание:\n").append(this.summary);
        return spell.toString();
    }

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
