package com.chefcode.android.patungan.ui.accounthistory;

import com.chefcode.android.patungan.services.response.HistoryResponse;

public interface AccountHistoryView {
    void showListHistory(HistoryResponse historyResponse);

    void HandleError();

    void loading(boolean loading);
}
