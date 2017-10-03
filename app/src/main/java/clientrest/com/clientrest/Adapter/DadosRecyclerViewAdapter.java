package clientrest.com.clientrest.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import clientrest.com.clientrest.Activity.MainActivity;
import clientrest.com.clientrest.Frament.BlankFragment;
import clientrest.com.clientrest.Frament.NotificationFrament;
import clientrest.com.clientrest.R;
import clientrest.com.clientrest.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link BlankFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class DadosRecyclerViewAdapter extends RecyclerView.Adapter<DadosRecyclerViewAdapter.ViewHolder> {

    private final List<Document> mValues;
    private final BlankFragment.OnListFragmentInteractionListener mListener;
    private Context context;
    private static final int ACCEPT = 1;
    private static final int DENY = 2;
    private static final int NEGOTIATE = 3;

    public DadosRecyclerViewAdapter(List<Document> items, BlankFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        System.out.println("Estou no DadosRecyclerViewAdapter");
        System.out.println(items.get(0).toJson());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_dados, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        JSONObject obj = null;
        try {
           // holder.tvPedido.setText("Pedido");
            obj = new JSONObject(mValues.get(position).toJson());
            holder.tvAtributo.setText(getDadoSolicitado(obj));
            holder.tvResposta.setText(getResposta(obj));
            holder.tvNivel.setText(getNivel(obj));
           // holder.tvMotivo.setText(getMotivo(obj.getJSONObject("Porque")));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                  /*  Parcelable a = new Parcelable() {
                        @Override
                        public int describeContents() {
                            return 0;
                        }

                        @Override
                        public void writeToParcel(Parcel parcel, int i) {

                        }
                    };
                    try {
                        JSONObject obj = new JSONObject(mValues.get(position).toJson());
                        fragmentJump(a, obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                }

            }
        });
    }


    private String getNivel(JSONObject obj) {
        try {
            return "Nível Confiança: "+obj.getString("NivelConfiança")+"%";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getResposta(JSONObject obj) {
        try {
            return "Resposta Mecanismo: "+codeToString( obj.getInt("Code"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String codeToString(int code) {
        if(code == ACCEPT){
            return "Autorizar";
        }else{
            if (code==DENY){
                return "Negar";
            }else{
                return "Negociar";
            }

        }
    }

    private String getDadoSolicitado(JSONObject obj) {
        try {
            return "Tributo: "+obj.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }




    private void fragmentJump(Parcelable mItemSelected, JSONObject obj) {
        NotificationFrament mFragment = new NotificationFrament();
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
        public final TextView tvAtributo;
        public final TextView tvResposta;
        public final TextView tvNivel;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvAtributo = (TextView) itemView.findViewById(R.id.tvAtributo);
            tvResposta = (TextView) itemView.findViewById(R.id.tvResposta);
            tvNivel = (TextView) itemView.findViewById(R.id.tvNivel);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvAtributo.getText() + "'";
        }
    }
}
