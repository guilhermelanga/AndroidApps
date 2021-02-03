package com.example.organizzebylanga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.organizzebylanga.R;
import com.example.organizzebylanga.model.Movimentos;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class AdapterMovimentacao extends RecyclerView.Adapter<AdapterMovimentacao.MyViewHolder> {

    List<Movimentos> movimentacoes;
    Context context;

    public AdapterMovimentacao(List<Movimentos> movimentacoes, Context context) {
        this.movimentacoes = movimentacoes;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movimentacao, parent, false);
        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movimentos movimentos = movimentacoes.get(position);

        holder.titulo.setText(movimentos.getDescricao());
        holder.valor.setText(String.valueOf(movimentos.getValor()));
        holder.categoria.setText(movimentos.getCategoria());
        holder.valor.setTextColor(context.getResources().getColor(R.color.colorPrimaryReceita));

        if (movimentos.getTipo() == "d" || movimentos.getTipo().equals("d")) {
            holder.valor.setTextColor(context.getResources().getColor(R.color.colorPrimaryDespesa));
            holder.valor.setText("-" + movimentos.getValor());
        }
    }


    @Override
    public int getItemCount() {
        return movimentacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, valor, categoria;

        public MyViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textAdapterTitulo);
            valor = itemView.findViewById(R.id.textAdapterValor);
            categoria = itemView.findViewById(R.id.textAdapterCategoria);
        }

    }

}
