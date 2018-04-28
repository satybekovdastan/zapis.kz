package ds.zapiskz.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ds.zapiskz.DetailActivity;
import ds.zapiskz.Models.News;
import ds.zapiskz.R;

public class RVNewsAdapter extends RecyclerView.Adapter<RVNewsAdapter.PersonViewHolder> {

    Context context;
    News vse;

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView textName,textType;

        PersonViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            textName = itemView.findViewById(R.id.textName);
            textType = itemView.findViewById(R.id.textType);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DetailActivity.class);
                    vse = new News();
                    int id = getAdapterPosition();
                    vse = listVse.get(id);
                    intent.putExtra("id", vse.getId());
                    Activity activity = (Activity) context;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.animation, R.anim.animation2);
                }
            });

        }

    }

    ArrayList<News> listVse;

    public RVNewsAdapter(Context context, ArrayList<News> listVse) {
        this.listVse = listVse;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {
        vse = new News();
        vse = listVse.get(i);

        personViewHolder.textName.setText(vse.getName());
        personViewHolder.textType.setText(vse.getType());

        Picasso.with(context).load("https://zapis.kz/"+vse.getPictureUrl()).error(R.drawable.no_image).placeholder(R.drawable.no_image)
                .into(personViewHolder.image);

    }

    @Override
    public int getItemCount() {
        return listVse.size();
    }

}