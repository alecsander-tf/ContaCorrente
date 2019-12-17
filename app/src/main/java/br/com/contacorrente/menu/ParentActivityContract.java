package br.com.contacorrente.menu;

import androidx.fragment.app.Fragment;

public interface ParentActivityContract {

    void changeActivity(Class<?> args);
    void logout();

}
