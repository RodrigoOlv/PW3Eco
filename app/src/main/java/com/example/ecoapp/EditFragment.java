package com.example.ecoapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ecoapp.dao.AppDatabase;
import com.example.ecoapp.dao.ProductDAO;
import com.example.ecoapp.entity.Product;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class EditFragment extends Fragment {

    private TextInputEditText name;
    private TextInputEditText description;
    private TextInputEditText cost;
    private TextInputEditText supplier;
    private Button btnEditar;
    private int id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);
        Bundle bundle = getArguments();
        id = bundle.getInt("ID");
        name = root.findViewById(R.id.edtName);
        name.setText(bundle.getString("NAME"));
        description = root.findViewById(R.id.edtDescription);
        description.setText(bundle.getString("DESCRIPTION"));
        cost = root.findViewById(R.id.edtCost);
        cost.setText(bundle.getString("COST"));
        supplier = root.findViewById(R.id.edtSupplier);
        supplier.setText(bundle.getString("SUPPLIER"));
        btnEditar = root.findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });
        return root;
    }

    private void edit() {
        new AlertDialog.Builder(getContext())
                .setTitle("Editando produto")
                .setMessage("Tem certeza que deseja editar esse produto?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Product product = new Product();
                        product.setId(id);
                        product.setName(name.getText().toString().trim());
                        product.setDescription(description.getText().toString().trim());
                        product.setCost(cost.getText().toString().trim());
                        product.setSupplier(supplier.getText().toString().trim());
                        Log.d("OBJ", product.toString());
                        AsyncTask<Void, Void, Void> execute = new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                ProductDAO productDAO = AppDatabase.getInstance(getContext()).createProductDAO();
                                productDAO.update(product);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                Snackbar.make(getView(), "item editado!!!", Snackbar.LENGTH_LONG).show();
                                Navigation.findNavController(getView()).navigate(R.id.action_nav_ediFragment_to_nav_listFragment);
                            }
                        }.execute();

                    }
                }).setNegativeButton("Não", null).show();
    }
}