package com.example.mohamedashour.test.data.network;


import com.example.mohamedashour.test.data.models.CurrenciesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {

    @GET
    Observable<CurrenciesResponse> getCurrencies(@Url String url);
}
