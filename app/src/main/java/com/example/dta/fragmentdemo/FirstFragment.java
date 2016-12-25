package com.example.dta.fragmentdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirstFragment extends Fragment {

    private OnFragmentInteractionListener mListener1;
    private ListView mainListView;
    private Planet[] planets;
    private ArrayAdapter<Planet> listAdapter;

    private Menu mMenu;
    private static boolean isSelection;
    private int cntSelected;

    public FirstFragment() {
    }

    public static FirstFragment newInstance() {
        Bundle args = new Bundle();
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);

        mainListView = (ListView) v.findViewById(R.id.mainListView);

        mainListView.setOnItemClickListener(new itemClickListener());
        mainListView.setOnItemLongClickListener(new itemLongClickListener());

        initialize();

        return v;
    }

    private void initialize() {
        planets = new Planet[]{
                new Planet("Mercury"), new Planet("Venus"), new Planet("Earth"),
                new Planet("Mars"), new Planet("Jupiter"), new Planet("Saturn"),
                new Planet("Uranus"), new Planet("Neptune"), new Planet("Ceres"),
                new Planet("Pluto"), new Planet("Haumea"), new Planet("Makemake"),
                new Planet("Eris")
        };
        ArrayList<Planet> planetList = new ArrayList<>();
        planetList.addAll(Arrays.asList(planets));

        listAdapter = new PlanetArrayAdapter(getActivity(), planetList);
        mainListView.setAdapter(listAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener1 = (OnFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener1 = null;
    }

    private static class PlanetViewHolder {
        private CheckBox checkBox;
        private TextView textView;

        public PlanetViewHolder() {
        }

        public PlanetViewHolder(TextView textView, CheckBox checkBox) {
            this.checkBox = checkBox;
            this.textView = textView;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

    private static class PlanetArrayAdapter extends ArrayAdapter<Planet> {

        private LayoutInflater inflater;

        public PlanetArrayAdapter(Context context, List<Planet> planetList) {
            super(context, R.layout.simplerow, R.id.rowTextView, planetList);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Planet to display
            Planet planet = (Planet) this.getItem(position);

            // The child views in each row.
            CheckBox checkBox;
            TextView textView;

            // Create a new row view
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.simplerow, null);

                // Find the child views.
                textView = (TextView) convertView.findViewById(R.id.rowTextView);
                checkBox = (CheckBox) convertView.findViewById(R.id.cb_item);

                // Optimization: Tag the row with it's child views, so we don't have to
                // call findViewById() later when we reuse the row.
                convertView.setTag(new PlanetViewHolder(textView, checkBox));

                // If CheckBox is toggled, update the planet it is tagged with.
                checkBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Planet planet = (Planet) cb.getTag();
                        planet.setChecked(cb.isChecked());
                    }
                });
            }
            // Reuse existing row view
            else {
                // Because we use a ViewHolder, we avoid having to call findViewById().
                PlanetViewHolder viewHolder = (PlanetViewHolder) convertView.getTag();
                checkBox = viewHolder.getCheckBox();
                textView = viewHolder.getTextView();
            }

            // Tag the CheckBox with the Planet it is displaying, so that we can
            // access the planet in onClick() when the CheckBox is toggled.
            checkBox.setTag(planet);

            // Display planet data
            checkBox.setChecked(planet.isChecked());
            textView.setText(planet.getName());
            if (isSelection) {
                convertView.findViewById(R.id.cb_item).setVisibility(View.VISIBLE);
            } else {
                convertView.findViewById(R.id.cb_item).setVisibility(View.GONE);
            }
            return convertView;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenu = menu;
        defaultMenu(menu, true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                exitSelectionMode();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private class itemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
            boolean isChecked = false;
            Planet planet = listAdapter.getItem(position);
            planet.toggleChecked();
            isChecked = planet.isChecked();
            PlanetViewHolder viewHolder = (PlanetViewHolder) item.getTag();
            viewHolder.getCheckBox().setChecked(isChecked);
            if (isChecked) {
                cntSelected++;
            } else {
                cntSelected--;
            }
        }
    }

    private class itemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View item, int pos, long id) {
            if (!isSelection) {
                isSelection = !isSelection;
                setSelectionMenu(mMenu, isSelection);
                listAdapter.notifyDataSetChanged();
                mListener1.onFragmentInteractionHome(FirstFragment.this, isSelection, mMenu);
            }

            // for current itemt long click
            boolean isChecked = false;
            Planet planet = listAdapter.getItem(pos);
            planet.toggleChecked();
            isChecked = planet.isChecked();
            PlanetViewHolder viewHolder = (PlanetViewHolder) item.getTag();
            viewHolder.getCheckBox().setChecked(isChecked);

            if (isChecked) {
                cntSelected++;
            } else {
                cntSelected--;
            }

            return true;
        }
    }

    private void defaultMenu(Menu menu, boolean isNotSelection) {
        MenuHelper.setMenuItemVisibility(menu, R.id.action_search, isNotSelection);
        MenuHelper.setMenuItemVisibility(menu, R.id.action_a, isNotSelection);
        MenuHelper.setMenuItemVisibility(menu, R.id.action_b, isNotSelection);
    }

    private void setSelectionMenu(Menu menu, boolean isSelection) {
        defaultMenu(menu, !isSelection);
        MenuHelper.setMenuItemVisibility(menu, R.id.action_cancel, isSelection);
        MenuHelper.setMenuItemVisibility(menu, R.id.action_c, isSelection);
        MenuHelper.setMenuItemVisibility(menu, R.id.action_d, isSelection);
        MenuHelper.setMenuItemShowAsAction(menu, R.id.action_cancel, MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    void exitSelectionMode() {
        cntSelected = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            Planet mPlanet = listAdapter.getItem(i);
            mPlanet.setChecked(false);
        }
        setSelectionMenu(mMenu, isSelection);
        listAdapter.notifyDataSetChanged();
        mListener1.onFragmentInteractionHome(FirstFragment.this, isSelection, mMenu);
    }
}
