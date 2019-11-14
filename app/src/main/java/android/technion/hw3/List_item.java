package android.technion.hw3;

public class List_item {
    private String _text;
//    private String _first_letter;

    List_item(){
    }

    public List_item(String text){
        this._text = text;
//        this._first_letter = text.substring(0, 1);
    }

    public String get_text(){
        return _text;
    }

}
