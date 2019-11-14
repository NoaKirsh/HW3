package android.technion.hw3;

public class List_item {
    private String text;
    private String first_letter;

    public List_item(String text){
        this.text = text;
        this.first_letter = text.substring(0, 1);
    }

    public String get_text(){
        return text;
    }

    public String get_first_letter(){
        return first_letter;
    }

}
