package clientrest.com.clientrest.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import clientrest.com.clientrest.DataBase.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.InferredDecisionAttributes;
import clientrest.com.clientrest.DataBase.Entity.Request;
import clientrest.com.clientrest.R;

public class MyDadosRecyclerViewAdapter extends RecyclerView.Adapter<MyDadosRecyclerViewAdapter.DataObjectHolder> {
    private Context context;
    private Request request;
    boolean flag_ContemInformacao;
    private EditText edtInformacaoResp;
    private List<InferredDecisionAttributes> mDataset;
    private static MyClickListener myClickListener;
    private TextView tvAtributoResp, tvRespostaResp, tvNivelResp, tvInserirResp;
    private static final String ALLOW = "allow";
    private static final String DENY = "deny";
    private static final String NEGOTIATE = "negotiate";


    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvAtributo;
        TextView tvResposta;
        TextView tvNivel;
        LinearLayout lnBackColor;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tvAtributo = (TextView) itemView.findViewById(R.id.tvAtributo);
            tvResposta = (TextView) itemView.findViewById(R.id.tvResposta);
            tvNivel = (TextView) itemView.findViewById(R.id.tvNivel);
            lnBackColor = (LinearLayout) itemView.findViewById(R.id.lnBackColor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public MyDadosRecyclerViewAdapter(Request request, Context context) {
        this.context = context;
        this.request = request;
        mDataset = request.getInferredDecisionId().getInferredDecisionAttributesList();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dados, parent, false);
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
            holder.tvResposta.setText("Decisão Mecanismo: " + IntToStringDecision(mDataset.get(position).getDataAttributes().getInferred()));
            holder.tvNivel.setText("Nivel Confiança: " + mDataset.get(position).getTrustLevel().toString() + " %");
        } else {
            holder.tvResposta.setText("A informação solicitada não contêm na sua base de dados.");
            holder.tvNivel.setText("Nível Confiança: Inexistente");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(view.getContext());
                final View DialogView = factory.inflate(R.layout.fragment_dados_resposta, null);
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
                                dabase.saveUserDecision(request, mDataset.get(position).getDataAttributes(), context.getResources().getInteger(R.integer.ACCEPT), "");
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

        flag_ContemInformacao = (mDataset.get(position).getTrustLevel() <= 0) ? false : true;
        tvAtributoResp.setText("Atributo: " + mDataset.get(position).getDataAttributes().getAttribute());
        if (flag_ContemInformacao) {
            tvRespostaResp.setVisibility(View.VISIBLE);
            tvNivelResp.setVisibility(View.VISIBLE);

            edtInformacaoResp.setVisibility(View.GONE);
            tvInserirResp.setVisibility(View.GONE);

            tvRespostaResp.setText("Decisão Mecanismo: " + IntToStringDecision(mDataset.get(position).getDataAttributes().getInferred()));
            tvNivelResp.setText("Nivel Confiança: " + mDataset.get(position).getTrustLevel().toString() + " %");

        } else {
            tvRespostaResp.setVisibility(View.GONE);
            tvNivelResp.setVisibility(View.GONE);

            tvInserirResp.setText("A informação solicitada não contêm na sua base de dados \nInsira esta informação:");
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