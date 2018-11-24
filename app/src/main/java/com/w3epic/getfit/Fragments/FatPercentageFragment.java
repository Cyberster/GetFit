package com.w3epic.getfit.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.w3epic.getfit.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FatPercentageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FatPercentageFragment extends Fragment {
    EditText upper;
    EditText middle;
    EditText lower;
    TextView resultFat;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FatPercentageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FatPercentageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FatPercentageFragment newInstance(String param1, String param2) {
        FatPercentageFragment fragment = new FatPercentageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_fat_percentage, container, false);

        ((Button) rootView.findViewById(R.id.btnCalculateFatPercentage))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBodyFatPercentage(rootView);
            }
        });

        return rootView;
    }

//    // TO DO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    public void calculateBodyFatPercentage(View view) {
        upper = view.findViewById(R.id.upper);
        middle = view.findViewById(R.id.middle);
        lower = view.findViewById(R.id.lower);
        resultFat = view.findViewById(R.id.resultFAT);

        String uppertStr = upper.getText().toString();
        String middleStr = middle.getText().toString();
        String lowerStr = lower.getText().toString();

        if (uppertStr != null && !"".equals(uppertStr)
                && middleStr != null && !"".equals(middleStr)
                && lowerStr != null && !"".equals(lowerStr)) {
            float upperValue = Float.parseFloat(uppertStr);
            float middleValue = Float.parseFloat(middleStr);
            float lowerValue = Float.parseFloat(lowerStr);

            float fat = (upperValue + middleValue + lowerValue) / 3;

            //  displayFat(fat);
            resultFat.setText(fat + "%");
        }
    }
}
