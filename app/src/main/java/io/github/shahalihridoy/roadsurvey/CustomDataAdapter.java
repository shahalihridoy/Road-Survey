package io.github.shahalihridoy.roadsurvey;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CustomDataAdapter extends BaseAdapter{

    Context context;
    MainActivity mainActivity;
    List<DataTableCreator> fnf;
    public static LayoutInflater inflater = null;
    Database db;

    public CustomDataAdapter(MainActivity mainActivity, List<DataTableCreator> fnf) {
        this.context = mainActivity;
        this.mainActivity = mainActivity;
        this.fnf = fnf;
        db = new Database(context.getApplicationContext());
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fnf.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Helper helper = new Helper();

        final View view = inflater.inflate(R.layout.data_list, null);

        helper.dCodetextView = (TextView) view.findViewById(R.id.defect_code);
        helper.dValueTextView = (TextView) view.findViewById(R.id.defect_value);
        helper.dLocationTextView = (TextView) view.findViewById(R.id.defect_location);

        helper.dCodetextView.setText( fnf.get(position).dCode);
        helper.dValueTextView.setText( fnf.get(position).dValue);
        helper.dLocationTextView.setText( fnf.get(position).dLocation);

        helper.delete = (TextView) view.findViewById(R.id.delete);

        helper.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deletes("road_survey","rowid",fnf.get(position).rid);
                mainActivity.refreshItemFragment();
                System.out.println("you clicked on "+fnf.get(position).rid);
            }
        });

        return view;
    }

    public class Helper{
        TextView dCodetextView;
        TextView dValueTextView;
        TextView dLocationTextView;
        TextView delete;
    }

}
