package com.example.ecoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;

import com.example.ecoapp.R;
import com.example.ecoapp.dao.AppDatabase;
import com.example.ecoapp.dao.ProductDAO;
import com.example.ecoapp.entity.Product;
import com.example.ecoapp.ui.edit.EditFragment;
import com.example.ecoapp.ui.list.ListFragment;

import java.util.List;

public class LineAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Product> products;
    private Context context;
    private ListFragment listFragment;
    private FragmentManager fragmentManager;

    public LineAdapter(Context context, List<Product> products, ListFragment listFragment, FragmentManager fragmentManager) {
        this.context = context;
        this.products = products;
        this.listFragment = listFragment;
        this.fragmentManager = fragmentManager;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fragment_line, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.productCode = convertView.findViewById(R.id.productCode);
            viewHolder.productName = convertView.findViewById(R.id.productName);
            viewHolder.productDescription = convertView.findViewById(R.id.productDescription);
            viewHolder.productCost = convertView.findViewById(R.id.productCost);
            viewHolder.productSupplier = convertView.findViewById(R.id.productSupplier);
            viewHolder.btnDelete = convertView.findViewById(R.id.btnDelete);
            viewHolder.btnEdit = convertView.findViewById(R.id.btnEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Product product = products.get(position);

        viewHolder.productCode.setText(String.valueOf(product.getId()));
        viewHolder.productName.setText(product.getName());
        viewHolder.productDescription.setText(product.getDescription());
        viewHolder.productCost.setText(product.getCost());
        viewHolder.productSupplier.setText(product.getSupplier());

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDAO productDAO = AppDatabase.getInstance(context.getApplicationContext()).createProductDAO();
                productDAO.delete(product);
                refreshList(position);
                Toast.makeText(context, "Produto excluído com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listFragment.openEditFragment(product, fragmentManager);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        AppCompatTextView productCode;
        AppCompatTextView productName;
        AppCompatTextView productDescription;
        AppCompatTextView productCost;
        AppCompatTextView productSupplier;
        AppCompatButton btnDelete;
        AppCompatButton btnEdit;
    }

    public void refreshList(int position) {
        products.remove(position);
        notifyDataSetChanged();
    }
}
