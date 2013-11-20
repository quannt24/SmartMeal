package com.example.smartmeal.tab;

import vn.hust.smie.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.smartmeal.R;
import com.example.smartmeal.save.Save;

public class Setup extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.setup, container, false);

		// saved info
		((EditText) rootView.findViewById(R.id.editText1)).setText(Save.getSave().getString(Save.NAME));
		switch(Save.getSave().getInt(Save.GENDER)){
		case User.SEX_MALE:
			((RadioGroup) rootView.findViewById(R.id.radioGroup2)).check(R.id.radio01);
			break;
		case User.SEX_FEMALE:
			((RadioGroup) rootView.findViewById(R.id.radioGroup2)).check(R.id.radio00);
			break;
		}
		if( Save.getSave().getInt(Save.BIRTH) != 0) ((EditText) rootView.findViewById(R.id.editText4)).setText("" + Save.getSave().getInt(Save.BIRTH));
		if( Save.getSave().getInt(Save.WEIGHT) != 0) ((EditText) rootView.findViewById(R.id.editText2)).setText("" + Save.getSave().getInt(Save.WEIGHT));
		if( Save.getSave().getInt(Save.HEIGHT) != 0) ((EditText) rootView.findViewById(R.id.editText3)).setText("" + Save.getSave().getInt(Save.HEIGHT));
		switch(Save.getSave().getInt(Save.ACTIVITY)){
		case User.ACTIVITY_FEW:
			((RadioGroup) rootView.findViewById(R.id.radioGroup1)).check(R.id.radio11);
			break;
		case User.ACTIVITY_NORMAL:
			((RadioGroup) rootView.findViewById(R.id.radioGroup1)).check(R.id.radio12);
			break;
		case User.ACTIVITY_MANY:
			((RadioGroup) rootView.findViewById(R.id.radioGroup1)).check(R.id.radio13);
			break;
		}

		return rootView;
	}
}
