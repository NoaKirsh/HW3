package android.technion.hw3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


//---------------------------------------------------------------------------------

public class list_activity extends AppCompatActivity {

    private My_recycle_adapter my_recycle_adapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button insert_bottun;
    Button logout_bottun;
    EditText new_item_bottun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        mAuth = FirebaseAuth.getInstance();
        insert_bottun = findViewById(R.id.insert);
        new_item_bottun = findViewById(R.id.new_item);
        logout_bottun = findViewById(R.id.button_log_out);

        Query query = db
                .collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .collection("list")
                .orderBy("_text", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<List_item> options = new FirestoreRecyclerOptions.Builder<List_item>()
                .setQuery(query, List_item.class)
                .build();

        my_recycle_adapter = new My_recycle_adapter(options);
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(my_recycle_adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        my_recycle_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        my_recycle_adapter.stopListening();
    }

    void disableViews() {
        logout_bottun.setEnabled(false);
        insert_bottun.setEnabled(false);
        new_item_bottun.setEnabled(false);

    }

    void enableViews() {
        logout_bottun.setEnabled(true);
        insert_bottun.setEnabled(true);
        new_item_bottun.setEnabled(true);
    }

    public void on_insert(View v) {
        disableViews();
        EditText i = findViewById(R.id.new_item);
        final String item = (i).getText().toString();
        if (item.isEmpty())
            Toast.makeText(getApplicationContext(), "Item must not be empty", Toast.LENGTH_SHORT).show();
        else {
                db.collection("users")
                    .document(mAuth.getCurrentUser().getUid())
                    .collection("list")
                    .add(new List_item(item))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(this.getClass().getName(), "DocumentSnapshot added with ID: " + documentReference.getId());
                        enableViews();
                    }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(this.getClass().getName(), "Error adding document", e);
                        enableViews();
                        }
                    });
            }
            Toast.makeText(this, "new item was added", Toast.LENGTH_SHORT).show();
            i.getText().clear();
        }

    public void on_sign_out(View view) {
        mAuth.signOut();
        Intent i = new Intent(this, sign_in_activity.class);
        startActivity(i);
        finish();
    }
}