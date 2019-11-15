package android.technion.hw3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sign_up_activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button sign_up_button;
    EditText email_button;
    EditText password_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        mAuth = FirebaseAuth.getInstance();
        sign_up_button = findViewById(R.id.button_sign_up);
        email_button = findViewById(R.id.Email_2);
        password_button = findViewById(R.id.Password_2);
    }

    void disableViews() {
        sign_up_button.setEnabled(false);
        email_button.setEnabled(false);
        password_button.setEnabled(false);

    }

    void enableViews() {
        sign_up_button.setEnabled(true);
        email_button.setEnabled(true);
        password_button.setEnabled(true);
    }

    public void go_back_to_sign_in(View view) {
        final String email = ((EditText) findViewById(R.id.Email_2)).getText().toString();
        final String password = ((EditText) findViewById(R.id.Password_2)).getText().toString();
        if (email.length() == 0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Email is not in the valid form", Toast.LENGTH_SHORT).show();

        } else if (password.length() == 0 || password.length() < 6) {
            Toast.makeText(getApplicationContext(), "password must have at least 6 characters", Toast.LENGTH_SHORT).show();

        } else {
            disableViews();
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Map<String, Object> user = new HashMap<>();
                            user.put("email", mAuth.getCurrentUser().getEmail());
                            //                    user.put("password",password);
                            db.collection("users")
                                    .document(mAuth.getCurrentUser().getUid())
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void v) {
                                            Log.d(this.getClass().getName(), "Document added");
                                            enableViews();
                                            mAuth.signOut();
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(this.getClass().getName(), "Error adding document", e);
                                            enableViews();
                                        }
                                    });
                            mAuth.signOut();
                            finish();
                        } else {
                            // If the sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Invalid input. Try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
}
