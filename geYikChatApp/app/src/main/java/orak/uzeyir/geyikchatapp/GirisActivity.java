package orak.uzeyir.geyikchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GirisActivity extends AppCompatActivity {

    EditText kullaniciAdiEdittext;
    Button kayitOlButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // bu kısımda action bardaki yazı ve rengi degistiriyoruz değiştiriyoruz.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));
        actionBar.setTitle("GeYik");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        tanimla();
    }

    public void tanimla()
    {
        kullaniciAdiEdittext = (EditText)findViewById(R.id.kullaniciAdiEdittext);
        kayitOlButton = (Button)findViewById(R.id.kayitOlButton);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        kayitOlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = kullaniciAdiEdittext.getText().toString();
                kullaniciAdiEdittext.setText("");
                ekle(userName);
            }
        });
    }

    public void ekle(final String kadi)  // kullanıcıyı db'ye ekleme fonksiyonu
    {
        reference.child("Kullanıcılar").child(kadi).child("kullaniciAdi").setValue(kadi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "İşlem Başarılı", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GirisActivity.this, MainActivity2.class);
                    intent.putExtra("kadi", kadi);
                    startActivity(intent);
                }
            }
        });
    }
}