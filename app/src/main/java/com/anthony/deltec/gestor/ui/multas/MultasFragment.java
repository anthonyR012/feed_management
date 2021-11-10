package com.anthony.deltec.gestor.ui.multas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anthony.deltec.gestor.R;
import com.anthony.deltec.gestor.dao.pojos.PersonasPojo;
import com.anthony.deltec.gestor.dao.pojos.ResponsePersona;
import com.anthony.deltec.gestor.databinding.FragmentHomeBinding;
import com.anthony.deltec.gestor.databinding.FragmentMultasBinding;

import java.util.ArrayList;

public class MultasFragment extends Fragment {

    private MultasViewModel multasViewModel;
    private FragmentMultasBinding binding;
    private PersonasPojo.ResponsePojo responsePersona;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        multasViewModel =
                new ViewModelProvider(this).get(MultasViewModel.class);

        binding = FragmentMultasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setComboBoxes();
        return root;
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
                            (responsePojo.getResponse().get(i).getId()
                                    +" "+responsePojo.getResponse().get(i).getNombre());

                }
                ArrayAdapter<CharSequence> adapterPersonas = new ArrayAdapter
                        (getActivity(), android.R.layout.simple_spinner_item,listaPersona);
                binding.idSpinnerPersonas.setAdapter(adapterPersonas);
            }
        });


        ArrayAdapter<CharSequence> adapterCurso = ArrayAdapter.createFromResource
                (getActivity(), R.array.combo_curso, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource
                (getActivity(), R.array.combo_estado, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource
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