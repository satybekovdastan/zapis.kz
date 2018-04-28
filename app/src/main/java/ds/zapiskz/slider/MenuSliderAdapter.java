package ds.zapiskz.slider;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import ds.zapiskz.R;


public class MenuSliderAdapter extends PagerAdapter {

    private ArrayList<ImageModel> images;
    private LayoutInflater inflater;
    private Context context;

    public MenuSliderAdapter(Context context, ArrayList<ImageModel> images) {
        this.context = context;
        this.images=images;
        try {
            inflater = LayoutInflater.from(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.image_slide, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image_slider2);
        Picasso.with(context).load(images.get(position).getImage_drawable()).into(myImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
