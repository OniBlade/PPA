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

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import clientrest.com.clientrest.DatabaseDAO;
import clientrest.com.clientrest.R;

public class MyDadosRecyclerViewAdapter extends RecyclerView
        .Adapter<MyDadosRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<Document> mDataset;
    Context context;
    private static MyClickListener myClickListener;
    boolean flag_ContemInformacao;
    TextView tvAtributoResp, tvRespostaResp, tvNivelResp, tvInserirResp;
    JSONObject jsonObject, jsonOriginal;
    Document document;
    EditText edtInformacaoResp;

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

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyDadosRecyclerViewAdapter(JSONObject myDataset, Context contexto) {
        jsonOriginal = myDataset;
        mDataset = getListAtributos(myDataset, contexto);
    }

    private List<Document> getListAtributos(JSONObject myDataset, Context context) {
        List<Document> results = new ArrayList<>();
        Document doc = null;
        try {
            JSONObject objGenerico = myDataset.getJSONObject(context.getResources().getString(R.string.objeto_decisao_inferida));
            JSONArray array = objGenerico.getJSONArray(context.getResources().getString(R.string.objeto_decisao_inferida_atributo));
            for (int i = 0; i < array.length(); i++) {
                results.add(doc.parse(array.get(i).toString()));
            }
            return results;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dados, parent, false);
        context = view.getContext();
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        final int pos = position;
        flag_ContemInformacao = true;

        try {
            jsonObject = new JSONObject(mDataset.get(position).toJson());
            RepaintItemView(holder, position);
            holder.tvAtributo.setText(getDadoSolicitado(jsonObject, position));
            flag_ContemInformacao = getContemDado(jsonObject);

            if (flag_ContemInformacao) {
                holder.tvResposta.setText(getResposta(jsonObject));
                holder.tvNivel.setText(getNivel(jsonObject));
            } else {
                holder.tvResposta.setText("A informação solicitada não contêm na sua base de dados.");
                holder.tvNivel.setText("Nível Confiança: Inexistente");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(view.getContext());
                final View DialogView = factory.inflate(R.layout.fragment_dados_resposta, null);
                final AlertDialog Dialog = new AlertDialog.Builder(view.getContext()).create();
                Dialog.setView(DialogView);
                try {
                    jsonObject = new JSONObject(mDataset.get(pos).toJson());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adicionarInformaçõesDialog(DialogView, pos);

                DialogView.findViewById(R.id.btnAutorizar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!edtInformacaoResp.getText().toString().trim().equals("")) {
                            SalvarRespostaAtributo(jsonObject, pos, context.getResources().getInteger(R.integer.ACCEPT), edtInformacaoResp.getText().toString());
                            holder.lnBackColor.setBackgroundColor(Color.GREEN);
                            holder.itemView.refreshDrawableState();
                            Dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Informação não pode ser em branco!!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                DialogView.findViewById(R.id.btnNegociar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!edtInformacaoResp.getText().toString().trim().equals("")) {
                            SalvarRespostaAtributo(jsonObject, pos, context.getResources().getInteger(R.integer.NEGOTIATE), edtInformacaoResp.getText().toString());
                            holder.lnBackColor.setBackgroundColor(Color.YELLOW);
                            holder.itemView.refreshDrawableState();
                            Dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Informação não pode ser em branco!!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                DialogView.findViewById(R.id.btnNegar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!edtInformacaoResp.getText().toString().trim().equals("")) {
                            SalvarRespostaAtributo(jsonObject, pos, context.getResources().getInteger(R.integer.DENY), edtInformacaoResp.getText().toString());
                            holder.lnBackColor.setBackgroundColor(Color.RED);
                            holder.itemView.refreshDrawableState();
                            Dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Informação não pode ser em branco!!!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                Dialog.show();
            }
        });

    }

    private void RepaintItemView(DataObjectHolder holder, int posicao) {
        JSONObject objGenerico = null;
        try {
            if (!jsonOriginal.isNull(context.getResources().getString(R.string.objeto_decisao_usuario))) {
                objGenerico = jsonOriginal.getJSONObject(context.getResources().getString(R.string.objeto_decisao_usuario));
                JSONArray aux = objGenerico.getJSONArray(context.getResources().getString(R.string.objeto_decisao_usuario_atributo));

                int codigo = aux.getJSONObject(posicao).getInt(context.getResources().getString(R.string.objeto_decisao_usuario_atributo_codigo));
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void SalvarRespostaAtributo(JSONObject obj, int pos, int decisao, String atributo) {
        DatabaseDAO dataBase = new DatabaseDAO(context);
        dataBase.SalvarDecisaoAtributo(jsonOriginal, obj, pos, decisao, atributo);
    }

    private void adicionarInformaçõesDialog(View DialogView, int position) {

        tvAtributoResp = (TextView) DialogView.findViewById(R.id.tvAtributoResp);
        tvRespostaResp = (TextView) DialogView.findViewById(R.id.tvRespostaResp);
        tvNivelResp = (TextView) DialogView.findViewById(R.id.tvNivelResp);
        tvInserirResp = (TextView) DialogView.findViewById(R.id.tvInserirResp);
        edtInformacaoResp = (EditText) DialogView.findViewById(R.id.edtInformacaoResp);

        flag_ContemInformacao = getContemDado(jsonObject);
        tvAtributoResp.setText(getDadoSolicitado(jsonObject, position));
        if (flag_ContemInformacao) {
            tvRespostaResp.setVisibility(View.VISIBLE);
            tvNivelResp.setVisibility(View.VISIBLE);

            edtInformacaoResp.setVisibility(View.GONE);
            tvInserirResp.setVisibility(View.GONE);

            tvRespostaResp.setText(getResposta(jsonObject));
            tvNivelResp.setText(getNivel(jsonObject));
        } else {
            tvRespostaResp.setVisibility(View.GONE);
            tvNivelResp.setVisibility(View.GONE);

            tvInserirResp.setText("A informação solicitada não contêm na sua base de dados \nInsira esta informação:");
            edtInformacaoResp.setVisibility(View.VISIBLE);
            tvInserirResp.setVisibility(View.VISIBLE);
        }
    }

    private String getDadoSolicitado(JSONObject obj, int position) {
        try {
            return "Atributo: " + obj.getString(context.getResources().getString(R.string.objeto_field) + position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean getContemDado(JSONObject obj) {
        try {
            return obj.getBoolean(context.getResources().getString(R.string.objeto_decisao_inferida_atributo_contem));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getNivel(JSONObject obj) {
        try {
            return "Nível Confiança: " + obj.getString(context.getResources().getString(R.string.objeto_decisao_inferida_atributo_nivelconfianca)) + "%";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getResposta(JSONObject obj) {
        try {

            return "Decisão Mecanismo: " + ToStringCodDecision(obj.getInt(context.getResources().getString(R.string.objeto_decisao_inferida_atributo_codigo)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String ToStringCodDecision(int decisao) {
        if (context.getResources().getInteger(R.integer.ACCEPT) == decisao) {
            return context.getResources().getString(R.string.ACCEPT);
        } else {
            if (context.getResources().getInteger(R.integer.DENY) == decisao) {
                return context.getResources().getString(R.string.DENY);
            } else {
                if (context.getResources().getInteger(R.integer.NEGOTIATE) == decisao) {
                    return context.getResources().getString(R.string.NEGOTIATE);
                } else {
                    return null;
                }
            }
        }
    }

    public void addItem(Document dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}