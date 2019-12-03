package br.com.contacorrente.menu.fragment.myAccount;

import androidx.fragment.app.Fragment;

public interface ParentActivityContract {

    void changeFragment(Fragment fragment);
    void logout();

}
