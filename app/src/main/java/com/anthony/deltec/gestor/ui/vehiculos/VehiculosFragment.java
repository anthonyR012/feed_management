package com.anthony.deltec.gestor.ui.vehiculos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.anthony.deltec.gestor.R;
import com.anthony.deltec.gestor.dao.pojos.MultasPojo;
import com.anthony.deltec.gestor.dao.pojos.PersonasPojo;
import com.anthony.deltec.gestor.dao.pojos.ResponseMulta;
import com.anthony.deltec.gestor.dao.pojos.ResponseVehiculo;
import com.anthony.deltec.gestor.dao.pojos.VehiculoPojo;
import com.anthony.deltec.gestor.databinding.FragmentPersonaBinding;
import com.anthony.deltec.gestor.databinding.FragmentVehiculosBinding;
import com.anthony.deltec.gestor.ui.personas.PersonaViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

public class VehiculosFragment extends Fragment {


    private VehiculosViewModel vehiculosViewModel;
    private FragmentVehiculosBinding binding;
    View root;
    private PersonasPojo.ResponsePojo responsePersona;
    private ArrayAdapter<CharSequence> adapterAnio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vehiculosViewModel =
                new ViewModelProvider(this).get(VehiculosViewModel.class);

        binding = FragmentVehiculosBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        getEvents();
        setComboBoxes();
        return root;
    }

    private void getEvents() {
        binding.idMatricula.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {

                    if(event.getRawX() >= (binding.idMatricula.getRight() - binding.idMatricula.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        getVehiculo();
                        return true;
                    }
                }
                return false;
            }
        });

        binding.btnRegistrarVehiculo.setOnClickListener(view -> {createVehiculo();});
        binding.imgBorrarVehiculo.setOnClickListener(view -> {deleteVehiculo();});
        binding.imgEditarVehiculo.setOnClickListener(view -> {updateVehiculo();});
        binding.imgLimpiarVehiculo.setOnClickListener(view -> {clearAll();});
    }

    private void clearAll() {
        binding.idMatricula.setText("");
        binding.marcaVehi.setText("");
        binding.categoriaVehi.setText("");
        setComboBoxes();

    }

    private void updateVehiculo() {
        boolean verified = !binding.categoriaVehi.getText().toString().isEmpty() &&
                !binding.marcaVehi.getText().toString().isEmpty() &&
                !binding.idMatricula.getText().toString().isEmpty();

        if (verified){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Esta seguro de editar el registro?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ResponseVehiculo vehiculo = new ResponseVehiculo(
                                    binding.categoriaVehi.getText().toString(),
                                    binding.idMatricula.getText().toString(),
                                    binding.marcaVehi.getText().toString(),
                                    binding.idSpinnerAnio.getSelectedItem().toString(),0);


                            vehiculosViewModel.setResultUpdate(vehiculo);

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

    private void deleteVehiculo() {
        boolean verified = !binding.categoriaVehi.getText().toString().isEmpty() &&
                !binding.marcaVehi.getText().toString().isEmpty() &&
                !binding.idMatricula.getText().toString().isEmpty();

        if (verified){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Esta seguro de eliminar el registro?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            vehiculosViewModel.setResultDelete(binding.idMatricula.getText().toString());

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

    private void createVehiculo() {

        boolean verified =
                !binding.categoriaVehi.getText().toString().isEmpty() &&
                        !binding.marcaVehi.getText().toString().isEmpty() &&
                        !binding.idMatricula.getText().toString().isEmpty();


        if (verified){


            ResponseVehiculo vehiculo = new ResponseVehiculo(
                    binding.categoriaVehi.getText().toString(),
                    binding.idMatricula.getText().toString(),
                    binding.marcaVehi.getText().toString(),
                    binding.idSpinnerAnio.getSelectedItem().toString(),0);

            for (int i = 0;i < responsePersona.getResponse().size(); i++){
                if (responsePersona.getResponse().get(i).getNombre().equals(binding.idSpinnerPertenece.getSelectedItem().toString())){
                    Log.i("reponse",responsePersona.getResponse().get(i).getId());
                    vehiculo.setIdPersona(Integer.parseInt(responsePersona.getResponse().get(i).getId()));
                }
            }

            vehiculosViewModel.setResultInsert(vehiculo);

            Snackbar.make(root, R.string.title_insert_complete, BaseTransientBottomBar.LENGTH_LONG).show();
            clearAll();


        }else{
            Snackbar.make(root, R.string.title_complete_input,BaseTransientBottomBar.LENGTH_LONG).show();
        }

    }

    private void getVehiculo() {

        vehiculosViewModel.SearchVehiculo(binding.idMatricula.getText().toString());
        vehiculosViewModel.getVehiculo().observe(getViewLifecycleOwner(), new Observer<VehiculoPojo>() {
            @Override
            public void onChanged(VehiculoPojo vehiculoPojo) {
                ArrayAdapter<CharSequence> adapterpersona = new ArrayAdapter
                        (getActivity(), android.R.layout.simple_spinner_item, Collections.singletonList(vehiculoPojo.getResponsable()));

                ArrayAdapter<CharSequence> adapteranio = new ArrayAdapter
                        (getActivity(), android.R.layout.simple_spinner_item, Collections.singletonList(vehiculoPojo.getAnio()));

                binding.idSpinnerAnio.setAdapter(adapteranio);
                binding.idSpinnerPertenece.setAdapter(adapterpersona);
                binding.categoriaVehi.setText(vehiculoPojo.getCategoria());
                binding.marcaVehi.setText(vehiculoPojo.getMarca());

            }
        });

    }


    private void setComboBoxes() {



        vehiculosViewModel.getAllPersonas().observe(getViewLifecycleOwner(), new Observer<PersonasPojo.ResponsePojo>() {
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
                binding.idSpinnerPertenece.setAdapter(adapterPersonas);
            }
        });

        adapterAnio = ArrayAdapter.createFromResource
                (getActivity(), R.array.combo_anio, android.R.layout.simple_spinner_item);


        binding.idSpinnerAnio.setAdapter(adapterAnio);


    }

}