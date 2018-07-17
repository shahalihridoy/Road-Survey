package io.github.shahalihridoy.roadsurvey;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    Database db;
    Cursor c;
    CharSequence[] roadsId = new CharSequence[0];

    boolean shouldClose;

    Double longitude, latitude;
    GPSTracker gps;
    Location location;


    //    these are for database
    String dc, rid, dvalue, dlocation, dtime, ddate;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy:MM:dd");

    //    creating instances of two fragment for furthure use
    Fragment buttonFragment = new ButtonFragment();
    Fragment itemFragment = new ItemFragment();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Boolean isButtonFragment = true;
    String temp;

//    int confirm=0;
//
//    LocationManager locationManager;
//    LocationListener locationListener;

    TextView lat;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        enable gps service
        lat = (TextView) findViewById(R.id.lat);
        gps = new GPSTracker(this,lat);

        new Thread(){
            @Override
            public void run() {
                security();
            }
        }.start();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, buttonFragment);
        fragmentTransaction.commit();

        if (
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 10);
            }
        } else {
            location = gps.getLocation();
        }

//        load data from csv
        db = new Database(this);
        c = db.getRoadList();
        int counter = 0;
        if (c.getCount() > 0) {
            loadRoadId();
        } else {
            loadData();
            loadRoadId();
        }

        File f = new File(Environment.getExternalStorageDirectory() + "/Road Survey");
        if (!f.exists())
            f.mkdir();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    location = gps.getLocation();
                    return;
                }
        }
    }

    private void loadData() {
        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("RoadList.csv"));
            BufferedReader reader = new BufferedReader(is);

            reader.readLine();
            String line;
            String[] temp;
            while ((line = reader.readLine()) != null) {
                temp = line.split(",");
                db.insertToRoadList(temp[0], temp[1], temp[2]);
            }

        } catch (Exception e) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.road) {
            showRoadChooser();
        } else if (id == R.id.show) {
            if (isButtonFragment) {
                fragmentTransaction.remove(buttonFragment);
                fragmentTransaction.replace(R.id.fragmentContainer, itemFragment);
                fragmentTransaction.commit();
                isButtonFragment = false;
            } else {
                fragmentTransaction.remove(itemFragment);
                fragmentTransaction.replace(R.id.fragmentContainer, buttonFragment);
                fragmentTransaction.commit();
                isButtonFragment = true;
            }
        } else if (id == R.id.exports) {
            createCSVfile();
        } else if (id == R.id.imports) {
            readCSVfile();
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        return super.onOptionsItemSelected(item);
    }

    private void readCSVfile() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("text/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, 5);
    }

    private void createCSVfile() {

        try {
            File folder = new File(Environment.getExternalStorageDirectory() + "/Road Survey");
            if (!folder.exists()) {
                folder.mkdir();
            }
            final String filename = folder.toString() + "/Road_Survey.csv";
            CharSequence contentTitle = getString(R.string.app_name);

            final ProgressDialog p = new ProgressDialog(this);
            p.setTitle(contentTitle);
            p.setMessage("Exporting file");
            p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            p.setIndeterminate(true);
            p.show();

            final Thread t = new Thread() {
                public boolean isSuccess = false;

                @Override
                public void run() {
                    try {
                        BufferedWriter riter = new BufferedWriter(new FileWriter(new File(filename)));
                        Cursor c = db.getRoadSurvey();
                        riter.write("RId,Road,Length,Dcode,Value,Time,Latitude,Longitude,Date");
                        riter.newLine();

                        if (c.getCount() > 0) {
                            c.moveToFirst();
                            do {
                                System.out.println(c.getString(0));
                                riter.write(c.getString(0));
                                for (int i = 1; i < c.getColumnCount(); i++) {
                                    riter.write("," + c.getString(i));
                                    System.out.println(c.getString(i));
                                }
                                riter.newLine();

                            } while (c.moveToNext());
                        }
                        c.close();
                        riter.close();
                        db.close();
                        p.dismiss();
                        isSuccess = true;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            t.start();

        } catch (Exception e) {
            Toast.makeText(this, "Error in exporting file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        Toast.makeText(this, "File exporting successful to\nInternal Storage/Road Survey/", Toast.LENGTH_LONG).show();
    }

    public void listener(View view) {
//        Button button = (Button) view;
        temp = (String) view.getTag();

        dc = temp.substring(0, temp.length() - 1);
        dvalue = String.valueOf(temp.charAt(temp.length() - 1));

        System.out.println("code: " + dc + " value: " + dvalue);

        testLocation();
    }

    public void listeners(View view) {
        temp = (String) view.getTag();
        double value = 0.25;
        char letter = temp.charAt(temp.length() - 1);

        if (letter == 'S')
            value = 0.25;
        else if (letter == 'M')
            value = 0.5;
        else value = 1;

        dc = temp.substring(0, temp.length() - 1);
        dvalue = String.valueOf(value);

        System.out.println("code: " + dc + " value: " + dvalue);

        testLocation();
    }


    @SuppressLint("MissingPermission")
    public void testLocation() {

        longitude = gps.getLongitude();
        latitude = gps.getLatitude();

        date = Calendar.getInstance().getTime();
        dtime = sdf.format(date);
        ddate = sdf1.format(date);
        if (rid == null)
            Toast.makeText(this, "Please select a road first", Toast.LENGTH_LONG).show();
        else if (longitude == 0) {
            Toast.makeText(this, "Location is not tracked yet", Toast.LENGTH_LONG).show();
        } else db.insertToRoadSurvey(rid, dc, dvalue, latitude + "," + longitude, dtime, ddate);

        System.out.println(dtime);
        System.out.println(ddate);

        System.out.println(gps.getLatitude());
        System.out.println(gps.getLongitude());
        db.close();
    }

    public void refreshItemFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(itemFragment);
        fragmentTransaction.attach(itemFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            try {
                Uri uri = data.getData();
                String src = uri.getPath();
                String[] path;
                temp = uri.toString();
                String filePath = Environment.getExternalStorageDirectory() + "/" + "Road Survey/RoadList.csv";
                System.out.println(src);
                System.out.println(uri.getPath());
                System.out.println(filePath);


                BufferedReader reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));
//                BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));

                while ((temp = reader.readLine()) != null) {
                    path = temp.split(",");
                    System.out.println(temp);
                    if (path.length != 3) {
                        Toast.makeText(this, "Please select a valid file", Toast.LENGTH_LONG).show();
                        return;
                    }
                    db.insertToRoadList(path[0], path[1], path[2]);
                }
                Toast.makeText(this, "File is imported successfully", Toast.LENGTH_LONG).show();
                loadRoadId();
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Please select a file from internal storage", Toast.LENGTH_LONG).show();
                return;
            } catch (IOException e) {
                Toast.makeText(this, "Error while reading file", Toast.LENGTH_LONG).show();
                return;
            } catch (Exception e) {
                Toast.makeText(this, "Unknown error", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    private void showRoadChooser() {
        //            showing roads id choosing option
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a road");

        builder.setItems(roadsId, new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cursor c = db.runCustomQuery("select * from road_list where rcode like '" + roadsId[which] + "'");
                c.moveToFirst();
                toolbar.setTitle(c.getString(0) + "|" + c.getString(1) + "m");
                rid = c.getString(2);
                c.close();
            }
        });

        builder.show();
    }

    private void loadRoadId() {
        int counter = 0;
        c = db.getRoadList();
        roadsId = new CharSequence[c.getCount()];
        c.moveToFirst();
        do {
            roadsId[counter] = c.getString(0);
            counter++;
        } while (c.moveToNext());
        c.close();
    }

    private void security() {

//        read from firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shouldClose");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                boolean value = dataSnapshot.getValue(boolean.class);
                shouldClose = value;
                System.out.println(shouldClose);
                String MY_PREFS_NAME = "RoadSurvey";
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                editor.putBoolean("shouldClose", value);
                editor.commit();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

//        read from preferences

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String MY_PREFS_NAME = "RoadSurvey";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (prefs.contains("shouldClose")) {
            shouldClose = prefs.getBoolean("shouldClose", true);
            System.out.println("this is from " + shouldClose);
            if (!shouldClose) {
//                intentional exception instead of System.exit(0);
                int i = 4/0;
            }
        }
    }

}
