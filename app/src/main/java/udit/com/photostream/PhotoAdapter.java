package udit.com.photostream;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by uagrawal on 1/25/15.
 */
public class PhotoAdapter extends ArrayAdapter<Photo> {

    public PhotoAdapter(Context context, ArrayList<Photo> photos) {
        super(context, R.layout.item_photo, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data
        Photo photo = getItem(position);

        //Check if we are using an recycle view
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        //Loading the subview with the template
        TextView textView = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivPhoto);

        textView.setText(photo.caption);
        textView.setTextColor(Color.BLACK);
        imageView.getLayoutParams().height = photo.height;

        imageView.setImageResource(0);

        Picasso.with(getContext()).load(photo.imageUrl).into(imageView);

        return convertView;
    }
}
