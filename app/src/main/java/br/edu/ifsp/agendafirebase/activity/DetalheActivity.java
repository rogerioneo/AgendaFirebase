package br.edu.ifsp.agendafirebase.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsp.agendafirebase.R;
import br.edu.ifsp.agendafirebase.model.Contato;

public class DetalheActivity extends AppCompatActivity {

    Contato c;
    String ContatoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        if (getIntent().hasExtra("contato"))
        {

            ContatoID = getIntent().getStringExtra("contato");



            // Código para buscar o Contato no Firebase pelo ID



            EditText nome = findViewById(R.id.editTextNome);
            nome.setText(c.getNome());

            EditText fone = findViewById(R.id.editTextFone);
            fone.setText(c.getFone());

            EditText email = findViewById(R.id.editTextEmail);
            email.setText(c.getEmail());

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

            // Código para alterar informações do contato

            Toast.makeText(getApplicationContext(),"Contato alterado",Toast.LENGTH_LONG).show();

            finish();
        }

        if (id ==R.id.action_excluirContato) {

            // Código para excluir o contato

            Toast.makeText(getApplicationContext(),"Contato excluído",Toast.LENGTH_LONG).show();
            finish();

        }


        return super.onOptionsItemSelected(item);
    }




}
