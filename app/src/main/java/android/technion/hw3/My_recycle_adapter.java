package android.technion.hw3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class My_recycle_adapter extends FirestoreRecyclerAdapter<List_item, My_recycle_adapter.MyViewHolder> {

    public My_recycle_adapter(@NonNull FirestoreRecyclerOptions<List_item> options) {
        super(options);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView my_view  = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, true);
        return new MyViewHolder(my_view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull My_recycle_adapter.MyViewHolder viewHolder, int position, @NonNull List_item l) {
        viewHolder.item.setText(l.get_text());
        viewHolder.first_letter.setText(l.get_first_letter());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item;
        public TextView first_letter;

        public MyViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            first_letter = itemView.findViewById(R.id.first_letter);
        }
    }
}