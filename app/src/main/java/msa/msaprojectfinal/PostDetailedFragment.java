package msa.msaprojectfinal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PostDetailedFragment extends Fragment {
    Button interested;
    TextView datePosted, userName, description, payment, availability, title;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View myFragmentView = inflater.inflate(R.layout.activity_postdetail, container, false);
        getActivity().setTitle("Post details");
        Bundle bundle = getArguments();
        Post currentPost = (Post) bundle.getParcelable("post");
        title = myFragmentView.findViewById(R.id.textViewTitlePost2);
        title.setText(currentPost.title);
        datePosted = myFragmentView.findViewById(R.id.textViewDatePosted2);
        datePosted.setText("Date posted: "+currentPost.date_posted);
        description = myFragmentView.findViewById(R.id.textViewDescription2);
        description.setText(currentPost.description);
        userName = myFragmentView.findViewById(R.id.textViewPostedBy2);
        userName.setText("Posted by: "+currentPost.user_name);
        payment = myFragmentView.findViewById(R.id.textViewPayment);
        payment.setText("Payment offered: "+ currentPost.payment + " lei");
        availability = myFragmentView.findViewById(R.id.textViewAvailable);
        if(currentPost.available){
            availability.setText("Post is Available");
        }else{
            availability.setText("Post is Unavailable");
        }
        interested = myFragmentView.findViewById(R.id.buttonImInterested);

        return myFragmentView;
    }
}
