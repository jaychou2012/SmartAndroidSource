package com.smartandroid.sa.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Author Tandong
 * 
 * @author tandong
 * 
 */

public class AutoReFreshListView extends ListView implements OnScrollListener {

	/** Tandong */

	public static String DATE_FORMAT_STR = "yyyy年MM月dd HH:mm";
	/** tandong */
	public static int RATIO = 3;

	public final static int RELEASE_TO_REFRESH = 0;
	public final static int PULL_TO_REFRESH = 1;
	public final static int REFRESHING = 2;
	public final static int DONE = 3;
	public static int LOADING = 4;

	/** 锟斤拷锟斤拷锟斤�? */
	public final int ENDINT_LOADING = 1;
	/** 锟街讹拷锟斤拷锟剿拷锟�? */
	public final int ENDINT_MANUAL_LOAD_DONE = 2;
	/** 锟皆讹拷锟斤拷锟剿拷锟�? */
	public final int ENDINT_AUTO_LOAD_DONE = 3;

	/**
	 * 0:RELEASE_TO_REFRESH;
	 * <p>
	 * 1:PULL_To_REFRESH;
	 * <p>
	 * 2:REFRESHING;
	 * <p>
	 * 3:DONE;
	 * <p>
	 * 4:LOADING
	 */
	public static int mHeadState;
	/**
	 * 0:锟斤拷锟�锟饺达拷刷锟斤�?;
	 * <p>
	 * 1:锟斤拷锟斤拷锟斤�?
	 */
	public static int mEndState;

	// ================================= 锟斤拷锟斤拷锟斤拷锟斤拷Flag
	// ================================

	/** 锟斤拷锟皆硷拷锟截革拷锟�? */
	public static boolean mCanLoadMore = false;
	/** 锟斤拷锟斤拷锟斤拷锟斤拷刷锟铰ｏ�? */
	public static boolean mCanRefresh = false;
	/**
	 * 锟斤拷锟斤拷锟皆讹拷锟斤拷锟截革拷锟斤拷穑浚锟阶拷猓拷锟斤拷卸锟斤拷欠锟斤拷屑锟斤拷馗锟洁，
	 * 锟斤拷锟矫伙拷校锟斤拷锟斤拷flag也没锟斤拷锟斤拷锟藉�?
	 */
	public static boolean mIsAutoLoadMore = true;
	/** 锟斤拷锟斤拷刷锟铰猴拷锟角凤拷锟斤拷示锟斤拷�?��斤拷Item */
	public static boolean mIsMoveToFirstItemAfterRefresh = false;
	public static Context c;

	public boolean isCanLoadMore() {

		return mCanLoadMore;
	}

	public void setCanLoadMore(boolean pCanLoadMore) {
		mCanLoadMore = pCanLoadMore;
		if (mCanLoadMore && getFooterViewsCount() == 0) {
			addFooterView();
		}
	}

	public boolean isCanRefresh() {

		return mCanRefresh;
	}

	public void setCanRefresh(boolean pCanRefresh) {
		mCanRefresh = pCanRefresh;
	}

	public boolean isAutoLoadMore() {
		return mIsAutoLoadMore;
	}

	public void setAutoLoadMore(boolean pIsAutoLoadMore) {
		mIsAutoLoadMore = pIsAutoLoadMore;
	}

	public boolean isMoveToFirstItemAfterRefresh() {
		return mIsMoveToFirstItemAfterRefresh;
	}

	public void setMoveToFirstItemAfterRefresh(
			boolean pIsMoveToFirstItemAfterRefresh) {
		mIsMoveToFirstItemAfterRefresh = pIsMoveToFirstItemAfterRefresh;
	}

	// ============================================================================

	private LayoutInflater mInflater;

	private LinearLayout mHeadView;
	private TextView mTipsTextView;
	private TextView mLastUpdatedTextView;
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;

