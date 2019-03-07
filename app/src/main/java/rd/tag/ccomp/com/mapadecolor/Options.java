package rd.tag.ccomp.com.mapadecolor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class Options extends AppCompatActivity {


    Button avancar;
    RadioButton difUm, difDois, difTres;
    RadioButton nivel1, nivel2, nivel3, nivel4, nivel5, nivel6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        avancar = findViewById(R.id.buttonAvancar);

        difUm = findViewById(R.id.radioButton3Cores);
        difDois = findViewById(R.id.radioButton4Cores);
        difTres = findViewById(R.id.radioButton5Cores);

        nivel1 = findViewById(R.id.nivel1);
        nivel2 = findViewById(R.id.nivel2);
        nivel3 = findViewById(R.id.nivel3);
        nivel4 = findViewById(R.id.nivel4);
        nivel5 = findViewById(R.id.nivel5);
        nivel6 = findViewById(R.id.nivel6);

        //select = findViewById(R.id.seekBarSelectFase);
         //final TextView fase = findViewById(R.id.textViewF);


        View.OnClickListener niveis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.nivel1 || v.getId() == R.id.nivel2 || v.getId() == R.id.nivel3){
                    nivel4.setChecked(false);
                    nivel5.setChecked(false);
                    nivel6.setChecked(false);
                }
                else if(v.getId() == R.id.nivel4 || v.getId() == R.id.nivel5 || v.getId() == R.id.nivel6){
                    nivel1.setChecked(false);
                    nivel2.setChecked(false);
                    nivel3.setChecked(false);
                }
            }
        };

        nivel1.setOnClickListener(niveis);
        nivel2.setOnClickListener(niveis);
        nivel3.setOnClickListener(niveis);
        nivel4.setOnClickListener(niveis);
        nivel5.setOnClickListener(niveis);
        nivel6.setOnClickListener(niveis);





        avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                final Intent intent = new Intent(Options.this, TelaPintura.class);

                if(nivel1.isChecked()){
                    b.putInt("nivel",0);
                }
                else if(nivel2.isChecked()){
                    b.putInt("nivel",1);
                }
                else if(nivel3.isChecked()){
                    b.putInt("nivel",2);
                }
                else if(nivel4.isChecked()){
                    b.putInt("nivel",3);
                }
                else if(nivel5.isChecked()){
                    b.putInt("nivel",4);
                }
                else if(nivel6.isChecked()){
                    b.putInt("nivel",5);
                }

                if(difUm.isChecked()){
                    b.putInt("dificuldade",1);
                }
                else if(difDois.isChecked()){
                    b.putInt("dificuldade",2);
                }
                else if(difTres.isChecked()){
                    b.putInt("dificuldade",3);
                }


                intent.putExtras(b);

                startActivity(intent);
            }
        });


    }
}
