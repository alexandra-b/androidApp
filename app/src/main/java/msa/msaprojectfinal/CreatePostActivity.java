package msa.msaprojectfinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePostActivity extends AppCompatActivity {
    public EditText title, description, payment, category;
    Button buttonCreatePost;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);
        title = findViewById(R.id.editTextEmail);
        description = findViewById(R.id.editTextFName);
        payment = findViewById(R.id.editTextLName);
        category = findViewById(R.id.editTextCategory);
        buttonCreatePost = findViewById(R.id.buttonCreatePost);
        buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String titleString = title.getText().toString();
                String descriptionString = description.getText().toString();
                String paymentString = payment.getText().toString();
                String categoryString = category.getText().toString();
                Post post = new Post();
                //updateDatabase()
            }
        });
    }

}
