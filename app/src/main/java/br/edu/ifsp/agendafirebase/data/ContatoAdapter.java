package br.edu.ifsp.agendafirebase.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.ifsp.agendafirebase.R;
import br.edu.ifsp.agendafirebase.model.Contato;

public class ContatoAdapter {



    private static ItemClickListener clickListener;




    public void setClickListener(ItemClickListener itemClickListener)
    {
        clickListener = itemClickListener;

    }






    public class ContatoViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        final TextView nome;

        public ContatoViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.nome);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
              if (clickListener!=null)
                  clickListener.onItemClick(getAdapterPosition());
        }
    }


    public  interface ItemClickListener
    {
        void onItemClick(int position);
    }


}
