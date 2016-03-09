package com.vsa.filmoteca.data.model.event;

import com.squareup.otto.Bus;

/**
 * Created by albertovecinasanchez on 7/12/15.
 */
public class BUS {

    private static Bus mBus;

    public static Bus getInstance() {
        if (mBus == null)
            mBus = new Bus();
        return mBus;
    }

}
