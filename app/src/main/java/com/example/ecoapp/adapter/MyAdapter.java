package com.example.ecoapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.R;
import com.example.ecoapp.dao.AppDatabase;
import com.example.ecoapp.dao.ProductDAO;
import com.example.ecoapp.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Product> listProduct = new ArrayList<>();
    Context context;
    public MyAdapter(List<Product> products) {
        this.listProduct = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemList = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_card_icons, viewGroup, false);

        this.context = viewGroup.getContext();
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") int position) {
        //exibe os itens no Recycler
        Product p = listProduct.get(position);
        myViewHolder.name.setText(p.getName());
        myViewHolder.description.setText(p.getDescription());
        myViewHolder.cost.setText(p.getCost());
        myViewHolder.supplier.setText(p.getSupplier());
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerItem(position);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putInt("ID", listProduct.get(position).getId());
        bundle.putString("NAME", listProduct.get(position).getName());
        bundle.putString("DESCRIPTION", listProduct.get(position).getDescription());
        bundle.putString("COST", listProduct.get(position).getCost());
        bundle.putString("SUPPLIER", listProduct.get(position).getSupplier());
        myViewHolder.btnEdit.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_edit, bundle));
    }

    @Override
    public int getItemCount() {
        //retorna a quantidade de itens que será exibida
        return listProduct.size();
    }

    public void removerItem(final int position) {
        new AlertDialog.Builder(context)
                .setTitle("Deletando produto")
                .setMessage("Tem certeza que deseja deletar esse produto?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                ProductDAO productDAO = AppDatabase.getInstance(context).createProductDAO();
                                List<Product> products = productDAO.getAllProduct();
                                productDAO.delete(products.get(position));
                                return null;
                            }
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                listProduct.remove(position);
                                notifyItemRemoved(position);
                            }
                        }.execute();
                    }}).setNegativeButton("Não", null).show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView description;
        TextView cost;
        TextView supplier;
        ImageButton btnDelete;
        ImageButton btnEdit;

        public MyViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.textViewName);
            description = itemView.findViewById(R.id.textViewDescription);
            cost = itemView.findViewById(R.id.textViewCost);
            supplier = itemView.findViewById(R.id.textViewSupplier);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit= itemView.findViewById(R.id.btnEdit);
        }
    }
}
