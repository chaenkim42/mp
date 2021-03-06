package com.example.myapplication.Search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.NewPlace;
import com.example.myapplication.Database.Place;
import com.example.myapplication.R;
import com.example.myapplication.Schedule.ScheduleForm;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;
//import static com.example.myapplication.Search.SearchMap.locationInformation;


public class PlaceInfoBoxFragment extends Fragment implements  View.OnClickListener {
//    ImageButton downArrow = getView().findViewById(R.id.fragment_place_info_box_downArrowBtn);
//    ToggleButton starBtn =  getView().findViewById(R.id.fragment_place_info_box_starToggleBtn);
//    ConstraintLayout container;

    private OnFragmentInteractionListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View v, Place selectedPlace,int selectedDay, String selectedTripName);
    }
    private OnItemClickListener mmListener = null;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mmListener = listener;
    }

    public interface OnInputListener {
        void sendInput(int inputId);
    }

    public OnInputListener mOnInputListener;
    private ArrayList<Place> poiData = new ArrayList<Place>();
    private String selectedPoiName;
    public static Place selectedPlace = new Place();
    public static String selectedTripName = "";
    public static int selectedDay = -1;


    public static String getSelectedTripName(){
        return selectedTripName;
    }
    public static int getSelectedDay(){
        return selectedDay;
    }



    public PlaceInfoBoxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageButton downArrow = getView().findViewById(R.id.fragment_place_info_box_downArrowBtn);
//        ToggleButton starBtn = getView().findViewById(R.id.fragment_place_info_box_starToggleBtn);
        downArrow.setOnClickListener(this);
//        starBtn.setOnCheckedChangeListener(this);

        TextView placeName = getView().findViewById(R.id.fragment_place_info_box_placeName);
        TextView address = getView().findViewById(R.id.fragment_place_info_box_address);
        TextView tel = getView().findViewById(R.id.fragment_place_info_box_tel);
        TextView openTime = getView().findViewById(R.id.fragment_place_info_box_openTime);
        TextView enterFee = getView().findViewById(R.id.fragment_place_info_box_enterFee);

        Button addToListBtn = getView().findViewById(R.id.fragment_place_info_box_btnAddToList);
        addToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPlace newPlace = NewPlace.getInstance();
                selectedTripName = newPlace.getSelectedTripName();
                selectedDay = newPlace.getSelectedDay();
                if( selectedDay != -1 ){
                    if (mmListener != null) {
                        mmListener.onItemClick(v, selectedPlace, selectedDay, selectedTripName);
                    }
                }
            }
        });

        placeName.setText(selectedPlace.getName());
        address.setText(selectedPlace.getAddress());
        tel.setText(selectedPlace.getTel());
        openTime.setText(" ");
        enterFee.setText(" ");

//        Toast.makeText(getActivity().getApplicationContext(), poiName, Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_info_box, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputListener = (OnInputListener) getActivity();
        } catch(ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException" + e.getMessage());

        }
        if(getActivity() != null && getActivity() instanceof SearchMap){
            poiData = ((SearchMap)getActivity()).getData();
            selectedPoiName = ((SearchMap)getActivity()).getPoiName();
            for(int i=0; i<poiData.size(); i++){
                if(poiData.get(i).getName() == selectedPoiName){
                    selectedPlace = poiData.get(i);
                    break;
                }
            }
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if(isChecked){
//            buttonView.setBackgroundResource(R.drawable.star_filled);
//        }else{
//            buttonView.setBackgroundResource(R.drawable.star_empty);
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_place_info_box_downArrowBtn:
                mOnInputListener.sendInput(v.getId());
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
