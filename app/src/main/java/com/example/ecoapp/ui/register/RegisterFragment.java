package com.example.ecoapp.ui.register;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecoapp.R;
import com.example.ecoapp.dao.AppDatabase;
import com.example.ecoapp.entity.Product;

public class RegisterFragment extends Fragment {

    private AppCompatEditText name;
    private AppCompatEditText description;
    private AppCompatEditText cost;
    private AppCompatEditText supplier;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        name = view.findViewById(R.id.edtName);
        description = view.findViewById(R.id.edtDescription);
        cost = view.findViewById(R.id.edtCost);
        supplier = view.findViewById(R.id.edtSupplier);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerProduct();
            }
        });

        return view;
    }

    private void registerProduct() {
        String productName = name.getText().toString();
        String productDescription = description.getText().toString();
        String productCost = cost.getText().toString();
        String productSupplier = supplier.getText().toString();

        // Validating input fields
        if (productName.isEmpty() || productDescription.isEmpty() || productCost.isEmpty() || productSupplier.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product();
        product.setName(productName);
        product.setDescription(productDescription);
        product.setCost(productCost);
        product.setSupplier(productSupplier);

        // Inserting the product into the database
        AppDatabase.getInstance(getContext().getApplicationContext()).createProductDAO().insert(product);

        Toast.makeText(getContext(), "Produto cadastrado!", Toast.LENGTH_SHORT).show();

        // Clearing input fields after successful registration
        name.getText().clear();
        description.getText().clear();
        cost.getText().clear();
        supplier.getText().clear();
    }
}
