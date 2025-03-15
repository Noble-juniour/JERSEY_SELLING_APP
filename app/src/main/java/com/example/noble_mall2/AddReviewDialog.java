package com.example.noble_mall2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.noble_mall2.Model.Jerseyitem;
import com.example.noble_mall2.Model.Review;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReviewDialog extends DialogFragment {
    private static final String TAG = "AddReviewDialog";

    private EditText editTextName, editTextReview;
    private TextView txtWarning,txtitemname;
    private Button btnAdd;

    private int itemid = 0;

    //callback
     interface AddReview {
        void onAddReviewResult(Review review);
    }
    private AddReview addReview;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Add Review")
                .setView(view);

        initViews(view);
        Bundle bundle = getArguments();
        try {
            Jerseyitem item = bundle.getParcelable("item");
            txtitemname.setText(item.getName());
            this.itemid = item.getId();
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddReview();
            }
        });

        return builder.create();
    }

    private void AddReview()
    {
        Log.d(TAG, "AddReview: started");

        if (validateData())
        {
            String name = editTextName.getText().toString();
            String ReviewText = editTextReview.getText().toString();
            String date = getCurrentDate();

            Review review = new Review(itemid,name,date, ReviewText);

            try {
                //from the callback
                addReview = (AddReview) getActivity();

                addReview.onAddReviewResult(review);
            } catch (ClassCastException e)
            {
                e.printStackTrace();
            }
        }
        else {
            txtWarning.setVisibility(View.VISIBLE);
        }

    }

    private  boolean validateData()
    {
        Log.d(TAG, "validateData: started");
        if (editTextName.getText().toString().equals(""))
        {
            return  false;
        }
        if (editTextReview.getText().toString().equals(""))
        {
            return false;
        }
        return true;
    }
    private  String getCurrentDate()
    {
        Log.d(TAG, "getCurrentDate: started");
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(date);
    }

    private void initViews(View view)
    {
        Log.d(TAG, "initViews: started");
        editTextName = (EditText) view.findViewById(R.id.edtTxtname);
        editTextReview = (EditText) view.findViewById(R.id.edtTxtReview);

        txtWarning = (TextView) view.findViewById(R.id.txtWarning);
        txtitemname = (TextView) view.findViewById(R.id.itemName);


        btnAdd = (Button) view.findViewById(R.id.btnAdd);
    }
}
