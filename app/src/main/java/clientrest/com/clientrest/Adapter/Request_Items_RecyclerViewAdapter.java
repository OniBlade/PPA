package clientrest.com.clientrest.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import clientrest.com.clientrest.DataBase.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.InferredDecisionAttributes;
import clientrest.com.clientrest.DataBase.Entity.Request;
import clientrest.com.clientrest.R;

public class Request_Items_RecyclerViewAdapter extends RecyclerView.Adapter<Request_Items_RecyclerViewAdapter.DataObjectHolder> {
    private Context context;
    private Request request;
    boolean flag_ContemInformacao;
    private EditText edtInformacaoResp;
    private List<InferredDecisionAttributes> mDataset;
    private static MyClickListener myClickListener;
    private TextView tvAtributoResp, tvRespostaResp, tvNivelResp, tvInserirResp;
    private TextView tvLocation, tvRetention, tvShared, tvInferred;
    private LinearLayout linearResp, linearNivel;
    private View viewRes, viewNivel;
    private static final String ALLOW = "Permitir";
    private static final String DENY = "Negar";
    private static final String NEGOTIATE = "Negociar";

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvAtributo;
        TextView tvResposta;
        TextView tvNivel;
        LinearLayout lnBackColor;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tvAtributo = itemView.findViewById(R.id.tvAtributo);
            tvResposta = itemView.findViewById(R.id.tvResposta);
            tvNivel = itemView.findViewById(R.id.tvNivel);
            lnBackColor = itemView.findViewById(R.id.lnBackColor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public Request_Items_RecyclerViewAdapter(Request request, Context context) {
        this.context = context;
        this.request = request;
        mDataset = request.getInferredDecisionId().getInferredDecisionAttributesList();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_attributes_fragment, parent, false);
        context = view.getContext();
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        flag_ContemInformacao = true;
        RepaintItemView(holder, position);
        holder.tvAtributo.setText("Atributo: " + mDataset.get(position).getDataAttributes().getAttribute());
        flag_ContemInformacao = (mDataset.get(position).getTrustLevel() <= 0) ? false : true;

        if (flag_ContemInformacao) {
            holder.tvResposta.setText("Decisão Mecanismo: " + IntToStringDecision(mDataset.get(position).getState()));
            holder.tvNivel.setText("Nivel Confiança: " + mDataset.get(position).getTrustLevel().toString() + " %");
        } else {
            holder.tvResposta.setText("A informação solicitada não contêm na sua base de dados.");
            holder.tvNivel.setText("Nível Confiança: Inexistente");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(view.getContext());
                final View DialogView = factory.inflate(R.layout.data_attribute_response_dialog, null);
                final AlertDialog Dialog = new AlertDialog.Builder(view.getContext()).create();
                Dialog.setView(DialogView);

                addDialogInformation(DialogView, position);

                DialogView.findViewById(R.id.btnAutorizar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHelper dabase = new DBHelper(context);
                        if (flag_ContemInformacao) {
                            dabase.saveUserDecision(request, mDataset.get(position).getDataAttributes(), context.getResources().getInteger(R.integer.ACCEPT), "");
                            holder.lnBackColor.setBackgroundColor(Color.GREEN);
                            holder.itemView.refreshDrawableState();
                            Dialog.dismiss();
                        } else {
                            if (!edtInformacaoResp.getText().toString().trim().equals("")) {
                                dabase.saveUserDecision(request, mDataset.get(position).getDataAttributes(), context.getResources().getInteger(R.integer.ACCEPT), edtInformacaoResp.getText().toString());
                                holder.lnBackColor.setBackgroundColor(Color.GREEN);
                                holder.itemView.refreshDrawableState();
                                Dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Informação não pode ser em branco!!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                DialogView.findViewById(R.id.btnNegociar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHelper dabase = new DBHelper(context);
                        if (flag_ContemInformacao) {
                            dabase.saveUserDecision(request, mDataset.get(position).getDataAttributes(), context.getResources().getInteger(R.integer.NEGOTIATE), "");
                            holder.lnBackColor.setBackgroundColor(Color.YELLOW);
                            holder.itemView.refreshDrawableState();
                            Dialog.dismiss();
                        } else {
                            if (!edtInformacaoResp.getText().toString().trim().equals("")) {
                                dabase.saveUserDecision(request, mDataset.get(position).getDataAttributes(), context.getResources().getInteger(R.integer.NEGOTIATE), edtInformacaoResp.getText().toString());
                                holder.lnBackColor.setBackgroundColor(Color.YELLOW);
                                holder.itemView.refreshDrawableState();
                                Dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Informação não pode ser em branco!!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                DialogView.findViewById(R.id.btnNegar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHelper dabase = new DBHelper(context);
                        dabase.saveUserDecision(request, mDataset.get(position).getDataAttributes(), context.getResources().getInteger(R.integer.DENY), edtInformacaoResp.getText().toString());
                        holder.lnBackColor.setBackgroundColor(Color.RED);
                        holder.itemView.refreshDrawableState();
                        Dialog.dismiss();

                    }
                });
                Dialog.show();
            }
        });

    }

    private void updateRequest() {
        DBHelper database = new DBHelper(context);
        request = database.getRequest(request.getRequestId());
    }

    private String IntToStringDecision(int code) {
        Log.i("TAG", "code:" + code);
        if (code == context.getResources().getInteger(R.integer.ACCEPT)) {
            return ALLOW;
        } else {
            if (code == context.getResources().getInteger(R.integer.DENY)) {
                return DENY;
            } else {
                if (code == context.getResources().getInteger(R.integer.NEGOTIATE)) {
                    return NEGOTIATE;
                } else {
                    return null;
                }
            }
        }
    }

    private void RepaintItemView(DataObjectHolder holder, int posicao) {
        try {
            int codigo = request.getUserDecisionId().getUserDecisionAttributesList().get(posicao).getState();
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


    private void addDialogInformation(View DialogView, int position) {
        updateRequest();
        tvAtributoResp = DialogView.findViewById(R.id.tvAtributoResp);
        tvRespostaResp = DialogView.findViewById(R.id.tvRespostaResp);
        tvNivelResp = DialogView.findViewById(R.id.tvNivelResp);
        tvInserirResp = DialogView.findViewById(R.id.tvInserirResp);
        edtInformacaoResp = DialogView.findViewById(R.id.edtInformacaoResp);
        tvLocation = DialogView.findViewById(R.id.tvLocation);
        tvRetention = DialogView.findViewById(R.id.tvRetention);
        tvShared = DialogView.findViewById(R.id.tvShared);
        tvInferred = DialogView.findViewById(R.id.tvInferred);
        linearNivel = DialogView.findViewById(R.id.linearNivel);
        linearResp = DialogView.findViewById(R.id.linearResp);
        viewRes = DialogView.findViewById(R.id.viewResp);
        viewNivel = DialogView.findViewById(R.id.viewNivel);

        flag_ContemInformacao = (mDataset.get(position).getTrustLevel() <= 0) ? false : true;
        tvAtributoResp.setText(mDataset.get(position).getDataAttributes().getAttribute());
        String aux = (mDataset.get(position).getDataAttributes().getShared().equals(1)) ? "Sim" : "Não";
        tvShared.setText(aux);
        tvLocation.setText(request.getLocation());
        aux = (mDataset.get(position).getDataAttributes().getInferred().equals(1)) ? "Sim" : "Não";
        tvInferred.setText(aux);
        tvRetention.setText(mDataset.get(position).getDataAttributes().getRetention());

        if (flag_ContemInformacao) {
            linearResp.setVisibility(View.VISIBLE);
            linearNivel.setVisibility(View.VISIBLE);
            viewRes.setVisibility(View.VISIBLE);
            viewNivel.setVisibility(View.VISIBLE);

            edtInformacaoResp.setVisibility(View.GONE);
            tvInserirResp.setVisibility(View.GONE);

            tvRespostaResp.setText(IntToStringDecision(mDataset.get(position).getState()));
            tvNivelResp.setText(mDataset.get(position).getTrustLevel().toString() + " %");

        } else {
            linearResp.setVisibility(View.GONE);
            linearNivel.setVisibility(View.GONE);
            viewRes.setVisibility(View.GONE);
            viewNivel.setVisibility(View.GONE);

            tvInserirResp.setText("A informação solicitada não contêm na sua base de dados. \nInsira esta informação:");
            edtInformacaoResp.setVisibility(View.VISIBLE);
            tvInserirResp.setVisibility(View.VISIBLE);
            if (request.getUserDecisionId().getUserDecisionId() != 0) {
                for (int i = 0; i < request.getUserDecisionId().getUserDecisionAttributesList().size(); i++) {
                    if (request.getUserDecisionId().getUserDecisionAttributesList().get(i).getDataAtttributeId().getDataAttributesId() == mDataset.get(position).getDataAttributes().getDataAttributesId()) {

                        if (!request.getUserDecisionId().getUserDecisionAttributesList().get(i).getInformation().isEmpty()) {
                            edtInformacaoResp.setText(request.getUserDecisionId().getUserDecisionAttributesList().get(i).getInformation());
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}