package rd.tag.ccomp.com.mapadecolor;

import android.graphics.Bitmap;
import android.graphics.Point;

public class Nivel {

    private Bitmap mapa;
    private double propAltura;
    private double propLargura;
    private Point[] regioes;

    public Nivel(Bitmap mapa, double propAltura, double propLargura, Point[] regioes) {
        this.mapa = mapa;
        this.propAltura = propAltura;
        this.propLargura = propLargura;
        this.regioes = regioes;

    }

    public Bitmap getMapa() {
        return mapa;
    }

    public void setMapa(Bitmap mapa) {
        this.mapa = mapa;
    }

    public double getPropAltura() {
        return propAltura;
    }

    public void setPropAltura(int propAltura) {
        this.propAltura = propAltura;
    }

    public double getPropLargura() {
        return propLargura;
    }

    public void setPropLargura(int propLargura) {
        this.propLargura = propLargura;
    }

    public Point[] getRegioes() {
        return regioes;
    }

    public void setRegioes(Point[] regioes) {
        this.regioes = regioes;
    }
}
