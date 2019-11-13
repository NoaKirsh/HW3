package android.technion.hw3;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

//---------------------------------------------------------------------------------

public class list_activity extends AppCompatActivity {
    ListView list;
    Button insert_bottun;
    private ArrayList<String> arrayList;
    Costum_Array_Adapter customAdapter;
//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        list = findViewById(R.id.myList);
        arrayList = new ArrayList<>();
        customAdapter = new Costum_Array_Adapter(this, arrayList);
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser curr = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();

        insert_bottun = findViewById(R.id.insert);
        list.setAdapter(customAdapter);
    }

    public void on_insert(View v) {
        EditText e = findViewById(R.id.new_item);
        customAdapter.add(e.getText().toString());
    }

    public void on_sign_out(View view) {
//        mAuth.signOut();
        Intent i = new Intent(this, sign_in_activity.class);
        startActivity(i);
        finish();
    }
}