package com.anthony.deltec.gestor.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anthony.deltec.gestor.R;
import com.anthony.deltec.gestor.dao.pojos.MultasPersonaPojo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolderDatos> implements View.OnClickListener {


    List<OrderDataImplement> multas;
    private View.OnClickListener listener;
    private Context context;


    /**
     * Constructor recibe datos Onchange de petición HTTP
     * @param multas json de la api rest
     * @param context
     */
    public AdapterHome(List<OrderDataImplement> multas,Context context) {

        this.multas=multas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multas_personas,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignardatos(multas.get(position),context,position);

    }

    /**
     * Numero de vistas creadas
     * @return tamaño lista
     */
    @Override
    public int getItemCount() {
        return multas.size();
    }

    public void setOnclikListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView tipoMulta,descripcion,valor,estado,nombre;


        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tipoMulta =itemView.findViewById(R.id.tipomulta);
            valor=itemView.findViewById(R.id.valor);
            descripcion=itemView.findViewById(R.id.descripcion);
            imagen=itemView.findViewById(R.id.imgPersona);
            estado = itemView.findViewById(R.id.stateMulta);
            nombre = itemView.findViewById(R.id.nombrePersona);
        }

        /**
         * Genera vista para los datos
         * y setea
         * @param modelo
         * @param context
         * @param position
         */
        public void asignardatos(final OrderDataImplement modelo, Context context, int position) {


            tipoMulta.setText("Tipo: "+modelo.getTipo());
            valor.setText("Total: "+modelo.getTotal());
            descripcion.setText(modelo.getDescription());
            estado.setText(modelo.getState());
            nombre.setText(modelo.getNamePerson());


                Picasso.with(context)
                        .load(modelo.getImg())
                        .resize(150,300)
                        .centerCrop()
                        .into(imagen);


//
//            Log.i("entr","");
        }
    }
}
