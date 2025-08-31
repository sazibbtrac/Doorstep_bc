package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentMenuPac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.btracsolution.deliverysystem.Features.Agent.Fragments.Profile.ActivityProfileAgent;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.ActivityDaydetailsBinding;
import com.btracsolution.deliverysystem.databinding.ActivityMenuDesignBinding;

import java.util.ArrayList;

/*import butterknife.BindView;
import butterknife.ButterKnife;*/

/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class AgentMenu extends Fragment {

    private ActivityMenuDesignBinding binding;


    AgentMenuPresenter agentMenuPresenter;
  /*  @BindView(R.id.simpleListView)
    ListView simpleListView;*/
    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/*        v = inflater.inflate(R.layout.activity_menu_design, container, false);
        ButterKnife.bind(this, v);
        return v;*/

        binding = ActivityMenuDesignBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        setOnclickListener();
        return binding.getRoot();
    }

    private void setOnclickListener() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        agentMenuPresenter = new AgentMenuPresenter(getActivity(), AgentMenu.this);
        agentMenuPresenter.getServerData(true);
        setHasOptionsMenu(true);
    }

    public void setSimpleListView(ArrayList arrayList) {

        if (getActivity() != null && !getActivity().isFinishing()) {
            String[] from = {"name"};//string array
            int[] to = {R.id.tx_menu_category};//int array of views id's
            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.row_agent_menu, from, to);//Create object and set the parameters for simpleAdapter
            binding.simpleListView.setAdapter(simpleAdapter);//sets the adapter for listView

            //perform listView item click event
            binding.simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    agentMenuPresenter.goRespectiveCategories(i);
                }
            });
        }
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint() && v != null) {
            return;
        }
        agentMenuPresenter.getServerData(false);


        //INSERT CUSTOM CODE HERE

    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here

        try {
            // inflater = getActivity().getMenuInflater();
            // Inflate menu to add items to action bar if it is present.
            inflater.inflate(R.menu.menu_profile, menu);
            // Associate searchable configuration with the SearchView
        } catch (Exception e) {
            e.printStackTrace();
        }
        // super.onCreateOptionsMenu(menu, inflater);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_menu:
                ActivityProfileAgent.open(getActivity());
                break;
        }
        return false;
    }

}
