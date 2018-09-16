package com.coinpany.core.android.widget.calendar;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.coinpany.core.android.widget.calendar.dateutil.PersianCalendar;
import com.coinpany.core.android.widget.calendar.dateutil.PersianCalendarUtils;
import com.iranplanner.tourism.iranplanner.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CPersianDatePicker extends LinearLayout {

    private OnDateChangedListener mListener;
    private Spinner yearNumberPicker;
    private Spinner monthNumberPicker;
    private Spinner dayNumberPicker;

    private int minYear;
    private int maxYear;
    private int yearRange;

    private boolean displayDescription;
//    private TextView descriptionTextView;

    private enum SpinnerType {
        DAY, MONTH, YEAR;
    }

    public CPersianDatePicker(Context context) {
        this(context, null, -1);
    }

    public CPersianDatePicker(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CPersianDatePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.sl_persian_date_picker, this);
        yearNumberPicker = (Spinner) view.findViewById(R.id.year);
        yearNumberPicker.setTag(SpinnerType.YEAR);
        monthNumberPicker = (Spinner) view.findViewById(R.id.month);
        monthNumberPicker.setTag(SpinnerType.MONTH);
        dayNumberPicker = (Spinner) view.findViewById(R.id.day);
        dayNumberPicker.setTag(SpinnerType.DAY);
//        descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);

        PersianCalendar pCalendar = new PersianCalendar();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CPersianDatePicker, 0, 0);

        boolean disableSoftKeyboard = a.getBoolean(R.styleable.CPersianDatePicker_disableSoftKeyboard, false);
        if (disableSoftKeyboard) {
            yearNumberPicker.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
            monthNumberPicker.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
            dayNumberPicker.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        }


        yearRange = a.getInteger(R.styleable.CPersianDatePicker_yearRange, 10);

		/*
         * Initializing yearNumberPicker min and max values If minYear and
		 * maxYear attributes are not set, use (current year - 10) as min and
		 * (current year + 10) as max.
		 */
        minYear = a.getInt(R.styleable.CPersianDatePicker_minYear, pCalendar.getPersianYear() - yearRange);
        maxYear = a.getInt(R.styleable.CPersianDatePicker_maxYear, pCalendar.getPersianYear() + yearRange);

        setMinValue(yearNumberPicker, minYear);
        setMaxValue(yearNumberPicker, maxYear);
//        setMinValue(yearNumberPicker,minYear);
//        setMaxValue(yearNumberPicker,maxYear);

        int selectedYear = a.getInt(R.styleable.CPersianDatePicker_selectedYear, pCalendar.getPersianYear());
        if (selectedYear > maxYear || selectedYear < minYear) {
            throw new IllegalArgumentException(String.format("Selected year (%d) must be between minYear(%d) and maxYear(%d)", selectedYear, minYear, maxYear));
        }
        setValue(yearNumberPicker, selectedYear);
        yearNumberPicker.setOnItemSelectedListener(dateChangeListener);

		/*
         * initializng monthNumberPicker
		 */
        boolean displayMonthNames = a.getBoolean(R.styleable.CPersianDatePicker_displayMonthNames, false);
        setMinValue(monthNumberPicker, 1);
        setMaxValue(monthNumberPicker, 12);
        if (displayMonthNames) {
//            monthNumberPicker.setDisplayedValues(PersianCalendarConstants.persianMonthNames);
        }
        int selectedMonth = a.getInteger(R.styleable.CPersianDatePicker_selectedMonth, pCalendar.getPersianMonth());
        if (selectedMonth < 1 || selectedMonth > 12) {
            throw new IllegalArgumentException(String.format("Selected month (%d) must be between 1 and 12", selectedMonth));
        }
        setValue(monthNumberPicker, selectedMonth);

        monthNumberPicker.setOnItemSelectedListener(dateChangeListener);

		/*
         * initializiing dayNumberPicker
		 */
        setMinValue(dayNumberPicker, 1);
        setMaxValue(dayNumberPicker, 31);
        int selectedDay = a.getInteger(R.styleable.CPersianDatePicker_selectedDay, pCalendar.getPersianDay());
        if (selectedDay > 31 || selectedDay < 1) {
            throw new IllegalArgumentException(String.format("Selected day (%d) must be between 1 and 31", selectedDay));
        }
        if (selectedMonth > 6 && selectedMonth < 12 && selectedDay == 31) {
            selectedDay = 30;
        } else {
            boolean isLeapYear = PersianCalendarUtils.isPersianLeapYear(selectedYear);
            if (isLeapYear && selectedDay == 31) {
                selectedDay = 30;
            } else if (selectedDay > 29) {
                selectedDay = 29;
            }
        }
        setValue(dayNumberPicker, selectedDay);
        dayNumberPicker.setOnItemSelectedListener(dateChangeListener);

		/*
         * displayDescription
		 */
        displayDescription = a.getBoolean(R.styleable.CPersianDatePicker_displayDescription, false);