	private View mEndRootView;
	private ProgressBar mEndLoadProgressBar;
	private TextView mEndLoadTipsTextView;

	/** headView锟斤拷锟斤拷 */
	private RotateAnimation mArrowAnim;
	/** headView锟斤拷转锟斤拷锟斤拷 */
	private RotateAnimation mArrowReverseAnim;

	/** 锟斤拷锟节憋拷证startY锟斤拷�?锟斤拷一锟斤拷锟斤拷锟斤拷锟絫ouch锟铰硷拷锟斤拷只锟斤拷锟斤拷录一锟斤�? */
	private boolean mIsRecored;

	private int mHeadViewWidth;
	private int mHeadViewHeight;

	private int mStartY;
	private boolean mIsBack;

	private int mFirstItemIndex;
	private int mLastItemIndex;
	private int mCount;
	private boolean mEnoughCount;// 锟姐够锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷幕锟斤拷

	private OnRefreshListener mRefreshListener;
	private OnLoadMoreListener mLoadMoreListener;

	public AutoReFreshListView(Context pContext, AttributeSet pAttrs) {
		super(pContext, pAttrs);
		init(pContext);
		this.c = pContext;
	}

	public AutoReFreshListView(Context pContext) {
		super(pContext);
		init(pContext);
	}

	public AutoReFreshListView(Context pContext, AttributeSet pAttrs,
			int pDefStyle) {
		super(pContext, pAttrs, pDefStyle);
		init(pContext);
	}

