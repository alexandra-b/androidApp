package msa.msaprojectfinal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FilterFragment extends Fragment{
    Button filter_button;
    Spinner sortBySpinner, CategorySpinner, SearchInSpinner;
    String category_chosen, sort_by, search_in;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    String emailToSearch;
    User currentUser;
    ArrayList<Post> filteredPosts;
    ArrayList<Post> rev_filteredPosts;
    int all_c = 0, asc = 1, my_p = 1;

    private static final String[] pathsSortBy = {"Most rewarded first", "Less rewarded first"};
    private static final String[] pathsCategory = {"Cleaning", "House", "Garden", "Education", "Jobs", "All categories"};
    private static final String[] pathsSearchIn = {"My Posts Only", "All Posts"};

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View myFragmentView = inflater.inflate(R.layout.activity_filter, container, false);
        getActivity().setTitle("Post filter");
        filteredPosts = new ArrayList<Post>();
        Bundle bundle = getArguments();
        currentUser = (User) bundle.getParcelable("user");
        sortBySpinner = (Spinner)myFragmentView.findViewById(R.id.spinnerSortBy);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, pathsSortBy);
        sortBySpinner.setAdapter(aa);
        sortBySpinner.setOnItemSelectedListener(new SortBySpinnerClass());
        CategorySpinner = (Spinner)myFragmentView.findViewById(R.id.spinnerCategoryF);
        ArrayAdapter<String> bb = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, pathsCategory);
        CategorySpinner.setAdapter(bb);
        CategorySpinner.setOnItemSelectedListener(new CategorySpinnerClass());
        SearchInSpinner = (Spinner)myFragmentView.findViewById(R.id.spinnerSearchIn);
        ArrayAdapter<String> cc = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, pathsSearchIn);
        SearchInSpinner.setAdapter(cc);
        SearchInSpinner.setOnItemSelectedListener(new SearchInSpinnerClass());
        filter_button = (Button) myFragmentView.findViewById(R.id.buttonFilter);
        filter_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(search_in.equals("All posts")){
                    my_p = 0;
                }
                if(sort_by.equals("Most rewarded first")){
                    asc = 0;
                }
                if(category_chosen.equals("All categories")){
                    all_c = 1;
                }
                Query query = mDatabase.child("Posts").orderByChild("payment");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            System.out.println("found dem");
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                // do with your result
                                Post currentPost = (Post)issue.getValue(Post.class);
                                if(my_p!=0){
                                    //only user s posts
                                    if(!currentPost.user_id.equals(currentUser.userId)){
                                        continue;
                                    }
                                }
                                if(all_c == 0 ){
                                    if(!currentPost.category.equals(category_chosen)){
                                        continue;
                                    }
                                }
                                filteredPosts.add(currentPost);
                            }
                            //open new fragment
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            FilteredListFragment fragment2 = new FilteredListFragment();
                            Bundle bundle = new Bundle();
                            if(asc == 0) {
                            //sort reversed order
                                rev_filteredPosts = new ArrayList<Post>();
                                for(int i = filteredPosts.size()-1; i>=0; i--){
                                    rev_filteredPosts.add(filteredPosts.get(i));
                                }
                                bundle.putParcelableArrayList("posts", rev_filteredPosts);
                                System.out.println("tried desc");
                            }else{
                                System.out.println("tried asc");
                                bundle.putParcelableArrayList("posts", filteredPosts);
                            }
                            bundle.putString("all_c", category_chosen);
                            bundle.putInt("my_p", my_p);
                            bundle.putInt("asc", asc);
                            bundle.putInt("found",1);
                            fragment2.setArguments(bundle);
                            ft.replace(R.id.fragment_container, fragment2);
                            ft.addToBackStack(null);
                            ft.commit();

                        }else{
                            //nothing found for filter applied
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            FilteredListFragment fragment2 = new FilteredListFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("all_c", all_c);
                            bundle.putInt("my_p", my_p);
                            bundle.putInt("asc", asc);
                            bundle.putInt("found",0);
                            fragment2.setArguments(bundle);
                            ft.replace(R.id.fragment_container, fragment2);
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        //select category -> if all of them or some category
        //select which db to take from : all posts or just from the same user all posts
        //order either by reward (ascending or descending) or by time: most recent or least recent
        return myFragmentView;
    }
    class SortBySpinnerClass implements AdapterView.OnItemSelectedListener
    {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            sort_by = pathsSortBy[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class CategorySpinnerClass implements AdapterView.OnItemSelectedListener
    {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            category_chosen = pathsCategory[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    class SearchInSpinnerClass implements AdapterView.OnItemSelectedListener
    {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            search_in = pathsSearchIn[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
