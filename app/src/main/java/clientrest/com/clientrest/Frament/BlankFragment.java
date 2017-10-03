package clientrest.com.clientrest.Frament;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import clientrest.com.clientrest.Activity.MainActivity;
import clientrest.com.clientrest.Adapter.MyDadosRecyclerViewAdapter;
import clientrest.com.clientrest.DatabaseDAO;
import clientrest.com.clientrest.R;
import clientrest.com.clientrest.dummy.DummyContent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;
    private JSONObject jsonObject;
    private OnListFragmentInteractionListener mListener;
    private NotificationFragment_item.OnListFragmentInteractionListener mListener2;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                jsonObject = new JSONObject(getArguments().getString("response"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_blank, container, false);
        context = view.getContext();

        Button btn_Concluir = (Button) view.findViewById(R.id.btn_Concluir);

        CardView cvConsumidor = (CardView) view.findViewById(R.id.cvConsumidor);
        CardView cvDados = (CardView) view.findViewById(R.id.cvDados);
        CardView cvmotivo = (CardView) view.findViewById(R.id.cvMotivo);

        final TextView tvDescription_Solicitante = (TextView) view.findViewById(R.id.tvDescription_Solicitante);
        final TextView tvDescription_Motivo = (TextView) view.findViewById(R.id.tvDescription_Motivo);
        final View vSeparador = (View) view.findViewById(R.id.vSeparador);
        final View vSeparadorMotivo = (View) view.findViewById(R.id.vSeparadorMotivo);
        final ImageView item_img_consumer = (ImageView) view.findViewById(R.id.item_img_consumer);
        final ImageView item_img_motivo = (ImageView) view.findViewById(R.id.item_img_motivo);
        final ImageView item_img_dados = (ImageView) view.findViewById(R.id.item_img_dados);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewlist);
        recyclerView.setAdapter(new MyDadosRecyclerViewAdapter(jsonObject, view.getContext()));

        item_img_dados.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        recyclerView.setVisibility(View.VISIBLE);

        btn_Concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VerificarConclusaoSolicitacao()) {
                    FinalizarConclusao();
                    Toast.makeText(context, "Finalizado com sucesso!!!", Toast.LENGTH_LONG).show();
                    fragmentJump();
                } else {
                    Toast.makeText(context,"É necesario responder todos os dados solicitados",Toast.LENGTH_LONG).show();
                }
            }
        });

        cvConsumidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvDescription_Solicitante.setText(getSolicitante(jsonObject));

                if (tvDescription_Solicitante.getVisibility() == View.GONE) {
                    item_img_consumer.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    tvDescription_Solicitante.setVisibility(View.VISIBLE);
                    vSeparador.setVisibility(View.VISIBLE);
                } else {
                    item_img_consumer.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);
                    tvDescription_Solicitante.setVisibility(View.GONE);
                    vSeparador.setVisibility(View.GONE);
                }
            }
        });

        cvmotivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDescription_Motivo.setText(getMotivo(jsonObject));
                if (tvDescription_Motivo.getVisibility() == View.GONE) {
                    item_img_motivo.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    tvDescription_Motivo.setVisibility(View.VISIBLE);
                    vSeparadorMotivo.setVisibility(View.VISIBLE);
                } else {
                    tvDescription_Motivo.setVisibility(View.GONE);
                    item_img_motivo.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);
                    vSeparadorMotivo.setVisibility(View.GONE);
                }
            }
        });

        cvDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerView.getVisibility() == View.GONE) {
                    item_img_dados.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    item_img_dados.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);
                }

            }
        });
        return view;
    }

    private void FinalizarConclusao() {
        DatabaseDAO data = new DatabaseDAO(context);
        data.FinalizarNotificao(false, jsonObject);
    }

    private void fragmentJump() {
        NotificationFragment_item mFragment = new NotificationFragment_item();
        Bundle mBundle = new Bundle();
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

    private boolean VerificarConclusaoSolicitacao() {
        boolean flag = false;
        DatabaseDAO dataBase = new DatabaseDAO(context);
        Document doc = dataBase.findID(context.getResources().getString(R.string.collection), getNumeroPedido(jsonObject));
        try {
            jsonObject = new JSONObject(doc.toJson());
            JSONObject objGenerico = jsonObject.getJSONObject(context.getResources().getString(R.string.objeto_decisao_usuario));
            JSONArray arrayAtributoUsuario = objGenerico.getJSONArray(context.getResources().getString(R.string.objeto_decisao_usuario_atributo));

            objGenerico = jsonObject.getJSONObject(context.getResources().getString(R.string.objeto_decisao_inferida));
            JSONArray arratAtributoInferencia = objGenerico.getJSONArray(context.getResources().getString(R.string.objeto_decisao_inferida_atributo));

            if (arrayAtributoUsuario.length() == arratAtributoInferencia.length()) {
                flag = true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flag;
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
            System.out.println(objectAtributo.getString(context.getResources().getString(R.string.objeto_field) + cont));

            try {
                while (objectAtributo.getString(context.getResources().getString(R.string.objeto_field) + cont) != null) {
                    field_atributo = objectAtributo.getString(context.getResources().getString(R.string.objeto_field) + cont);
                    field_valores = objectValores.getString(context.getResources().getString(R.string.objeto_field) + cont);
                    field_return += field_atributo + ": " + field_valores + "\n";
                    cont++;
                }
            } catch (JSONException e) {
            }

            return field_return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMotivo(JSONObject obj) {
        try {
            return "Motivo: " + obj.getString(context.getResources().getString(R.string.objeto_motivo)).toString() + "\n";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyContent.DummyItem item);
    }
}
