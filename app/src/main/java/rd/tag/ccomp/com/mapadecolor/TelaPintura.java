package rd.tag.ccomp.com.mapadecolor;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TelaPintura extends AppCompatActivity {

    ImageView imageView;
    TextView textoLog;
    Bitmap myBitmap;
    RadioButton vermelho, verde, azul, amarelo, roxo, rosa, marrom, laranja;
    int corAtual;
    //Point []regioes;
    int dificuldade;
    int nivel;
    //Bitmap [] niveis = new Bitmap[10];
    int cores[];
    Nivel [] todosNiveis;




    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_pintura);

        setNiveis();


        imageView = findViewById(R.id.mapaBrasilBranco);
        textoLog = findViewById(R.id.textoLog);
        vermelho = findViewById(R.id.caixaCorVermelho);
        verde = findViewById(R.id.caixaCorVerde);
        azul = findViewById(R.id.caixaCorAzul);
        amarelo = findViewById(R.id.caixaCorAmarelo);
        roxo = findViewById(R.id.caixaCorRoxo);
        rosa = findViewById(R.id.caixaCorRosa);
        marrom = findViewById(R.id.caixaCorMarrom);
        laranja = findViewById(R.id.caixaCorLaranja);

        final ProgressBar paleta = findViewById(R.id.progressBarPaleta);


        //Predefinicao de pintura, pra comecar com uma cor (vermelho)

        vermelho.setChecked(true);
        corAtual = Color.rgb(244,67,54);
        paleta.getProgressDrawable().setColorFilter(corAtual, android.graphics.PorterDuff.Mode.SRC_IN);


        Bundle bundle = getIntent().getExtras();

        //Recuperacao do que foi passado pra activity

        nivel = bundle.getInt("nivel");
        Toast.makeText(this, ""+nivel, Toast.LENGTH_SHORT).show();
        myBitmap = todosNiveis[nivel].getMapa();
        //regioes = converteParaPoint(bundle.getIntArray("xRegioes"),bundle.getIntArray("yRegioes"));
        dificuldade = bundle.getInt("dificuldade");

        imageView.setImageBitmap(myBitmap);

        cores= new int[todosNiveis[nivel].getRegioes().length];


        //TODO: Passar os valores dos pontos adequadamente


        myBitmap = myBitmap.copy(Bitmap.Config.ARGB_8888, true);

        final Bitmap copyMap = myBitmap;

        final int pixels[] = new int[myBitmap.getWidth() * myBitmap.getHeight()];
