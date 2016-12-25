package com.example.dta.fragmentdemo;

import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by DTA on 12/18/2016.
 */

public class MenuHelper {
    public static void setMenuItemVisibility(Menu menu, int id, boolean visibility) {
        if (menu == null) {
            return;
        }

        MenuItem item = menu.findItem(id);
        if (item != null) {
            item.setVisible(visibility);
        }
    }

    public static boolean isMenuItemVisible(Menu menu, int id) {
        if (menu == null) {
            return false;
        }

        MenuItem item = menu.findItem(id);
        if (item != null) {
            return item.isVisible();
        }
        return false;
    }

    public static void setMenuItemEnabled(Menu menu, int id, boolean enabled) {
        if (menu == null) {
            return;
        }

        MenuItem item = menu.findItem(id);
        if (item != null) {
            item.setEnabled(enabled);
        }
    }

    public static void setMenuItemEnable(Menu menu, int id, boolean isEnabled) {
        if (menu == null) {
            return;
        }

        MenuItem item = menu.findItem(id);
        if (item != null) {
            item.setEnabled(isEnabled);
            if (item.getIcon() != null) {
                item.getIcon().setAlpha(isEnabled ? 255 : 100); //100% : 40% (dim)
            }
        }
    }

    public static void setMenuItemIcon(Menu menu, int id, int resId) {
        if (menu == null) {
            return;
        }

        MenuItem item = menu.findItem(id);
        if (item != null) {
            item.setIcon(resId);
        }
    }

    public static void setMenuItemIcon(Menu menu, int id, Drawable icon) {
        if (menu == null) {
            return;
        }

        MenuItem item = menu.findItem(id);
        if (item != null) {
            item.setIcon(icon);
        }
    }

    public static void setMenuItemTitle(Menu menu, int id, String title) {
        if (menu == null) {
            return;
        }

        MenuItem item = menu.findItem(id);
        if (item != null) {
            item.setTitle(title);
        }
    }

    public static void setMenuItemShowAsAction(Menu menu, int id, int action) {
        if (menu == null) {
            return;
        }

        MenuItem item = menu.findItem(id);
        if (item != null) {
            item.setShowAsAction(action);
        }
    }

}