//        if (displayDescription) {
//            descriptionTextView.setVisibility(View.VISIBLE);
//        }

        a.recycle();
    }

    AdapterView.OnItemSelectedListener dateChangeListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int year = getValue(yearNumberPicker);
            boolean isLeapYear = PersianCalendarUtils.isPersianLeapYear(year);

            int month = getValue(monthNumberPicker);
            int day = getValue(dayNumberPicker);

            if (month < 7) {
                setMinValue(dayNumberPicker, 1);
                setMaxValue(dayNumberPicker, 31);
            } else if (month > 6 && month < 12) {
                if (day == 31) {
                    setValue(dayNumberPicker, 30);
                }
                setMinValue(dayNumberPicker, 1);
                setMaxValue(dayNumberPicker, 30);
            } else if (month == 12) {
                if (isLeapYear) {
                    if (day == 31) {
                        setValue(dayNumberPicker, 30);
                    }
                    setMinValue(dayNumberPicker, 1);
                    setMaxValue(dayNumberPicker, 30);
                } else {
                    if (day > 29) {
                        setValue(dayNumberPicker, 29);
                    }
                    setMinValue(dayNumberPicker, 1);
                    setMaxValue(dayNumberPicker, 29);
                }
            }

            // Set description
//            if (displayDescription) {
//                descriptionTextView.setText(getDisplayPersianDate().getPersianLongDate());
//            }

            if (mListener != null) {
                mListener.onDateChanged(getValue(yearNumberPicker), getValue(monthNumberPicker),
                        getValue(dayNumberPicker));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }


    };

    public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        mListener = onDateChangedListener;
    }

    /**
     * The callback used to indicate the user changed the date.
     * A class that wants to be notified when the date of PersianDatePicker
     * changes should implement this interface and register itself as the
     * listener of date change events using the PersianDataPicker's
     * setOnDateChangedListener method.
     */
    public interface OnDateChangedListener {

        /**
         * Called upon a date change.
         *
         * @param newYear  The year that was set.
         * @param newMonth The month that was set (1-12)
         * @param newDay   The day of the month that was set.
         */
        void onDateChanged(int newYear, int newMonth, int newDay);
    }

    public Date getDisplayDate() {
        PersianCalendar displayPersianDate = new PersianCalendar();
        displayPersianDate.setPersianDate(getValue(yearNumberPicker), getValue(monthNumberPicker), getValue(dayNumberPicker));
        return displayPersianDate.getTime();
    }

    public void setDisplayDate(Date displayDate) {
        if (displayDate != null) {
            setDisplayPersianDate(new PersianCalendar(displayDate.getTime()));
        } else {
            setDisplayPersianDate(new PersianCalendar(System.currentTimeMillis()));
        }
    }

    public PersianCalendar getDisplayPersianDate() {
        PersianCalendar displayPersianDate = new PersianCalendar();
        displayPersianDate.setPersianDate(getValue(yearNumberPicker), getValue(monthNumberPicker), getValue(dayNumberPicker));
        return displayPersianDate;
    }

    public void setDisplayPersianDate(PersianCalendar displayPersianDate) {
        int year = displayPersianDate.getPersianYear();
        int month = displayPersianDate.getPersianMonth();
        int day = displayPersianDate.getPersianDay();
        if (month > 6 && month < 12 && day == 31) {
            day = 30;
        } else {
            if (month == 12) {
                boolean isLeapYear = PersianCalendarUtils.isPersianLeapYear(year);
                if (isLeapYear && day > 29) {
                    day = 29;
                }
            }
        }
        setValue(dayNumberPicker, day);

//        minYear = year - yearRange;
//        maxYear = year + yearRange;
//        setMinValue(yearNumberPicker, minYear);
//        setMaxValue(yearNumberPicker, maxYear);

        setValue(yearNumberPicker, year);
        setValue(monthNumberPicker, month);
        setValue(dayNumberPicker, day);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        // begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        // end

        ss.datetime = this.getDisplayDate().getTime();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        // begin boilerplate code so parent classes can restore state
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        // end

        setDisplayDate(new Date(ss.datetime));
    }

    static class SavedState extends BaseSavedState {
        long datetime;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.datetime = in.readLong();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeLong(this.datetime);
        }

        // required field that makes Parcelables from a Parcel
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }


    private int getValue(Spinner spinner) {
        return (int) spinner.getSelectedItem();
    }

    private void setValue(Spinner spinner, int value) {
        if (spinner.getTag() == null || !(spinner.getTag() instanceof SpinnerType)) {
            throw new RuntimeException("The spinner has not valid tag.");
        }

        SpinnerType type = (SpinnerType) spinner.getTag();
        int count = spinner.getCount();
        int i;

        if (type.equals(SpinnerType.YEAR)) {
            if (value - minYear > count) {
                throw new RuntimeException("value - minYear > count");
            }
            i = (value - minYear) % count;
        } else {
            if (value > count) {
                throw new RuntimeException("value - 1 > count");
            }
            i = (value - 1 % count);
        }
        spinner.setSelection(i);
    }

    private void setMinValue(Spinner spinner, int value) {
        ArrayAdapter oldAdapter = (ArrayAdapter) spinner.getAdapter();
        if (oldAdapter != null
                && oldAdapter.getCount() > 0
                && (int) oldAdapter.getItem(0) == value) {
            return;
        }

        if (spinner.getTag() == null || !(spinner.getTag() instanceof SpinnerType)) {
            throw new RuntimeException("The spinner has not valid tag.");
        }

        SpinnerType type = (SpinnerType) spinner.getTag();

        List<Integer> range = new ArrayList<>();
        if (type.equals(SpinnerType.YEAR)) {
            int maxYear = oldAdapter == null || oldAdapter.getCount() == 0 ? this.maxYear : (int) oldAdapter.getItem(oldAdapter.getCount() - 1);
            for (int i = value; i <= maxYear; i++) {
                range.add(i);
            }
        } else if (type.equals(SpinnerType.MONTH)) {
            int maxYear = oldAdapter == null || oldAdapter.getCount() == 0 ? 12 : (int) oldAdapter.getItem(oldAdapter.getCount() - 1);
            for (int i = value; i <= maxYear; i++) {
                range.add(i);
            }
        } else if (type.equals(SpinnerType.DAY)) {
            int maxYear = oldAdapter == null || oldAdapter.getCount() == 0 ? 31 : (int) oldAdapter.getItem(oldAdapter.getCount() - 1);
            for (int i = value; i <= maxYear; i++) {
                range.add(i);
            }
        }


        ArrayAdapter<Integer> newAdapter = new ArrayAdapter<>(this.getContext(), R.layout.rtl_spinner_layout, range);

        spinner.setAdapter(newAdapter);
    }

    private void setMaxValue(Spinner spinner, int value) {
        ArrayAdapter oldAdapter = (ArrayAdapter) spinner.getAdapter();
        if (oldAdapter != null
                && oldAdapter.getCount() > 0
                && (int) oldAdapter.getItem(oldAdapter.getCount() - 1) == value) {
            return;
        }


        if (spinner.getTag() == null || !(spinner.getTag() instanceof SpinnerType)) {
            throw new RuntimeException("The spinner has not valid tag.");
        }

        SpinnerType type = (SpinnerType) spinner.getTag();

        List<Integer> range = new ArrayList<>();
        if (type.equals(SpinnerType.YEAR)) {
            int minYear = oldAdapter == null || oldAdapter.getCount() == 0 ? this.minYear : (int) oldAdapter.getItem(0);
            for (int i = minYear; i <= value; i++) {
                range.add(i);
            }
        } else if (type.equals(SpinnerType.MONTH)) {
            int minYear = oldAdapter == null || oldAdapter.getCount() == 0 ? 1 : (int) oldAdapter.getItem(0);
            for (int i = minYear; i <= value; i++) {
                range.add(i);
            }
        } else if (type.equals(SpinnerType.DAY)) {
            int minYear = oldAdapter == null || oldAdapter.getCount() == 0 ? 1 : (int) oldAdapter.getItem(0);
            for (int i = minYear; i <= value; i++) {
                range.add(i);
            }
        }


        ArrayAdapter<Integer> newAdapter = new ArrayAdapter<>(this.getContext(), R.layout.rtl_spinner_layout, range);

        int selected = spinner.getSelectedItemPosition();
        spinner.setAdapter(newAdapter);
        spinner.setSelection(selected % spinner.getCount());

    }


}