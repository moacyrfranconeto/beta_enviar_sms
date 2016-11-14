package br.com.moacyr.franco.sms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmacaoActivity extends AppCompatActivity {

    private String fone;
    private String mensagem;

    private static final int REQUEST_SMS = 1;
    private static String[] PERMISSIONS_SMS = {
            Manifest.permission.SEND_SMS
    };
    private boolean permissaoEnviarSMS = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confrimacao);
        verificaPermissao();
        iniciaComponentes();
    }

    private void iniciaComponentes(){
        Intent intent = getIntent();
        this.fone = intent.getStringExtra("FONE");
        this.mensagem = intent.getStringExtra("MENSAGEM");

        TextView txtFone = (TextView) findViewById(R.id.txtFone);
        TextView txtMensagem = (TextView) findViewById(R.id.txtMensagem);

        txtFone.setText(this.fone);
        txtMensagem.setText(this.mensagem);

        Button btEnviar = (Button) findViewById(R.id.btEnviar);
        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permissaoEnviarSMS) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(fone, null, mensagem, null, null);
                    Toast.makeText(getApplicationContext(), "Mensagem enviada com sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    mostraMensagemSemPermissao();
                }
            }
        });

    }

    private void verificaPermissao(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            int writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
            if (writePermission != PackageManager.PERMISSION_GRANTED) {
                permissaoEnviarSMS = false;
                requerPermissao();
                return;
            }
        }
    }

    private void requerPermissao(){
        ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_SMS,
                REQUEST_SMS
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissaoEnviarSMS = true;
            } else {
                mostraMensagemSemPermissao();
            }
        }
    }

    private void mostraMensagemSemPermissao(){
        Toast.makeText(getApplicationContext(), "Sem permissão para enviar SMS", Toast.LENGTH_SHORT).show();
    }

}
