package com.butterflymx.butterflymxapiclient.utils.mvp;

public interface MvpView {
    void showLoading();

    void hideLoading();

    void showMessage(String message);

    void onCompleteMainAction();

}
