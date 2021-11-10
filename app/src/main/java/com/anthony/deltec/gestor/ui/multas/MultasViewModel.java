package com.anthony.deltec.gestor.ui.multas;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anthony.deltec.gestor.dao.Instance;
import com.anthony.deltec.gestor.dao.pojos.PersonasPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultasViewModel extends ViewModel {
    private Instance retrofit = new Instance();
    private MutableLiveData<PersonasPojo.ResponsePojo> personas;


    public MutableLiveData<PersonasPojo.ResponsePojo> getAllPersonas(){

        if (personas == null) {
            personas = new MutableLiveData<PersonasPojo.ResponsePojo>();
            QueryData();
        }
        return personas;
    }

    private void QueryData() {
        Instance.MultasApi api;

        api = Instance.getInstance();
        Call<PersonasPojo.ResponsePojo> call = api.getPersonas();
        MultasViewModel.ThreadImplement hilo = new MultasViewModel.ThreadImplement(call);
        hilo.start();
    }

    public class ThreadImplement extends Thread {
        private Call<PersonasPojo.ResponsePojo> call;

        public ThreadImplement(Call<PersonasPojo.ResponsePojo> call) {

            this.call = call;
        }

        @Override
        public void run() {
            super.run();
            call.enqueue(new Callback<PersonasPojo.ResponsePojo>() {
                @Override
                public void onResponse(Call<PersonasPojo.ResponsePojo> call, Response<PersonasPojo.ResponsePojo> response) {
                    Log.i("response",response.body().getResponse().get(0).getNombre());
                    if (response.isSuccessful()){
                        personas.setValue(response.body());
                    }

                }

                @Override
                public void onFailure(Call<PersonasPojo.ResponsePojo> call, Throwable t) {
                    Log.i("response",t.getMessage());
                }
            });

        }
    }

}