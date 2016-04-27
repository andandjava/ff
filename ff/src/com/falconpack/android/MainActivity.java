package com.falconpack.android;

import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	// private
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	android.support.v7.app.ActionBar mActionBar;
	static ScrollView mDrawerList;
	RelativeLayout rl_account, relativeLayout2;
	static LinearLayout headerLayout;
	static LinearLayout headerLayout1;
	static DrawerLayout mDrawerLayout;
	static Fragment fragment;
	static FragmentManager fragmentManager1;
	private ActionBarDrawerToggle mDrawerToggle;
	public static TextView tv_header;
	public static ImageView notificationImg;// , searchImg,filterImg ;
	TextView tv_home, tv_aboutUs, tv_contactUs, tv_editProfile, tv_exit;

	ImageView menu_icon, menu_icon1;
	public static TextView tv_NotificationCount;
	View mCustomView;
	Intent i;
	Context context;
	static String cName = "";
	TranslateAnimation moveLefttoRight;
	TranslateAnimation moveRighttoLeft;
	static Bundle bundle;
	static Activity activity;
	static Resources res;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		activity = MainActivity.this;
		res = MainActivity.this.getResources();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ScrollView) findViewById(R.id.left_drawer);
		// enable ActionBar app icon to behave as action to toggle nav drawer
		mActionBar = getSupportActionBar();
		mActionBar.setIcon(new ColorDrawable(getResources().getColor(
				android.R.color.transparent)));

		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowCustomEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		// LayoutInflater mInflater = LayoutInflater.from(this);

		mCustomView = getLayoutInflater().inflate(R.layout.custom_actionbar,
				null);

		mActionBar.setCustomView(mCustomView);
		Toolbar parent = (Toolbar) mCustomView.getParent();
		parent.setContentInsetsAbsolute(0, 0);

		moveLefttoRight = new TranslateAnimation(0, 5, 0, 0);
		moveLefttoRight.setDuration(300);
		moveLefttoRight.setFillAfter(true);

		moveRighttoLeft = new TranslateAnimation(5, 0, 0, 0);
		moveRighttoLeft.setDuration(300);
		moveRighttoLeft.setFillAfter(true);

		relativeLayout2 = (RelativeLayout) mCustomView
				.findViewById(R.id.relativeLayout2);

		headerLayout = (LinearLayout) mCustomView.findViewById(R.id.ll_left);
		headerLayout1 = (LinearLayout) mCustomView.findViewById(R.id.ll_left1);

		tv_NotificationCount = (TextView) mCustomView
				.findViewById(R.id.notifictn_count_txt);
		notificationImg = (ImageView) mCustomView
				.findViewById(R.id.notificatin_img);

		tv_header = (TextView) mCustomView.findViewById(R.id.tv_header);

		menu_icon = (ImageView) mCustomView.findViewById(R.id.menu);
		menu_icon1 = (ImageView) mCustomView.findViewById(R.id.menu1);

		headerLayout.setVisibility(View.VISIBLE);
		headerLayout1.setVisibility(View.GONE);
		notificationImg.setVisibility(View.GONE);
		tv_NotificationCount.setVisibility(View.GONE);
		// flagImg.setVisibility(View.GONE);

		tv_NotificationCount.setTypeface(Constants
				.getProximanova_regular(activity));
		notificationImg.setOnClickListener(notificationClick);
		tv_NotificationCount.setOnClickListener(notificationClick);

		menu_icon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				CommonUtility.hideKeyBoard(activity, v);
				if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
					mDrawerLayout.closeDrawer(mDrawerList);
				} else {
					mDrawerLayout.openDrawer(mDrawerList);
				}
			}
		});

		menu_icon1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonUtility.hideKeyBoard(activity, v);
				if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
					mDrawerLayout.closeDrawer(mDrawerList);
				} else {
					mDrawerLayout.openDrawer(mDrawerList);
				}
			}
		});

		mActionBar.setCustomView(mCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);
		// R.drawable.menu_white, // nav drawer image to replace 'Up'
		// // image

		mDrawerToggle = new ActionBarDrawerToggle(this, // host Activity
				mDrawerLayout, // DrawerLayout object
				R.drawable.menu_gray, R.string.app_name, // "open drawer"
															// description for
															// accessibility
				R.string.app_name // "close drawer" description for
									// accessibility
		) {

			public void onDrawerClosed(View view) {

				mActionBar.setTitle(mTitle);
				supportInvalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {

				mActionBar.setTitle(mDrawerTitle);

				supportInvalidateOptionsMenu(); // creates call to
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {

			bundle = getIntent().getExtras();

			if (bundle != null) {

				if ("reg".equalsIgnoreCase(bundle.getString("reg").toString())) {

					cName = "";
					tv_header.setText("Registraton");
					headerLayout.setVisibility(View.VISIBLE);
					headerLayout1.setVisibility(View.GONE);
					notificationImg.setVisibility(View.GONE);
					tv_NotificationCount.setVisibility(View.GONE);
					Fragment newContent = new EditAccountDetailsFragment();
					switchFragment(newContent,
							res.getString(R.string.edtaccdetailsfrag));

				} else {

					cName = "home";
					headerLayout.setVisibility(View.GONE);
					headerLayout1.setVisibility(View.VISIBLE);
					Fragment newContent = new DashBoardFragment();
					switchFragment(newContent,
							res.getString(R.string.dashboadfrag));

				}

			}
		}
		slidemenu_init();
	}

	public static String getUserType() {

		if (bundle != null) {
			return bundle.getString("userType").toString();
		}

		return "";
	}

	void slidemenu_init() {

		tv_home = (TextView) findViewById(R.id.tv_home);
		tv_aboutUs = (TextView) findViewById(R.id.tv_aboutUs);
		tv_contactUs = (TextView) findViewById(R.id.tv_contactUs);
		tv_editProfile = (TextView) findViewById(R.id.tv_editProfile);
		tv_exit = (TextView) findViewById(R.id.tv_exit);

		tv_header.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));
		tv_home.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));
		tv_aboutUs.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));
		tv_contactUs.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));
		tv_editProfile.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));
		tv_exit.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));

		tv_home.setOnClickListener(this);
		tv_aboutUs.setOnClickListener(this);
		tv_contactUs.setOnClickListener(this);
		tv_editProfile.setOnClickListener(this);
		tv_exit.setOnClickListener(this);

	}

	@SuppressLint("NewApi")
	private static void switchFragment(Fragment fragment, String tag) {

		mDrawerLayout.closeDrawer(mDrawerList);

		fragmentManager1 = activity.getFragmentManager();
		fragmentManager1.beginTransaction()
				.replace(R.id.frame_container, fragment).addToBackStack(tag)
				.commit();

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		switch (v.getId()) {

		case R.id.tv_home:
			getActionBarIcons("home");
			// Toast.makeText(MainActivity.this, " home ", Toast.LENGTH_SHORT)
			// .show();
			getDashBoardFragment();
			break;
		case R.id.tv_aboutUs:
			getAboutFragment();
			break;
		case R.id.tv_contactUs:
			getContactUsFragment();
			break;
		case R.id.tv_editProfile:
			getEditProfileFragment();
			break;

		case R.id.tv_exit:

			finish();
			moveTaskToBack(true);
			System.exit(0);

			break;

		}

	}

	OnClickListener notificationClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			getNotificationFragment();
		}
	};

	public static void setActionTitle(String title) {

		if (title.equalsIgnoreCase("")) {
			tv_header.setText(title);
		} else {
			tv_header.setText(title);
		}
	}

	public static void getActionBarIcons(String cName) {

		MainActivity.cName = cName;

		if (cName.equalsIgnoreCase("catalogue")
				|| cName.equalsIgnoreCase("products")) {

			headerLayout.setVisibility(View.VISIBLE);
			tv_header.setVisibility(View.VISIBLE);
			headerLayout1.setVisibility(View.GONE);
			notificationImg.setVisibility(View.GONE);
			tv_NotificationCount.setVisibility(View.GONE);

		} else if (cName.equalsIgnoreCase("home")) {

			headerLayout.setVisibility(View.GONE);
			headerLayout1.setVisibility(View.VISIBLE);
			tv_header.setVisibility(View.GONE);
		} else {

			headerLayout.setVisibility(View.VISIBLE);
			tv_header.setVisibility(View.VISIBLE);

			headerLayout1.setVisibility(View.GONE);
			notificationImg.setVisibility(View.GONE);
			tv_NotificationCount.setVisibility(View.GONE);

		}
	}

	public static void getNotificationFragment() {
		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		getActionBarIcons("");
		setActionTitle("Notifications");
		mDrawerLayout.closeDrawer(mDrawerList);
		Fragment newContent1 = new NotificationsFragment();
		switchFragment(newContent1, res.getString(R.string.notificationfrag));
	}

	public static void getDashBoardFragment() {

		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		if (getActiveFragment().equalsIgnoreCase(
				res.getString(R.string.dashboadfrag))) {

			System.out.println(" dashboard fragment");

		} else {
			System.out.println(" dashboard fragment");

		}

		mDrawerLayout.closeDrawer(mDrawerList);
		fragment = new DashBoardFragment();
		getActionBarIcons("home");
		fragmentManager1 = activity.getFragmentManager();
		fragmentManager1.beginTransaction()
				.replace(R.id.frame_container, fragment)
				.addToBackStack(res.getString(R.string.dashboadfrag)).commit();

	}

	public static void getAboutFragment() {
		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		getActionBarIcons("");
		setActionTitle("About Falconpack");
		fragment = new AboutFragment();
		switchFragment(fragment, res.getString(R.string.aboutfrag));
	}

	public static void getContactUsFragment() {
		getActionBarIcons("");
		setActionTitle("Contact us");
		fragment = new ContactFragment();
		switchFragment(fragment, res.getString(R.string.contactfrag));
	}

	public static void getEditProfileFragment() {
		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		// backImg.setVisibility(View.GONE);
		setActionTitle("Edit Profile");
		getActionBarIcons("");
		// mDrawerLayout.closeDrawer(mDrawerList);
		fragment = new EditAccountDetailsFragment();
		switchFragment(fragment, res.getString(R.string.edtaccdetailsfrag));
	}

	public static void getLeaveMangFragment() {
		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		getActionBarIcons("");

		setActionTitle("Leave Management");
		FragmentTransaction ft = activity.getFragmentManager()
				.beginTransaction();
		ft.replace(R.id.frame_container, new LeaveManagmentFragment())
				.addToBackStack(res.getString(R.string.leavemangfrag));
		ft.commit();
	}

	public static void getProductMangFragment() {
		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		getActionBarIcons("");
		tv_header.setText("Catalogue");
		FragmentTransaction ft = activity.getFragmentManager()
				.beginTransaction();
		ft.replace(R.id.frame_container, new CatalogueFragment(),
				res.getString(R.string.productmangfrag));
		ft.commit();
	}

	public static void getDocMangFragment() {
		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		getActionBarIcons("");
		setActionTitle("View Documents");
		FragmentTransaction ft = activity.getFragmentManager()
				.beginTransaction();
		ft.replace(R.id.frame_container, new DocumentManagmentFragment())
				.addToBackStack(res.getString(R.string.docmangfrag));
		ft.commit();
	}

	public static void getHRDMangFragment() {
		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		getActionBarIcons("");
		setActionTitle("HRD");
		FragmentTransaction ft = activity.getFragmentManager()
				.beginTransaction();
		ft.replace(R.id.frame_container, new HRDManagmentFragment())
				.addToBackStack(res.getString(R.string.hrdfrag));
		ft.commit();
	}

	public static void getExpensMangFragment() {

		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		getActionBarIcons("");
		setActionTitle("Expense Management");
		FragmentTransaction ft = activity.getFragmentManager()
				.beginTransaction();
		ft.replace(R.id.frame_container, new ExpenseManagmentFragment())
				.addToBackStack(res.getString(R.string.expsmangfrag));
		ft.commit();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() >= 0) {
			onBackPressed();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		return;
	}

	// @SuppressLint("NewApi")
	// @Override
	// public void onBackPressed() {
	//
	// FragmentManager fragmentManager = this.getFragmentManager();
	//
	// System.out
	// .println(" count " + fragmentManager.getBackStackEntryCount());
	//
	// int backStackCount = fragmentManager.getBackStackEntryCount();
	//
	// // getFragmentManager().popBackStack();
	// String tag = "";
	//
	// if (backStackCount > 0) {
	//
	// getFragmentManager().popBackStackImmediate();
	// // Fragment lFragment = getActiveFragment();
	//
	// tag = getActiveFragment();
	//
	// if (tag == null)
	// return;
	//
	// if (tag.equalsIgnoreCase(res.getString(R.string.dashboadfrag))) {
	// // ((ProfileView) lFragment).updateProfileView();
	// }
	//
	// System.out.println(" tag " + tag);
	//
	// }
	// // if(backStackCount==1){
	// //
	// // tag = fragmentManager.getBackStackEntryAt(
	// // fragmentManager.getBackStackEntryCount()).getName();
	// //
	// // System.out.println("tag name "+tag);
	// //
	// // }
	//
	// // tag = fragmentManager.getBackStackEntryAt(
	// // fragmentManager.getBackStackEntryCount() - 2).getName();
	// //
	//
	// // if (tag.equalsIgnoreCase(getResources().getString(
	// // R.string.destination_sidemenu))) {
	// // }
	//
	// // System.out.println(" backstack Count " + backStackCount);
	//
	// // FragmentManager fm = getFragmentManager();
	// // int lastFragmentCount = fm.getBackStackEntryCount();
	// // if(lastFragmentCount > 0) {
	// // getFragmentManager().popBackStackImmediate();
	// // Fragment lFragment = ((FragmentManager) fm).getFragments();
	// // List<Fragment> fragList=fm.getFragments();
	// // for(Fragment fr: fragList){
	// // String fragClassName = fr.getClass().getName();
	// // if(fragClassName.equals(Abc.class.getName())){
	// // Log.i("Fragment:","Abc");
	// // }else if (fragClassName.equals(Xyz.class.getName())) {
	// // Log.i("Fragment:","Xyz");
	// // }
	// // }
	// // if(lFragment == null) return;
	// // if(lFragment instanceof ProfileView) {
	// // ((ProfileView) lFragment).updateProfileView();
	// // } else if(lFragment instanceof AddNewCardView) {
	// // ((AddNewCardView) lFragment).updateAddNewCardView();
	// // } else if(lFragment instanceof ChangeMobileNumber) {
	// // ((ChangeMobileNumber) lFragment).updateMobileNumberView();
	// // } else if(lFragment instanceof ChangePasswordView) {
	// // ((ChangePasswordView) lFragment).updateChangePasswordView();
	// // } else if(lFragment instanceof MyRidesView) {
	// // ((MyRidesView) lFragment).updateMyRidesView();
	// // } else if(lFragment instanceof PaymentView) {
	// // ((PaymentView) lFragment).updatePaymentView();
	// // } else if(lFragment instanceof SupportView) {
	// // ((SupportView) lFragment).updateSupportView();
	// // }
	// // } else {
	// // mProfileFragment.updateProfileView();
	// }

	@SuppressLint("NewApi")
	public static String getActiveFragment() {
		if (activity.getFragmentManager().getBackStackEntryCount() == 0) {
			return null;
		}
		String tag = activity
				.getFragmentManager()
				.getBackStackEntryAt(activity.getFragmentManager().getBackStackEntryCount() - 1)
				.getName();
		// return (Fragment) getFragmentManager().findFragmentByTag(tag);
		return tag;
	}
}
