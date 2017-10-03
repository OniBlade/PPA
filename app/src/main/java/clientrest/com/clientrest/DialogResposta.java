package clientrest.com.clientrest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fagner Roger on 18/09/2017.
 */

public class DialogResposta extends AlertDialog implements
        android.view.View.OnClickListener {

    JSONObject jsonObject;
    Document document;
    Context context;
    public Button btnAutorizar, btnNegar, bntNegociar;
    TextView tvAtributoResp, tvRespostaResp, tvNivelResp, tvInserirResp;
    EditText edtInformacaoResp;
     private int resultado;
    private static final int ACCEPT = 1;
    private static final int DENY = 2;
    private static final int NEGOTIATE = 3;

    public DialogResposta(Context c, Document object) {
        super(c);
        // TODO Auto-generated constructor stub
        this.context = c;
        this.document = object;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_dados_resposta);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        tvAtributoResp = (TextView) findViewById(R.id.tvAtributoResp);
        tvRespostaResp = (TextView) findViewById(R.id.tvRespostaResp);
        tvNivelResp = (TextView) findViewById(R.id.tvNivelResp);
        tvInserirResp = (TextView) findViewById(R.id.tvInserirResp);
        edtInformacaoResp = (EditText) findViewById(R.id.edtInformacaoResp);

        setInformacoes();

        btnAutorizar = (Button) findViewById(R.id.btnAutorizar);
        btnNegar = (Button) findViewById(R.id.btnNegar);
        bntNegociar = (Button) findViewById(R.id.btnNegociar);

        btnAutorizar.setOnClickListener(this);
        btnNegar.setOnClickListener(this);
        bntNegociar.setOnClickListener(this);

    }

    private void setInformacoes() {
        boolean flag_ContemInformacao = false;
        jsonObject = new JSONObject(document);
        tvAtributoResp.setText(getDadoSolicitado(jsonObject));
        if (flag_ContemInformacao) {
            tvRespostaResp.setVisibility(View.VISIBLE);
            tvNivelResp.setVisibility(View.VISIBLE);

            edtInformacaoResp.setVisibility(View.GONE);
            tvInserirResp.setVisibility(View.GONE);

            tvRespostaResp.setText(getResposta(jsonObject));
            tvNivelResp.setText(getNivel(jsonObject));
        }else{
            tvRespostaResp.setVisibility(View.GONE);
            tvNivelResp.setVisibility(View.GONE);

            tvInserirResp.setText("A informação solicitada não contêm na sua base de dados \nInsira esta informação:");
            edtInformacaoResp.setVisibility(View.VISIBLE);
            tvInserirResp.setVisibility(View.VISIBLE);
        }
    }

    private String getDadoSolicitado(JSONObject obj) {
        try {
            return "Atributo: " + obj.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getResultado(){
        return  resultado;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAutorizar:
                resultado = ACCEPT;
                dismiss();
                break;
            case R.id.btnNegociar:
                resultado = NEGOTIATE;
                dismiss();
                break;
            case R.id.btnNegar:
                resultado = DENY;
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }


    private String getNivel(JSONObject obj) {
        try {
            return "Nível Confiança: " + obj.getString("NivelConfiança") + "%";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getResposta(JSONObject obj) {
        try {

            return "Resposta Mecanismo: " + codeToString(obj.getInt("Code"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String codeToString(int code) {
        if (code == ACCEPT) {
            return "Autorizar";
        } else {
            if (code == DENY) {
                return "Negar";
            } else {
                return "Negociar";
            }

        }
    }

}