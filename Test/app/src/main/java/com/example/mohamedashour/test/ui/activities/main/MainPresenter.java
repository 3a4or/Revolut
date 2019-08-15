package com.example.mohamedashour.test.ui.activities.main;

import com.example.mohamedashour.test.data.models.CurrenciesResponse;
import com.example.mohamedashour.test.data.models.CurrencyModel;
import com.example.mohamedashour.test.data.network.RetrofitSingleton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.MainPresenter{

    MainContract.MainView view;
    public CompositeDisposable compositeDisposable;
    HashMap<String, String> flagsMap;

    public MainPresenter(MainContract.MainView view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
        flagsMap = new HashMap<>();
        setFlags();
    }


    @Override
    public void getCurrencies(String base, double amount) {
        compositeDisposable.add(Observable.interval(0, 1, TimeUnit.SECONDS)
                .flatMap((Function<Long, ObservableSource<?>>) aLong -> RetrofitSingleton.webService()
                        .getCurrencies("latest?base=" + base + "&amount=" + amount)
                        .takeUntil(Observable.timer(1, TimeUnit.SECONDS))
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    CurrenciesResponse currenciesResponse = (CurrenciesResponse) result;
                    setCurrenciesData(currenciesResponse.getRates().toString(), base, amount);
                }, throwable -> {

                }));
    }

    @Override
    public void getCurrenciesWithoutRepeat(String base, double amount) {
        compositeDisposable.add(RetrofitSingleton.webService().getCurrencies("latest?base=" + base + "&amount=" + amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currenciesResponse -> setCurrenciesData(currenciesResponse.getRates().toString(), base, amount), throwable -> {
                }));
    }

    private void setCurrenciesData(String response, String base, double amount) {
        try {
            List<CurrencyModel> currenciesList = new ArrayList<>();
            JSONObject currenciesJSON = new JSONObject(response);
            Iterator<String> keysIterator = currenciesJSON.keys();
            while (keysIterator.hasNext()) {
                CurrencyModel currencyModel = new CurrencyModel();
                String keyStr = keysIterator.next();
                double valueStr = currenciesJSON.getDouble(keyStr);
                if (flagsMap.containsKey(keyStr)) {
                    currencyModel.setFlag(flagsMap.get(keyStr));
                    currencyModel.setKey(keyStr);
                    currencyModel.setVal(valueStr);
                    currenciesList.add(currencyModel);
                }
            }
            CurrencyModel firstCurrencyModel = new CurrencyModel();
            firstCurrencyModel.setKey(base);
            firstCurrencyModel.setVal(amount);
            if (flagsMap.containsKey(base)) {
                firstCurrencyModel.setFlag(flagsMap.get(base));
            }
            currenciesList.add(0, firstCurrencyModel);
            view.receiveCurrencies(currenciesList);
        } catch (JSONException e) {

        }

    }

    public void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void subscribeFromObservable() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    private void setFlags() {
        flagsMap.put("EUR", "https://cdn2.iconfinder.com/data/icons/popular-coins/154/euro-europe-flag-stars-round-512.png");
        flagsMap.put("BGN", "https://flagpedia.net/data/flags/small/bg.png");
        flagsMap.put("BRL", "https://flagpedia.net/data/flags/small/br.png");
        flagsMap.put("CAD", "https://flagpedia.net/data/flags/small/ca.png");
        flagsMap.put("CHF", "https://flagpedia.net/data/flags/small/ch.png");
        flagsMap.put("CNY", "https://flagpedia.net/data/flags/small/cn.png");
        flagsMap.put("CZK", "https://flagpedia.net/data/flags/small/cz.png");
        flagsMap.put("DKK", "https://flagpedia.net/data/flags/small/dk.png");
        flagsMap.put("GBP", "https://flagpedia.net/data/flags/small/gb.png");
        flagsMap.put("HKD", "https://flagpedia.net/data/flags/small/cn.png");
        flagsMap.put("HRK", "https://flagpedia.net/data/flags/small/hr.png");
        flagsMap.put("HUF", "https://flagpedia.net/data/flags/small/hu.png");
        flagsMap.put("IDR", "https://flagpedia.net/data/flags/small/id.png");
        flagsMap.put("ILS", "https://flagpedia.net/data/flags/small/il.png");
        flagsMap.put("INR", "https://flagpedia.net/data/flags/small/in.png");
        flagsMap.put("ISK", "https://flagpedia.net/data/flags/small/is.png");
        flagsMap.put("JPY", "https://flagpedia.net/data/flags/small/jp.png");
        flagsMap.put("KRW", "https://flagpedia.net/data/flags/small/kr.png");
        flagsMap.put("MXN", "https://flagpedia.net/data/flags/small/mx.png");
        flagsMap.put("MYR", "https://flagpedia.net/data/flags/small/my.png");
        flagsMap.put("NOK", "https://flagpedia.net/data/flags/small/no.png");
        flagsMap.put("NZD", "https://flagpedia.net/data/flags/small/nz.png");
        flagsMap.put("PHP", "https://flagpedia.net/data/flags/small/ph.png");
        flagsMap.put("PLN", "https://flagpedia.net/data/flags/small/pl.png");
        flagsMap.put("RON", "https://flagpedia.net/data/flags/small/ro.png");
        flagsMap.put("RUB", "https://flagpedia.net/data/flags/small/ru.png");
        flagsMap.put("SEK", "https://flagpedia.net/data/flags/small/se.png");
        flagsMap.put("SGD", "https://flagpedia.net/data/flags/small/sg.png");
        flagsMap.put("THB", "https://flagpedia.net/data/flags/small/th.png");
        flagsMap.put("TRY", "https://flagpedia.net/data/flags/small/tr.png");
        flagsMap.put("USD", "https://flagpedia.net/data/flags/small/us.png");
        flagsMap.put("ZAR", "https://flagpedia.net/data/flags/small/za.png");
        flagsMap.put("AUD", "https://flagpedia.net/data/flags/small/au.png");
    }
}
