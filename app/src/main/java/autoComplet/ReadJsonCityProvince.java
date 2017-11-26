package autoComplet;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import entity.CityProvince;
import entity.Province;

/**
 * Created by Hoda on 24/01/2017.
 */
public class ReadJsonCityProvince {
    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("cityprovince.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public List<CityProvince> getListOfCityProvience(Context context) {
        List<CityProvince> list = new ArrayList<CityProvince>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context));
            JSONArray m_jArry = obj.getJSONArray("ListOfCityProvince");
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                CityProvince cityProvince = new CityProvince();
                cityProvince.setId(jo_inside.getString("id"));
                cityProvince.setTitle(jo_inside.getString("title"));
                cityProvince.setType(jo_inside.getString("type"));
                cityProvince.setParent(jo_inside.getString("parent"));
                cityProvince.setPosition_lat(jo_inside.getString("position_lat"));
                cityProvince.setPosition_lon(jo_inside.getString("position_lon"));
                list.add(cityProvince);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
