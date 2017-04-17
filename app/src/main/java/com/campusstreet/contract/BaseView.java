
package com.campusstreet.contract;

public interface BaseView<T> {

    /**
     * 给View绑定Presenter
     * @param presenter
     */
    void setPresenter(T presenter);

}
