package io.github.shahalihridoy.roadsurvey;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CustomAdapter extends BaseAdapter {

    Context context;
    MainActivity mainActivity;
    String[] fnf;
    public static LayoutInflater inflater = null;

    public CustomAdapter(MainActivity mainActivity, String[] fnf) {
        this.context = mainActivity;
        this.mainActivity = mainActivity;
        this.fnf = fnf;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fnf.length;
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
        View view = inflater.inflate(R.layout.button_list, null);
        helper.addFnF = (Button) view.findViewById(R.id.Dcode);
        helper.addFnF.setText(fnf[position]);

        helper.d1 = (Button) view.findViewById(R.id.d1);
        helper.d2 = (Button) view.findViewById(R.id.d2);
        helper.d3 = (Button) view.findViewById(R.id.d3);
        helper.d4 = (Button) view.findViewById(R.id.d4);
        helper.d5 = (Button) view.findViewById(R.id.d5);
        helper.d6 = (Button) view.findViewById(R.id.d6);
        helper.d7 = (Button) view.findViewById(R.id.d7);
        helper.d8 = (Button) view.findViewById(R.id.d8);
        helper.d9 = (Button) view.findViewById(R.id.d9);

        final List<Button> buttons = new ArrayList<Button>();
        buttons.add(helper.d1);
        buttons.add(helper.d2);
        buttons.add(helper.d3);
        buttons.add(helper.d4);
        buttons.add(helper.d5);
        buttons.add(helper.d6);
        buttons.add(helper.d7);
        buttons.add(helper.d8);
        buttons.add(helper.d9);

        for (int i = 0;i<buttons.size();i++){
            final int finalI = i;
            buttons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mainActivity.dc = fnf[position];
                    mainActivity.dvalue = "0."+buttons.get(finalI).getTag().toString();
                    mainActivity.testLocation();

                    System.out.println("you clicked on "+fnf[position]+" under "+buttons.get(finalI).getTag());
                }
            });
        }

        helper.addFnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("you clicked on "+fnf[position]);
            }
        });

        return view;
    }

    public class Helper{
//        TextView textView;
//        TextView no;
        Button addFnF;
        Button d1,d2,d3,d5,d4,d6,d7,d8,d9;
//        ImageView imageView;
    }
}
