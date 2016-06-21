package org.chromium.latency.walt;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

public class NumberPickerPreference extends DialogPreference {
    private int currentValue;

    // TODO: handle default value, min and max from XML
    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDialogLayoutResource(R.layout.numberpicker_dialog);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);

        setDialogIcon(null);
    }

    public int getValue() {
        return currentValue;
    }

    public void setValue(int value) {
        currentValue = value;
        persistInt(currentValue);
    }

    public static class NumberPickerPreferenceDialogFragmentCompat
            extends PreferenceDialogFragmentCompat {
        private static final String SAVE_STATE_VALUE = "NumberPickerPreferenceDialogFragment.value";
        private NumberPicker picker;
        private int currentValue = 1;

        public NumberPickerPreferenceDialogFragmentCompat() {
        }

        public static NumberPickerPreferenceDialogFragmentCompat newInstance(String key) {
            NumberPickerPreferenceDialogFragmentCompat fragment =
                    new NumberPickerPreferenceDialogFragmentCompat();
            Bundle b = new Bundle(1);
            b.putString("key", key);
            fragment.setArguments(b);
            return fragment;
        }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (savedInstanceState == null) {
                currentValue = getNumberPickerPreference().getValue();
            } else {
                currentValue = savedInstanceState.getInt(SAVE_STATE_VALUE);
            }
        }

        public void onSaveInstanceState(@NonNull Bundle outState) {
            outState.putInt(SAVE_STATE_VALUE, currentValue);
        }

        private NumberPickerPreference getNumberPickerPreference() {
            return (NumberPickerPreference) this.getPreference();
        }

        @Override
        protected void onBindDialogView(View view) {
            super.onBindDialogView(view);
            picker = (NumberPicker) view.findViewById(R.id.numpicker_pref);
            picker.setMaxValue(20);
            picker.setMinValue(1);
            picker.setValue(currentValue);
        }

        @Override
        public void onDialogClosed(boolean b) {
            if (b) {
                int value = picker.getValue();
                if(getPreference().callChangeListener(value)) {
                    getNumberPickerPreference().setValue(value);
                }
            }
        }
    }


}