//        textoLog.setText(copyMap.getWidth());
        Log.e("Larg", myBitmap.getWidth() + "");


        View.OnClickListener listenerCores = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (azul.isChecked())
                    corAtual = Color.rgb(33,150,243);
                else if (verde.isChecked())
                    corAtual = Color.rgb(76,175,80);
                else if (vermelho.isChecked())
                    corAtual = Color.rgb(244,67,54);
                else if (amarelo.isChecked())
                    corAtual = Color.rgb(255,235,59);
                else if(rosa.isChecked())
                    corAtual = Color.rgb(233,30,99);
                else if (marrom.isChecked())
                    corAtual = Color.rgb(121,85,72);
                else if (roxo.isChecked())
                    corAtual = Color.rgb(103,58,183);
                else if (laranja.isChecked())
                    corAtual = Color.rgb(255,152,0);
                paleta.getProgressDrawable().setColorFilter(
                        corAtual, android.graphics.PorterDuff.Mode.SRC_IN);
            }
        };

        azul.setOnClickListener(listenerCores);
        vermelho.setOnClickListener(listenerCores);
        verde.setOnClickListener(listenerCores);
        amarelo.setOnClickListener(listenerCores);
        roxo.setOnClickListener(listenerCores);
        rosa.setOnClickListener(listenerCores);
        marrom.setOnClickListener(listenerCores);
        laranja.setOnClickListener(listenerCores);



        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final float[] ponto = getPointerCoords(imageView, event);

                //f.floodFill(copyMap, new Point((int) ponto[0], (int) ponto[1]), Color.WHITE, corAtual);

                if(ponto[0] < 0 || ponto[1] < 0)
                    return false;
                final int corTocada = myBitmap.getPixel((int)ponto[0],(int)ponto[1]) == Color.BLACK || myBitmap.getPixel((int)ponto[0],(int)ponto[1])  == Color.rgb(153,217,234)? Color.WHITE : myBitmap.getPixel((int)ponto[0],(int)ponto[1]);

                Runnable pt = new Runnable() {
                    @Override
                    public void run() {
                        FloodFillThread floodFillThread = new FloodFillThread(Thread.currentThread(), copyMap, new Point((int)ponto[0],(int)ponto[1]),corTocada,corAtual);
                        floodFillThread.run();
                        imageView.setImageBitmap(copyMap);
                        todosNiveis[nivel].setMapa(copyMap);

                    }
                };

                pt.run();



                myBitmap.getPixels(pixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());

                Log.e("tamanho",pixels.length+"");
                Log.e("larguraaltura",myBitmap.getWidth()+"x"+myBitmap.getHeight()+"");
                Log.e("pixels", "Tamanho pixels: " + pixels.length);

                Log.e("corAtual", todosNiveis[nivel].getMapa().getPixel((int)ponto[0],(int)ponto[1])+"");

                String temp = "";
                for (int i = 0; i < todosNiveis[nivel].getRegioes().length; i++) {

                    int temp1 = (int)(todosNiveis[nivel].getRegioes()[i].x * todosNiveis[nivel].getPropLargura());
                   int temp2 = (int)(todosNiveis[nivel].getRegioes()[i].y * todosNiveis[nivel].getPropAltura());
                    double temp3 = todosNiveis[nivel].getPropAltura();

                    //Log.e("xy",temp3+"   ");

                    cores[i] = todosNiveis[nivel].getMapa().getPixel(temp1,temp2);
                    temp += "Regiao "+i+": "+nomeDaCor(cores[i])+"\n";
                }



                Log.e("Ponto", ponto[0]+" x "+ponto[1]);

                Log.e("Regioes",temp);
                Toast.makeText(TelaPintura.this, temp, Toast.LENGTH_SHORT).show();
                //Toast.makeText(TelaPintura.this, "Numero de Cores: "+contaCores(), Toast.LENGTH_SHORT).show();

                return false;
            }
        });



    }

    private void setNiveis() {
        todosNiveis = new Nivel[10];
        Bitmap temp = BitmapFactory.decodeResource(getResources(), R.drawable.mapa_test20);
        todosNiveis[0] = new Nivel(temp, temp.getHeight()/2171.0,temp.getWidth()/3071.0, converteParaPoint(new int[]{1226 ,2168 ,1428 ,1929 ,1559},new int[]{606 ,916 ,1189 ,1402 ,1772}));

        temp = BitmapFactory.decodeResource(getResources(), R.drawable.regiao_norte);
        todosNiveis[1] = new Nivel(temp, temp.getHeight()/2171.0,temp.getWidth()/3071.0, converteParaPoint(new int[]{586,1003,1142,1345,2143,2245,2599}, new int[] {1538 ,1007 ,1626 ,399 ,1046 ,475 ,1665}));

        temp = BitmapFactory.decodeResource(getResources(), R.drawable.regiao_nordeste);
        todosNiveis[2] = new Nivel(temp, temp.getHeight()/2171.0, temp.getWidth()/3071.0, converteParaPoint(new int[]{703,1764 ,2000 ,2500 ,2400 ,2000 ,2500 ,2500 ,1078},new int[]{534,644,420,576 ,759,869 ,1052 ,1128 ,1244}));

        temp = BitmapFactory.decodeResource(getResources(), R.drawable.regiao_centro_oeste);
        todosNiveis[3] = new Nivel(temp, temp.getHeight()/2171.0, temp.getWidth()/3071.0, converteParaPoint(new int[]{918,1927 ,936},new int[]{709, 1154, 1580}));

               temp = BitmapFactory.decodeResource(getResources(), R.drawable.regiao_sudeste);
        todosNiveis[4] = new Nivel(temp, temp.getHeight()/2171.0, temp.getWidth()/3071.0, converteParaPoint(new int[]{1576 ,2600 ,2700 ,1886},new int[]{ 862 ,1400 ,1500,1700}));

        temp = BitmapFactory.decodeResource(getResources(), R.drawable.regiao_sul);
        todosNiveis[5] = new Nivel(temp, temp.getHeight()/2171.0, temp.getWidth()/3071.0, converteParaPoint(new int[]{1300 ,1670 ,1168},new int[]{ 495 ,800 ,1420}));



    }


    private String nomeDaCor(int cor) {

            int azul, verde, vermelho, amarelo,rosa, marrom, roxo, laranja;
            azul = Color.rgb(33,150,243);
            verde = Color.rgb(76,175,80);
            vermelho = Color.rgb(244,67,54);
            amarelo = Color.rgb(255,235,59);
            rosa = Color.rgb(233,30,99);
            marrom = Color.rgb(121,85,72);
            roxo = Color.rgb(103,58,183);
            laranja = Color.rgb(255,152,0);

        if(cor == azul)
            return "Azul";
        if(cor == verde)
            return "Verde";
        if(cor == vermelho)
            return "Vermelho";
        if(cor == amarelo)
            return "Amarelo";
        if(cor == rosa)
            return "Rosa";
        if(cor == marrom)
            return "Marrom";
        if(cor == roxo)
            return "Roxo";
        if(cor == laranja)
            return "Laranja";

        return "";

    }

    final float[] getPointerCoords(ImageView view, MotionEvent e) {
        final int index = e.getActionIndex();
        final float[] coords = new float[]{e.getX(index), e.getY(index)};
        Matrix matrix = new Matrix();
        view.getImageMatrix().invert(matrix);
        matrix.postTranslate(view.getScrollX(), view.getScrollY());
        matrix.mapPoints(coords);
        return coords;
    }
    Point[] converteParaPoint(int []x, int []y){
        Point [] pontos = new Point[x.length];
        for (int i = 0; i < x.length; i++) {
            pontos[i] = new Point(x[i],y[i]);
        }
        return pontos;
    }

    int contaCores(){

        //TODO: implementar esse caralho
        return 0;
    }
}