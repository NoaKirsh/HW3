package android.technion.hw3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//---------------------------------------------------------------------------------

public class list_activity extends AppCompatActivity {
    ListView list;
    Button insert_bottun;
    private ArrayList<String> arrayList;
    Costum_Array_Adapter customAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser curr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        list = findViewById(R.id.myList);
        arrayList = new ArrayList<String>();
        customAdapter = new Costum_Array_Adapter(this, arrayList);
        mAuth = FirebaseAuth.getInstance();
//        curr = FirebaseAuth.getInstance().getCurrentUser();

        insert_bottun = findViewById(R.id.insert);
        list.setAdapter(customAdapter);
    }

    public void on_insert(View v) {
        EditText i = findViewById(R.id.new_item);
        final String item = (i).getText().toString();
        if (item.isEmpty())
            Toast.makeText(getApplicationContext(), "Item must not be empty", Toast.LENGTH_SHORT).show();
        else {
            customAdapter.add(item);
            db.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .collection("list")
                .add(new List_item(item));
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