package msa.msaprojectfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private Context mContext;
    private List<Post> postList = new ArrayList<>();

    public PostAdapter(Context context, ArrayList<Post> list) {
        super(context, 0 , list);
        mContext = context;
        postList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listItem = inflater.inflate(R.layout.post_item, null);

        if (listItem == null) {
            listItem = inflater.inflate(R.layout.post_item, null);
            System.out.println("NULL ITEM HELLO!");
        }
        Post currentPost = postList.get(position);
        ImageView image = (ImageView)listItem.findViewById(R.id.imageView2);
        switch(currentPost.category){
            case "House":
                image.setImageResource(R.drawable.ic_action_house);
                break;
            case "Cleaning":
                image.setImageResource(R.drawable.ic_action_cleaning);
                break;
            case "Education":
                image.setImageResource(R.drawable.ic_action_education);
                break;
            case "Jobs":
                image.setImageResource(R.drawable.ic_action_job);
                break;
            case "Garden":
                image.setImageResource(R.drawable.ic_action_garden);
                break;
        }
        //image.setImageResource(R.drawable.ic_action_house);
        TextView title = (TextView) listItem.findViewById(R.id.textViewTitlePost);
        title.setText(currentPost.title);
        TextView reward = (TextView) listItem.findViewById(R.id.textViewReward);
        reward.setText(currentPost.payment+" lei");
        TextView date_posted =  (TextView) listItem.findViewById(R.id.textViewPostDate);
        date_posted.setText(currentPost.date_posted);
        return listItem;
    }


}
