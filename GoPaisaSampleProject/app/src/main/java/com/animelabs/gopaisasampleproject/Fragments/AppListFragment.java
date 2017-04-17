package com.animelabs.gopaisasampleproject.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.animelabs.gopaisasampleproject.Activities.MainActivity;
import com.animelabs.gopaisasampleproject.Adapter.AppListAdapter;
import com.animelabs.gopaisasampleproject.AppController.ApplicationController;
import com.animelabs.gopaisasampleproject.Model.Attributes;
import com.animelabs.gopaisasampleproject.Model.Entry;
import com.animelabs.gopaisasampleproject.Model.Image;
import com.animelabs.gopaisasampleproject.Model.Name;
import com.animelabs.gopaisasampleproject.Model.Price;
import com.animelabs.gopaisasampleproject.Model.PriceAttributes;
import com.animelabs.gopaisasampleproject.Model.Summary;
import com.animelabs.gopaisasampleproject.R;
import com.animelabs.gopaisasampleproject.StorageHelper.DatabaseHelper;
import com.animelabs.gopaisasampleproject.Utility.URlConstants;
import com.animelabs.gopaisasampleproject.Utility.UtilityMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class AppListFragment extends Fragment {
    private Context context;
    private AppListAdapter appListAdapter;
    public static final String TAG="IN_DEBUG_MODE";
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private ArrayList<Entry> entryArrayList;
    private ProgressDialog pDialog;
    private DatabaseHelper db;
    private Button button1, button2, button3;
    private TextView textView;
    private ImageView imageView;
    private Animation slide_up;
    RecyclerView recyclerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        entryArrayList = new ArrayList<>();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_list,container, false );
        Log.d(TAG,"Inside Fragment");
        button1 = (Button)view.findViewById(R.id.button);
        button2 = (Button)view.findViewById(R.id.button2);
        imageView = (ImageView)view.findViewById(R.id.imageView3);
        button3 = (Button)view.findViewById(R.id.button3);
        textView = (TextView)view.findViewById(R.id.textView6);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        slide_up = AnimationUtils.loadAnimation(getActivity(),
                R.anim.scale_up_animation);
        imageView.setAnimation(slide_up);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAndUpdateData(URlConstants.API_URL);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAndUpdateData(URlConstants.API_URL_XML);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAndUpdateData(URlConstants.API_URL);
            }
        });

        return view;

    }

    public AppListFragment(){

    }

    public void setContext(Context context){
        this.context = context;
    }
    private void fetchAndUpdateData(String url){
        button2.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        if(UtilityMethods.hasInternetAccess(context)) {
            //To check we can use endswithString method
            if(url.endsWith("/json")){
                loadDataFromServer();
            }else {
                loadDataFromXML();
            }

        }else {
            loadDataFromDatabase();
        }
    }
    public void setEntryArrayList(ArrayList<Entry> entryArrayList){
        this.entryArrayList = entryArrayList;
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void loadDataFromXML(){
        showProgressDialog();
        StringRequest req = new StringRequest(Request.Method.GET, URlConstants.API_URL_XML,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        Document doc = getDomElement(response);

                        NodeList nl = doc.getElementsByTagName("entry");

                        // looping through all item nodes <item>
                        for (int i = 0; i < nl.getLength(); i++) {
                            Element e = (Element) nl.item(i);
                            String name = getValue(e, "title");
                            String summary = getValue(e, "summary");
                            String imageURL = getValue(e, "im:image");
                            String id = getValue(e, "id", "im:id");
                            String price = getValue(e, "im:price", "amount");
                            String currency = getValue(e, "im:price", "currency");

                            Entry entry = new Entry(imageURL,summary, name, price, currency, id);

                            entryArrayList.add(entry);


                            if(db.getEntry(id) == null)
                                db.createEntry(entry);
                            else
                                db.updateToDo(entry);

                            Log.d(TAG, id + "-" + name);
                        }
                        hideProgressDialog();
                        setAdapterForList();
                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error response
                    }
                }
        );
        ApplicationController.getInstance().addToRequestQueue(req,
                tag_json_obj);
    }

    public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    private void loadDataFromServer() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URlConstants.API_URL,"",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        /*ResponseModel entry;
                        Gson gson = new Gson();
                        entry = gson.fromJson(response.toString(), ResponseModel.class);*/
                        //Log.d(TAG, entry.getImage().getLabel() + "");

                        try {
                            JSONObject jsonObject = response.getJSONObject("feed");
                            JSONArray jsonArray = jsonObject.getJSONArray("entry");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject parentObject = (JSONObject) jsonArray.get(i);

                                JSONObject nameObj = parentObject.getJSONObject("im:name");
                                Name name = new Name();
                                name.setLabel(nameObj.getString("label"));

                                JSONArray imageObjArray = parentObject.getJSONArray("im:image");
                                JSONObject imageHDObject = imageObjArray.getJSONObject(imageObjArray.length() - 1);
                                Image image = new Image();
                                image.setLabel(imageHDObject.getString("label"));
                                Attributes attributes = new Attributes();
                                JSONObject imageAttributesObject = imageHDObject.getJSONObject("attributes");
                                attributes.setheight(imageAttributesObject.getString("height"));
                                image.setPn(attributes);

                                JSONObject summaryObject = parentObject.getJSONObject("summary");
                                Summary summary = new Summary();
                                summary.setLabel(summaryObject.getString("label"));

                                JSONObject priceObject = parentObject.getJSONObject("im:price");
                                Price price = new Price();
                                JSONObject priceAttribute = priceObject.getJSONObject("attributes");
                                PriceAttributes priceAttributes = new PriceAttributes();
                                priceAttributes.setAmount(priceAttribute.getString("amount"));
                                priceAttributes.setCurrency(priceAttribute.getString("currency"));
                                price.setPriceAttributes(priceAttributes);
                                price.setLabel("Price");

                                JSONObject idObject = parentObject.getJSONObject("id");
                                JSONObject idAttributes = idObject.getJSONObject("attributes");
                                String id = idAttributes.getString("im:id");

                                Entry entry = new Entry(image, summary, name, price, id);
                                Log.d("Combined Object is", entry.toString() + "");

                                entryArrayList.add(entry);

                                if(db.getEntry(id) == null)
                                    db.createEntry(entry);
                                else
                                    db.updateToDo(entry);

                                //Log.d("Database Saved Alert", db.getEntry(id).getAppId() + "");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        hideProgressDialog();
                        setAdapterForList();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        // Adding request to request queue
        ApplicationController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
    }

    private void loadDataFromDatabase(){

        entryArrayList = (ArrayList<Entry>)db.getAllToDos();
        if(entryArrayList.size() > 0){
            Log.d("Printed","Printed");
            setAdapterForList();
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.parentLayout), "Offline Records Fetched", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }else {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.parentLayout), "Offline, No Records Found", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public String getValue(Element item, String str, String id) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementAttributeValue(n.item(0), id);
    }

    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public final String getElementAttributeValue( Node elem , String attribute) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return elem.getAttributes().getNamedItem(attribute).getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public void setAdapterForList(){
        appListAdapter = new AppListAdapter(entryArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(appListAdapter);
    }

}
