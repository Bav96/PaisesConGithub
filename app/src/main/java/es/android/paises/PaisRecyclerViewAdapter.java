package es.android.paises;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.android.paises.placeholder.PlaceholderContent.Pais;
import es.android.paises.databinding.FragmentPaisBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Pais}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PaisRecyclerViewAdapter extends RecyclerView.Adapter<PaisRecyclerViewAdapter.ViewHolder> {

    private final List<Pais> mValues;
    private final List<Pais> ListaFiltrada;


    public PaisRecyclerViewAdapter(List<Pais> items) {
        mValues = items;
        ListaFiltrada= new ArrayList<Pais>();
        ListaFiltrada.addAll(items);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentPaisBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = ListaFiltrada.get(position);
        holder.mContentView.setText(ListaFiltrada.get(position).nombre);
    }

    @Override
    public int getItemCount() {
        return ListaFiltrada.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mContentView;
        public Pais mItem;

        public ViewHolder(FragmentPaisBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            Bundle args = new Bundle();
            args.putParcelable("pais", mItem);
            args.putString("title", mItem.nombre);

            Navigation.findNavController(v)
                    .navigate(R.id.action_paisesFragment_to_detallePaisFragment, args);
        }
    }
    public void filtrado(final String Search){
        int longitud = Search.length();
        if(longitud==0){
            ListaFiltrada.clear();
            ListaFiltrada.addAll(mValues);

        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                List<Pais>collecion= ListaFiltrada.stream()
                        .filter(i->i.nombre.toLowerCase().contains(Search.toLowerCase()))
                        .collect(Collectors.toList());
                ListaFiltrada.clear();
                ListaFiltrada.addAll(collecion);
            }else{
                for (Pais P : ListaFiltrada){
                    if (P.nombre.toLowerCase().contains(Search.toLowerCase())){
                        ListaFiltrada.add(P);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}