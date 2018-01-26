package es.iesnervion.qa.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import es.iesnervion.qa.model.Category;
import es.iesnervion.qa.R;
import es.iesnervion.qa.ui.View.GamingActivity;

/**
 * Created by adripol94 on 1/26/17.
 */

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private List<Category> categories;
    private Context c;
    private ViewHolder vh;

    public CategoriaAdapter(List<Category> categories, Context c) {
        this.categories = categories;
        this.c = c;
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
     * {@link (ViewHolder, int, List)}. Since it will be re-used to display
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
    public CategoriaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_categories, parent, false);

        ViewHolder vh = new ViewHolder(v, R.id.template_categoria_categoria_txt,
                R.id.template_categoria_categoria_cv);
        this.vh = vh;
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
     * Override {@link (ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(CategoriaAdapter.ViewHolder holder, final int position) {
        holder.getTv().setText(categories.get(position).getName());

        holder.getCv().setBackground(new BitmapDrawable(c.getResources(), categories.get(position).getImgBitMap()));

        holder.getCv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(c, GamingActivity.class);
                it.putExtra(Category.CATEGORY_KEY, categories.get(position));
                c.startActivity(it);
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
        return categories.size();
    }

    /**
     * Clase ViewHolder.
     * Extiende de {@link RecyclerView.ViewHolder} y implementa {@link View.OnClickListener}
     */
    public class ViewHolder extends RecyclerView.ViewHolder {  //implements View.OnClickListener{
        private TextView tv;
        private CardView cv;

        //FIXME Implementation {@link OnClickListener} error not working!

        /**
         * ViewHolder responsable del {@see RecyclerView} de {@link R.layout#template_categories}.
         * @param itemView Vista
         * @param idCategoriaTxt id del {@link android.widget.TextView}
         * @param idCategoriaCardView id del {@link android.support.v7.widget.CardView}
         */
        public ViewHolder(View itemView, int idCategoriaTxt, int idCategoriaCardView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(idCategoriaTxt);
            cv = (CardView)itemView.findViewById(idCategoriaCardView);
            //itemView.setOnClickListener(this);
        }

        public TextView getTv() {
            return tv;
        }

        public CardView getCv() {
            return cv;
        }


        /*@Override
        public void onClick(View view) {
            Snackbar.make(view, "Replace for StartActivity", Snackbar.LENGTH_LONG).show();
        }
        */
    }
}
