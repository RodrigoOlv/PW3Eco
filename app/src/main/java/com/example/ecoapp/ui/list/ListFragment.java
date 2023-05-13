package com.example.ecoapp.ui.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ecoapp.R;
import com.example.ecoapp.adapter.LineAdapter;
import com.example.ecoapp.dao.AppDatabase;
import com.example.ecoapp.dao.ProductDAO;
import com.example.ecoapp.entity.Product;
import com.example.ecoapp.ui.edit.EditFragment;

import java.util.List;

public class ListFragment extends Fragment {

    private ListView listProducts;
    private FragmentManager fragmentManager;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = requireActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        listProducts = rootView.findViewById(R.id.listProducts);

        ProductDAO productDAO = AppDatabase.getInstance(getContext().getApplicationContext()).createProductDAO();

        getAll(productDAO.getAllProduct());

        return rootView;
    }

    protected void getAll(List<Product> products) {
        listProducts.setAdapter(new LineAdapter(requireContext(), products, this, fragmentManager));
    }

    public void openEditFragment(Product product, FragmentManager fragmentManager) {
        // Criar uma instância do EditFragment
        EditFragment editFragment = EditFragment.newInstance(product);

        // Substituir o fragmento atual pelo EditFragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, editFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
