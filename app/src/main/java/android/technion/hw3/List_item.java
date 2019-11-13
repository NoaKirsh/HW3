package android.technion.hw3;

public class List_item {
    private String text;
    private String first_letter;

    List_item(String text){
        this.text = text;
        this.first_letter = text.substring(0, 1);
    }

    String get_text(){
        return text;
    }

    String get_first_letter(){
        return first_letter;
    }

}
