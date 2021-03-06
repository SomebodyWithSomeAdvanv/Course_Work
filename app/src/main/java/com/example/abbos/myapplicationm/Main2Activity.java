package com.example.abbos.myapplicationm;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int[] IMAGES ={R.drawable.tp_1,R.drawable.tp_2
            ,R.drawable.tp_3_,R.drawable.tp_4,R.drawable.tp_5
            ,R.drawable.tp_6,R.drawable.tp_7,R.drawable.tp_8
            ,R.drawable.tp_9,R.drawable.tp_10,R.drawable.hg_1
            ,R.drawable.hg_2,R.drawable.hg_3,R.drawable.hg_4
    ,R.drawable.hg_5,R.drawable.hg_6,R.drawable.hg_7,R.drawable.hg_8
    ,R.drawable.hg_9,R.drawable.hg_10};
    String[] NameOfMovie ={"The Shawshank Redemption ","The Godfather"
    ,"The Godfather: Part II","The Dark Knight","12 Angry Men"
     ,"Schindler's List","The Lord of the Rings: The Return of the King",
    "Pulp Fiction","Il buono, il brutto, il cattivo","Fight Club ",
    "Avatar","Titanic","Star Wars: The Force Awakens"
    ,"Avengers: Infinity War","Jurassic World","The Avengers",
    "Furious 7","Avengers: Age of Ultron","Black Panther"
            ,"Harry Potter and the Deathly Hallows – Part 2"};
    String[] RankingOfMovies ={"9.2","9.2","9.0","9.0","8.9",
            "8.9","8.9","8.9","8.9","8.8","7.8","7.8","8.0",
            "8.6","7.0","8.1","7.2","7.4","7.4","8.1"};

    private Bitmap[] bitmap =new Bitmap[15];
    private String[] Title =new String[15];
    private String[] Description =new String[15];
    private Toolbar toolbar;
    private ListView listView;
    private int CountAdded;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView =findViewById(R.id.listview);

        CountAdded =0;

        obnovit();
        final CustomAdapter customAdapter=new CustomAdapter();

        customAdapter.fds=20+CountAdded;
        customAdapter.added=CountAdded;
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int mk=CountAdded;
                if((Integer)listView.getTag()<-10 && (Integer)listView.getTag()>-20 ){
                    position=position+10+CountAdded;mk=0;
                }else{
                    if((Integer)listView.getTag()>=-8){position=(Integer)listView.getTag()+mk;mk=0;
                    }else {
                        if((Integer)listView.getTag()<-20 ){ mk=0;position=position+CountAdded;
                        }
                    }
                }

              if(mk>position){
                  Intent myIntent = new Intent(Main2Activity.this, ItemClicked.class);
                  myIntent.putExtra("key", position); //Optional parameters
                  Main2Activity.this.startActivity(myIntent);
              }else{
                  Intent myIntent = new Intent(Main2Activity.this, ItemClicked.class);
                  myIntent.putExtra("key", position); //Optional parameters
                  Main2Activity.this.startActivity(myIntent);
              }
            }
        });

        handleIntent(getIntent());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    void obnovit(){
        CountAdded=0;
        for(int i=0;i<15;i++){
            File file = new File(Environment.getExternalStorageDirectory() + "/DirName", "image"+i);
            if(file.exists()){
                bitmap[CountAdded] = BitmapFactory.decodeFile(file.getAbsolutePath());
                try{
                    FileOutputStream out = new FileOutputStream(file);

                    bitmap[CountAdded].compress(Bitmap.CompressFormat.JPEG, 20, out);

                    out.flush();
                    out.close();

                    StringBuilder text = new StringBuilder();
                    File file2 = new File(Environment.getExternalStorageDirectory() + "/DirName","text"+i);

                    BufferedReader br = new BufferedReader(new FileReader(file2));

                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                      //  text.append('\n');
                    }

                    br.close() ;
                    for(int o=0;o<20;o++){
                        if(text.toString().substring(o, o + 2).equals("%%")){
                            Title[CountAdded]=text.toString().substring(0, o);
                            Description[CountAdded]=text.toString().substring(o+2,text.toString().length());
                            break;
                        }
                    }

                }catch(IOException e) {
                    e.printStackTrace();
                }
                CountAdded++;
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        getMenuInflater().inflate(R.menu.main2, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
      /**/
            searchView.setSearchableInfo(searchableInfo);

        return true;
    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            //Log.i("Search21","Search="+query);
          for(int i=0;i<NameOfMovie.length;i++){
                if(NameOfMovie[i].contains(query)){
                  //  toolbar.setTitle("All Movies");
                    CustomAdapter customAdapter=new CustomAdapter();
                    customAdapter.fds=1;
                    customAdapter.asd="4";
                    customAdapter.search=i;
                    listView.setAdapter(customAdapter);
                    break;
                }
                if(i==NameOfMovie.length-1){
                    Toast.makeText(this,"Not Found",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            toolbar.setTitle("All Movies");
            obnovit();
            CustomAdapter customAdapter=new CustomAdapter();
            customAdapter.fds=20+CountAdded;
            customAdapter.added=CountAdded;
            listView.setAdapter(customAdapter);
        }else if(id == R.id.nav_top) {
            toolbar.setTitle("Top movies of All Time");
            CustomAdapter customAdapter=new CustomAdapter();
            customAdapter.fds=10;customAdapter.asd="5";
            listView.setAdapter(customAdapter);
        }else if(id == R.id.nav_highest) {
            toolbar.setTitle("Highest grossing films ");
            CustomAdapter customAdapter=new CustomAdapter();
            customAdapter.fds=10;customAdapter.asd="3";
            listView.setAdapter(customAdapter);
        }else if(id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }else if(id == R.id.add_movie) {
            Intent intent = new Intent(this, Add.class);
            startActivity(intent);
            finish();
        }
        /*else if (id == R.id.nav_sign) { }*/

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

class CustomAdapter extends BaseAdapter{
    int fds=0;int added=0;
    String asd ="1";
    int search =0;
    @Override
    public int getCount() {
        return fds;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        convertView =getLayoutInflater().inflate(R.layout.cutomlayout,null);
        ImageView imageView=convertView.findViewById(R.id.imageView3);

        TextView textView =convertView.findViewById(R.id.textView2);
        TextView textView2 =convertView.findViewById(R.id.textView3);
        listView.setTag(-9);
        if(added>position){
            try {
                imageView.setImageBitmap(bitmap[position]);
                textView.setText(Title[position]);
                textView2.setText(String.format("IMBD: %s", "7"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }else{
            position=position-added;
            if(asd.equals("3")){//highest movies
                position=position+10;
                listView.setTag(-19);
            }
            if(asd.equals("4")){//searc movies
                position=search;
                listView.setTag(position);
            }
            if(asd.equals("5")){//searc movies
                listView.setTag(-29);
            }
            imageView.setImageResource(IMAGES[position]);
            textView.setText(NameOfMovie[position]);
            textView2.setText(String.format("IMBD: %s", RankingOfMovies[position]));
        }
        return convertView;
    }
}

}
