package br.com.contacorrente.menu;

import androidx.fragment.app.Fragment;

public interface ParentActivityContract {

    void changeFragment(Fragment fragment, String fragmentTitle, int menuDrawerItemIndex);
    void logout();

}
