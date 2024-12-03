package dnd.dmtools;

import java.io.Serializable;

public class QuestDnDForDm implements Serializable {
    public String title;
    public String process;
    public String summary;
    public String notes;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public QuestDnDForDm() {
        this.title = "Новое задание";
        this.process = "В ожидания принятия";
        this.summary = "Добавьте описание вашему заданию";
        this.notes = "Впишите сюда свои заметки по поводу задания";
    }

    @Override
    public String toString() {
        StringBuilder quest = new StringBuilder();
        quest.append(this.title).append("\n");
        quest.append("Стадия прогресса: ").append(this.process).append("\n");
        quest.append("Описание: ").append(this.summary).append("\n");
        quest.append("Заметка: ").append(this.notes);

        return quest.toString();
    }
}
