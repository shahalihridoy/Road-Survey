package io.github.shahalihridoy.roadsurvey;

import android.content.Context;
import android.database.Cursor;
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
    Database db;

    public ItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        view = inflater.inflate(R.layout.fragment_item, container, false);
        list.clear();
        mainActivity = (MainActivity) getActivity();
        db = new Database(mainActivity.getApplicationContext());
        Cursor c = db.runCustomQuery("select logcode,logvalue,gps,rowid from road_survey order by logdate,logtime desc");
        if(c.getCount()>0){
            c.moveToFirst();
            do {
                list.add(new DataTableCreator(c.getString(0),c.getString(1),c.getString(2),c.getString(3)));
            }while (c.moveToNext());
        }

        dataList = (ListView) view.findViewById(R.id.dataList);
        dataList.setAdapter(new CustomDataAdapter(mainActivity, list));
        return view;
    }
}
