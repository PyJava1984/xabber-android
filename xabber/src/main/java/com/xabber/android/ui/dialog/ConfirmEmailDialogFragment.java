package com.xabber.android.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xabber.android.R;

public class ConfirmEmailDialogFragment extends DialogFragment {

    private String email;
    private Listener listener;

    private EditText edtCode;

    public interface Listener {
        void onResendCodeClick(String email);
        void onConfirmClick(String email, String code);
    }

    public static ConfirmEmailDialogFragment newInstance(Listener listener, String email) {
        ConfirmEmailDialogFragment fragment = new ConfirmEmailDialogFragment();
        fragment.email = email;
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(setupView())
                .setTitle("Confirm email")
                .setNeutralButton("Resend code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onResendCodeClick(email);
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onConfirmClick(email, edtCode.getText().toString());
                    }
                });

        return builder.create();
    }

    public View setupView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirm_email, null);

        TextView tvDescription = view.findViewById(R.id.tvDescription);
        tvDescription.setText(getString(R.string.xmpp_confirm_title, email));
        edtCode = view.findViewById(R.id.edtCode);

        return view;
    }
}
