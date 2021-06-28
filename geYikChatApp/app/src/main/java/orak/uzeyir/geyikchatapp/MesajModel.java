package orak.uzeyir.geyikchatapp;

public class MesajModel {             //mesajları alabilmemiz için bir model oluşturmamız gerekiyor.
    private String from, text;

    public MesajModel()
    {

    }

    public MesajModel(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
