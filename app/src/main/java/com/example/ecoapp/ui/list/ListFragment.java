package com.example.ecoapp.ui.list;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ecoapp.R;
import com.example.ecoapp.adapter.LineAdapter;
import com.example.ecoapp.adapter.MyAdapter;
import com.example.ecoapp.dao.AppDatabase;
import com.example.ecoapp.dao.ProductDAO;
import com.example.ecoapp.entity.Product;

import java.util.List;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        new AsyncTask<Void,Void, List<Product>>() {

            @Override
            protected List<Product> doInBackground(Void... voids) {
                ProductDAO productDAO = AppDatabase.getInstance(getContext()).createProductDAO();
                return productDAO.getAllProduct();
            }

            @Override
            protected void onPostExecute(List<Product> products) {
                super.onPostExecute(products);
                inicializaRecycler(products);
            }

        }.execute();

        return rootView;
    }

    private void inicializaRecycler(List<Product> pessoas){
        MyAdapter myAdapter = new MyAdapter(pessoas);//new MyAdapter(Pessoa.inicializaLista());
        recyclerView.setAdapter(myAdapter);
        //linha de código usada para otimizar o recycler
        recyclerView.setHasFixedSize(true);

        //configurar o gerenciador de layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        //definindo o layout do recycler
        recyclerView.setLayoutManager(layoutManager);
    }
}
