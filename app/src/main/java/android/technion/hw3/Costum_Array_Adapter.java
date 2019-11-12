package android.technion.hw3;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class Costum_Array_Adapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> elements;

    public Costum_Array_Adapter(Activity context, ArrayList<String> elements) {
        super(context, R.layout.row, elements);
        this.context = context;
        this.elements = elements;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(R.layout.row, null,true);

        TextView element = v.findViewById(R.id.element);
        TextView index = v.findViewById(R.id.index);

        element.setText(elements.get(i));
        index.setText(String.valueOf((i+1)));
        return v;
    }
}
