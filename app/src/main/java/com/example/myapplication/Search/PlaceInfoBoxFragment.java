package com.example.myapplication.Search;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myapplication.R;

import org.w3c.dom.Text;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.myapplication.Search.SearchMap.locationInformation;


public class PlaceInfoBoxFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
//    ImageButton downArrow = getView().findViewById(R.id.fragment_place_info_box_downArrowBtn);
//    ToggleButton starBtn =  getView().findViewById(R.id.fragment_place_info_box_starToggleBtn);
//    ConstraintLayout container;

    private OnFragmentInteractionListener mListener;

    public interface OnInputListener {
        void sendInput(int inputId);
    }

    public OnInputListener mOnInputListener;
    private String poiName;

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
        ToggleButton starBtn = getView().findViewById(R.id.fragment_place_info_box_starToggleBtn);
        downArrow.setOnClickListener(this);
        starBtn.setOnCheckedChangeListener(this);

        TextView placeName = getView().findViewById(R.id.fragment_place_info_box_placeName);
        TextView address = getView().findViewById(R.id.fragment_place_info_box_address);
        TextView tel = getView().findViewById(R.id.fragment_place_info_box_tel);
        TextView openTime = getView().findViewById(R.id.fragment_place_info_box_openTime);
        TextView enterFee = getView().findViewById(R.id.fragment_place_info_box_enterFee);

        placeName.setText(locationInformation[0]);
        address.setText(locationInformation[1]);
        tel.setText(locationInformation[2]);
        openTime.setText(locationInformation[3]);
        enterFee.setText(locationInformation[4]);

        Toast.makeText(getActivity().getApplicationContext(), poiName, Toast.LENGTH_SHORT).show();

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
            poiName = ((SearchMap)getActivity()).getData();
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            buttonView.setBackgroundResource(R.drawable.star_filled);
        }else{
            buttonView.setBackgroundResource(R.drawable.star_empty);
        }
    }

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
