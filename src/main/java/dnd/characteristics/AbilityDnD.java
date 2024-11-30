package dnd.characteristics;

import dnd.values.characteristicsvalues.JobsDnD;

import java.io.Serializable;
import java.util.HashSet;

public class AbilityDnD implements Serializable {
    public String title;
    public int level;
    public HashSet<JobsDnD> jobs = new HashSet<>();
    public HashSet<String> prestigeJobs = new HashSet<>();

    public String summary;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addJobs(JobsDnD job) {
        this.jobs.add(job);
    }

    public void delJobs(JobsDnD job) {
        this.jobs.remove(job);
    }

    public void addPrestigeJob(String prestigeJob) {
        this.prestigeJobs.add(prestigeJob);
    }

    public void delPrestigeJob(String prestigeJob) {
        this.prestigeJobs.remove(prestigeJob);
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public AbilityDnD() {
        this.title = "Своя способность";
        this.level = 1;

        this.summary = "Описание способности";
    }

    @Override
    public String toString() {
        StringBuilder ability = new StringBuilder();
        ability.append(this.title).append("\n");
        ability.append("Уровень: ").append(this.level).append("\n");
        ability.append("Классы: ");
        if (jobs.isEmpty()) {
            ability.append("Нет., ");
        }
        else {
            for (JobsDnD job : jobs) {
                ability.append(job.toString()).append(", ");
            }
        }
        ability.setLength(ability.length() - 2);
        ability.append("\nПодклассы: ");
        if (prestigeJobs.isEmpty()) {
            ability.append("Нет.\n, ");
        }
        else {
            for (String prestigeJob : prestigeJobs) {
                ability.append(prestigeJob).append(", ");
            }
        }
        ability.setLength(ability.length() - 2);
        ability.append("\nОписание:\n").append(this.summary);
        return ability.toString();
    }
}
