package com.anthony.deltec.gestor.ui.multas;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anthony.deltec.gestor.dao.Instance;
import com.anthony.deltec.gestor.dao.pojos.MultasPojo;
import com.anthony.deltec.gestor.dao.pojos.PersonasPojo;
import com.anthony.deltec.gestor.dao.pojos.ResponseMulta;
import com.anthony.deltec.gestor.dao.pojos.ResponsePersona;
import com.anthony.deltec.gestor.dao.pojos.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultasViewModel extends ViewModel {
    private Instance retrofit = new Instance();
    private MutableLiveData<PersonasPojo.ResponsePojo> personas;
    private MutableLiveData<MultasPojo> multas;

    public MutableLiveData<PersonasPojo.ResponsePojo> getAllPersonas(){

        if (personas == null) {
            personas = new MutableLiveData<PersonasPojo.ResponsePojo>();
            QueryData();
        }
        return personas;
    }

    public MutableLiveData<MultasPojo> getMultas() {
        return multas;
    }

    public void setResultDelete(int id_multa) {
        Instance.MultasApi api;
        api = Instance.getInstance();
        Call<Result> call = api.DeleteMulta(id_multa);
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

    public void SearchMulta(int id){
//        if (multas == null) {
            multas = new MutableLiveData<MultasPojo>();
            SeachData(id);
//        }
    }

    public void setResultUpdate(ResponseMulta pojo) {
        Instance.MultasApi api;
        api = Instance.getInstance();
        Call<Result> call = api.updateMulta(pojo);
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
    private void SeachData(int id) {
        Instance.MultasApi api;

        api = Instance.getInstance();
        Call<MultasPojo.ListaMultasPojo> call = api.searchMulta(id);
        call.enqueue(new Callback<MultasPojo.ListaMultasPojo>() {
            @Override
            public void onResponse(Call<MultasPojo.ListaMultasPojo> call, Response<MultasPojo.ListaMultasPojo> response) {
               if (response.isSuccessful()){
                   multas.setValue(response.body().getResponse().get(0));
               }

                Log.i("busqueda",response.body().getResponse().get(0).getResponsable());
            }

            @Override
            public void onFailure(Call<MultasPojo.ListaMultasPojo> call, Throwable t) {
                Log.i("busqueda",t.getMessage());
            }
        });
    }
    public void setResultInsert(ResponseMulta pojo) {
        Instance.MultasApi api;
        api = Instance.getInstance();
        Call<Result> call = api.insertMulta(pojo);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.i("ok",response.body().getResponse());
                Log.i("ok",response.message());
                Log.i("ok",response.body().getResponse());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.i("ok",t.getMessage());
            }
        });


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