package io.github.shahalihridoy.roadsurvey;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

//    creating instances of two fragment for furthure use
    Fragment buttonFragment = new ButtonFragment();
    Fragment itemFragment = new ItemFragment();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Boolean isButtonFragment = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,buttonFragment);
        fragmentTransaction.commit();
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
        final CharSequence roadsId[] = new CharSequence[]{"3","34","4343","3","34","4343","3","34","4343","3","34","4343","3","34","4343","3","34","4343"};
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if(id == R.id.road){
//            showing roads id choosing option
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose a road");

            builder.setItems(roadsId, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    toolbar.setTitle(roadsId[which]);
                }
            });

            builder.show();
        }
        else if(id == R.id.show){
            if(isButtonFragment){
                fragmentTransaction.remove(buttonFragment);
                fragmentTransaction.replace(R.id.fragmentContainer,itemFragment);
                fragmentTransaction.commit();
                isButtonFragment = false;
            }
            else {
                fragmentTransaction.remove(itemFragment);
                fragmentTransaction.replace(R.id.fragmentContainer,buttonFragment);
                fragmentTransaction.commit();
                isButtonFragment = true;
            }
        }
        else if (id == R.id.action_settings) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
