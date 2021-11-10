package com.anthony.deltec.gestor.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.anthony.deltec.gestor.dao.pojos.MultasPersonaPojo;
import com.anthony.deltec.gestor.dao.pojos.PersonasPojo;
import com.anthony.deltec.gestor.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getMultasDePersonas();
        return root;
    }

    /**
     * llama view model
     * trae multas y
     * Ordenar datos para el adaptador
     */
    private void getMultasDePersonas() {
        binding.myRecycle.setLayoutManager(new GridLayoutManager(getContext(), 1));
        homeViewModel.getMultasPersonas().observe(getViewLifecycleOwner(), new Observer<MultasPersonaPojo.MultasPersonasPojoList>() {
            @Override
            public void onChanged(MultasPersonaPojo.MultasPersonasPojoList multasPersonasPojoList) {
                //CREAMOS LISTA CON LOS DATOS NECESARIOS DE LA VISTA HOLDER
                List<OrderDataImplement> multas = new ArrayList<>();
                //ORGANIZAMOS ESAS DATOS POR MEDIO DE UN OBJETO DE TIPO ORDERDATAIMPLEMENT
                for (int i = 0;i< multasPersonasPojoList.getResponse().size();i++){
                    for (int e =0;e<multasPersonasPojoList.getResponse().get(i).getMultas().size();e++){
                        OrderDataImplement implement = new OrderDataImplement
                                (multasPersonasPojoList.getResponse().get(i).getNombre(),
                                        multasPersonasPojoList.getResponse().get(i).getImg(),
                                        multasPersonasPojoList.getResponse().get(i).getMultas().get(e).getTotal(),
                                        multasPersonasPojoList.getResponse().get(i).getMultas().get(e).getTipo(),
                                        multasPersonasPojoList.getResponse().get(i).getMultas().get(e).getEstado(),
                                        multasPersonasPojoList.getResponse().get(i).getMultas().get(e).getDescripcion());

                        multas.add(implement);

                    }


                }
                Log.i("multa",multas.get(0).getNamePerson());
//                PASAMOS LISTA POR PARAMETRO AL ADAPTADOR
                AdapterHome adapter = new AdapterHome(multas,getContext());
                binding.myRecycle.setAdapter(adapter);
//                Log.i("Response",multasPersonasPojoList.getResponse().get(0).getNombre());
            }
        });

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;
    }
}