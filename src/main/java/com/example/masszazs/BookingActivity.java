package com.example.masszazs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.masszazs.Booking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        View mBookButton = findViewById(R.id.book_button);

        mBookButton.setOnClickListener(v -> book());
    }

    private void book() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bookingsRef = database.getReference("users").child(mAuth.getUid()).child("bookings");

        String time = mTimeEditText.getText().toString();
        long date = mCalendarView.getDate();

        Booking booking = new Booking(mAuth.getUid(), time, date);
        bookingsRef.push().setValue(booking).addOnSuccessListener(aVoid -> {
            Toast.makeText(BookingActivity.this, "Booking successful!", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(BookingActivity.this, "Booking failed!", Toast.LENGTH_SHORT).show();
        });
    }

}
