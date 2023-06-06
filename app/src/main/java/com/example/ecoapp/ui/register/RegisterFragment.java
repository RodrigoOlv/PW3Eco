package com.example.ecoapp.ui.register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecoapp.R;
import com.example.ecoapp.dao.AppDatabase;
import com.example.ecoapp.dao.ProductDAO;
import com.example.ecoapp.entity.Product;
import com.google.android.material.snackbar.Snackbar;

public class RegisterFragment extends Fragment {

    private AppCompatEditText name;
    private AppCompatEditText description;
    private AppCompatEditText cost;
    private AppCompatEditText supplier;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        context = getContext();

        name = view.findViewById(R.id.edtName);
        description = view.findViewById(R.id.edtDescription);
        cost = view.findViewById(R.id.edtCost);
        supplier = view.findViewById(R.id.edtSupplier);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldleak")
            @Override
            public void onClick(View v) {
                new AsyncTask<Void,Integer, Integer>() {
                    @Override
                    protected Integer doInBackground(Void... voids) {
                        ProductDAO productDAO = AppDatabase.getInstance(context).createProductDAO();
                        Product product = new Product();
                        product.setName(name.getText().toString());
                        product.setDescription(description.getText().toString());
                        product.setCost(cost.getText().toString());
                        product.setSupplier(supplier.getText().toString());

                        productDAO.insert(product);
                        return product.getId();
                    }

                    @Override
                    protected void onPostExecute(Integer id) {
                        if(id==null)
                            Snackbar.make(view, "Erro ao inserir registro", Snackbar.LENGTH_LONG).show();
                        else {
                            Snackbar.make(view, "Nome informado = "+ name.getText().toString(), Snackbar.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_nav_registerFragment_to_nav_home);        }
                    }
                }.execute();
            }
        });

        return view;
    }
}
