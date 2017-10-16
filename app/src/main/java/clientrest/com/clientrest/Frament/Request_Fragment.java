package clientrest.com.clientrest.Frament;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import clientrest.com.clientrest.Activity.MainActivity;
import clientrest.com.clientrest.Adapter.Request_Items_Adapter;
import clientrest.com.clientrest.DataBase.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.ConsumerAttributes;
import clientrest.com.clientrest.DataBase.Entity.Request;
import clientrest.com.clientrest.R;
import clientrest.com.clientrest.Service.MLP;
import clientrest.com.clientrest.Service.MQTTService;
import clientrest.com.clientrest.dummy.DummyContent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Request_Fragment.OnListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Request_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Request_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int PUBLISH = 4;
    private ObjectAnimator anim;
    private View DialogView;
    private AlertDialog Dialog;
    private boolean isHistory, isInferredMechanism;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private Request request;
    private DBHelper database;
    private Button btn_Concluir;
    private CardView cvConsumidor, cvDados, cvmotivo;
    private TextView tvDescription_Solicitante, tvDescription_Motivo, tvRequesting_Number;
    private View vSeparador, vSeparadorMotivo;
    private ImageView item_img_consumer, item_img_motivo, item_img_dados;
    private RecyclerView recyclerView;
    private OnListFragmentInteractionListener mListener;
    private Request_List_Fragment.OnListFragmentInteractionListener mListener2;
    private ProgressBar mProgressBar;

    public Request_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Request_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Request_Fragment newInstance(String param1, String param2) {
        Request_Fragment fragment = new Request_Fragment();
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
            isHistory = getArguments().getBoolean("history");
            request = database.getRequest(getArguments().getInt("response"));
            if (isHistory) {
                isInferredMechanism = getArguments().getBoolean("isInferredMechanism");
            }

        }
    }

    private void instantiateComponents(View view) {
        mProgressBar = view.findViewById(R.id.circular_progress_bar);
        btn_Concluir = view.findViewById(R.id.btn_Concluir);
        tvRequesting_Number = view.findViewById(R.id.tvRequesting_Number);
        cvConsumidor = view.findViewById(R.id.cvConsumidor);
        cvDados = view.findViewById(R.id.cvDados);
        cvmotivo = view.findViewById(R.id.cvMotivo);
        tvDescription_Solicitante = view.findViewById(R.id.tvDescription_Solicitante);
        tvDescription_Motivo = view.findViewById(R.id.tvDescription_Motivo);
        vSeparador = view.findViewById(R.id.vSeparador);
        vSeparadorMotivo = view.findViewById(R.id.vSeparadorMotivo);
        item_img_consumer = view.findViewById(R.id.item_img_consumer);
        item_img_motivo = view.findViewById(R.id.item_img_motivo);
        item_img_dados = view.findViewById(R.id.item_img_dados);
        recyclerView = view.findViewById(R.id.RecyclerViewlist);

        if(isHistory){
            btn_Concluir.setText("Atualizar Resposta");
        }else{
            btn_Concluir.setText("Finalizar Solicitação");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.request_fragment, container, false);
        context = view.getContext();
        instantiateComponents(view);

        tvRequesting_Number.setText(request.getRequestId().toString());
        if (!isHistory) {
            recyclerView.setAdapter(new Request_Items_Adapter(request, view.getContext()));
        } else {
            recyclerView.setAdapter(new Request_Items_Adapter(request, view.getContext(), isInferredMechanism));
        }
        item_img_dados.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        recyclerView.setVisibility(View.VISIBLE);

        btn_Concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllAttributes()) {
                    new FinishTask().execute();
                } else {
                    Toast.makeText(context, "É necessário responder todos os dados solicitados", Toast.LENGTH_LONG).show();
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
        DBHelper database = new DBHelper(context);
        database.updateRequestStatus(request, true);
    }

    private void sendReplyConsumer() {
        DBHelper database = new DBHelper(context);
        Intent intent = new Intent(context, MQTTService.class);
        Bundle mBundle2 = new Bundle();
        mBundle2.putInt("CODE", PUBLISH);
        mBundle2.putString("reply", database.getConsumerResponse(request));
        mBundle2.putString("topic", request.getUuid());
        intent.putExtras(mBundle2);
        context.startService(intent);
    }

    private boolean checkAllAttributes() {
        boolean flag = false;
        if (!isHistory) {
            updateRequest();
            if (request.getUserDecisionId().getUserDecisionId() != 0) {
                if (request.getUserDecisionId().getUserDecisionAttributesList().size() == request.getDataId().getDataAttributesList().size()) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        }else{
            flag =true;
        }
        return flag;
    }

    private void updateRequest() {
        DBHelper database = new DBHelper(context);
        request = database.getRequest(request.getRequestId());
    }

    private String getConsumerAttributes(List<ConsumerAttributes> consumerAttributesList) {
        String string_return = "";
        for (int i = 0; i < consumerAttributesList.size(); i++) {
            string_return += consumerAttributesList.get(i).getAttribute() + ": " + consumerAttributesList.get(i).getValue() + "\n";
        }
        return string_return;
    }

    private void fragmentJumpRequestList() {
        Request_List_Fragment mFragment = new Request_List_Fragment();
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

    private void fragmentJumpHistoryList() {
        History_List_Fragment mFragment = new History_List_Fragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt("CODE", (isInferredMechanism)? 0:1);
        mFragment.setArguments(mBundle);
        switchContent(R.id.content_main, mFragment);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private class FinishTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            LayoutInflater factory = LayoutInflater.from(context);
            DialogView = factory.inflate(R.layout.finalize_task_dialog, null);
            Dialog = new AlertDialog.Builder(context).create();
            Dialog.setView(DialogView);
            Dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            MLP mlp = new MLP(context);

            if (!isHistory) {
                FinalizeRequest();
                sendReplyConsumer();
                mlp.RetrainMLP();
                fragmentJumpRequestList();
            } else {
                mlp.RetrainMLP();
                fragmentJumpHistoryList();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            Dialog.dismiss();
            Toast.makeText(context, "Finalizado com sucesso!!!", Toast.LENGTH_SHORT).show();
        }
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
