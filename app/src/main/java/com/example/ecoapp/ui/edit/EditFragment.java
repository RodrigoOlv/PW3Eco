package com.example.ecoapp.ui.edit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecoapp.R;
import com.example.ecoapp.dao.AppDatabase;
import com.example.ecoapp.dao.ProductDAO;
import com.example.ecoapp.entity.Product;
import com.example.ecoapp.ui.list.ListFragment;

public class EditFragment extends Fragment {

    AppCompatEditText productName;
    AppCompatEditText productDescription;
    AppCompatEditText productCost;
    AppCompatEditText productSupplier;
    AppCompatButton btnEdit;
    Product product;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(Product product) {
        EditFragment fragment = new EditFragment();
        // Passar o objeto Product para o fragmento como argumento
        Bundle args = new Bundle();
        args.putSerializable("product", product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit, container, false);

        productName = rootView.findViewById(R.id.edtName);
        productDescription = rootView.findViewById(R.id.edtDescription);
        productCost = rootView.findViewById(R.id.edtCost);
        productSupplier = rootView.findViewById(R.id.edtSupplier);
        btnEdit = rootView.findViewById(R.id.btnEdit);

        product = (Product) getActivity().getIntent().getSerializableExtra("product");

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productCost.setText(product.getCost());
        productSupplier.setText(product.getSupplier());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProduct();
            }
        });

        return rootView;
    }

    private void changeProduct() {
        if (productName.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Nome do produto é obrigatório!", Toast.LENGTH_LONG).show();
            productName.requestFocus();
        } else if (productDescription.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Descrição do produto é obrigatória!", Toast.LENGTH_LONG).show();
            productDescription.requestFocus();
        } else if (productCost.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Valor do produto é obrigatório!", Toast.LENGTH_LONG).show();
            productCost.requestFocus();
        } else if (productSupplier.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Nome do fornecedor do produto é obrigatório!", Toast.LENGTH_LONG).show();
            productSupplier.requestFocus();
        } else {
            product.setName(productName.getText().toString().trim());
            product.setDescription(productDescription.getText().toString().trim());
            product.setCost(productCost.getText().toString().trim());
            product.setSupplier(productSupplier.getText().toString().trim());

            ProductDAO productDAO = AppDatabase.getInstance(getContext()).createProductDAO();
            productDAO.update(product);

            productName.getText().clear();
            productDescription.getText().clear();
            productCost.getText().clear();
            productSupplier.getText().clear();

            showMessage();
        }
    }

    public void showMessage() {
        String msg = "Produto alterado com sucesso! ";

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle(R.string.app_name);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getContext(), ListFragment.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        alertDialog.show();
    }
}
