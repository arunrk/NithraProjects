package nithra.tamil.jokes;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ARUNRK THOMAS on 3/6/2017.
 */

public class RecyclerCatAdapter extends RecyclerView.Adapter<RecyclerCatAdapter.DataObjectHolder> {

    List<Jokes> list;
    AppCompatActivity activity;

    private int lastPosition = -1;
    private int FADE_DURATION = 1000;
    OnCatClickAdapter onCurrentCourseClickAdapter;

    public interface OnCatClickAdapter {
        public void onCatClickAdapter(String id, String title);
    }

    public RecyclerCatAdapter(AppCompatActivity activity, List<Jokes> list) {
        this.list = list;
        this.activity = activity;
        onCurrentCourseClickAdapter = (OnCatClickAdapter) activity;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

        holder.txtItem.setText(list.get(position).getCat());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCurrentCourseClickAdapter.onCatClickAdapter(list.get(position).getCat_id(), list.get(position).getCat());
            }
        });

    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView txtItem;
        CardView card;
        ImageView imageView;
        public DataObjectHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            txtItem = (TextView) itemView.findViewById(R.id.txtItem);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
