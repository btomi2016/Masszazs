package com.example.masszazs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.example.masszazs.Booking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class BookingActivity extends AppCompatActivity {

    private EditText mTimeEditText;
    private CalendarView mCalendarView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mAuth = FirebaseAuth.getInstance();

        mTimeEditText = findViewById(R.id.time_edit_text);
        mCalendarView = findViewById(R.id.calendar_view);
        Button mBookButton = findViewById(R.id.book_button);
        Button mProfileButton = findViewById(R.id.profile_button);

        mBookButton.setOnClickListener(v -> book());
        mProfileButton.setOnClickListener(v -> goToProfile(mTimeEditText.getText().toString(), mCalendarView.getDate()));
    }

    private void book() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bookingsRef = database.getReference("users").child(Objects.requireNonNull(mAuth.getUid())).child("bookings");

        String time = mTimeEditText.getText().toString();
        long date = mCalendarView.getDate();

        String uid = mAuth.getUid();
        if (uid == null) {
            Toast.makeText(BookingActivity.this, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        Booking booking = new Booking(uid, time, date);
        bookingsRef.push().setValue(booking).addOnSuccessListener(aVoid -> {
            Toast.makeText(BookingActivity.this, "Booking successful!", Toast.LENGTH_SHORT).show();
            goToProfile(time, date);
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(BookingActivity.this, "Booking failed!", Toast.LENGTH_SHORT).show();
        });
    }

    private void goToProfile(String time, long date) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("time", time);
        intent.putExtra("date", date);
        startActivity(intent);
    }

}
