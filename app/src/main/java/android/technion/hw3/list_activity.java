package android.technion.hw3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//---------------------------------------------------------------------------------

public class list_activity extends AppCompatActivity {
    Button insert_bottun;
    private ArrayList<List_item> arrayList;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        mAuth = FirebaseAuth.getInstance();
        insert_bottun = findViewById(R.id.insert);
        arrayList = new ArrayList<>();
        do_all_the_firestore_stuff(savedInstanceState);
    }

    protected void do_all_the_firestore_stuff(Bundle savedInstanceState) {
        Query query = FirebaseFirestore.getInstance()
                .collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .collection("list")
                .orderBy("text", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<List_item> options = new FirestoreRecyclerOptions.Builder<List_item>()
                .setQuery(query, List_item.class)
                .build();
        My_recycle_adapter my_recycle_adapter = new My_recycle_adapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(my_recycle_adapter);
    }

    public void on_insert(View v) {
            EditText i = findViewById(R.id.new_item);
            final String item = (i).getText().toString();
            if (item.isEmpty())
                Toast.makeText(getApplicationContext(), "Item must not be empty", Toast.LENGTH_SHORT).show();
            else {
                try {
                db.collection("users")
                        .document(mAuth.getCurrentUser().getUid())
                        .collection("list")
                        .add(new List_item(item));
                } catch (NullPointerException e) {
                    System.out.print("NullPointerException caught");
                }
//            db.collection("users").document(mAuth.getCurrentUser().getUid())
//                    .collection("list")

                Toast.makeText(this, "new item was added", Toast.LENGTH_SHORT).show();
                i.getText().clear();
            }
    }
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Log.d(this.getClass().getName(), "DocumentSnapshot added with ID: " + documentReference.getId());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(this.getClass().getName(), "Error adding document", e);
//                        }
//                    });
//       }
//    }

    public void on_sign_out(View view) {
        mAuth.signOut();
        Intent i = new Intent(this, sign_in_activity.class);
        startActivity(i);
        finish();
    }
}