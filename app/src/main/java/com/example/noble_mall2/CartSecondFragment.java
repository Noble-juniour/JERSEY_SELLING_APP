package com.example.noble_mall2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.noble_mall2.Model.Order;

public class CartSecondFragment extends Fragment {

    private static final String TAG = "com.example.noble_mall2.CartSecondFragment";

    private EditText edittxtAddress,editTextZipcode,editTextPhoneNumber,editTextEmail;
    private Button btnBack,btnNext;
    private RelativeLayout parent,addressRellayout,emailRellayout,phonenumberRellayout,zipcodeRellayout;
    private NestedScrollView nestedScrollView;

    private Order incomingOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_cart,container,false);

        initViews(view);

        Bundle bundle = getArguments();
        if (null!=bundle)
        {
            incomingOrder = bundle.getParcelable("order");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.out,R.anim.in)
                        .replace(R.id.fragment_container,new CartFirstFragment()).commit();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData())
                {
                    PassData();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Fill All The Fields")
                            .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.create().show();
                };
            }
        });

        initRelLayouts();

        return view;
    }

    private void PassData()
    {
        Log.d(TAG, "PassData: started");

        Bundle bundle = new Bundle();
        bundle.putParcelable("order", incomingOrder);
        ThirdCartFragment thirdCartFragment = new ThirdCartFragment();
        thirdCartFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.in, R.anim.out)
                .replace(R.id.fragment_container,thirdCartFragment)
                .commit();
    }

    private boolean validateData()
    {
        Log.d(TAG, "validateData: started");
        if (edittxtAddress.getText().toString().equals(""))
        {
            return false;
        }
        if (editTextEmail.getText().toString().equals(""))
        {
            return false;
        }
        if (editTextZipcode.getText().toString().equals(""))
        {
            return false;
        }
        if (editTextPhoneNumber.getText().toString().equals(""))
        {
            return false;
        }
        return true;
    }

    private void initViews(View view)
    {
        Log.d(TAG, "initViews: started");

        edittxtAddress = (EditText) view.findViewById(R.id.edittxtAddress);
        editTextZipcode = (EditText) view.findViewById(R.id.editTextZipcode);
        editTextPhoneNumber = (EditText) view.findViewById(R.id.editTextPhoneNumber);
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);

        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        parent = (RelativeLayout) view.findViewById(R.id.parent);
        addressRellayout = (RelativeLayout) view.findViewById(R.id.addressRelLayout);
        emailRellayout = (RelativeLayout) view.findViewById(R.id.EmailRelLayout);
        phonenumberRellayout = (RelativeLayout) view.findViewById(R.id.PhoneNumberRelLayout);
        zipcodeRellayout = (RelativeLayout) view.findViewById(R.id.ZipCodeRelLayout);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedscrollView);


    }
    private void closekeyboard()
    {
        View view = getActivity().getCurrentFocus();

        if (null != view)
        {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void initRelLayouts()
    {
        Log.d(TAG, "initRelLayouts: started");

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closekeyboard();
            }
        });
        addressRellayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closekeyboard();
            }
        });
        emailRellayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closekeyboard();
            }
        });
        phonenumberRellayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closekeyboard();
            }
        });
        zipcodeRellayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closekeyboard();
            }
        });
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                closekeyboard();
            }
        });
    }
}
