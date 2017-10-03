package clientrest.com.clientrest.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import clientrest.com.clientrest.Activity.MainActivity;
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

    private final List<Document> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public NotificationRecyclerViewAdapter(List<Document> items, OnListFragmentInteractionListener listener) {
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
        JSONObject jsonObject = null;
        try {
            holder.tvPedido.setText("N° Pedido:");
            jsonObject = new JSONObject(mValues.get(position).toJson());
            holder.tvNumPedido.setText(getNumeroPedido(jsonObject));
            holder.tvSolicitante.setText(getSolicitante(jsonObject,0));
            holder.tvMotivo.setText(getMotivo(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                    try {
                        JSONObject obj = new JSONObject(mValues.get(position).toJson());
                        fragmentJump(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    private String getNumeroPedido(JSONObject jsonObject) {
        try {
            JSONObject idObj = jsonObject.getJSONObject("_id");
            return (String) idObj.get("$oid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getSolicitante(JSONObject jsonObject, int cont) {
        try {
            String field_atributo , field_valores = "";
            JSONObject object = new JSONObject(jsonObject.getJSONObject(context.getResources().getString(R.string.objeto_solicitante)).toString());
            object = object.getJSONObject(context.getResources().getString(R.string.objeto_solicitante_atributo));
            field_atributo = object.getString(context.getResources().getString(R.string.objeto_field)+cont);

            object = new JSONObject(jsonObject.getJSONObject(context.getResources().getString(R.string.objeto_solicitante)).toString());
            object = object.getJSONObject(context.getResources().getString(R.string.objeto_solicitante_valores));
            field_valores = object.getString(context.getResources().getString(R.string.objeto_field)+cont);

            return "Solicitante: "+field_valores;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMotivo(JSONObject obj) {
        try {
            return "Motivo: " + obj.getString(context.getResources().getString(R.string.objeto_motivo)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void fragmentJump(JSONObject obj) {
        BlankFragment mFragment = new BlankFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("response", obj.toString());
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
