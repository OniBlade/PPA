package clientrest.com.clientrest.Frament;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import clientrest.com.clientrest.Activity.MainActivity;
import clientrest.com.clientrest.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificationFrament.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationFrament#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFrament extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private JSONObject jsonObject;
    private String idPedido = "";
    private static final int ACCEPT = 1;
    private static final int DENY = 2;
    private static final int NEGOTIATE = 3;
    private String HOST = "192.168.1.102";
    private String COLLECTION = "request";
    private Context context;

    private OnFragmentInteractionListener mListener;

    public NotificationFrament() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFrament.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFrament newInstance(String param1, String param2) {
        NotificationFrament fragment = new NotificationFrament();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HOST = getInformacaoHost();
        if (getArguments() != null) {
            try {
                jsonObject = new JSONObject(getArguments().getString("response"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String getInformacaoHost() {
        final SharedPreferences sharedPreferences = context.getSharedPreferences("Conexao", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Host", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the expandable_card_row for this fragment
        final View view = inflater.inflate(R.layout.fragment_notification_frament, container, false);
        context = view.getContext();

        TextView tvId = (TextView) view.findViewById(R.id.tvPedidoId);
        TextView tvSolicitante = (TextView) view.findViewById(R.id.tvSolicitante);
        TextView tvSolicitanteItens = (TextView) view.findViewById(R.id.tvSolicitanteItens);
        TextView tvDados = (TextView) view.findViewById(R.id.tvdados);
        TextView tvDadosItens = (TextView) view.findViewById(R.id.tvDadosItens);
        TextView tvMotivo = (TextView) view.findViewById(R.id.tvMotivo);
        TextView tvMotivoItens = (TextView) view.findViewById(R.id.tvMotivoItens);
        TextView tvInferencia = (TextView) view.findViewById(R.id.tvInferencia);

        Button btnAceitar = (Button) view.findViewById(R.id.btnAceitar);
        Button btnNegociar = (Button) view.findViewById(R.id.btnNegociar);
        Button btnNegar = (Button) view.findViewById(R.id.btnNegar);

        try {
            idPedido = getNumeroPedido(jsonObject);
            tvId.setText("Pedido: " + idPedido);

            JSONObject objGenerico = new JSONObject();
            tvSolicitanteItens.setText(getSolicitante(jsonObject));

            objGenerico = jsonObject.getJSONObject("Dados");
            JSONArray array = objGenerico.getJSONArray("Tipo");
            String dados = "";
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                dados += "atributo: " + object.get("id").toString();
                dados += "\n";
            }
            tvDadosItens.setText(dados);

            objGenerico = jsonObject.getJSONObject("Porque");
            tvMotivoItens.setText(objGenerico.getString("Motivo"));

            objGenerico = jsonObject.getJSONObject("InferredDecision");
            array = objGenerico.getJSONArray("Atributo");
            JSONObject object = array.getJSONObject(0);
            tvInferencia.setText("Inferência Realizada: " + object.get("NivelConfiança").toString());


        } catch (JSONException e) {
            e.fillInStackTrace();
        }


      /*  btnAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseDAO dataBase = new DatabaseDAO(context);
                Document requestOpen = dataBase.findID( COLLECTION, idPedido);
                dataBase.SaveDataAndAnswer( COLLECTION, requestOpen, ACCEPT, idPedido);
                fragmentJump();

            }
        });

        btnNegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseDAO dataBase = new DatabaseDAO(context);
                Document requestOpen = dataBase.findID( COLLECTION, idPedido);
                dataBase.SaveDataAndAnswer( COLLECTION, requestOpen, DENY, idPedido);

                fragmentJump();

            }
        });

        btnNegociar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseDAO dataBase = new DatabaseDAO(context);
                Document requestOpen = dataBase.findID( COLLECTION, idPedido);
                dataBase.SaveDataAndAnswer( COLLECTION, requestOpen, NEGOTIATE, idPedido);
                fragmentJump();

            }
        });*/

        return view;
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

    private String getSolicitante(JSONObject jsonObject) {
        try {
            String field_atributo, field_valores, field_return = "";
            int cont = 0;

            JSONObject objectAtributo = new JSONObject(jsonObject.getJSONObject(context.getResources().getString(R.string.objeto_solicitante)).toString());
            objectAtributo = objectAtributo.getJSONObject(context.getResources().getString(R.string.objeto_solicitante_atributo));

            JSONObject objectValores = new JSONObject(jsonObject.getJSONObject(context.getResources().getString(R.string.objeto_solicitante)).toString());
            objectValores = objectValores.getJSONObject(context.getResources().getString(R.string.objeto_solicitante_valores));

            while (objectAtributo.getString(context.getResources().getString(R.string.objeto_field) + cont) != null) {
                field_atributo = objectAtributo.getString(context.getResources().getString(R.string.objeto_field) + cont);
                field_valores = objectValores.getString(context.getResources().getString(R.string.objeto_field) + cont);
                field_return += field_atributo + ": " + field_valores + "\n";
            }

            return field_return;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void fragmentJump() {
        NotificationFragment_item mFragment = new NotificationFragment_item();
        Bundle mBundle = new Bundle();
        mBundle.putInt("column-count", 0);
        mFragment.setArguments(mBundle);
        switchContent(R.id.content_main, mFragment);
    }

    public void switchContent(int id, Fragment fragment) {
        if (context == null)
            return;
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            mainActivity.switchContent(id, fragment, "NotificationFragment_item");
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
