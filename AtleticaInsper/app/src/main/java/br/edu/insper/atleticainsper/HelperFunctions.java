package br.edu.insper.atleticainsper;

import android.view.View;
import android.view.animation.AlphaAnimation;

public class HelperFunctions {

    public void fadeIn(View v, int duration) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(duration);
        anim.setRepeatCount(0);
        v.startAnimation(anim);
    }

    public void fadeOut(View v, int duration) {
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(duration);
        anim.setRepeatCount(0);
        v.startAnimation(anim);
    }
}
