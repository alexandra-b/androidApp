package msa.msaprojectfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePostFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    public EditText title, description, payment, category;
    Button buttonCreatePost;
    private Spinner spinner;
    private String category_chosen;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private static final String[] paths = {"Cleaning", "House", "Garden", "Education", "Jobs"};
    private User currentUser;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View myFragmentView = inflater.inflate(R.layout.activity_addpost, container, false);
        getActivity().setTitle("Create post");
        Bundle bundle = getArguments();
        currentUser = (User) bundle.getParcelable("user");
        spinner = (Spinner)myFragmentView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        title = myFragmentView.findViewById(R.id.editTextTitle);
        description = myFragmentView.findViewById(R.id.editTextDescription);
        payment = myFragmentView.findViewById(R.id.editTextPayment);
        //category = findViewById(R.id.editTextCategory);
        buttonCreatePost = myFragmentView.findViewById(R.id.buttonCreatePost);
        buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String titleString = title.getText().toString();
                String descriptionString = description.getText().toString();
                double paymentDouble = Double.parseDouble(payment.getText().toString());
                String categoryString = category_chosen;
                //category.getText().toString();
                addNewPost(currentUser.userId, currentUser.username, titleString, descriptionString, categoryString, paymentDouble);
                //getFragmentManager().popBackStack();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                UserPostsFragment fragment2 = new UserPostsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", currentUser);
                fragment2.setArguments(bundle);
                ft.replace(R.id.fragment_container, fragment2);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return myFragmentView;
    }

    private void addNewPost(String uid, String author, String title, String description, String category, double payment) {
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
