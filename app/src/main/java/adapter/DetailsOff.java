package adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 李英杰 on 2017/10/8.
 */

public class DetailsOff extends RecyclerView.ItemDecoration {
    private int lr;
    private int tb;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
/*        outRect.left=divide;
        outRect.right=divide;
        outRect.bottom=divide;
        if (parent.getChildAdapterPosition(view)==0){
            outRect.top=divide;
        }*/

        int height=parent.getChildAdapterPosition(view);

        if (height!=0){
            if (height%2==0){
                outRect.bottom=tb;
                outRect.top=tb;
            }else {
                outRect.top=tb;
                outRect.bottom=tb;
            }
        }else if (height==0){
            outRect.left=0;
            outRect.top=0;
        }

    }

    public DetailsOff(int divide, int tb) {
        this.lr = divide;
        this.tb=tb;
    }
}
