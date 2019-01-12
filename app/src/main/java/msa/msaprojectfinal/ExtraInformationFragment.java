package msa.msaprojectfinal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExtraInformationFragment extends Fragment {
    TextView fName, lName, email, userName;
    User currentUser;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View myFragmentView = inflater.inflate(R.layout.activity_extrainfo, container, false);
        getActivity().setTitle("Contact information");
        Bundle bundle = getArguments();
        currentUser = (User) bundle.getParcelable("user");
        userName = myFragmentView.findViewById(R.id.textViewUserNameExtra);
        fName = myFragmentView.findViewById(R.id.textViewFNameExtra);
        lName = myFragmentView.findViewById(R.id.textViewLNameExtra);
        email = myFragmentView.findViewById(R.id.textViewEmailExtra);
        userName.setText("User name : "+ currentUser.username);
        fName.setText("First name : "+currentUser.firstName);
        lName.setText("Last name : "+currentUser.lastName);
        email.setText("Email : "+currentUser.email);
        return myFragmentView;
    }

}
