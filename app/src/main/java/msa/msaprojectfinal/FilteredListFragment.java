package msa.msaprojectfinal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FilteredListFragment extends Fragment {
    TextView filter_title, categSortedBy;
    ListView listview;
    ArrayList<Post> postsList;
    private PostAdapter mAdapter;
    User currentUser;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View myFragmentView = inflater.inflate(R.layout.activity_filteredlist, container, false);
        getActivity().setTitle("Filtered posts");
        filter_title = myFragmentView.findViewById(R.id.textViewfilter_title);
        categSortedBy = myFragmentView.findViewById(R.id.textViewCategSortedBy);
        Bundle bundle = getArguments();
        int my_p = bundle.getInt("my_p");
        System.out.println("my_p is "+my_p);
        String all_c = bundle.getString("all_c");
        int asc = bundle.getInt("asc");
        currentUser = bundle.getParcelable("user");
        filter_title.setText("Posts featured in "+ all_c + ((my_p!=0)? " in my posts":" in all posts"));
        categSortedBy.setText("sorted by "+((asc!=0)? "ascending":"descending") + " order by payment:");
        int found = bundle.getInt("found");
        if(found!=0){
            //display the list because posts were found
            listview = (ListView)  myFragmentView.findViewById(R.id.myfilteredlistview);
            postsList = bundle.getParcelableArrayList("posts");
            mAdapter = new PostAdapter(this.getActivity(), postsList);
            mAdapter.notifyDataSetChanged();
            listview.setAdapter(mAdapter);
            listview.setClickable(true);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Post currentPost = (Post)listview.getItemAtPosition(position);
                    System.out.println("ITEM AT POSITION IS "+currentPost.key_id);
                    //start new activity with post description
                    FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    PostDetailedFragment fragment2 = new PostDetailedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("post", currentPost);
                    fragment2.setArguments(bundle);
                    ft.replace(R.id.fragment_container, fragment2);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }else{
            //no matching criteria found

        }

        return myFragmentView;
    }

    public void onBackPressed() {
        FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        FilterFragment fragment2 = new FilterFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", currentUser);
        fragment2.setArguments(bundle);
        ft.replace(R.id.fragment_container, fragment2);
        ft.addToBackStack(null);
        ft.commit();
    }
}
