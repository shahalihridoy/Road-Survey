package io.github.shahalihridoy.roadsurvey;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.Telephony;
import android.service.autofill.AutofillService;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CustomAdapter extends BaseAdapter {

    Context context;
    String[] fnf;
    public static LayoutInflater inflater = null;

    public CustomAdapter(MainActivity mainActivity, String[] fnf) {
        this.context = mainActivity;
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

//        helper.imageView = (ImageView) view.findViewById(R.id.list_image);
//        helper.no = (TextView) view.findViewById(R.id.no);
//        helper.textView = (TextView) view.findViewById(R.id.list_text);
//        helper.addFnF = (Button) view.findViewById(R.id.addFnF);

//        helper.imageView.setImageResource(sign[position]);
//        helper.no.setText(Integer.toString(position + 1));
//        helper.no.append(".");
//        helper.textView.setText(fnf[position]);

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
//        ImageView imageView;
    }
}
