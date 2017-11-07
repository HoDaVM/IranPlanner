package com.iranplanner.tourism.iranplanner.ui.activity.confirmHotelReservation;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iranplanner.tourism.iranplanner.R;

import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import tools.CustomDialogNumberPicker;
import tools.Util;

/**
 * Created by h.vahidimehr on 19/02/2017.
 */

public class ConfirmReservationViewPagerAdapter extends PagerAdapter {
    Integer roomPricefinal;
    Integer PriceHalfboardIn;
    Integer PriceHalfboardOut, roomCapacityExtra, priceAddPeople, priceHalfInPrice, roomCapacityExtraPrice,
            priceHalfOutPrice;
    int durationTravel;
    Date startOfTravel;
    int selectAddPeople = 0;


    Context context;
    Activity activity;
    int hotelPosition;
    List<entity.ResultRoom> resultRooms;
    @BindView(R.id.roomType)
    TextView roomType;
    @BindView(R.id.txtOkRoom)
    TextView txtOkRoom;
    @BindView(R.id.txtPrice)
    TextView txtPrice;
    @BindView(R.id.txtAddPeople)
    TextView txtAddPeople;

    @BindView(R.id.checkHalfIn)
    CheckBox checkHalfIn;
    @BindView(R.id.checkHalfOut)
    CheckBox checkHalfOut;
    @BindView(R.id.txthalfInPrice)
    TextView txthalfInPrice;
    @BindView(R.id.txtaddPersonValue)
    TextView txtaddPersonValue;
    @BindView(R.id.txtNationalityValue)
    TextView txtNationalityValue;
    @BindView(R.id.txtDiscount)
    TextView txtDiscount;
    @BindView(R.id.endPrice)
    TextView endPrice;
    @BindView(R.id.roomDelete)
    TextView roomDelete;
    @BindView(R.id.selectHoldetHalf)
    LinearLayout selectHoldetHalf;
    @BindView(R.id.imgHotelList)
    ImageView imageView;


    @BindView(R.id.edtHeadNameReservation)
    EditText edtHeadNameReservation;
    @BindView(R.id.edtHeadLastNameReservation)
    EditText edtHeadLastNameReservation;
    @BindView(R.id.holderNationality)
    RelativeLayout holderNationality;
    @BindView(R.id.addPerHolderHolder)
    RelativeLayout addPerHolderHolder;
    @BindView(R.id.NationalHolder)
    RelativeLayout NationalHolder;
    @BindView(R.id.holder)
    RelativeLayout holder;
    @BindView(R.id.nextHolder)
    RelativeLayout nextHolder;
    @BindView(R.id.okEndHolder)
    RelativeLayout okEndHolder;
    @BindView(R.id.txtReqNumber)
    TextView txtReqNumber;
    String name, family;
    View viewHolder;
    String bundleId;
    String hotelName;

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        ((ViewPager) container).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public ConfirmReservationViewPagerAdapter(Context context, Activity activity, List<entity.ResultRoom> resultRooms, int durationTravel, Date startOfTravel, String bundleId,String hotelName) {
        this.context = context;
        this.activity = activity;
        this.resultRooms = resultRooms;
        this.startOfTravel = startOfTravel;
        this.durationTravel = durationTravel;
        this.bundleId = bundleId;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        viewHolder = inflater.inflate(R.layout.content_room_reservation_fill, null);
        ButterKnife.bind(this, viewHolder);
        roomCapacityExtraPrice = 0;

        setDefaultValue(position);
        ((ViewPager) container).addView(viewHolder, 0);
        addPerHolderHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogNumber(position, roomCapacityExtra, txtaddPersonValue, null, " نفراضافه ");
//                changeBtnOKConfirm(viewHolder, position);
            }
        });

        NationalHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogString(position, 1, txtNationalityValue, new String[]{"خارجی" , "ایرانی"}, " ملیت ");
            }
        });

        txtOkRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultRooms.get(position).getHeadLastName() != null && !resultRooms.get(position).getHeadLastName().equals("") && resultRooms.get(position).getHeadName() != null && !resultRooms.get(position).getHeadName().equals("")) {
//                    resultRooms.get(position).setHeadName(edtHeadNameReservation.getText().toString());
//                    resultRooms.get(position).setHeadLastName(edtHeadLastNameReservation.getText().toString());
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, "txtOkRoom", resultRooms.get(position), view, viewHolder.findViewById(R.id.okEndHolder));
                    }
                } else {
                    Toast.makeText(context, "مشخصات سرپرست اتاق وارد نشده است", Toast.LENGTH_LONG).show();
                }
            }
        });

        nextHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, "nextHolder", null, view, (View) okEndHolder);
                }
            }
        });
        okEndHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, "okEndHolder", null, view, okEndHolder);
                }
            }
        });
        roomDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                resultRooms.remove(position);
