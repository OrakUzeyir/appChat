package orak.uzeyir.geyikchatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    String userName, otherName;
    TextView chatUserName;
    ImageView sendImage;
    EditText chatEditText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView recyclerView;
    MesajAdapter mesajAdapter;
    List<MesajModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // bu kısımda action bardaki yazı ve rengi degistiriyoruz değiştiriyoruz.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));
        actionBar.setTitle("GeYik");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tanimla();
        loadMesaj();
    }

    public void tanimla()
    {
        userName = getIntent().getExtras().getString("userName");
        otherName = getIntent().getExtras().getString("otherName");
        chatUserName = (TextView)findViewById(R.id.chatUserName);
        sendImage = (ImageView)findViewById(R.id.sendImage);
        chatEditText = (EditText)findViewById(R.id.chatEditText);
        chatUserName.setText(otherName);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        list = new ArrayList<>();

        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesaj = chatEditText.getText().toString();
                chatEditText.setText("");
                mesajGonder(mesaj);
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChatActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        mesajAdapter = new MesajAdapter(ChatActivity.this, list, ChatActivity.this, userName);
        recyclerView.setAdapter(mesajAdapter);

    }

    public void mesajGonder(String text)
    {
        final String key = reference.child("Mesajlar").child(userName).child(otherName).push().getKey();
        final Map messageMap = new HashMap();
        messageMap.put("text", text);
        messageMap.put("from", userName);
        reference.child("Mesajlar").child(userName).child(otherName).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    reference.child("Mesajlar").child(otherName).child(userName).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }

            }
        });
    }

    public void loadMesaj()
    {
        reference.child("Mesajlar").child(userName).child(otherName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MesajModel mesajModel = dataSnapshot.getValue(MesajModel.class);
                list.add(mesajModel);
                mesajAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(list.size()-1);

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