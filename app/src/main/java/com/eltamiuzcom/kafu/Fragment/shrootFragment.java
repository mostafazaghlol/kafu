package com.eltamiuzcom.kafu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eltamiuzcom.kafu.Activity.MainActivity;
import com.eltamiuzcom.kafu.Activity.SplachActivity;
import com.eltamiuzcom.kafu.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link shrootFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class shrootFragment extends Fragment {
    @BindView(R.id.shrootTxt)
    HtmlTextView htmlTextView;
    @OnClick(R.id.shrootback)
    public void back(){
        startActivity(new Intent(getContext(),MainActivity.class));
        getActivity().finish();
    }
    public shrootFragment() {
        // Required empty public constructor
    }

    public static shrootFragment newInstance() {
        return new shrootFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shrootfragment, container, false);
        ButterKnife.bind(this, view);
        htmlTextView.setHtml(SplachActivity.mGetinfo.getData().getInfo().get(0).getPolicy().toString());
        return view;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
