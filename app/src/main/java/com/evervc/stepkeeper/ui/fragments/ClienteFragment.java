package com.evervc.stepkeeper.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evervc.stepkeeper.R;

public class ClienteFragment extends Fragment {

    public ClienteFragment() {
        // Required empty public constructor
    }
    public static ClienteFragment newInstance(String param1, String param2) {
        ClienteFragment fragment = new ClienteFragment();
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
        View view = inflater.inflate(R.layout.fragment_cliente, container, false);
        return view;
    }
}