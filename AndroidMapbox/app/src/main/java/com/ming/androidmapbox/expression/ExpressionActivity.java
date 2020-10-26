package com.ming.androidmapbox.expression;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ming.androidmapbox.R;

/**
 * Expression演示界面
 */
public class ExpressionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expression);
        //默认展示第一个
        showFragment(ExTypesFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.expressions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ex_types:
                showFragment(ExTypesFragment.newInstance());
                return true;
            case R.id.ex_feature_data:
                showFragment(ExFeatureDataFragment.newInstance());
                return true;
            case R.id.ex_look_up:
                showFragment(ExLookUpFragment.newInstance());
                return true;
            case R.id.ex_decision:
                showFragment(ExDecisionFragment.newInstance());
                return true;
            case R.id.ex_ramps_scales_curves:
                showFragment(ExRampsScalesCurvesFragment.newInstance());
                return true;
            case R.id.ex_string:
                showFragment(ExStringFragment.newInstance());
                return true;
            case R.id.ex_variable_binding:
                showFragment(ExVariableBindingFragment.newInstance());
                return true;
            case R.id.ex_color:
                showFragment(ExColorFragment.newInstance());
                return true;
            case R.id.ex_math:
                showFragment(ExMathFragment.newInstance());
                return true;
            case R.id.ex_zoom:
                showFragment(ExZoomFragment.newInstance());
                return true;
            case R.id.ex_heatmap:
                showFragment(ExHeatmapFragment.newInstance());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commitAllowingStateLoss();
    }
}