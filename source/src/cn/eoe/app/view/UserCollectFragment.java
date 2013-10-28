package cn.eoe.app.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.eoe.app.R;
import cn.eoe.app.entity.UserResponse;

import java.io.Serializable;

public class UserCollectFragment extends Fragment {

	LinearLayout mLinearLayout;
	private FragmentActivity mActivity;
	private UserResponse mUserResponse;
	private FragmentManager mFragmentManager;
	private Context mContext;
	private WindowManager wm;
	private UserCollectListFragment mUserFragment;

    public UserCollectFragment()
    {
    }

	public UserCollectFragment(UserResponse userResponse,
			FragmentActivity activity) {
		mActivity = activity;
		newInstance(userResponse);
	}

    public void newInstance(UserResponse result) {
        Bundle args = new Bundle();
        args.putSerializable("mUserResponse", (Serializable) result);
        setArguments(args);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity=(FragmentActivity)activity;
        Bundle args=getArguments();
        if(args!=null)
        {
            mUserResponse= (UserResponse) args.getSerializable("mUserResponse");
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		mContext = inflater.getContext();
		wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
		View view = inflater.inflate(R.layout.user_collect_fragment, null);
		mLinearLayout = (LinearLayout) view
				.findViewById(R.id.user_linear_collect_name);
		initLinear();
		mFragmentManager = mActivity.getSupportFragmentManager();
		mUserFragment = new UserCollectListFragment(mActivity, mUserResponse
				.getFavorite().get(0));
		mFragmentManager.beginTransaction()
				.replace(R.id.user_linear_Collect_replace, mUserFragment)
				.commit();
		return view;
	}

	private void initLinear() {
		for (int i = 0, count = mUserResponse.getFavorite().size(); i < count; i++) {
			mLinearLayout.addView(CreateTextView(i, mUserResponse.getFavorite()
					.get(i).getName()));
			if (i < count - 1) {
				mLinearLayout.addView(CreateSide());
			}
		}
	}

	private TextView CreateTextView(final int i, String name) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(mContext);
		int width = wm.getDefaultDisplay().getWidth() / 3;
		LayoutParams layout = new LayoutParams(width, LayoutParams.MATCH_PARENT);
		tv.setLayoutParams(layout);
		if (i == 0) {
			tv.setBackgroundResource(R.drawable.dis_usercollect_left);
			mLinearLayout.setTag(tv);
		}
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(Color.BLACK);
		tv.setText(name);
		tv.setClickable(true);
		tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mUserFragment
						.setListContent(mUserResponse.getFavorite().get(i));
				if (mLinearLayout.getTag() != null) {
					((View) mLinearLayout.getTag())
							.setBackgroundColor(Color.TRANSPARENT);
				}
				mLinearLayout.setTag(v);
				if (i == 0) {
					v.setBackgroundResource(R.drawable.dis_usercollect_left);
				} else if (i == mUserResponse.getFavorite().size() - 1) {
					v.setBackgroundResource(R.drawable.dis_usercollect_right);
				}
			}
		});
		return tv;
	}

	private ImageView CreateSide() {
		ImageView img = new ImageView(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		img.setLayoutParams(params);
		img.setBackgroundResource(R.drawable.dis_behind_verticalside);
		return img;
	}
}
