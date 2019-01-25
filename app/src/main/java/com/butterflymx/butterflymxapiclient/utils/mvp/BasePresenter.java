package com.butterflymx.butterflymxapiclient.utils.mvp;

public abstract class BasePresenter<T extends BaseView> implements MvpPresenter<T> {

    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    public T getView() {
        return view;
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void viewIsReady() {
    }


}