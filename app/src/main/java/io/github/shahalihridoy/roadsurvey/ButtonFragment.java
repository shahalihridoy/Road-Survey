package io.github.shahalihridoy.roadsurvey;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ButtonFragment extends Fragment {

    ListView buttonList;
    View view;
    MainActivity mainActivity;
    String[] dCode = {"D1","D2","D3a","D3b","D4","D7","D9a","D9b"};

    public ButtonFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_button, container, false);

        mainActivity = (MainActivity) getActivity();
        buttonList = (ListView) view.findViewById(R.id.list_view_button);
        buttonList.setAdapter(new CustomAdapter(mainActivity,dCode));

        return view;
    }

}
