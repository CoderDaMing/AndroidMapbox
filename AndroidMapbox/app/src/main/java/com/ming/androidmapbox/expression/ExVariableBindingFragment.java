package com.ming.androidmapbox.expression;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.ming.androidmapbox.R;

public class ExVariableBindingFragment extends Fragment implements View.OnClickListener {
    private TextView tv_ex_title;
    private TextView tv_ex_msg;

    public ExVariableBindingFragment() {
        // Required empty public constructor
    }

    public static ExVariableBindingFragment newInstance() {
        ExVariableBindingFragment fragment = new ExVariableBindingFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_variable_binding, container, false);
        tv_ex_title = view.findViewById(R.id.tv_ex_title);
        tv_ex_msg = view.findViewById(R.id.tv_ex_msg);

        View viewlLet = view.findViewById(R.id.let);
        viewlLet.setOnClickListener(this);
        viewlLet.callOnClick();

        view.findViewById(R.id.var).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.let) {
            /**
             * let
             * 将表达式绑定到命名变量，然后可以使用[“ var”，“ variable_name”]在结果表达式中引用它们。
             *
             * ["let",
             *     string (alphanumeric literal), any, string (alphanumeric literal), any, ...,
             *     OutputType
             * ]: OutputType
             */
//            Expression.let();
            setText("let将表达式绑定到命名变量，然后可以使用[“ var”，“ variable_name”]在结果表达式中引用它们。",
                    "Expression.let();");
        } else if (viewId == R.id.var) {
            /**
             * var
             * 引用变量绑定使用“ let”。
             *
             * ["var", previously bound variable name]: the type of the bound expression
             */
//            Expression.var();
            setText("* var引用变量绑定使用“ let”。",
                    "Expression.var();");
        }
    }

    private void setText(String title, String msg){
        //设置文字---------------------------------------------------------------------------------------------------------------------------------
        tv_ex_title.setText(title);
        tv_ex_msg.setText(msg);
    }
}
