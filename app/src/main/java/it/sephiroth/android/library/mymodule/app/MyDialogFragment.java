package it.sephiroth.android.library.mymodule.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import it.sephiroth.android.library.tooltip.Tooltip;

/**
 * Created by crugnola on 2/20/16.
 * android-target-tooltip
 */
public class MyDialogFragment extends DialogFragment
    implements View.OnClickListener, Tooltip.Callback, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = MyDialogFragment.class.getSimpleName();
    Button mButton1;
    Button mButton2;
    Button mButton3;
    Button mButton4;
    Button mButton5;
    SwitchCompat mSwitch1;
    SwitchCompat mSwitch2;
    SwitchCompat mSwitch3;
    SwitchCompat mSwitch4;
    private Tooltip.TooltipView tooltip;
    private Tooltip.ClosePolicy mClosePolicy = Tooltip.ClosePolicy.TOUCH_ANYWHERE_CONSUME;

    @Nullable
    @Override
    public View onCreateView(
        final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButton1 = (Button) view.findViewById(R.id.button1);
        mButton2 = (Button) view.findViewById(R.id.button2);
        mButton3 = (Button) view.findViewById(R.id.button3);
        mButton4 = (Button) view.findViewById(R.id.button4);
        mButton5 = (Button) view.findViewById(R.id.button5);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);

        mSwitch1 = (SwitchCompat) view.findViewById(R.id.switch1);
        mSwitch2 = (SwitchCompat) view.findViewById(R.id.switch2);
        mSwitch3 = (SwitchCompat) view.findViewById(R.id.switch3);
        mSwitch4 = (SwitchCompat) view.findViewById(R.id.switch4);

        final int policy = mClosePolicy.getPolicy();
        mSwitch1.setChecked(Tooltip.ClosePolicy.touchInside(policy));
        mSwitch2.setChecked(Tooltip.ClosePolicy.consumeInside(policy));
        mSwitch3.setChecked(Tooltip.ClosePolicy.touchOutside(policy));
        mSwitch4.setChecked(Tooltip.ClosePolicy.consumeOutside(policy));

        mSwitch1.setOnCheckedChangeListener(this);
        mSwitch2.setOnCheckedChangeListener(this);
        mSwitch3.setOnCheckedChangeListener(this);
        mSwitch4.setOnCheckedChangeListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(final View v) {
        final int id = v.getId();

        Log.i(TAG, "onClick: " + id);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        if (id == mButton1.getId()) {

            Tooltip.make(
                getContext(),
                new Tooltip.Builder()
                    .anchor(v, Tooltip.Gravity.RIGHT)
                    .closePolicy(mClosePolicy, 5000)
                    .text(
                        "RIGHT. Touch outside to close this tooltip. RIGHT. Touch outside to close this tooltip. RIGHT. Touch"
                            + " outside to close this tooltip.")
                    .withArrow(true)
                    .fitToScreen(true)
                    .maxWidth(metrics.widthPixels / 2)
                    .withOverlay(true)
                    .withCallback(this)
                    .activateDelay(500)
                    .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                    .build()
            ).show();

        } else if (id == mButton2.getId()) {

            Tooltip.make(
                getContext(),
                new Tooltip.Builder()
                    .anchor(mButton2, Tooltip.Gravity.BOTTOM)
                    .fitToScreen(true)
                    .closePolicy(mClosePolicy, 10000)
                    .text("BOTTOM. Touch outside to dismiss the tooltip")
                    .withArrow(true)
                    .maxWidth(metrics.widthPixels / 2)
                    .withCallback(this)
                    .build()
            ).show();

        } else if (id == mButton3.getId()) {
            Tooltip.make(
                getContext(),
                new Tooltip.Builder()
                    .anchor(mButton3, Tooltip.Gravity.TOP)
                    .closePolicy(mClosePolicy, 10000)
                    .text("TOP. Touch Inside the tooltip to dismiss..")
                    .withArrow(true)
                    .maxWidth((int) (metrics.widthPixels / 2.5))
                    .withCallback(this)
                    .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                    .build()
            ).show();

        } else if (id == mButton4.getId()) {
            Tooltip.make(
                getContext(),
                new Tooltip.Builder()
                    .anchor(v, Tooltip.Gravity.TOP)
                    .closePolicy(mClosePolicy, 5000)
                    .text("TOP. Touch Inside exclusive.")
                    .withArrow(true)
                    .withOverlay(false)
                    .maxWidth(metrics.widthPixels / 3)
                    .withCallback(this)
                    .build()
            ).show();

        } else if (id == mButton5.getId()) {

            if (null == tooltip) {

                tooltip = Tooltip.make(
                    getActivity(),
                    new Tooltip.Builder()
                        .anchor(v, Tooltip.Gravity.BOTTOM)
                        .closePolicy(mClosePolicy, 5000)
                        .text("LEFT. Touch None, so the tooltip won't disappear with a touch, but with a delay")
                        .withArrow(false)
                        .withOverlay(false)
                        .maxWidth(metrics.widthPixels / 3)
                        .showDelay(300)
                        .withCallback(this)
                        .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                        .build()
                );
                tooltip.show();
            } else {
                tooltip.hide();
                tooltip = null;
            }
        }
    }

    @Override
    public void onTooltipClose(final Tooltip.TooltipView view, final boolean fromUser, final boolean containsTouch) {
        Log.d(TAG, "onTooltipClose: " + view + ", fromUser: " + fromUser + ", containsTouch: " + containsTouch);
        if (null != tooltip && tooltip.getTooltipId() == view.getTooltipId()) {
            tooltip = null;
        }
    }

    @Override
    public void onTooltipFailed(Tooltip.TooltipView view) {
        Log.d(TAG, "onTooltipFailed: " + view.getTooltipId());
    }

    @Override
    public void onTooltipShown(Tooltip.TooltipView view) {
        Log.d(TAG, "onTooltipShown: " + view.getTooltipId());
    }

    @Override
    public void onTooltipHidden(Tooltip.TooltipView view) {
        Log.d(TAG, "onTooltipHidden: " + view.getTooltipId());
    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        Log.i(TAG, "onCheckedChanged: " + buttonView.getId() + ", checked: " + isChecked);

        Log.v(TAG, "[pre] closePolicy: " + mClosePolicy.getPolicy());

        mClosePolicy
            .insidePolicy(mSwitch1.isChecked(), mSwitch2.isChecked())
            .outsidePolicy(mSwitch3.isChecked(), mSwitch4.isChecked());

        Log.v(TAG, "[post] closePolicy: " + mClosePolicy.getPolicy());
        Log.v(TAG, "touch inside: " + Tooltip.ClosePolicy.touchInside(mClosePolicy.getPolicy()));
        Log.v(TAG, "touch outside: " + Tooltip.ClosePolicy.touchOutside(mClosePolicy.getPolicy()));
        Log.v(TAG, "consume inside: " + Tooltip.ClosePolicy.consumeInside(mClosePolicy.getPolicy()));
        Log.v(TAG, "consume outside: " + Tooltip.ClosePolicy.consumeOutside(mClosePolicy.getPolicy()));

        Tooltip.removeAll(getActivity());
    }
}
