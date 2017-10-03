package clientrest.com.clientrest.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import clientrest.com.clientrest.R;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<JSONObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView tvPedido;
        TextView tvSolicitante;
        TextView tvMotivo;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tvPedido = (TextView) itemView.findViewById(R.id.tvAtributo);
            tvSolicitante = (TextView) itemView.findViewById(R.id.tvsolicitante);
            tvMotivo = (TextView) itemView.findViewById(R.id.tvMotivo);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<JSONObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        try {
            holder.tvPedido.setText("Pedido");
            holder.tvSolicitante.setText(getSolicitante(mDataset.get(position).getJSONObject("Solicitante")));
            holder.tvMotivo.setText(getMotivo(mDataset.get(position).getJSONObject("Porque")));

          /*  final boolean isExpanded = position== 1;
            holder.details.setVisibility(isExpanded?View.VISIBLE:View.GONE);
            holder.itemView.setActivated(isExpanded);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandedPosition = isExpanded ? -1:position;
                    TransitionManager.beginDelayedTransition(recyclerView);
                    notifyDataSetChanged();
                }
            });
*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private String getSolicitante(JSONObject obj) {
        try {
            return "Solicitante: "+obj.getString("nome");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMotivo(JSONObject obj) {
        try {
            return "Motivo: "+obj.getString("Motivo").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addItem(JSONObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}