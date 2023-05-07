package com.example.masszazs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;

public class ProfileActivity extends AppCompatActivity {

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView nameTextView = findViewById(R.id.name_text_view);
        TextView emailTextView = findViewById(R.id.email_text_view);
        TextView timeTextView = findViewById(R.id.time_text_view);
        TextView dateTextView = findViewById(R.id.date_text_view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();

            nameTextView.setText(name);
            emailTextView.setText(email);

            Intent intent = getIntent();
            if (intent.hasExtra("time") && intent.hasExtra("date")) {
                String time = intent.getStringExtra("time");
                long date = intent.getLongExtra("date", 0);
                timeTextView.setText(time);
                dateTextView.setText(DateFormat.getDateInstance().format(date));
            } else {
                Toast.makeText(this, "No booking found!", Toast.LENGTH_SHORT).show();
            }
        }

        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
