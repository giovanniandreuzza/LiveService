package it.giovanniandreuzza.liveservice;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.app.Service.*;

public class LiveServiceAnnotation {

    @IntDef(value = {
            START_STICKY_COMPATIBILITY,
            START_STICKY,
            START_NOT_STICKY,
            START_REDELIVER_INTENT,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface StartResult {}


}