	/**
	 * 锟斤拷始锟斤拷锟斤拷锟斤�?
	 * 
	 */
	private void init(Context pContext) {
		this.c = pContext;
		setCacheColorHint(pContext.getResources().getColor(
				android.R.color.transparent));
		mInflater = LayoutInflater.from(pContext);

		addHeadView();

		setOnScrollListener(this);

		initPullImageAnimation(0);
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟剿拷碌锟紿eadView
	 * 
	 */
	private void addHeadView() {
		mHeadView = (LinearLayout) mInflater
				.inflate(
						getResources().getIdentifier("arfl_head", "layout",
								c.getPackageName()), null);

		mArrowImageView = (ImageView) mHeadView
				.findViewById(getResources().getIdentifier(
						"head_arrowImageView", "id", c.getPackageName()));
		mArrowImageView.setMinimumWidth(70);
		mArrowImageView.setMinimumHeight(50);
		mProgressBar = (ProgressBar) mHeadView.findViewById(getResources()
				.getIdentifier("head_progressBar", "id", c.getPackageName()));

		mTipsTextView = (TextView) mHeadView.findViewById(getResources()
				.getIdentifier("head_tipsTextView", "id", c.getPackageName()));

		mLastUpdatedTextView = (TextView) mHeadView.findViewById(getResources()
				.getIdentifier("head_lastUpdatedTextView", "id",
						c.getPackageName()));

		measureView(mHeadView);
		mHeadViewHeight = mHeadView.getMeasuredHeight();
		mHeadViewWidth = mHeadView.getMeasuredWidth();

		mHeadView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
		mHeadView.invalidate();

		Log.v("size", "width:" + mHeadViewWidth + " height:" + mHeadViewHeight);

		addHeaderView(mHeadView, null, false);

		mHeadState = DONE;
	}

	private void addFooterView() {
		mEndRootView = mInflater.inflate(
				getResources().getIdentifier("arfl_foot", "layout",
						c.getPackageName()), null);

		mEndRootView.setVisibility(View.VISIBLE);
		mEndLoadProgressBar = (ProgressBar) mEndRootView
				.findViewById(getResources().getIdentifier(
						"pull_to_refresh_progress", "id", c.getPackageName()));

		mEndLoadTipsTextView = (TextView) mEndRootView
				.findViewById(getResources().getIdentifier("load_more", "id",
						c.getPackageName()));

		mEndRootView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mCanLoadMore) {
					if (mCanRefresh) {
						// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷刷锟斤拷时锟斤拷锟斤拷锟紽ootView没锟斤拷锟斤拷锟节硷拷锟截ｏ拷锟斤拷锟斤拷HeadView没锟斤拷锟斤拷锟斤拷刷锟铰ｏ拷锟脚匡拷锟皆碉拷锟斤拷锟斤拷馗锟洁�?
						if (mEndState != ENDINT_LOADING
								&& mHeadState != REFRESHING) {
							mEndState = ENDINT_LOADING;
							onLoadMore();
						}
					} else if (mEndState != ENDINT_LOADING) {
						// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷刷锟斤拷时锟斤拷FootView锟斤拷锟斤拷锟节硷拷锟斤拷时锟斤拷锟脚匡拷锟皆碉拷锟斤拷锟斤拷馗锟洁�?
						mEndState = ENDINT_LOADING;
						onLoadMore();
					}
				}
			}
		});

		addFooterView(mEndRootView);

		if (mIsAutoLoadMore) {
			mEndState = ENDINT_AUTO_LOAD_DONE;
		} else {
			mEndState = ENDINT_MANUAL_LOAD_DONE;
		}
	}

	/**
	 * 实锟斤拷锟斤拷锟斤拷刷锟铰的硷拷头锟侥讹拷锟斤拷效锟斤拷
	 * 
	 */
	private void initPullImageAnimation(final int pAnimDuration) {

		int _Duration;

		if (pAnimDuration > 0) {
			_Duration = pAnimDuration;
		} else {
			_Duration = 250;
		}
		// Interpolator _Interpolator;
		// switch (pAnimType) {
		// case 0:
		// _Interpolator = new AccelerateDecelerateInterpolator();
		// break;
		// case 1:
		// _Interpolator = new AccelerateInterpolator();
		// break;
		// case 2:
		// _Interpolator = new AnticipateInterpolator();
		// break;
		// case 3:
		// _Interpolator = new AnticipateOvershootInterpolator();
		// break;
		// case 4:
		// _Interpolator = new BounceInterpolator();
		// break;
		// case 5:
		// _Interpolator = new CycleInterpolator(1f);
		// break;
		// case 6:
		// _Interpolator = new DecelerateInterpolator();
		// break;
		// case 7:
		// _Interpolator = new OvershootInterpolator();
		// break;
		// default:
		// _Interpolator = new LinearInterpolator();
		// break;
		// }

		Interpolator _Interpolator = new LinearInterpolator();

		mArrowAnim = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mArrowAnim.setInterpolator(_Interpolator);
		mArrowAnim.setDuration(_Duration);
		mArrowAnim.setFillAfter(true);

		mArrowReverseAnim = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mArrowReverseAnim.setInterpolator(_Interpolator);
		mArrowReverseAnim.setDuration(_Duration);
		mArrowReverseAnim.setFillAfter(true);
	}

	/**
	 * 锟斤拷锟斤拷HeadView锟斤拷锟�注锟解�?
	 * 锟剿凤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷LinearLayout锟斤拷锟斤拷锟斤拷锟斤拷约锟斤拷锟斤拷锟斤拷锟街わ拷锟�
	 * 
	 */
	private void measureView(View pChild) {
		ViewGroup.LayoutParams p = pChild.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;

		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		pChild.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 为锟斤拷锟叫断伙拷锟斤拷锟斤拷ListView锟阶诧拷�?
	 */
	@Override
	public void onScroll(AbsListView pView, int pFirstVisibleItem,
			int pVisibleItemCount, int pTotalItemCount) {
		mFirstItemIndex = pFirstVisibleItem;
		mLastItemIndex = pFirstVisibleItem + pVisibleItemCount - 2;
		mCount = pTotalItemCount - 2;
		if (pTotalItemCount > pVisibleItemCount) {
			mEnoughCount = true;
			// endingView.setVisibility(View.VISIBLE);
		} else {
			mEnoughCount = false;
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷械锟斤拷遥锟斤拷锟揭讹拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟剿★拷
	 */
	@Override
	public void onScrollStateChanged(AbsListView pView, int pScrollState) {
		if (mCanLoadMore) {// 锟斤拷锟节硷拷锟截革拷喙︼拷锟�
			if (mLastItemIndex == mCount && pScrollState == SCROLL_STATE_IDLE) {
				// SCROLL_STATE_IDLE=0锟斤拷锟斤拷锟斤拷停�?
				if (mEndState != ENDINT_LOADING) {
					if (mIsAutoLoadMore) {// 锟皆讹拷锟斤拷锟截革拷啵拷锟斤拷锟斤拷锟紽ootView锟斤拷示
											// 锟斤拷锟斤拷 锟洁�?
						if (mCanRefresh) {
							// 锟斤拷锟斤拷锟斤拷锟斤拷刷锟铰诧拷锟斤拷HeadView没锟斤拷锟斤拷锟斤拷刷锟斤拷时锟斤拷FootView锟斤拷锟斤拷锟皆讹拷锟斤拷锟截革拷锟�?
							if (mHeadState != REFRESHING) {
								// FootView锟斤拷示 : 锟斤�?锟斤�?---> 锟斤拷锟斤拷锟斤�?..
								mEndState = ENDINT_LOADING;
								onLoadMore();
								changeEndViewByState();
							}
						} else {// 没锟斤拷锟斤拷锟斤拷刷锟铰ｏ拷锟斤拷锟斤拷直锟接斤拷锟叫硷拷锟截革拷锟�
								// FootView锟斤拷示 : 锟斤�?锟斤�?---> 锟斤拷锟斤拷锟斤�?..
							mEndState = ENDINT_LOADING;
							onLoadMore();
							changeEndViewByState();
						}
					} else {// 锟斤拷锟斤拷锟皆讹拷锟斤拷锟截革拷啵拷锟斤拷锟斤拷锟紽ootView锟斤拷示
							// 锟斤拷锟斤拷锟斤拷锟斤拷亍锟�?
							// FootView锟斤拷示 : 锟斤拷锟斤拷锟斤拷锟�?--> 锟斤拷锟斤拷锟斤�?..
						mEndState = ENDINT_MANUAL_LOAD_DONE;
						changeEndViewByState();
					}
				}
			}
		} else if (mEndRootView != null
				&& mEndRootView.getVisibility() == VISIBLE) {
			// 突然锟截闭硷拷锟截革拷喙︼拷锟街拷锟斤拷锟斤拷锟揭拷瞥锟紽ootView锟斤�?
			System.out.println("this.removeFooterView(endRootView);...");
			mEndRootView.setVisibility(View.GONE);
			this.removeFooterView(mEndRootView);
		}
	}

	/**
	 * 锟侥憋拷锟斤拷馗锟斤拷状�?
	 * 
	 */
	private void changeEndViewByState() {
		if (mCanLoadMore) {
			// 锟斤拷锟斤拷锟斤拷馗锟斤�?
			switch (mEndState) {
			case ENDINT_LOADING:// 刷锟斤拷锟斤�?

				// 锟斤拷锟斤拷锟斤�?..
				String s = getResources().getString(
						getResources().getIdentifier(
								"p2refresh_doing_end_refresh", "string",
								c.getPackageName()));
				if (mEndLoadTipsTextView.getText().equals(s)) {
					break;
				}
				mEndLoadTipsTextView.setText(s);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.VISIBLE);
				break;
			case ENDINT_MANUAL_LOAD_DONE:// 锟街讹拷刷锟斤拷锟斤拷锟�?

				// 锟斤拷锟斤拷锟斤拷锟�?
				String ss = getResources().getString(
						getResources().getIdentifier(
								"p2refresh_end_click_load_more", "string",
								c.getPackageName()));
				mEndLoadTipsTextView.setText(ss);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.GONE);

				mEndRootView.setVisibility(View.VISIBLE);
				break;
			case ENDINT_AUTO_LOAD_DONE:// 锟皆讹拷刷锟斤拷锟斤拷锟�?

				// 锟斤�?锟斤�?
				String sss = getResources().getString(
						getResources().getIdentifier("p2refresh_end_load_more",
								"string", c.getPackageName()));
				mEndLoadTipsTextView.setText(sss);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.GONE);

				mEndRootView.setVisibility(View.VISIBLE);
				break;
			default:
				// 原锟斤拷锟侥达拷锟斤拷锟斤拷为锟剿ｏ�?
				// 锟斤拷锟斤拷锟斤拷item锟侥高讹拷小锟斤拷ListView锟斤拷锟斤拷母叨锟绞憋拷锟�
				// 要锟斤拷锟截碉拷FootView锟斤拷锟斤拷锟斤拷约锟饺ピ拷锟斤拷叩拇锟斤拷锟轿匡拷锟斤拷

				// if (enoughCount) {
				// endRootView.setVisibility(View.VISIBLE);
				// } else {
				// endRootView.setVisibility(View.GONE);
				// }
				break;
			}
		}
	}

	/**
	 * 原锟斤拷锟竭的ｏ拷锟斤拷没锟侥讹拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷呕锟斤拷锟�?
	 */
	public boolean onTouchEvent(MotionEvent event) {

		if (mCanRefresh) {
			if (mCanLoadMore && mEndState == ENDINT_LOADING) {
				// 锟斤拷锟斤拷锟节硷拷锟截革拷喙︼拷埽锟斤拷锟斤拷业锟角帮拷锟斤拷诩锟斤拷馗锟洁，默锟较诧拷锟斤拷锟斤拷锟斤拷锟斤拷刷锟铰ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷锟较猴拷锟斤拷锟绞癸拷谩锟�
				return super.onTouchEvent(event);
			}

			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				if (mFirstItemIndex == 0 && !mIsRecored) {
					mIsRecored = true;
					mStartY = (int) event.getY();
				}
				break;

			case MotionEvent.ACTION_UP:

				if (mHeadState != REFRESHING && mHeadState != LOADING) {
					if (mHeadState == DONE) {

					}
					if (mHeadState == PULL_TO_REFRESH) {
						mHeadState = DONE;
						changeHeaderViewByState();
					}
					if (mHeadState == RELEASE_TO_REFRESH) {
						mHeadState = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
					}
				}

				mIsRecored = false;
				mIsBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();

				if (!mIsRecored && mFirstItemIndex == 0) {
					mIsRecored = true;
					mStartY = tempY;
				}

				if (mHeadState != REFRESHING && mIsRecored
						&& mHeadState != LOADING) {

					// 锟斤拷证锟斤拷锟斤拷锟斤拷padding锟侥癸拷锟斤拷校锟斤拷锟角帮拷锟轿伙拷锟揭恢憋拷锟斤拷锟絟ead锟斤�?
					// 锟斤拷锟斤拷锟斤拷锟斤拷�?锟斤拷锟斤拷幕锟侥伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟狡碉拷时锟斤拷锟叫憋拷锟酵憋拷锟斤拷泄锟斤拷锟�?
					// 锟斤拷锟斤拷锟斤拷锟斤拷去刷锟斤拷锟斤拷
					if (mHeadState == RELEASE_TO_REFRESH) {

						setSelection(0);

						// 锟斤拷锟斤拷锟斤拷锟剿ｏ拷锟狡碉拷锟斤拷锟斤拷幕锟姐够锟节革拷head锟侥程度ｏ拷锟斤拷锟角伙拷没锟斤拷锟狡碉拷全锟斤拷锟节盖的地诧拷
						if (((tempY - mStartY) / RATIO < mHeadViewHeight)
								&& (tempY - mStartY) > 0) {
							mHeadState = PULL_TO_REFRESH;
							changeHeaderViewByState();
						}
						// �?��斤拷锟斤拷锟狡碉拷锟斤拷锟斤�?
						else if (tempY - mStartY <= 0) {
							mHeadState = DONE;
							changeHeaderViewByState();
						}
						// 锟斤拷锟斤拷锟斤拷锟剿ｏ拷锟斤拷锟竭伙拷没锟斤拷锟斤拷锟狡碉拷锟斤拷幕锟斤拷锟斤拷锟节革拷head锟侥地诧�?
					}
					// 锟斤拷没锟叫碉拷锟斤拷锟斤拷示锟缴匡拷刷锟铰碉拷时锟斤拷,DONE锟斤拷锟斤拷锟斤拷PULL_To_REFRESH状�?
					if (mHeadState == PULL_TO_REFRESH) {

						setSelection(0);

						// 锟斤拷锟斤拷锟斤拷锟斤拷锟皆斤拷锟斤拷RELEASE_TO_REFRESH锟斤拷状�?
						if ((tempY - mStartY) / RATIO >= mHeadViewHeight) {
							mHeadState = RELEASE_TO_REFRESH;
							mIsBack = true;
							changeHeaderViewByState();
						} else if (tempY - mStartY <= 0) {
							mHeadState = DONE;
							changeHeaderViewByState();
						}
					}

					if (mHeadState == DONE) {
						if (tempY - mStartY > 0) {
							mHeadState = PULL_TO_REFRESH;
							changeHeaderViewByState();
						}
					}

					if (mHeadState == PULL_TO_REFRESH) {
						mHeadView.setPadding(0, -1 * mHeadViewHeight
								+ (tempY - mStartY) / RATIO, 0, 0);

					}

					if (mHeadState == RELEASE_TO_REFRESH) {
						mHeadView.setPadding(0, (tempY - mStartY) / RATIO
								- mHeadViewHeight, 0, 0);
					}
				}
				break;
			}
		}

		return super.onTouchEvent(event);
	}

	/**
	 * 锟斤拷HeadView状�?锟侥憋拷时锟津，碉拷锟矫该凤拷锟斤拷锟斤拷锟皆革拷锟铰斤拷锟斤拷
	 */
	private void changeHeaderViewByState() {
		switch (mHeadState) {
		case RELEASE_TO_REFRESH:
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
			mTipsTextView.setVisibility(View.VISIBLE);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);

			mArrowImageView.clearAnimation();
			mArrowImageView.startAnimation(mArrowAnim);
			// 锟缴匡拷刷锟斤拷
			String s = getResources().getString(
					getResources().getIdentifier("p2refresh_release_refresh",
							"string", c.getPackageName()));
			mTipsTextView.setText(s);
			break;
		case PULL_TO_REFRESH:
			mProgressBar.setVisibility(View.GONE);
			mTipsTextView.setVisibility(View.VISIBLE);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.VISIBLE);
			String ss = getResources().getString(
					getResources().getIdentifier("p2refresh_pull_to_refresh",
							"string", c.getPackageName()));
			if (mIsBack) {
				mIsBack = false;
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mArrowReverseAnim);
				// 锟斤拷锟斤拷刷锟斤拷
				mTipsTextView.setText(ss);
			} else {
				// 锟斤拷锟斤拷刷锟斤拷
				mTipsTextView.setText(ss);
			}
			break;

		case REFRESHING:
			mHeadView.setPadding(0, 0, 0, 0);

			mProgressBar.setVisibility(View.VISIBLE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.GONE);
			String sss = getResources().getString(
					getResources().getIdentifier(
							"p2refresh_doing_head_refresh", "string",
							c.getPackageName()));
			mTipsTextView.setText(sss);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);

			break;
		case DONE:
			mHeadView.setPadding(0, -1 * mHeadViewHeight, 0, 0);

			// 锟剿达拷锟斤拷锟皆改斤拷同锟斤拷锟斤拷锟斤拷锟斤�?

			mProgressBar.setVisibility(View.GONE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setImageResource(getResources().getIdentifier(
					"arrow", "drawable", c.getPackageName()));
			// mArrowImageView.setImageResource(R.drawable.arrow);
			String ssss = getResources().getString(
					getResources().getIdentifier("p2refresh_pull_to_refresh",
							"string", c.getPackageName()));
			mTipsTextView.setText(ssss);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);

			break;
		}
	}

	/**
	 * 锟斤拷锟斤拷刷锟铰硷拷锟斤拷涌锟�?
	 * 
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}

	/**
	 * 锟斤拷锟截革拷锟斤拷锟斤拷涌锟�
	 */
	public interface OnLoadMoreListener {
		public void onLoadMore();
	}

	public void setOnRefreshListener(OnRefreshListener pRefreshListener) {
		if (pRefreshListener != null) {
			mRefreshListener = pRefreshListener;
			mCanRefresh = true;
		}
	}

	public void setOnLoadListener(OnLoadMoreListener pLoadMoreListener) {
		if (pLoadMoreListener != null) {
			mLoadMoreListener = pLoadMoreListener;
			mCanLoadMore = true;
			if (mCanLoadMore && getFooterViewsCount() == 0) {
				addFooterView();
			}
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷刷锟斤拷
	 * 
	 */
	private void onRefresh() {
		if (mRefreshListener != null) {
			mRefreshListener.onRefresh();
		}
	}

	/**
	 * 锟斤拷锟斤拷刷锟斤拷锟斤拷锟�?
	 * 
	 */
	public void onRefreshComplete() {
		// 锟斤拷锟斤拷刷锟铰猴拷锟角凤拷锟斤拷示锟斤拷�?��斤拷Item
		if (mIsMoveToFirstItemAfterRefresh)
			setSelection(0);

		mHeadState = DONE;
		String ssss = getResources().getString(
				getResources().getIdentifier("p2refresh_refresh_lasttime",
						"string", c.getPackageName()));
		mLastUpdatedTextView.setText(ssss
				+ new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA)
						.format(new Date()));
		changeHeaderViewByState();
	}

	/**
	 * 锟斤拷锟节硷拷锟截革拷啵現ootView锟斤拷示 锟斤�?锟斤拷锟斤拷锟斤�?..
	 * 
	 */
	private void onLoadMore() {
		if (mLoadMoreListener != null) {
			String ssss = getResources().getString(
					getResources().getIdentifier("p2refresh_doing_end_refresh",
							"string", c.getPackageName()));
			mEndLoadTipsTextView.setText(ssss);

			mEndLoadTipsTextView.setVisibility(View.VISIBLE);
			mEndLoadProgressBar.setVisibility(View.VISIBLE);

			mLoadMoreListener.onLoadMore();
		}
	}

	/**
	 * 锟斤拷锟截革拷锟斤拷锟斤�?
	 * 
	 */
	public void onLoadMoreComplete() {
		if (mIsAutoLoadMore) {
			mEndState = ENDINT_AUTO_LOAD_DONE;
		} else {
			mEndState = ENDINT_MANUAL_LOAD_DONE;
		}
		changeEndViewByState();
	}

	/**
	 * 锟斤拷要锟斤拷锟斤拷�?��斤拷刷锟斤拷时锟斤拷锟斤拷锟斤拷
	 * 
	 */
	public void setAdapter(BaseAdapter adapter) {
		String ssss = getResources().getString(
				getResources().getIdentifier("p2refresh_refresh_lasttime",
						"string", c.getPackageName()));
		mLastUpdatedTextView.setText(ssss
				+ new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA)
						.format(new Date()));
		super.setAdapter(adapter);
	}

}
