package com.anthony.deltec.gestor.ui.personas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anthony.deltec.gestor.R;
import com.anthony.deltec.gestor.dao.pojos.PersonasPojo;

import com.anthony.deltec.gestor.dao.pojos.ResponsePersona;

import com.anthony.deltec.gestor.databinding.FragmentPersonaBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class PersonaFragment extends Fragment {

    private PersonaViewModel dashboardViewModel;
    private FragmentPersonaBinding binding;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(PersonaViewModel.class);

        binding = FragmentPersonaBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        getEvents();
        return root;
    }

    /**
     * Eventos click
     * del layout
     */
    private void getEvents() {
        binding.identify.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {

                    if(event.getRawX() >= (binding.identify.getRight() - binding.identify.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        getPerson();
                        return true;
                    }
                }
                return false;
            }
        });
        
        binding.btnRegistrar.setOnClickListener(view -> {createPerson();});
        binding.imgBorrar.setOnClickListener(view -> {deletePerson();});
        binding.imgEditar.setOnClickListener(view -> {updatePerson();});
    }

    /**
     * accede a model actualiza persona
     */
    private void updatePerson() {
        boolean verified = !binding.identify.getText().toString().isEmpty() &&
                !binding.email.getText().toString().isEmpty() &&
                !binding.phone.getText().toString().isEmpty() &&
                !binding.nameFull.getText().toString().isEmpty();
        if (verified){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Esta seguro de editar el registro?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ResponsePersona persona = new ResponsePersona(
                                    binding.nameFull.getText().toString(),
                                    binding.identify.getText().toString(),
                                    binding.email.getText().toString(),
                                    binding.phone.getText().toString(),
                                    "");

                            dashboardViewModel.setResultUpdate(persona);

                            Snackbar.make(root, R.string.title_update_complete,BaseTransientBottomBar.LENGTH_LONG).show();
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
     * accede a model elimina persona
     */
    private void deletePerson() {
        boolean verified = !binding.identify.getText().toString().isEmpty() &&
                !binding.email.getText().toString().isEmpty() &&
                !binding.phone.getText().toString().isEmpty() &&
                !binding.nameFull.getText().toString().isEmpty();
        if (verified){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Esta seguro de eliminar el registro?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            dashboardViewModel.setResultDelete(binding.email.getText().toString());

                            Snackbar.make(root, R.string.title_delete_complete,BaseTransientBottomBar.LENGTH_LONG).show();
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
     * accede a model crea persona
     */
    private void createPerson() {
        Snackbar snackbar;
        boolean verified = !binding.identify.getText().toString().isEmpty() &&
                !binding.email.getText().toString().isEmpty() &&
                !binding.phone.getText().toString().isEmpty() &&
                !binding.nameFull.getText().toString().isEmpty();
        if (verified){

            ResponsePersona persona = new ResponsePersona(
                    binding.nameFull.getText().toString(),
                    binding.identify.getText().toString(),
                    binding.email.getText().toString(),
                    binding.phone.getText().toString(),
                    "");

            dashboardViewModel.setResultInsert(persona);

            Snackbar.make(root, R.string.title_insert_complete,BaseTransientBottomBar.LENGTH_LONG).show();



        }else{
            Snackbar.make(root, R.string.title_complete_input,BaseTransientBottomBar.LENGTH_LONG).show();
        }

    }

    /**
     * accede a model busca persona
     */
    private void getPerson() {
        
        dashboardViewModel.searchPerson(binding.identify.getText().toString());
        dashboardViewModel.getPersonas().observe(getViewLifecycleOwner(), new Observer<PersonasPojo>() {
            @Override
            public void onChanged(PersonasPojo personasPojo) {
                binding.nameFull.setText(personasPojo.getNombre());
                binding.email.setText(personasPojo.getCorreo());
                binding.phone.setText(personasPojo.getTelefono());


            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}