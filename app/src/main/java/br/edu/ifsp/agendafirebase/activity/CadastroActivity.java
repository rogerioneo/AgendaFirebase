package br.edu.ifsp.agendafirebase.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifsp.agendafirebase.R;
import br.edu.ifsp.agendafirebase.model.Contato;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_salvarContato) {

            String nome = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
            String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
            String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

            Contato c = new Contato(nome,fone, email);

            // Código para gravar contato no Firebase

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.push().setValue(c);


            Toast.makeText(getApplicationContext(),"Contato inserido",Toast.LENGTH_LONG).show();

            finish();
        }

        return super.onOptionsItemSelected(item);
    }



}
