package clientrest.com.clientrest.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import clientrest.com.clientrest.Activity.MainActivity;
import clientrest.com.clientrest.DataBase.Entity.Request;
import clientrest.com.clientrest.Frament.BlankFragment;
import clientrest.com.clientrest.Frament.NotificationFragment_item.OnListFragmentInteractionListener;
import clientrest.com.clientrest.R;
import clientrest.com.clientrest.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {

    private final List<Request> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public NotificationRecyclerViewAdapter(List<Request> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notification, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvPedido.setText("N° Pedido:");
        holder.tvNumPedido.setText(mValues.get(position).getRequestId().toString());
        holder.tvSolicitante.setText((mValues.get(position).getConsumerId().getConsumerAttributesList().get(0).getValue()));
        holder.tvMotivo.setText(mValues.get(position).getReason());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    //  mListener.onListFragmentInteraction(holder.mItem);
                    fragmentJump(mValues.get(position));
                }

            }
        });
    }



    private void fragmentJump(Request request) {
        BlankFragment mFragment = new BlankFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt("response", request.getRequestId());
        mFragment.setArguments(mBundle);
        switchContent(R.id.content_main, mFragment);
    }

    public void switchContent(int id, Fragment fragment) {
        if (context == null)
            return;
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            mainActivity.switchContent(id, fragment, "NotificationFrament");
        }
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvPedido;
        public final TextView tvSolicitante;
        public final TextView tvMotivo;
        public DummyItem mItem;
        public final TextView tvNumPedido;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvPedido = (TextView) itemView.findViewById(R.id.tvAtributo);
            tvSolicitante = (TextView) itemView.findViewById(R.id.tvsolicitante);
            tvMotivo = (TextView) itemView.findViewById(R.id.tvMotivo);
            tvNumPedido = (TextView) itemView.findViewById(R.id.tvNumPedido);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvSolicitante.getText() + "'";
        }
    }
}
