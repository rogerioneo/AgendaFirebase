package br.edu.ifsp.agendafirebase.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.edu.ifsp.agendafirebase.R;
import br.edu.ifsp.agendafirebase.model.Contato;

public class DetalheActivity extends AppCompatActivity {

    Contato c;
    String ContatoID;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (getIntent().hasExtra("contato"))
        {

            ContatoID = getIntent().getStringExtra("contato");

            // Código para buscar o Contato no Firebase pelo ID

            databaseReference.child(ContatoID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    c = dataSnapshot.getValue(Contato.class);
                    if (c!=null) {
                        EditText nome = findViewById(R.id.editTextNome);
                        nome.setText(c.getNome());

                        EditText fone = findViewById(R.id.editTextFone);
                        fone.setText(c.getFone());

                        EditText email = findViewById(R.id.editTextEmail);
                        email.setText(c.getEmail());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_alterarContato) {


            String nome = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
            String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
            String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

            c.setNome(nome);
            c.setFone(fone);
            c.setEmail(email);

            databaseReference.child(ContatoID).setValue(c);

            Toast.makeText(getApplicationContext(),"Contato alterado",Toast.LENGTH_LONG).show();

            finish();
        }

        if (id ==R.id.action_excluirContato) {

            databaseReference.child(ContatoID).removeValue();

            Toast.makeText(getApplicationContext(),"Contato excluído",Toast.LENGTH_LONG).show();
            finish();

        }


        return super.onOptionsItemSelected(item);
    }




}
