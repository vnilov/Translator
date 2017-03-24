/**
 * Created by vnilov on 22.03.17.
 */

package com.example.vnilov.translator;

import com.example.vnilov.translator.helpers.APIHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest=Config.NONE)

public class APIHelperTest {

    private APIHelper api;

    @Before
    public void init() {
        this.api = new APIHelper(RuntimeEnvironment.application);
    }


    @Test
    public void testGetLangList() {
        api.getLangList();
    }
}
