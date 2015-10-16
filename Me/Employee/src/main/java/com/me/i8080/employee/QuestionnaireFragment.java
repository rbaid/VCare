package com.me.i8080.employee;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by rishabh.baid on 5/1/2015.
 */
public class QuestionnaireFragment extends Fragment implements View.OnClickListener {

    View rootView;
    UserHistory mUserHistory;
    private Context mContext;
    private String mode = "default";
    private long _id = -1;

    ImageButton b_datepicker;

    public QuestionnaireFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.date_text);
        textView.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString());

        b_datepicker = (ImageButton) rootView.findViewById(R.id.b_datepicker);
        b_datepicker.setOnClickListener(this);

        Button b_submit = (Button) rootView.findViewById(R.id.b_submit);
        b_submit.setOnClickListener(this);

        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.rating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Log.i("Rating","I am in rating" + rating);
                if(rating<1.0) {
                    ratingBar.setRating(1.0f);
                }
            }
        });

        String buddy[] = {""};

        FetchPeopleListTask fetchPeopleListTask = new FetchPeopleListTask(mContext);
        try {
            buddy  = fetchPeopleListTask.execute().get();
            for(String s:buddy) {
                Log.i("logs", "I am inside onCreateView of Questionnaire " + s );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, buddy);


        MultiAutoCompleteTextView v = (MultiAutoCompleteTextView)
                rootView.findViewById(R.id.response_3);
        v.setAdapter(adapter);
        v.setThreshold(1); //Set number of characters before the dropdown should be shown
        v.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        //Create a new Tokenizer which will get text after '@' and terminate on ' '
//        v.setTokenizer(new MultiAutoCompleteTextView.Tokenizer() {
//
//            @Override
//            public CharSequence terminateToken(CharSequence text) {
//
//                int i = text.length();
//
//                while (i > 0 && text.charAt(i - 1) == ' ') {
//                    i--;
//                }
//
//                if (i > 0 && text.charAt(i - 1) == ' ') {
//                    return text;
//                } else {
//                    if (text instanceof Spanned) {
//                        SpannableString sp = new SpannableString(text + " ");
//                        TextUtils.copySpansFrom((Spanned) text, 0, text.length(), Object.class, sp, 0);
//                        return sp;
//                    } else {
//                        return text + " ";
//                    }
//                }
//            }
//
//            @Override
//            public int findTokenStart(CharSequence text, int cursor) {
//                int i = cursor;
//
//                while (i > 0 && text.charAt(i - 1) != '@') {
//                    i--;
//                }
//
//                //Check if token really started with @, else we don't have a valid token
//                if (i < 1 || text.charAt(i - 1) != '@') {
//                    return cursor;
//                }
//
//                return i;
//            }
//
//            @Override
//            public int findTokenEnd(CharSequence text, int cursor) {
//                int i = cursor;
//                int len = text.length();
//
//                while (i < len) {
//                    if (text.charAt(i) == ' ') {
//                        return i;
//                    } else {
//                        i++;
//                    }
//                }
//
//                return len;
//            }
//        });

        MultiAutoCompleteTextView v4 = (MultiAutoCompleteTextView)
                rootView.findViewById(R.id.response_4);
        v4.setAdapter(adapter);
        v4.setThreshold(1);
        v4.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
//        v4.setTokenizer(new MultiAutoCompleteTextView.Tokenizer() {
//
//            @Override
//            public CharSequence terminateToken(CharSequence text) {
//
//                for(int i=0;i<text.length();i++) {
//                    Log.i("terminatetoken", "i " + i + " " + text.charAt(i) + "-");
//                }
//                int i = text.length();
//
//                while (i > 0 && text.charAt(i - 1) == ' ') {
//                    i--;
//                }
//
//                if (i > 0 && text.charAt(i - 1) == ' ') {
//                    return text;
//                } else {
//                    if (text instanceof Spanned) {
//                        SpannableString sp = new SpannableString(text + " ");
//                        TextUtils.copySpansFrom((Spanned) text, 0, text.length(), Object.class, sp, 0);
//                        return sp;
//                    } else {
//                        return text + " ";
//                    }
//                }
//            }
//
//            @Override
//            public int findTokenStart(CharSequence text, int cursor) {
//                int i = cursor;
//
//                while (i > 0 && text.charAt(i - 1) != '@') {
//                    Log.i("terminatetoken", "j " + (i-1) + " " + text.charAt(i-1) + "-");
//                    i--;
//                }
//
//                //Check if token really started with @, else we don't have a valid token
//                if (i < 1 || text.charAt(i - 1) != '@') {
//                    return cursor;
//                }
//
//                return i;
//            }
//
//            @Override
//            public int findTokenEnd(CharSequence text, int cursor) {
//                int i = cursor;
//                int len = text.length();
//
//                while (i < len) {
//                    if (text.charAt(i) == ' ') {
//                        return i;
//                    } else {
//                        i++;
//                    }
//                }
//
//                return len;
//            }
//        });

        MultiAutoCompleteTextView v5 = (MultiAutoCompleteTextView)
                rootView.findViewById(R.id.response_5);
        v5.setAdapter(adapter);
        v5.setThreshold(1);
        v5.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

//        v5.setTokenizer(new MultiAutoCompleteTextView.Tokenizer() {
//
//            @Override
//            public CharSequence terminateToken(CharSequence text) {
//
//                for(int i=0;i<text.length();i++) {
//                    Log.i("terminatetoken", "i " + i + " " + text.charAt(i) + "-");
//                }
//                int i = text.length();
//
//                while (i > 0 && text.charAt(i - 1) == ' ') {
//                    i--;
//                }
//
//                if (i > 0 && text.charAt(i - 1) == ' ') {
//                    return text;
//                } else {
//                    if (text instanceof Spanned) {
//                        SpannableString sp = new SpannableString(text + " ");
//                        TextUtils.copySpansFrom((Spanned) text, 0, text.length(), Object.class, sp, 0);
//                        return sp;
//                    } else {
//                        return text + " ";
//                    }
//                }
//            }
//
//            @Override
//            public int findTokenStart(CharSequence text, int cursor) {
//                int i = cursor;
//
//                while (i > 0 && text.charAt(i - 1) != '@') {
//                    Log.i("terminatetoken", "j " + (i-1) + " " + text.charAt(i-1) + "-");
//                    i--;
//                }
//
//                //Check if token really started with @, else we don't have a valid token
//                if (i < 1 || text.charAt(i - 1) != '@') {
//                    return cursor;
//                }
//
//                return i;
//            }
//
//            @Override
//            public int findTokenEnd(CharSequence text, int cursor) {
//                int i = cursor;
//                int len = text.length();
//
//                while (i < len) {
//                    if (text.charAt(i) == ' ') {
//                        return i;
//                    } else {
//                        i++;
//                    }
//                }
//
//                return len;
//            }
//        });

        Bundle bundle = getArguments();
        if(bundle != null) {
            Log.i("logs", "Bundle is not null, inside Questionnaire fragment");
            mUserHistory = (UserHistory) bundle.getSerializable("userHistory");
            mode="Update";
            _id = mUserHistory.getId();

            MultiAutoCompleteTextView mv = (MultiAutoCompleteTextView) rootView.findViewById(R.id.response_1);
            mv.setText(mUserHistory.getDescription());

            ratingBar = (RatingBar) rootView.findViewById(R.id.rating);
            ratingBar.setRating(mUserHistory.getRating());

            mv = (MultiAutoCompleteTextView) rootView.findViewById(R.id.response_3);
            mv.setText(mUserHistory.getHappy());

            mv = (MultiAutoCompleteTextView) rootView.findViewById(R.id.response_4);
            mv.setText(mUserHistory.getSad());

            mv = (MultiAutoCompleteTextView) rootView.findViewById(R.id.response_5);
            mv.setText(mUserHistory.getRemember());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            b_datepicker.setText(sdf.format(new Date(mUserHistory.getDate())).toString());
            textView.setText(sdf.format(new Date(mUserHistory.getDate())).toString());
        }
       // getActivity().invalidateOptionsMenu();

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("log", "I am onResume of Questionnaire Fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("log","I am onPause of QuestionnaireFragment");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_datepicker:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                break;

            case R.id.b_submit:

                String mDescription="";
                String mHappy="";
                String mSad="";
                String mRemember="";
                float mRating=1.0f;
                long mDate = 0;

                MultiAutoCompleteTextView tv = (MultiAutoCompleteTextView) rootView.findViewById(R.id.response_1);
                mDescription = tv.getText().toString();

                tv = (MultiAutoCompleteTextView) rootView.findViewById(R.id.response_3);
                mHappy = tv.getText().toString();

                tv = (MultiAutoCompleteTextView) rootView.findViewById(R.id.response_4);
                mSad = tv.getText().toString();

                tv = (MultiAutoCompleteTextView) rootView.findViewById(R.id.response_5);
                mRemember = tv.getText().toString();

                RatingBar rb = (RatingBar) rootView.findViewById(R.id.rating);
                mRating = rb.getRating();

//                Button b_date = (Button) rootView.findViewById(R.id.b_datepicker);
//                mDate = dateToEpoch(b_date.getText().toString());

                TextView textView = (TextView) rootView.findViewById(R.id.date_text);
                mDate = dateToEpoch(textView.getText().toString());

                UserHistory mUserHistory = new UserHistory();

                mUserHistory.setDate(mDate);
                mUserHistory.setDescription(mDescription);
                mUserHistory.setRating(mRating);
                mUserHistory.setHappy(mHappy);
                mUserHistory.setSad(mSad);
                mUserHistory.setRemember(mRemember);

                UpdateDatabaseTask updateDatabaseTask = new UpdateDatabaseTask(mContext,mode,_id);
                try {
                    updateDatabaseTask.execute(mUserHistory).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                UpdatePeopleListTask updatePeopleListTask = new UpdatePeopleListTask(mContext);
                updatePeopleListTask.execute(mHappy,mSad,mRemember);


                if (getFragmentManager().getBackStackEntryCount() > 0 ){
                    getFragmentManager().popBackStack();
//                    getFragmentManager().invalidateOptionsMenu();
                }
            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("log", "I am onDestroyView of QuestionnaireFragment");
    }

    public void setDateView(int year, int month, int day) {

//        Button button = (Button) rootView.findViewById(R.id.b_datepicker);
        TextView tv = (TextView) rootView.findViewById(R.id.date_text);
        if(tv!=null) {
            Log.i("logs","I am inside setDateView of QuestionnaireFragment");
            tv.setText(day + "/" + (month+1) + "/" + year);
        }
       else {
            Log.e("logs","I am b_datepicker and null inside setDateView of QuestionnaireFragment");
        }
    }

    public long dateToEpoch(String d) {
        long epoch = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat(
                "dd/MM/yyyy");
        Date date = null;
        try {
            date = df.parse(d);
            epoch = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
          return epoch;
    }

}