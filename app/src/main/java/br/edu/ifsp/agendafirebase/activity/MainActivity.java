package br.edu.ifsp.agendafirebase.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.edu.ifsp.agendafirebase.R;
import br.edu.ifsp.agendafirebase.data.ContatoAdapter;
import br.edu.ifsp.agendafirebase.model.Contato;

public class MainActivity extends AppCompatActivity {

    ContatoAdapter adapter;
    DatabaseReference databaseReference;
    Query queryFirebase;
    FirebaseRecyclerOptions<Contato> options;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

       // Código para carregar todos os contatos

        databaseReference = FirebaseDatabase.getInstance().getReference();
        queryFirebase = databaseReference.orderByChild("nome");
        options = new FirebaseRecyclerOptions.Builder<Contato>()
                                            .setQuery(queryFirebase, Contato.class)
                                            .build();
        adapter = new ContatoAdapter(options);

        recyclerView.setAdapter(adapter);

        adapter.startListening();

       adapter.setClickListener(new ContatoAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(getApplicationContext(), DetalheActivity.class);

                // Código para obter o ID do contato selecionado
                i.putExtra("contato",adapter.getRef(position).getKey());

                startActivityForResult(i,2);

            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                  // Código para excluir o contato usando swipe
                  int position = viewHolder.getAdapterPosition();
                  databaseReference.child(adapter.getRef(position).getKey()).removeValue();

                  Toast.makeText(getApplicationContext(),"Contato apagado", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive)
            {
                Bitmap icon;
                Paint p = new Paint();

                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;

                p.setColor(ContextCompat.getColor(getBaseContext(), android.R.color.holo_orange_light));

                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());

                c.drawRect(background, p);

                icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_delete);

                RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width,
                        (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);

                c.drawBitmap(icon, null, icon_dest, null);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }




        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),CadastroActivity.class);
                startActivityForResult(i,1);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView;
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                //  código para filtrar os contatos
                queryFirebase = databaseReference.orderByChild("nome")
                            .startAt(query).endAt(query + "\uf8ff");
                options = new FirebaseRecyclerOptions.Builder<Contato>()
                            .setQuery(queryFirebase,Contato.class).build();
                adapter = new ContatoAdapter(options);
                recyclerView.setAdapter(adapter);
                adapter.startListening();

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
