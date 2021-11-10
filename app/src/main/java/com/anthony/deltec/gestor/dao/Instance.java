package com.anthony.deltec.gestor.dao;

import com.anthony.deltec.gestor.dao.pojos.MultasPersonaPojo;
import com.anthony.deltec.gestor.dao.pojos.PersonasPojo;

import com.anthony.deltec.gestor.dao.pojos.ResponsePersona;
import com.anthony.deltec.gestor.dao.pojos.Result;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * retrofit patron singleton
 *
 */
public class Instance {
    private static Retrofit retrofit = null;
    private static String BASE_URL = "https://kosmeticaavanzada.com/senasoft/";

    public static MultasApi getInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MultasApi.class);
    }

    public void destroyInstance(){
        retrofit = null;
    }

    /**
     * Clase interface encargado de peticiones
     * a la url
     */
    public interface MultasApi {
//GET ALL
        @GET("Querys.php?case=multas")
        Call<PersonasPojo.ResponsePojo> getMultas();

        @GET("Querys.php?case=vehiculos")
        Call<PersonasPojo.ResponsePojo> getVehiculos();

        @GET("Querys.php?case=personas")
        Call<PersonasPojo.ResponsePojo> getPersonas();

        @GET("Querys.php?case=multasPersonas")
        Call<MultasPersonaPojo.MultasPersonasPojoList> getMultasPersonas();

//CRUD PERSONA

        @GET("Search.php?case=persona")
        Call<PersonasPojo.ResponsePojo> searchPerson(@Query("identify") String identify);

        @POST("Insert.php?case=persona")
        Call<Result> insertPerson(@Body ResponsePersona pojo);

        @POST("Update.php?case=persona")
        Call<Result> updatePerson(@Body ResponsePersona pojo);

        @GET("Delete.php?case=persona")
        Call<Result> DeletePerson(@Query("email") String email);

//  CRUD MULTAS

    }
}
