package com.anthony.deltec.gestor.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anthony.deltec.gestor.dao.Instance;
import com.anthony.deltec.gestor.dao.pojos.MultasPersonaPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private Instance retrofit;
    private Instance.MultasApi api;
    private MutableLiveData<MultasPersonaPojo.MultasPersonasPojoList> personasMultas;

    /**
     * Livedata de api multas
     * @return multas de usuarios
     */
    public LiveData<MultasPersonaPojo.MultasPersonasPojoList> getMultasPersonas() {

            personasMultas = new MutableLiveData<MultasPersonaPojo.MultasPersonasPojoList>();
            QueryData();

        return personasMultas;
    }

    /**
     * pasa llamada segundo hilo
     */
    private void QueryData() {

        retrofit = new Instance();
        api = Instance.getInstance();
        Call<MultasPersonaPojo.MultasPersonasPojoList> call = api.getMultasPersonas();

        ThreadImplement hilo = new ThreadImplement(call);
        hilo.start();
    }

    /**
     * Segundo hilo de ejecución
     */
    public class ThreadImplement extends Thread {
        private Call<MultasPersonaPojo.MultasPersonasPojoList> call;

        public ThreadImplement(Call<MultasPersonaPojo.MultasPersonasPojoList> call) {

            this.call = call;
        }

        @Override
        public void run() {
            super.run();

            call.enqueue(new Callback<MultasPersonaPojo.MultasPersonasPojoList>() {
                @Override
                public void onResponse(Call<MultasPersonaPojo.MultasPersonasPojoList> call, Response<MultasPersonaPojo.MultasPersonasPojoList> response) {
                    if (response.isSuccessful()){

                        personasMultas.setValue(response.body());

                    }else{
                        Log.i("Error","Response message "+response.message());
                    }

                }

                @Override
                public void onFailure(Call<MultasPersonaPojo.MultasPersonasPojoList> call, Throwable t) {
                    Log.i("Error"," message "+t.getMessage());
                }
            });
        }
    }

}