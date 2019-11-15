package android.technion.hw3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_in_activity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            final Intent i = new Intent(this, list_activity.class);
            startActivity(i);
            finish();
        }
    }

    public void go_to_sign_up(View view){
        Intent i = new Intent(this, sign_up_activity.class);
        startActivity(i);
    }

    public void go_to_list_activity(View view){
        final Intent i = new Intent(this, list_activity.class);
        EditText e = findViewById(R.id.Email);
        String email = (e.getText().toString());
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();
        if(email.equals(""))
            Toast.makeText(getApplicationContext(), "please insert your email", Toast.LENGTH_SHORT).show();
        else if(password.equals(""))
            Toast.makeText(getApplicationContext(), "please insert your password", Toast.LENGTH_SHORT).show();
        else if(password.length() < 6)
            Toast.makeText(getApplicationContext(), "password much have at least 6 characters", Toast.LENGTH_SHORT).show();
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(getApplicationContext(), "authentication succeeded", Toast.LENGTH_SHORT).show();
                        Log.d(this.getClass().getName(), "signInUserWithEmail:success");
                        startActivity(i);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d(this.getClass().getName(), "signInUserWithEmail:fail", task.getException());
                        Toast.makeText(getApplicationContext(), "Invalid input. Try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
