package net.yoching.android;

/**
 * Created by marcrisney on 5/6/16.
 */
public class Wrexagram {
    @Override
    public String toString() {
        return "Wrexagram{" +
                "number=" + number +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", whatsUp='" + whatsUp + '\'' +
                '}';
    }

    private int number;
    private String title;
    private String subtitle;
    private String whatsUp;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubtitle() {return subtitle;}

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getWhatsUp() {
        return whatsUp;
    }

    public void setWhatsUp(String whatsUp) {
        this.whatsUp = whatsUp;
    }

}
