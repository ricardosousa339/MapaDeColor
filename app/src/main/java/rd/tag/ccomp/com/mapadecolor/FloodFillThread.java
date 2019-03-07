package rd.tag.ccomp.com.mapadecolor;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;

public class FloodFillThread extends Thread
{
    //ProgressDialog mProgressDialog;
    Bitmap mBitmap;
    int               mTargetColor;
    int            mNewColor;
    Point mPoint;
    Runnable       mCallback;

    public FloodFillThread( Runnable callback,
                           Bitmap bitmap, Point pt, int targetColor, int newColor)
    {
        mBitmap          = bitmap;
        mPoint           = pt;
        mTargetColor     = targetColor;
        mNewColor          = newColor;
        mCallback       = callback;
    }

    @Override
    public void run()
    {
        QueueLinearFloodFiller filler =
                new QueueLinearFloodFiller(mBitmap, mTargetColor, mNewColor);

        filler.setTolerance(10);

        filler.floodFill(mPoint.x, mPoint.y);

        handler.sendEmptyMessage(0);
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            mCallback.run();
        }
    };
}
