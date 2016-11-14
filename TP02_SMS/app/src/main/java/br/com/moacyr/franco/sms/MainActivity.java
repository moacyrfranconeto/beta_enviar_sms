package br.com.moacyr.franco.sms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciaComponentes();
    }

    private void iniciaComponentes(){
        Button btEnviar = (Button) findViewById(R.id.btEnviar);
        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtFone = (EditText) findViewById(R.id.txtFone);
                EditText txtMensagem = (EditText) findViewById(R.id.txtMensagem);
                if(txtFone.getText().toString().isEmpty()){
                    txtFone.setError("Informe o número de telefone");
                    return;
                }
                if(txtMensagem.getText().toString().isEmpty()){
                    txtMensagem.requestFocus();
                    txtMensagem.setError("Informe a mensagem");
                    return;
                }
                Intent intent = new Intent(MainActivity.this, ConfirmacaoActivity.class);
                String fone = txtFone.getText().toString();
                String mensagem = txtMensagem.getText().toString();
                intent.putExtra("FONE", fone);
                intent.putExtra("MENSAGEM", mensagem);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }


}
