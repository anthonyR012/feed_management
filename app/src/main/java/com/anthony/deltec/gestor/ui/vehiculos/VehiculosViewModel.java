package com.anthony.deltec.gestor.ui.vehiculos;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anthony.deltec.gestor.dao.Instance;
import com.anthony.deltec.gestor.dao.pojos.MultasPojo;
import com.anthony.deltec.gestor.dao.pojos.PersonasPojo;
import com.anthony.deltec.gestor.dao.pojos.ResponseMulta;
import com.anthony.deltec.gestor.dao.pojos.ResponseVehiculo;
import com.anthony.deltec.gestor.dao.pojos.Result;
import com.anthony.deltec.gestor.dao.pojos.VehiculoPojo;
import com.anthony.deltec.gestor.ui.multas.MultasViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiculosViewModel extends ViewModel {


    private MutableLiveData<PersonasPojo.ResponsePojo> personas;
    private MutableLiveData<VehiculoPojo> vehiculo;

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
        VehiculosViewModel.ThreadImplement hilo = new VehiculosViewModel.ThreadImplement(call);
        hilo.start();
    }

    public void setResultDelete(String matricula) {
        Instance.MultasApi api;
        api = Instance.getInstance();
        Call<Result> call = api.DeleteVehiculo(matricula);
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

    public void setResultUpdate(ResponseVehiculo pojo) {
        Instance.MultasApi api;
        api = Instance.getInstance();
        Call<Result> call = api.updateVehiculo(pojo);


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

    public void SearchVehiculo(String matricula){
//        if (vehiculo == null) {
            vehiculo = new MutableLiveData<VehiculoPojo>();
            SeachData(matricula);
//        }
    }

    public MutableLiveData<VehiculoPojo> getVehiculo() {
        return vehiculo;
    }

    private void SeachData(String id) {
        Instance.MultasApi api;

        api = Instance.getInstance();
        Call<VehiculoPojo.ListVehiculoPojo> call = api.searchVehiculo(id);

        call.enqueue(new Callback<VehiculoPojo.ListVehiculoPojo>() {
            @Override
            public void onResponse(Call<VehiculoPojo.ListVehiculoPojo> call, Response<VehiculoPojo.ListVehiculoPojo> response) {
                vehiculo.setValue(response.body().getResponse().get(0));
                Log.i("busqueda",response.body().getResponse().get(0).getResponsable());
            }

            @Override
            public void onFailure(Call<VehiculoPojo.ListVehiculoPojo> call, Throwable t) {
                Log.i("busqueda",t.getMessage());
            }
        });



    }

    public void setResultInsert(ResponseVehiculo pojo) {
        Instance.MultasApi api;
        api = Instance.getInstance();
        Call<Result> call = api.insertVehiculo(pojo);

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