//                notifyDataSetChanged();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, "roomDelete", null, view, okEndHolder);
                }
            }
        });
        checkHalfIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    resultRooms.get(position).setHalfIn(true);
                } else {
                    resultRooms.get(position).setHalfIn(false);
                }

                notifyDataSetChanged();
            }
        });
        checkHalfOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    resultRooms.get(position).setHalfOut(true);

                } else {
                    resultRooms.get(position).setHalfOut(false);
                }
                notifyDataSetChanged();
            }
        });

        edtHeadNameReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextName = (EditText) view.findViewById(R.id.edtHeadNameReservation);
                name = editTextName.getText().toString().trim();
                if (!name.equals("")) {
                    resultRooms.get(position).setHeadName(name);
                    notifyDataSetChanged();
                }

            }
        });
        edtHeadLastNameReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextName = (EditText) view.findViewById(R.id.edtHeadLastNameReservation);
                family = editTextName.getText().toString().trim();
                if (!family.equals("")) {
                    resultRooms.get(position).setHeadLastName(family);
                    notifyDataSetChanged();
                }

            }
        });

        edtHeadNameReservation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                                             if (start != 0 && (start == 0 && before == 0 && count == 1)) {
//                                                                 sendCommentBtn.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_send_grey));
//                                                             }
                if (start != 0 || (start == 0 && before == 0 && count == 1)) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("d", s.toString());
//                if (s.toString().equals("")) {
                name = s.toString();
                resultRooms.get(position).setHeadName(name);
//                notifyDataSetChanged();

//                }
            }
        });
        edtHeadLastNameReservation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                                             if (start != 0 && (start == 0 && before == 0 && count == 1)) {
//                                                                 sendCommentBtn.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_send_grey));
//                                                             }
                if (start != 0 || (start == 0 && before == 0 && count == 1)) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("d", s.toString());
//                if (s.toString().equals("")) {
                family = s.toString();
                resultRooms.get(position).setHeadLastName(family);
//                notifyDataSetChanged();

//                }
            }
        });
        return viewHolder;
    }


    @Override
    public int getCount() {
        return resultRooms.size();
    }

    private void setDefaultValue(final int position) {
        if (position == 0) {
            nextHolder.setVisibility(View.GONE);
//            okEndHolder.setVisibility(View.VISIBLE);
        }
        name = (resultRooms.get(position).getHeadName() != null) && resultRooms.get(position).getHeadName().equals("") ? resultRooms.get(position).getHeadName() : "";
        family = (resultRooms.get(position).getHeadLastName() != null && resultRooms.get(position).getHeadLastName().equals("")) ? resultRooms.get(position).getHeadLastName() : "";

        txtReqNumber.setText(bundleId != null ? Util.persianNumbers(bundleId) : "0");

        roomPricefinal = (resultRooms.get(position).getRoom_price_final() != null) ? Integer.valueOf(resultRooms.get(position).getRoom_price_final()) : 1;
        PriceHalfboardIn = (resultRooms.get(position).getRoomPriceHalfboardIn() != null) ? Integer.valueOf(resultRooms.get(position).getRoomPriceHalfboardIn()) : 0;
        PriceHalfboardOut = (resultRooms.get(position).getRoomPriceHalfboardOut() != null) ? Integer.valueOf(resultRooms.get(position).getRoomPriceHalfboardOut()) : 0;
        roomCapacityExtra = (resultRooms.get(position).getRoomCapacityExtra() != null) ? Integer.valueOf(resultRooms.get(position).getRoomCapacityExtra()) : 0;
        roomCapacityExtraPrice = (resultRooms.get(position).getRoomPriceAdPeople() != null) ? Integer.valueOf(resultRooms.get(position).getRoomPriceAdPeople()) : 0;
        priceAddPeople = (resultRooms.get(position).getPriceAddPeople() != null) ? Integer.valueOf(resultRooms.get(position).getPriceAddPeople()) : 0;
        priceHalfInPrice = (resultRooms.get(position).getHalfIn() != null && resultRooms.get(position).getHalfIn()) ? Integer.valueOf(resultRooms.get(position).getRoomPriceHalfboardIn()) : 0;
        priceHalfOutPrice = (resultRooms.get(position).getHalfOut() != null && resultRooms.get(position).getHalfOut()) ? Integer.valueOf(resultRooms.get(position).getRoomPriceHalfboardOut()) : 0;

        roomType.setText(resultRooms.get(position).getRoomTitle());
        txtPrice.setText(Util.persianNumbers(" " +Util.getPriceInToman( roomPricefinal * durationTravel )+ " تومان"));
        selectAddPeople = (resultRooms.get(position).getSelectedAddNumbers() != null) ? Integer.valueOf(resultRooms.get(position).getSelectedAddNumbers()) : 0;


        txtAddPeople.setText(Util.persianNumbers(" " + Util.getPriceInToman(roomCapacityExtraPrice * durationTravel * selectAddPeople )+ " تومان "));
        edtHeadNameReservation.setText(resultRooms.get(position).getHeadName());
        edtHeadLastNameReservation.setText(resultRooms.get(position).getHeadLastName());
        txtDiscount.setText((resultRooms.get(position).getRoomPriceDifference() != null) ? Util.persianNumbers(" " + Util.getPriceInToman(Integer.valueOf(resultRooms.get(position).getRoomPriceDifference())* durationTravel) + " تومان ") : "");
        setVisibleHalfBoard(position);
        endPrice.setText(Util.persianNumbers(Util.getPriceInToman((roomPricefinal * durationTravel) + priceHalfOutPrice + priceHalfInPrice + (roomCapacityExtraPrice * durationTravel * selectAddPeople)) + "تومان"));
        checkHalfOut.setChecked((resultRooms.get(position).getHalfOut() != null) ? resultRooms.get(position).getHalfOut() : false);
        checkHalfIn.setChecked((resultRooms.get(position).getHalfIn() != null) ? resultRooms.get(position).getHalfIn() : false);
        txtaddPersonValue.setText((resultRooms.get(position).getSelectedAddNumbers() != null) ? Util.persianNumbers(resultRooms.get(position).getSelectedAddNumbers()) + " نفراضافه " : "نفراضافه");
        txtNationalityValue.setText((resultRooms.get(position).getSelectedForeign() != null && resultRooms.get(position).getSelectedForeign().equals("0")) ?  "خارجی" : "ایرانی");

        //todo talk to farid about image url
//        Glide.with(context).load(resultRooms.get(position).get)
    }


    private void setVisibleHalfBoard(int position) {
//        viewHolder.txtOkRoom.setBackground(context.getDrawable(R.drawable.button_corner_green_stroke));
        if ((resultRooms.get(position).getRoomPriceHalfboardIn() != null && !resultRooms.get(position).getRoomPriceHalfboardIn().equals("0")) || (resultRooms.get(position).getRoomPriceHalfboardOut() != null && !resultRooms.get(position).getRoomPriceHalfboardOut().equals("0"))) {
            holder.setVisibility(View.VISIBLE);
        }
        if (resultRooms.get(position).getHalfIn() != null && resultRooms.get(position).getHalfOut() != null && resultRooms.get(position).getHalfIn() && resultRooms.get(position).getHalfOut()) {
            txthalfInPrice.setText(Util.persianNumbers(" " + Util.getPriceInToman(priceHalfOutPrice + priceHalfInPrice) + " تومان"));
        } else if (resultRooms.get(position).getHalfIn() != null && resultRooms.get(position).getHalfIn()) {
            txthalfInPrice.setText(Util.persianNumbers(" " + Util.getPriceInToman(priceHalfInPrice )+ " تومان "));
        } else if (resultRooms.get(position).getHalfOut() != null && resultRooms.get(position).getHalfOut()) {
            txthalfInPrice.setText(Util.persianNumbers(" " +Util.getPriceInToman( priceHalfOutPrice )+ " تومان"));
        } else {
            txthalfInPrice.setText(" ");
        }
    }


    private void showDialogNumber(final int position, int number, final TextView txtaddPersonValue, String[] strings, String title) {
        CustomDialogNumberPicker cdd = new CustomDialogNumberPicker(activity, number, 0, title, strings);
        cdd.show();
        cdd.setDialogResult(new CustomDialogNumberPicker.OnDialogNumberPick() {
            @Override
            public void finish(int result) {
                selectAddPeople = result;
                resultRooms.get(position).setSelectedAddNumbers(result + "");
                notifyDataSetChanged();
            }
        });
    }

    private void showDialogString(final int position, int number, final TextView txtaddPersonValue, String[] strings, String title) {
        CustomDialogNumberPicker cdd = new CustomDialogNumberPicker(activity, number, 0, title, strings);
        cdd.show();
        cdd.setDialogResult(new CustomDialogNumberPicker.OnDialogNumberPick() {
            @Override
            public void finish(int result) {
                resultRooms.get(position).setSelectedForeign(result + "");
                notifyDataSetChanged();
            }
        });
    }

    private OnItemClickViewPagerListener mOnItemClickListener;


    public interface OnItemClickViewPagerListener {
        void onItemClick(int position, String viewName, entity.ResultRoom room, View view, View viewEnd);
    }

    public void setOnItemClickListener(OnItemClickViewPagerListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public boolean validate() {
        boolean valid = true;

        if (edtHeadNameReservation.getText().toString().equals("")) {
            valid = false;
            edtHeadNameReservation.setError("نام وارد نشده است");
        } else {
            edtHeadNameReservation.setError(null);
        }
        if (edtHeadLastNameReservation.getText().toString().equals("")) {
            valid = false;
            edtHeadLastNameReservation.setError("نام خانوادگی وارد نشده است");
        } else {
            edtHeadLastNameReservation.setError(null);
        }


        return valid;
    }
}
