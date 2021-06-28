package orak.uzeyir.geyikchatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<String> list;
    String userName;
    RecyclerView userRecyView;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // bu kısımda action bardaki yazı ve rengi degistiriyoruz değiştiriyoruz.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));
        actionBar.setTitle("GeYik");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tanimla();
        listele();
    }

    public void tanimla()
    {
        userName = getIntent().getExtras().getString("kadi");
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        list = new ArrayList<>();
        userRecyView = (RecyclerView)findViewById(R.id.userRecyView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity2.this, 2);
        userRecyView.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(MainActivity2.this, list, MainActivity2.this, userName);
        userRecyView.setAdapter(userAdapter);
    }

    public void listele()
    {
        reference.child("Kullanıcılar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (!dataSnapshot.getKey().equals(userName))
                {
                    list.add(dataSnapshot.getKey());
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}