package com.anthony.deltec.gestor.ui.personas;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anthony.deltec.gestor.dao.Instance;
import com.anthony.deltec.gestor.dao.pojos.MultasPersonaPojo;
import com.anthony.deltec.gestor.dao.pojos.PersonasPojo;
import com.anthony.deltec.gestor.dao.pojos.ResponsePersona;

import com.anthony.deltec.gestor.dao.pojos.Result;
import com.anthony.deltec.gestor.ui.home.HomeViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class PersonaViewModel extends ViewModel {
    private Instance retrofit = new Instance();

    private MutableLiveData<PersonasPojo> personas;



    public void setResultDelete(String email) {
        Instance.MultasApi api;
        api = Instance.getInstance();
        Call<Result> call = api.DeletePerson(email);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.i("ok",response.body().getResponse());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.i("ok",t.getMessage());
            }
        });

    }

    /**
     * Actualizar usuarios registrados
     * con el correo
     * @param pojo
     */
    public void setResultUpdate(ResponsePersona pojo) {
        Instance.MultasApi api;
        api = Instance.getInstance();
        Call<Result> call = api.updatePerson(pojo);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.i("ok",response.body().getResponse());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.i("ok",t.getMessage());
            }
        });

    }

    /**
     * Creacion de un registro
     * @param pojo
     */
    public void setResultInsert(ResponsePersona pojo) {
        Instance.MultasApi api;
        api = Instance.getInstance();
        Call<Result> call = api.insertPerson(pojo);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.i("ok",response.body().getResponse());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.i("ok",t.getMessage());
            }
        });

    }



    /**
     *
     * @return personas registradas
     */
    public MutableLiveData<PersonasPojo> getPersonas() {
        return personas;
    }

    /**
     * Buscar persona por parametro
     * @param
     * indentify persona
     *
     */
    public void searchPerson(String indentify){
        if (personas == null) {
            personas = new MutableLiveData<PersonasPojo>();
            QueryData(indentify);
        }
    }

    private void QueryData(String indentify) {
        Instance.MultasApi api;

        api = Instance.getInstance();
        Call<PersonasPojo.ResponsePojo> call = api.searchPerson(indentify);
        call.enqueue(new Callback<PersonasPojo.ResponsePojo>() {
            @Override
            public void onResponse(Call<PersonasPojo.ResponsePojo> call, Response<PersonasPojo.ResponsePojo> response) {
                Log.i("person",response.body().getResponse().get(0).getNombre());
                personas.setValue(response.body().getResponse().get(0));
            }

            @Override
            public void onFailure(Call<PersonasPojo.ResponsePojo> call, Throwable t) {
                Log.i("Error"," message "+t.getMessage());
            }
        });

    }
    public class ThreadImplement extends Thread {
        private Call<PersonasPojo> call;

        public ThreadImplement(Call<PersonasPojo> call) {

            this.call = call;
        }

        @Override
        public void run() {
            super.run();


        }
    }

}