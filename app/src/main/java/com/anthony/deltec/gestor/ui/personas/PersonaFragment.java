package com.anthony.deltec.gestor.ui.personas;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anthony.deltec.gestor.R;
import com.anthony.deltec.gestor.dao.pojos.PersonasPojo;

import com.anthony.deltec.gestor.dao.pojos.ResponsePersona;

import com.anthony.deltec.gestor.databinding.FragmentPersonaBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PersonaFragment extends Fragment {
    private static final int PICK_FROM_GALLERY = 1;
    private PersonaViewModel dashboardViewModel;
    private FragmentPersonaBinding binding;
    View root;
    private Bitmap photoBitmap;
    private ActivityResultLauncher<Intent> imagenUp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(PersonaViewModel.class);

        binding = FragmentPersonaBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        imagenUp = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri imageUri = data.getData();
                            final InputStream imageStream;
                            try {
                                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                                 photoBitmap = BitmapFactory.decodeStream(imageStream);

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            binding.imgPerfil.setImageURI(imageUri);

                        }
                    }
                });

        getEvents();
        return root;
    }

    /**
     * Solicita permiso de galeria, ejecuta
     * registerForActivityResult para salvar la img
     * @param imagenUp
     */
    private void loaderImg(ActivityResultLauncher<Intent> imagenUp) {
        //CARGA IMAGEN Y LA SETEA

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);

        } else {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            gallery.setType("image/*");
            imagenUp.launch(gallery);
        }



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
        binding.imgPerfil.setOnClickListener(view -> {loaderImg(imagenUp);});
        binding.btnRegistrar.setOnClickListener(view -> {createPerson();});
        binding.imgBorrar.setOnClickListener(view -> {deletePerson();});
        binding.imgEditar.setOnClickListener(view -> {updatePerson();});
        binding.imgLimpiar.setOnClickListener(view -> {clearAll();});
    }

    /**
     * Limpiar input de entrada
     */
    private void clearAll() {
        binding.nameFull.setText("");
        binding.email.setText("");
        binding.phone.setText("");
        binding.identify.setText("");
        binding.imgPerfil.setImageResource(R.drawable.perfil);
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
     * accede a model crea persona
     */
    private void createPerson() {
        Snackbar snackbar;
        boolean verified = !binding.identify.getText().toString().isEmpty() &&
                !binding.email.getText().toString().isEmpty() &&
                !binding.phone.getText().toString().isEmpty() &&
                !binding.nameFull.getText().toString().isEmpty();
        if (verified){
            if (photoBitmap!=null) {


                String img = convertorImage(photoBitmap);

                ResponsePersona persona = new ResponsePersona(
                        binding.nameFull.getText().toString(),
                        binding.identify.getText().toString(),
                        binding.email.getText().toString(),
                        binding.phone.getText().toString(),
                        img);

                dashboardViewModel.setResultInsert(persona);

                Snackbar.make(root, R.string.title_insert_complete, BaseTransientBottomBar.LENGTH_LONG).show();
                clearAll();
            }else{
                Snackbar.make(root, "Estas editando",BaseTransientBottomBar.LENGTH_LONG).show();

            }

        }else{
            Snackbar.make(root, R.string.title_complete_input,BaseTransientBottomBar.LENGTH_LONG).show();
        }

    }

    private String convertorImage(Bitmap photoBitmap) {

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        photoBitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return imagenString;

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

                Picasso.with(getContext())
                        .load(personasPojo.getImg())
                        .resize(150,300)
                        .centerCrop()
                        .into(binding.imgPerfil);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}