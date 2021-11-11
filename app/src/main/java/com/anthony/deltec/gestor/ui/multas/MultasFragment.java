package com.anthony.deltec.gestor.ui.multas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anthony.deltec.gestor.R;
import com.anthony.deltec.gestor.dao.pojos.MultasPojo;
import com.anthony.deltec.gestor.dao.pojos.PersonasPojo;
import com.anthony.deltec.gestor.dao.pojos.ResponseMulta;
import com.anthony.deltec.gestor.dao.pojos.ResponsePersona;
import com.anthony.deltec.gestor.databinding.FragmentHomeBinding;
import com.anthony.deltec.gestor.databinding.FragmentMultasBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

public class MultasFragment extends Fragment {

    private MultasViewModel multasViewModel;
    private FragmentMultasBinding binding;
    private PersonasPojo.ResponsePojo responsePersona;
    private  ArrayAdapter<CharSequence> adapterCurso,adapterEstado,adapterTipo;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        multasViewModel =
                new ViewModelProvider(this).get(MultasViewModel.class);

        binding = FragmentMultasBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        evetsClick();
        setComboBoxes();
        return root;
    }




    private void evetsClick() {
        binding.idMulta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {

                    if(event.getRawX() >= (binding.idMulta.getRight() - binding.idMulta.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        getMulta();
                        return true;
                    }
                }
                return false;
            }
        });



        binding.btnRegistrarMulta.setOnClickListener(view -> {createMulta();});
        binding.imgBorrarMulta.setOnClickListener(view -> {deleteMulta();});
        binding.imgEditarMulta.setOnClickListener(view -> {updateMulta();});
        binding.imgLimpiarMulta.setOnClickListener(view -> {clearAll();});
    }

    /**
     * CRUD METODOS
     * ACTUALIZA MULTA
     */
    private void updateMulta() {

        boolean verified = !binding.idMulta.getText().toString().isEmpty() &&
                !binding.descripcionMulta.getText().toString().isEmpty() &&
                !binding.descuentoMulta.getText().toString().isEmpty() &&
                !binding.totalMulta.getText().toString().isEmpty() &&
        !binding.idSpinnerPersonas.getSelectedItem().toString().equals("Seleccione");

        if (verified){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Esta seguro de editar el registro?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ResponseMulta multa = new ResponseMulta(
                                    binding.idSpinnerEstado.getSelectedItem().toString(),
                                    Integer.parseInt(binding.idSpinnerTipos.getSelectedItem().toString()),
                                    binding.idSpinnerCursos.getSelectedItem().toString(),
                                    binding.descripcionMulta.getText().toString(),
                                    Integer.parseInt(binding.descuentoMulta.getText().toString()),
                                    Integer.parseInt(binding.totalMulta.getText().toString()),0);
                        multa.setId_multa(Integer.parseInt(binding.idMulta.getText().toString()));
                        Log.i("id", String.valueOf(binding.idMulta.getText().toString()));
                        multasViewModel.setResultUpdate(multa);

                            Snackbar.make(root, R.string.title_update_complete,BaseTransientBottomBar.LENGTH_LONG).show();
                            clearAll();
                        }
                    })
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    }).show();
            // Create the AlertDialog object and return it




        }else{
            Snackbar.make(root, R.string.title_complete_input,BaseTransientBottomBar.LENGTH_LONG).show();
        }

    }
    /**
     * CRUD METODOS
     * BORRA MULTA POR MATRICULA
     */
    private void deleteMulta() {
        boolean verified = !binding.idMulta.getText().toString().isEmpty() &&
                !binding.descuentoMulta.getText().toString().isEmpty() &&
                !binding.descripcionMulta.getText().toString().isEmpty() &&
                !binding.totalMulta.getText().toString().isEmpty();
        if (verified){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Esta seguro de eliminar el registro?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            multasViewModel.setResultDelete(Integer.parseInt(binding.idMulta.getText().toString()));

                            Snackbar.make(root, R.string.title_delete_complete,BaseTransientBottomBar.LENGTH_LONG).show();
                            clearAll();
                        }
                    })
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    }).show();
            // Create the AlertDialog object and return it




        }else{
            Snackbar.make(root, R.string.title_complete_input,BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }
    /**
     * CRUD METODOS
     * CREA MULTA
     */
    private void createMulta() {
        boolean verified =
                !binding.descripcionMulta.getText().toString().isEmpty() &&
                !binding.descuentoMulta.getText().toString().isEmpty() &&
                !binding.totalMulta.getText().toString().isEmpty() &&
                !binding.idSpinnerPersonas.getSelectedItem().toString().equals("Seleccione");


        if (verified) {

            if (!binding.idSpinnerEstado.getSelectedItem().toString().equals("Estado") &&
                    !binding.idSpinnerCursos.getSelectedItem().toString().equals("Curso") &&
                    !binding.idSpinnerCursos.getSelectedItem().toString().equals("Tipo Multas")) {

                int total = !binding.descuentoMulta.getText().equals("0")?
                        calcDescoint(binding.descuentoMulta.getText().toString(),binding.totalMulta.getText().toString()):
                        Integer.valueOf(binding.descuentoMulta.getText().toString());

                ResponseMulta multa = new ResponseMulta(
                        binding.idSpinnerEstado.getSelectedItem().toString(),
                        Integer.parseInt(binding.idSpinnerTipos.getSelectedItem().toString()),
                        binding.idSpinnerCursos.getSelectedItem().toString(),
                        binding.descripcionMulta.getText().toString(),
                        Integer.parseInt(binding.descuentoMulta.getText().toString()),
                        total, 0);

                for (int i = 0; i < responsePersona.getResponse().size(); i++) {
                    if (responsePersona.getResponse().get(i).getNombre().equals(binding.idSpinnerPersonas.getSelectedItem().toString())) {
                        Log.i("reponse", responsePersona.getResponse().get(i).getId());
                        multa.setIdPersona(Integer.parseInt(responsePersona.getResponse().get(i).getId()));
                    }
                }

                multasViewModel.setResultInsert(multa);

                Snackbar.make(root, R.string.title_insert_complete, BaseTransientBottomBar.LENGTH_LONG).show();
                clearAll();


            } else {
                Snackbar.make(root, R.string.title_combo_fail, BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }else{
            Snackbar.make(root, R.string.title_complete_input, BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }

    /**
     * Aplicar decuento
     * @param descuento
     * @param valor
     * @return
     */
    private int calcDescoint(String descuento, String valor) {

        return (int) (Double.parseDouble(valor) - ((Double.parseDouble(descuento) / 100) * Double.parseDouble(valor) ));

    }


    private void clearAll() {
        binding.descuentoMulta.setText("");
        binding.descripcionMulta.setText("");
        binding.totalMulta.setText("");
        binding.idMulta.setText("");
        setComboBoxes();
    }

    private void getMulta() {
        multasViewModel.SearchMulta(Integer.parseInt(binding.idMulta.getText().toString()));
       multasViewModel.getMultas().observe(getViewLifecycleOwner(), new Observer<MultasPojo>() {
           @Override
           public void onChanged(MultasPojo multasPojo) {
               ArrayAdapter<CharSequence> adapterpersona = new ArrayAdapter
                       (getActivity(), android.R.layout.simple_spinner_item, Collections.singletonList(multasPojo.getResponsable()));

               ArrayAdapter<CharSequence> adaptercurso = new ArrayAdapter
                       (getActivity(), android.R.layout.simple_spinner_item, Collections.singletonList(multasPojo.getCurso()));

               ArrayAdapter<CharSequence> adaptertipo = new ArrayAdapter
                       (getActivity(), android.R.layout.simple_spinner_item, Collections.singletonList(multasPojo.getTipo()));

               ArrayAdapter<CharSequence> adapterestado = new ArrayAdapter
                       (getActivity(), android.R.layout.simple_spinner_item, Collections.singletonList(multasPojo.getEstado()));




                binding.idSpinnerTipos.setAdapter(adaptertipo);

                binding.idSpinnerEstado.setAdapter(adapterestado);
                binding.idSpinnerCursos.setAdapter(adaptercurso);
               binding.idSpinnerPersonas.setAdapter(adapterpersona);
               binding.descripcionMulta.setText(multasPojo.getDescripcion());
               binding.totalMulta.setText(Math.round(Float.parseFloat(multasPojo.getTotal()))+"");
               binding.descuentoMulta.setText(Math.round(Float.parseFloat(multasPojo.getDescuento()))+"");

           }
       });


    }

    private void setComboBoxes() {



        multasViewModel.getAllPersonas().observe(getViewLifecycleOwner(), new Observer<PersonasPojo.ResponsePojo>() {
            @Override
            public void onChanged(PersonasPojo.ResponsePojo responsePojo) {
                responsePersona = responsePojo;
                ArrayList<String> listaPersona = new ArrayList<String>();
                listaPersona.add("Seleccione");
                for (int i = 0; i < responsePojo.getResponse().size();i++) {
                    listaPersona.add
                            (responsePojo.getResponse().get(i).getNombre());

                }
                ArrayAdapter<CharSequence> adapterPersonas = new ArrayAdapter
                        (getActivity(), android.R.layout.simple_spinner_item,listaPersona);
                binding.idSpinnerPersonas.setAdapter(adapterPersonas);
            }
        });


         adapterCurso = ArrayAdapter.createFromResource
                (getActivity(), R.array.combo_curso, android.R.layout.simple_spinner_item);
       adapterEstado = ArrayAdapter.createFromResource
                (getActivity(), R.array.combo_estado, android.R.layout.simple_spinner_item);
        adapterTipo = ArrayAdapter.createFromResource
                (getActivity(), R.array.combo_tipo, android.R.layout.simple_spinner_item);


        binding.idSpinnerCursos.setAdapter(adapterCurso);
        binding.idSpinnerEstado.setAdapter(adapterEstado);
        binding.idSpinnerTipos.setAdapter(adapterTipo);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}