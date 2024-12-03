package dnd.dmtools;

import java.io.Serializable;

public class NoteDnD implements Serializable {
    public String title;
    public String contents;
    public String pointsOfInterest;

    public NoteDnD() {
        this.title = "Новая заметка";
        this.contents = "Введите содержание заметки.";
        this.pointsOfInterest = "Место для точек интереса";
    }
}
