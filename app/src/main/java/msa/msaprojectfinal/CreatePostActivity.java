package msa.msaprojectfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public EditText title, description, payment, category;
    Button buttonCreatePost;
    private Spinner spinner;
    private String category_chosen;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private static final String[] paths = {"Cleaning", "House", "Garden", "Education", "Jobs"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);
        Bundle data = getIntent().getExtras();
        final User currentUser = (User) data.getParcelable("user");
        System.out.println("USER FROM PARCELABLE IS "+currentUser.username);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(CreatePostActivity.this, android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        title = findViewById(R.id.editTextTitle);
        description = findViewById(R.id.editTextDescription);
        payment = findViewById(R.id.editTextPayment);
        //category = findViewById(R.id.editTextCategory);
        buttonCreatePost = findViewById(R.id.buttonCreatePost);
        buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String titleString = title.getText().toString();
                String descriptionString = description.getText().toString();
                String paymentString = payment.getText().toString();
                String categoryString = category_chosen;
                //category.getText().toString();
                addNewPost(currentUser.userId, currentUser.username, titleString, descriptionString, categoryString, paymentString);
                Intent myIntent = new Intent(CreatePostActivity.this, UserActivity.class);
                myIntent.putExtra("emailID", currentUser.email);
                startActivity(myIntent);
            }
        });

    }
    private void addNewPost(String uid, String author, String title, String description, String category, String payment) {
        Log.d("Am here",title);
        String key = mDatabase.push().getKey();
        Post post = new Post(uid, author, title, description, category, payment);
        mDatabase.child("Posts").child(key).setValue(post);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                category_chosen = paths[0];
                break;
            case 1:
                category_chosen = paths[1];
                break;
            case 2:
                category_chosen = paths[2];
                break;
            case 3:
                category_chosen = paths[3];
                break;
            case 4:
                category_chosen = paths[4];
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
