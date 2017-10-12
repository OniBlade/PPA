package clientrest.com.clientrest.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import clientrest.com.clientrest.DataBase.Entity.HistoryObject;
import clientrest.com.clientrest.Frament.History_List_Fragment.OnListFragmentInteractionListener;

import java.util.List;

import clientrest.com.clientrest.R;

/**
 * {@link RecyclerView.Adapter} that can display a {@link HistoryObject} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class History_Items_Adapter extends RecyclerView.Adapter<History_Items_Adapter.ViewHolder> {

    private Context context;
    private boolean isInferredMechanism;
    private final List<HistoryObject> mValues;
    private final OnListFragmentInteractionListener mListener;

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvConsumer;
        public final TextView tvReason;
        public final TextView tvAttribute;
        public final TextView tvTrustLevel;
        public LinearLayout lnBackColor;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvConsumer = view.findViewById(R.id.tvConsumer);
            tvReason = view.findViewById(R.id.tvReason);
            tvAttribute = view.findViewById(R.id.tvAttribute);
            tvTrustLevel = view.findViewById(R.id.tvTrustLevel);
            lnBackColor = itemView.findViewById(R.id.lnBackColor);
        }
    }

    public History_Items_Adapter(List<HistoryObject> items, OnListFragmentInteractionListener listener, Context context, int CODE) {
        this.context = context;
        mValues = items;
        mListener = listener;
        isInferredMechanism = (CODE==0)? true:false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RepaintItemView(holder, position);
        holder.tvConsumer.setText("Solicitante: " + mValues.get(position).getConsumer_attribute() + " " + mValues.get(position).getConsumer_value());
        holder.tvReason.setText("Motivo: " + mValues.get(position).getRequest_reason());
        holder.tvAttribute.setText("Atributo: " + mValues.get(position).getData_attributes_attribute());
        if(isInferredMechanism) {
            holder.tvTrustLevel.setVisibility(View.VISIBLE);
            holder.tvTrustLevel.setText("Nível Confiança: " + mValues.get(position).getInferred_decision_attributes_trust_level().toString() + " %");
        }else{
            holder.tvTrustLevel.setVisibility(View.GONE);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    /// mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    private void RepaintItemView(ViewHolder holder, int posicao) {
        try {
            int codigo = mValues.get(posicao).getInferred_decision_attributes_state();
            if (codigo == context.getResources().getInteger(R.integer.ACCEPT)) {
                holder.lnBackColor.setBackgroundColor(Color.GREEN);
                holder.itemView.refreshDrawableState();
            } else {
                if (codigo == context.getResources().getInteger(R.integer.NEGOTIATE)) {
                    holder.lnBackColor.setBackgroundColor(Color.YELLOW);
                    holder.itemView.refreshDrawableState();
                } else {
                    if (codigo == context.getResources().getInteger(R.integer.DENY)) {
                        holder.lnBackColor.setBackgroundColor(Color.RED);
                        holder.itemView.refreshDrawableState();
                    }
                }
            }
        } catch (IndexOutOfBoundsException c) {
        }
    }


}
