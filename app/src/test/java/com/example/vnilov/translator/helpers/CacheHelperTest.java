package com.example.vnilov.translator.helpers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

import com.example.vnilov.translator.BuildConfig;

import java.io.File;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest=Config.NONE)

public class CacheHelperTest {

    private CacheHelper cacheHelper;
    private CacheHelper _cacheHelper;
    @Before
    public void init() {
        this.cacheHelper = CacheHelper.getInstance();
        this._cacheHelper = CacheHelper.getInstance();

        // set context
        this.cacheHelper.init(RuntimeEnvironment.application);
    }

    @Test
    public void testInstance() {
        assertEquals(this.cacheHelper, this._cacheHelper);
    }

    @Test
    public void testFunctions() {

        String data = "{\"text\":[\"Hello world\"]}";
        JSONObject jsonObject = null;
        JSONObject result = null;
        JSONObject resultAfterDelete = null;

        try {
            jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String filename = "testData";

        // add to cache
        this.cacheHelper.addData(jsonObject, filename);

        // read from cache
        result = this.cacheHelper.getData(filename);
        assertEquals(jsonObject.toString(), result.toString());

        // check cleaning all cache data
        this.cacheHelper.deleteCache();
        resultAfterDelete = this.cacheHelper.getData(filename);
        assertNull(resultAfterDelete);


        String _filename = "_testData";
        // add data to different cache dirs cache
        this.cacheHelper.addData(jsonObject, filename);
        this.cacheHelper.addData(jsonObject, _filename);

        // get specified dir object
        File dir = new File(RuntimeEnvironment.application.getCacheDir(), filename);
        resultAfterDelete = this.cacheHelper.getData(filename);
        assertNotNull(resultAfterDelete);
        // clean the first one
        this.cacheHelper.deleteDir(dir);
        resultAfterDelete = this.cacheHelper.getData(filename);
        assertNull(resultAfterDelete);
        // check the second directory still full of data
        resultAfterDelete = this.cacheHelper.getData(_filename);
        assertNotNull(resultAfterDelete);

        // delete all cache again
        this.cacheHelper.deleteCache();
        resultAfterDelete = this.cacheHelper.getData(filename);
        assertNull(resultAfterDelete);
    }


}
