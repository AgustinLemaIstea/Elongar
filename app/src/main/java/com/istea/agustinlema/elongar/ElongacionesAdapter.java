package com.istea.agustinlema.elongar;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by KyA on 29/1/2018.
 */

public class ElongacionesAdapter extends ArrayAdapter<EventoElongacion> {

    public ElongacionesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<EventoElongacion> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    //Se ejecuta por cada elemento de la lista
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        EventoElongacion evtElongacion = getItem(position);

        //Genero una vista en base a mi xml
        View v = LayoutInflater.from(getContext()).inflate(R.layout.evento_elongacion,null);

        //Accedo al textview de la vista que acabo de generar
        TextView tvFecha = v.findViewById(R.id.tvFecha);
        TextView tvHora = v.findViewById(R.id.tvHora);
        tvFecha.setText(evtElongacion.getFecha());
        tvHora.setText(evtElongacion.getHora());

        return v;
    }
}
