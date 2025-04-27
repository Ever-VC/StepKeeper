package com.evervc.stepkeeper.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evervc.stepkeeper.R;

public class EmpleadoFragment extends Fragment {

    public EmpleadoFragment() {
        // Required empty public constructor
    }

    public static EmpleadoFragment newInstance(String param1, String param2) {
        EmpleadoFragment fragment = new EmpleadoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empleado, container, false);
        return view;
    }
}