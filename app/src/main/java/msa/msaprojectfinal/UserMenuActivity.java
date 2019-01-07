package msa.msaprojectfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    User currentUser;
    TextView userNameTV, emailTV;
    FloatingActionButton fab;

    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        //
        fab = findViewById(R.id.fab);
        Intent intent = getIntent();
       // final String email = intent.getStringExtra("emailID");
        Bundle data = getIntent().getExtras();
        currentUser = data.getParcelable("user");
        System.out.println("Found CURRENT "+currentUser.username);
        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    /*
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Searching in DB");
                for (DataSnapshot currDataSnapshot : dataSnapshot.getChildren()) {
                    if(currDataSnapshot.getValue(User.class).email.equals(email)){
                        System.out.println("FOUND HIM");
                        currentUser = currDataSnapshot.getValue(User.class);

                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_menu, menu);
        userNameTV = (TextView)findViewById(R.id.userNameMenu);
        emailTV = (TextView)findViewById(R.id.userEmailMenu);
        userNameTV.setText(currentUser.username);
        emailTV.setText(currentUser.email);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_posts) {
            // user posts
            fab.hide();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            UserPostsFragment fragment2 = new UserPostsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", currentUser);
            fragment2.setArguments(bundle);
            ft.replace(R.id.fragment_container, fragment2);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_allposts) {
            //all posts
            fab.hide();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_container,(Fragment)new AllPostsFragment()).commit();
        } else if (id == R.id.nav_addpost) {
            fab.hide();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            CreatePostFragment fragment2 = new CreatePostFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", currentUser);
            fragment2.setArguments(bundle);
            ft.replace(R.id.fragment_container, fragment2);
            ft.addToBackStack(null);
            ft.commit();
        }else if (id == R.id.nav_logOut){
            FirebaseAuth.getInstance().signOut();
            Intent I = new Intent(UserMenuActivity.this, LoginActivity.class);
            startActivity(I);
        }else if (id == R.id.nav_info){
            fab.hide();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            UserInformationFragment fragment2 = new UserInformationFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userId", currentUser.userId);
            fragment2.setArguments(bundle);
            ft.replace(R.id.fragment_container, fragment2);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
