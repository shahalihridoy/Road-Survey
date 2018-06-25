package io.github.shahalihridoy.roadsurvey;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ItemFragment extends Fragment {

    View view;
    MainActivity mainActivity;
    ListView dataList;
    String[] dCode = {"dfs"};
    List<DataTableCreator> list = new ArrayList<DataTableCreator>();

    public ItemFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_item, container, false);

        list.add(new DataTableCreator("343","0.3","N45,S54"));
        list.add(new DataTableCreator("563","0.9","W45,S54"));
        list.add(new DataTableCreator("323","0.1","S65,N34"));
        list.add(new DataTableCreator("643","0.6","N55,S54"));
        list.add(new DataTableCreator("203","0.8","E65,S54"));
        list.add(new DataTableCreator("343","0.3","N45,S54"));
        list.add(new DataTableCreator("563","0.9","W45,S54"));
        list.add(new DataTableCreator("323","0.1","S65,N34"));
        list.add(new DataTableCreator("643","0.6","N55,S54"));
        list.add(new DataTableCreator("203","0.8","E65,S54"));
        list.add(new DataTableCreator("343","0.3","N45,S54"));
        list.add(new DataTableCreator("563","0.9","W45,S54"));
        list.add(new DataTableCreator("323","0.1","S65,N34"));
        list.add(new DataTableCreator("643","0.6","N55,S54"));
        list.add(new DataTableCreator("203","0.8","E65,S54"));
        list.add(new DataTableCreator("343","0.3","N45,S54"));
        list.add(new DataTableCreator("563","0.9","W45,S54"));
        list.add(new DataTableCreator("323","0.1","S65,N34"));
        list.add(new DataTableCreator("643","0.6","N55,S54"));
        list.add(new DataTableCreator("203","0.8","E65,S54"));
        list.add(new DataTableCreator("323","0.1","S65,N34"));
        list.add(new DataTableCreator("643","0.6","N55,S54"));
        list.add(new DataTableCreator("203","0.8","E65,S54"));
        list.add(new DataTableCreator("343","0.3","N45,S54"));
        list.add(new DataTableCreator("563","0.9","W45,S54"));
        list.add(new DataTableCreator("323","0.1","S65,N34"));
        list.add(new DataTableCreator("643","0.6","N55,S54"));
        list.add(new DataTableCreator("203","0.8","E65,S54"));
        list.add(new DataTableCreator("343","0.3","N45,S54"));
        list.add(new DataTableCreator("563","0.9","W45,S54"));
        list.add(new DataTableCreator("323","0.1","S65,N34"));
        list.add(new DataTableCreator("643","0.6","N55,S54"));
        list.add(new DataTableCreator("203","0.8","E65,S54"));

        mainActivity = (MainActivity) getActivity();
        dataList = (ListView) view.findViewById(R.id.dataList);
        dataList.setAdapter(new CustomDataAdapter(mainActivity, list));
        return view;
    }
}
