package es.iesnervion.qa.ui.Adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import es.iesnervion.qa.model.Answer;
import es.iesnervion.qa.R;

import static es.iesnervion.qa.R.layout.template_gaming;

/**
 * Created by adripol94 on 1/28/17.
 */

public class GamingAdapter extends RecyclerView.Adapter<GamingAdapter.ViewHolder> {
    private Answer[] answers;

    public GamingAdapter(Answer[] answers) {
        this.answers = answers;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * . Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public GamingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Creating view
        View v = LayoutInflater.from(parent.getContext()).inflate(template_gaming, parent, false);

        ViewHolder vh = new ViewHolder(v, R.id.answer_gaming_tv);

        return vh;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override  instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(GamingAdapter.ViewHolder holder, int position) {
        holder.getAnswerGaming().setText(this.answers[position].getAnswer());
        //FIXME Replae when fix bug #5 // STOPSHIP: 1/28/17 see CategoryAdapter
        holder.getAnswerGaming().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace for action", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return answers.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView answerGaming;

        public ViewHolder(View itemView, int idAnswerGaming) {
            super(itemView);
            answerGaming = (TextView)itemView.findViewById(idAnswerGaming);
        }

        public TextView getAnswerGaming() {
            return answerGaming;
        }
    }
}
