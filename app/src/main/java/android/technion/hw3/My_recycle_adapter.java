package android.technion.hw3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class My_recycle_adapter extends RecyclerView.Adapter<My_recycle_adapter.MyViewHolder> {
    private List<List_item> list;

    // Provide a suitable constructor (depends on the kind of dataset)
    public My_recycle_adapter(List<List_item> my_list) {
        list = my_list;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView my_view  = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new MyViewHolder(my_view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(My_recycle_adapter.MyViewHolder viewHolder, int position) {
        List_item item = list.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.item;
        TextView first_letter = viewHolder.first_letter;

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView item;
        private TextView first_letter;

        public MyViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            first_letter = itemView.findViewById(R.id.first_letter);
        }

        TextView get_item(){
            return item;
        }

        TextView first_letter(){
            return first_letter;
        }

    }
}