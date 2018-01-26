package es.iesnervion.qa.ui.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import es.iesnervion.qa.model.Answer;
import es.iesnervion.qa.R;


/**
 * Created by adripol94 on 1/28/17.
 */

public class ListAdapter extends ArrayAdapter<Answer> {

    public ListAdapter(Context context, int resource, Answer[] objects) {
        super(context, resource, objects);
    }

    public ListAdapter(Context context, int template_gaming, ArrayList<Answer> answer) {
        super(context, template_gaming, answer);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            v = inflater.inflate(R.layout.template_gaming, parent, false);

            vh = new ViewHolder(v, R.id.answer_gaming_tv);
            v.setTag(vh);
        } else {
            vh = (ViewHolder) v.getTag();
        }

        vh.getAnswer().setText(getItem(position).getAnswer());

        return v;
    }


    public static class ViewHolder {
        private TextView answer;

        public ViewHolder(View v, int idAnswer_tv) {
            answer = (TextView) v.findViewById(idAnswer_tv);
        }

        public TextView getAnswer() {
            return answer;
        }

    }
}
