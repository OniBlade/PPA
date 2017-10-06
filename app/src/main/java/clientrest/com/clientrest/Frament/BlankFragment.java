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

import java.util.List;

import clientrest.com.clientrest.Activity.MainActivity;
import clientrest.com.clientrest.Adapter.MyDadosRecyclerViewAdapter;
import clientrest.com.clientrest.DataBase.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.ConsumerAttributes;
import clientrest.com.clientrest.DataBase.Entity.Request;
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
    private Context context;
    private Request request;
    private DBHelper database;
    private Button btn_Concluir;
    private CardView cvConsumidor, cvDados, cvmotivo;
    private TextView tvDescription_Solicitante, tvDescription_Motivo,tvRequesting_Number;
    private View vSeparador, vSeparadorMotivo;
    private ImageView item_img_consumer, item_img_motivo, item_img_dados;
    private RecyclerView recyclerView;
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
        database = new DBHelper(getContext());
        if (getArguments() != null) {
            request = database.getRequest(getArguments().getInt("response"));
        }
    }

    private void instantiateComponents(View view) {
        btn_Concluir = (Button) view.findViewById(R.id.btn_Concluir);
        tvRequesting_Number  = view.findViewById(R.id.tvRequesting_Number);
        cvConsumidor = (CardView) view.findViewById(R.id.cvConsumidor);
        cvDados = (CardView) view.findViewById(R.id.cvDados);
        cvmotivo = (CardView) view.findViewById(R.id.cvMotivo);
        tvDescription_Solicitante = (TextView) view.findViewById(R.id.tvDescription_Solicitante);
        tvDescription_Motivo = (TextView) view.findViewById(R.id.tvDescription_Motivo);
        vSeparador = (View) view.findViewById(R.id.vSeparador);
        vSeparadorMotivo = (View) view.findViewById(R.id.vSeparadorMotivo);
        item_img_consumer = (ImageView) view.findViewById(R.id.item_img_consumer);
        item_img_motivo = (ImageView) view.findViewById(R.id.item_img_motivo);
        item_img_dados = (ImageView) view.findViewById(R.id.item_img_dados);
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewlist);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_blank, container, false);
        context = view.getContext();
        instantiateComponents(view);

        tvRequesting_Number.setText(request.getRequestId().toString());
        recyclerView.setAdapter(new MyDadosRecyclerViewAdapter(request, view.getContext()));

        item_img_dados.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        recyclerView.setVisibility(View.VISIBLE);

        btn_Concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllAttributes()) {
                    FinalizeRequest();
                    Toast.makeText(context, "Finalizado com sucesso!!!", Toast.LENGTH_LONG).show();
                    fragmentJump();
                } else {
                    Toast.makeText(context, "É necesario responder todos os dados solicitados", Toast.LENGTH_LONG).show();
                }
            }
        });

        cvConsumidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvDescription_Solicitante.setText(getConsumerAttributes(request.getConsumerId().getConsumerAttributesList()));

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
                tvDescription_Motivo.setText(request.getReason());
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

    private void FinalizeRequest() {
    }

    private boolean checkAllAttributes() {
        return false;
    }

    private String getConsumerAttributes(List<ConsumerAttributes> consumerAttributesList) {
        String string_return = "";
        for (int i = 0; i < consumerAttributesList.size(); i++) {
            string_return += consumerAttributesList.get(i).getAttribute() + ": " + consumerAttributesList.get(i).getValue() + "\n";
        }
        return string_return;
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
