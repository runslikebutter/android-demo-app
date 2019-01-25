package com.butterflymx.butterflymxapiclient.utils.mvp;

public interface MvpPresenter<T extends BaseView> {

    void attachView(T mvpView);

    void viewIsReady();

    void detachView();

    void destroy();
}