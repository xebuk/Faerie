package dnd.dmtools;

public class QuestDnDForPlayers {
    public String title;
    public String summary;
    public int valuableReward;

    public QuestDnDForPlayers(QuestDnDForDm qd) {
        this.title = qd.title;
        this.summary = qd.summary;
        this.valuableReward = qd.valuableReward;
    }
}
