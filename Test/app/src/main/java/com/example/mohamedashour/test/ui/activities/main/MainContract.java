package com.example.mohamedashour.test.ui.activities.main;

import com.example.mohamedashour.test.data.models.CurrencyModel;

import java.util.List;

public interface MainContract {

    interface MainView {
        void receiveCurrencies(List<CurrencyModel> list);
    }
    interface MainPresenter {
        void getCurrencies(String base, double amount);
        void getCurrenciesWithoutRepeat(String base, double amount);
    }
}
