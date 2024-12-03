package dnd.dmtools;

import java.io.Serializable;

public class QuestDnDForPlayers implements Serializable {
    public String title;
    public String process;
    public String summary;

    public QuestDnDForPlayers(QuestDnDForDm qd) {
        this.title = qd.title;
        this.summary = qd.summary;
    }

    @Override
    public String toString() {
        StringBuilder quest = new StringBuilder();
        quest.append(this.title).append("\n");
        quest.append("Стадия прогресса: ").append(this.process).append("\n");
        quest.append("Описание: ").append(this.summary);

        return quest.toString();
    }
}
